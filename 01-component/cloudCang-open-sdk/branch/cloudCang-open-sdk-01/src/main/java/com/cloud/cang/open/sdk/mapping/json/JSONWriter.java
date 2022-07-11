package com.cloud.cang.open.sdk.mapping.json;

import com.cloud.cang.open.sdk.common.CloudCangLogger;
import com.cloud.cang.open.sdk.mapping.BaseField;
import com.cloud.cang.open.sdk.mapping.BaseListField;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.CharacterIterator;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.StringCharacterIterator;
import java.util.*;

/**
 * @version 1.0
 * @Author: zhouhong
 * @Date: 2018/9/3 17:15
 */
public class JSONWriter {
    

    private StringBuffer buf;
    private Stack<Object> calls;
    private boolean emitClassName;
    private DateFormat format;
    static char[] hex = "0123456789ABCDEF".toCharArray();

    public JSONWriter(boolean emitClassName) {
        this.buf = new StringBuffer();
        this.calls = new Stack();
        this.emitClassName = true;
        this.emitClassName = emitClassName;
    }

    public JSONWriter() {
        this(false);
    }

    public JSONWriter(DateFormat format) {
        this(false);
        this.format = format;
    }

    public String write(Object object) {
        return this.write(object, false);
    }

    public String write(Object object, boolean isApiModel) {
        this.buf.setLength(0);
        this.value(object, isApiModel);
        return this.buf.toString();
    }

    public String write(long n) {
        return String.valueOf(n);
    }

    public String write(double d) {
        return String.valueOf(d);
    }

    public String write(char c) {
        return "\"" + c + "\"";
    }

    public String write(boolean b) {
        return String.valueOf(b);
    }

    private void value(Object object) {
        this.value(object, false);
    }

    private void value(Object object, boolean isApiModel) {
        if(object != null && !this.cyclic(object)) {
            this.calls.push(object);
            if(object instanceof Class) {
                this.string(object);
            } else if(object instanceof Boolean) {
                this.bool(((Boolean)object).booleanValue());
            } else if(object instanceof Number) {
                this.add(object);
            } else if(object instanceof String) {
                this.string(object);
            } else if(object instanceof Character) {
                this.string(object);
            } else if(object instanceof Map) {
                this.map((Map)object);
            } else if(object.getClass().isArray()) {
                this.array(object, isApiModel);
            } else if(object instanceof Iterator) {
                this.array((Iterator)object, isApiModel);
            } else if(object instanceof Collection) {
                this.array(((Collection)object).iterator(), isApiModel);
            } else if(object instanceof Date) {
                this.date((Date)object);
            } else if(isApiModel) {
                this.model(object);
            } else {
                this.bean(object);
            }

            this.calls.pop();
        } else {
            this.add((Object)null);
        }

    }

    private boolean cyclic(Object object) {
        Iterator it = this.calls.iterator();

        Object called;
        do {
            if(!it.hasNext()) {
                return false;
            }

            called = it.next();
        } while(object != called);

        return true;
    }

    private void bean(Object object) {
        this.add("{");
        boolean addedSomething = false;

        try {
            BeanInfo info = Introspector.getBeanInfo(object.getClass());
            PropertyDescriptor[] props = info.getPropertyDescriptors();

            for(int i = 0; i < props.length; ++i) {
                PropertyDescriptor prop = props[i];
                String name = prop.getName();
                Method accessor = prop.getReadMethod();
                if((this.emitClassName || !"class".equals(name)) && accessor != null) {
                    if(!accessor.isAccessible()) {
                        accessor.setAccessible(true);
                    }

                    Object value = accessor.invoke(object, (Object[])null);
                    if(value != null) {
                        if(addedSomething) {
                            this.add(',');
                        }

                        this.add(name, value);
                        addedSomething = true;
                    }
                }
            }

            Field[] ff = object.getClass().getFields();

            for(int i = 0; i < ff.length; ++i) {
                Field field = ff[i];
                Object value = field.get(object);
                if(value != null) {
                    if(addedSomething) {
                        this.add(',');
                    }

                    this.add(field.getName(), value);
                    addedSomething = true;
                }
            }
        } catch (IllegalAccessException var10) {
            var10.printStackTrace();
        } catch (InvocationTargetException var11) {
            var11.getCause().printStackTrace();
            var11.printStackTrace();
        } catch (IntrospectionException var12) {
            var12.printStackTrace();
        }

        this.add("}");
    }

    private void model(Object object) {
        this.add("{");
        boolean addedSomething = false;
        Field[] ff = object.getClass().getDeclaredFields();

        try {
            for(int i = 0; i < ff.length; ++i) {
                Field field = ff[i];
                BaseField jsonField = (BaseField)field.getAnnotation(BaseField.class);
                BaseListField listField = (BaseListField)field.getAnnotation(BaseListField.class);
                PropertyDescriptor pd;
                Method accessor;
                Object value;
                if(listField != null) {
                    pd = new PropertyDescriptor(field.getName(), object.getClass());
                    accessor = pd.getReadMethod();
                    if(!accessor.isAccessible()) {
                        accessor.setAccessible(true);
                    }

                    value = accessor.invoke(object, (Object[])null);
                    if(value != null) {
                        if(addedSomething) {
                            this.add(',');
                        }

                        this.add(listField.value(), value, true);
                        addedSomething = true;
                    }
                } else if(jsonField != null) {
                    pd = new PropertyDescriptor(field.getName(), object.getClass());
                    accessor = pd.getReadMethod();
                    if(!accessor.isAccessible()) {
                        accessor.setAccessible(true);
                    }

                    value = accessor.invoke(object, (Object[])null);
                    if(value != null) {
                        if(addedSomething) {
                            this.add(',');
                        }

                        this.add(jsonField.value(), value, true);
                        addedSomething = true;
                    }
                }
            }
        } catch (IntrospectionException var11) {
            CloudCangLogger.logBizError(var11);
        } catch (IllegalAccessException var12) {
            CloudCangLogger.logBizError(var12);
        } catch (IllegalArgumentException var13) {
            CloudCangLogger.logBizError(var13);
        } catch (InvocationTargetException var14) {
            CloudCangLogger.logBizError(var14);
        }

        this.add("}");
    }

    private void add(String name, Object value) {
        this.add(name, value, false);
    }

    private void add(String name, Object value, boolean isApiModel) {
        this.add('"');
        this.add(name);
        this.add("\":");
        this.value(value, isApiModel);
    }

    private void map(Map<?, ?> map) {
        this.add("{");
        Iterator it = map.entrySet().iterator();

        while(it.hasNext()) {
            Map.Entry<?, ?> e = (Map.Entry)it.next();
            this.value(e.getKey());
            this.add(":");
            this.value(e.getValue());
            if(it.hasNext()) {
                this.add(',');
            }
        }

        this.add("}");
    }

    private void array(Iterator<?> it) {
        this.array(it, false);
    }

    private void array(Iterator<?> it, boolean isApiModel) {
        this.add("[");

        while(it.hasNext()) {
            this.value(it.next(), isApiModel);
            if(it.hasNext()) {
                this.add(",");
            }
        }

        this.add("]");
    }

    private void array(Object object) {
        this.array(object, false);
    }

    private void array(Object object, boolean isApiModel) {
        this.add("[");
        int length = Array.getLength(object);

        for(int i = 0; i < length; ++i) {
            this.value(Array.get(object, i), isApiModel);
            if(i < length - 1) {
                this.add(',');
            }
        }

        this.add("]");
    }

    private void bool(boolean b) {
        this.add(b?"true":"false");
    }

    private void date(Date date) {
        if(this.format == null) {
            this.format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            this.format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        }

        this.add("\"");
        this.add(this.format.format(date));
        this.add("\"");
    }

    private void string(Object obj) {
        this.add('"');
        CharacterIterator it = new StringCharacterIterator(obj.toString());

        for(char c = it.first(); c != '\uffff'; c = it.next()) {
            if(c == 34) {
                this.add("\\\"");
            } else if(c == 92) {
                this.add("\\\\");
            } else if(c == 47) {
                this.add("\\/");
            } else if(c == 8) {
                this.add("\\b");
            } else if(c == 12) {
                this.add("\\f");
            } else if(c == 10) {
                this.add("\\n");
            } else if(c == 13) {
                this.add("\\r");
            } else if(c == 9) {
                this.add("\\t");
            } else if(Character.isISOControl(c)) {
                this.unicode(c);
            } else {
                this.add(c);
            }
        }

        this.add('"');
    }

    private void add(Object obj) {
        this.buf.append(obj);
    }

    private void add(char c) {
        this.buf.append(c);
    }

    private void unicode(char c) {
        this.add("\\u");
        int n = c;

        for(int i = 0; i < 4; ++i) {
            int digit = (n & '\uf000') >> 12;
            this.add(hex[digit]);
            n <<= 4;
        }

    }
}
