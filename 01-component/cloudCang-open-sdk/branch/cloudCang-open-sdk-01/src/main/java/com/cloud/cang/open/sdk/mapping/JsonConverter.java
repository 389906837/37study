package com.cloud.cang.open.sdk.mapping;

import com.cloud.cang.open.sdk.exception.CloudCangException;
import com.cloud.cang.open.sdk.mapping.json.ExceptionErrorListener;
import com.cloud.cang.open.sdk.mapping.json.JSONReader;
import com.cloud.cang.open.sdk.mapping.json.ResponseParseItem;
import com.cloud.cang.open.sdk.request.BaseRequest;
import com.cloud.cang.open.sdk.response.BaseResponse;
import com.cloud.cang.open.sdk.util.BaseEncrypt;
import com.cloud.cang.open.sdk.util.SignItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @version 1.0
 * @ClassName: cloudCangBranch
 * @Description:
 * @Author: zhouhong
 * @Date: 2018/9/3 16:42
 */
public class JsonConverter implements Converter {

    public JsonConverter() {
    }

    public <T extends BaseResponse> T toResponse(String rsp, Class<T> clazz) throws CloudCangException {
        JSONReader reader = new JSONValidatingReader(new ExceptionErrorListener());
        Object rootObj = reader.read(rsp);
        if(rootObj instanceof Map) {
            Map<?, ?> rootJson = (Map)rootObj;
            Collection<?> values = rootJson.values();
            Iterator var7 = values.iterator();

            while(var7.hasNext()) {
                Object rspObj = var7.next();
                if(rspObj instanceof Map) {
                    Map<?, ?> rspJson = (Map)rspObj;
                    return (T) this.fromJson(rspJson, clazz);
                }
            }
        }
        return null;
    }

    @Override
    public SignItem getSignItem(BaseRequest<?> request, String responseBody) throws CloudCangException {
        if(StringUtils.isEmpty(responseBody)) {
            return null;
        } else {
            SignItem signItem = new SignItem();
            String sign = this.getSign(responseBody);
            signItem.setSign(sign);
            String signSourceData = this.getSignSourceData(request, responseBody);
            if (!StringUtils.isEmpty(signSourceData)) {
                signSourceData = signSourceData.substring(1, signSourceData.length() - 1);
                signSourceData = signSourceData.replaceAll("\\\\","");
            }
            signItem.setSignSourceDate(signSourceData);
            return signItem;
        }
    }
    private String getSignSourceData(BaseRequest<?> request, String body) {
        String rootNode = request.getApiMethodName().replace('.', '_') + "_response";
        String errorRootNode = "error_response";
        int indexOfRootNode = body.indexOf(rootNode);
        int indexOfErrorRoot = body.indexOf(errorRootNode);
        return indexOfRootNode > 0?this.parseSignSourceData(body, rootNode, indexOfRootNode):(indexOfErrorRoot > 0?this.parseSignSourceData(body, errorRootNode, indexOfErrorRoot):null);
    }

    private String parseSignSourceData(String body, String rootNode, int indexOfRootNode) {
        int signDataStartIndex = indexOfRootNode + rootNode.length() + 2;
        int indexOfSign = body.indexOf("\"sign\"");
        if(indexOfSign < 0) {
            return null;
        } else {
            int signDataEndIndex = indexOfSign - 1;
            return body.substring(signDataStartIndex, signDataEndIndex);
        }
    }

    private String getSign(String body) {
        JSONReader reader = new JSONValidatingReader(new ExceptionErrorListener());
        Object rootObj = reader.read(body);
        //Map<?, ?> rootJson = (Map)rootObj;
        if(rootObj instanceof Map) {
            Map<?, ?> rootJson = (Map)rootObj;
            Collection<?> values = rootJson.values();
            Iterator var7 = values.iterator();

            while(var7.hasNext()) {
                Object rspObj = var7.next();
                if(rspObj instanceof Map) {
                    Map<?, ?> rspJson = (Map)rspObj;
                    return (String) rspJson.get("sign");
                }
            }
        }
        return null;
    }
    public String encryptSourceData(BaseRequest<?> request, String responseBody, String format, String encryptType, String encryptKey, String charset) throws CloudCangException {
        ResponseParseItem respSignSourceData = this.getJSONSignSourceData(request, responseBody);
        String bodyIndexContent = responseBody.substring(0, respSignSourceData.getStartIndex());
        String bodyEndContent = responseBody.substring(respSignSourceData.getEndIndex());
        return bodyIndexContent + BaseEncrypt.decryptContent(respSignSourceData.getEncryptContent(), encryptType, encryptKey, charset) + bodyEndContent;
    }

    private ResponseParseItem getJSONSignSourceData(BaseRequest<?> request, String body) {
        String rootNode = request.getApiMethodName().replace('.', '_') + "_response";
        String errorRootNode = "error_response";
        int indexOfRootNode = body.indexOf(rootNode);
        int indexOfErrorRoot = body.indexOf(errorRootNode);
        return indexOfRootNode > 0?this.parseJSONSignSourceData(body, rootNode, indexOfRootNode):(indexOfErrorRoot > 0?this.parseJSONSignSourceData(body, errorRootNode, indexOfErrorRoot):null);
    }
    private ResponseParseItem parseJSONSignSourceData(String body, String rootNode, int indexOfRootNode) {
        int signDataStartIndex = indexOfRootNode + rootNode.length() + 2;
        int indexOfSign = body.indexOf("\"sign\"");
        if(indexOfSign < 0) {
            indexOfSign = body.length();
        }
        int signDataEndIndex = indexOfSign - 1;
        String encryptContent = body.substring(signDataStartIndex + 1, signDataEndIndex - 1);
        return new ResponseParseItem(signDataStartIndex, signDataEndIndex, encryptContent);
    }


    public <T> T fromJson(final Map<?, ?> json, Class<T> clazz) throws CloudCangException {
        return Converters.convert(clazz, new Reader() {
            public boolean hasReturnField(Object name) {
                return json.containsKey(name);
            }

            public Object getPrimitiveObject(Object name) {
                return json.get(name);
            }

            public Object getObject(Object name, Class<?> type) throws CloudCangException {
                Object tmp = json.get(name);
                if(tmp instanceof Map) {
                    Map<?, ?> map = (Map)tmp;
                    return JsonConverter.this.fromJson(map, type);
                } else {
                    return null;
                }
            }

            public List<?> getListObjects(Object listName, Object itemName, Class<?> subType) throws CloudCangException {
                List<Object> listObjs = null;
                Object listTmp = json.get(listName);
                if(listTmp instanceof Map) {
                    Map<?, ?> jsonMap = (Map)listTmp;
                    Object itemTmp = jsonMap.get(itemName);
                    if(itemTmp == null && listName != null) {
                        String listNameStr = listName.toString();
                        itemTmp = jsonMap.get(listNameStr.substring(0, listNameStr.length() - 1));
                    }

                    if(itemTmp instanceof List) {
                        listObjs = this.getListObjectsInner(subType, itemTmp);
                    }
                } else if(listTmp instanceof List) {
                    listObjs = this.getListObjectsInner(subType, listTmp);
                }

                return listObjs;
            }

            private List<Object> getListObjectsInner(Class<?> subType, Object itemTmp) throws CloudCangException {
                List<Object> listObjs = new ArrayList();
                List<?> tmpList = (List)itemTmp;
                Iterator var5 = tmpList.iterator();

                while(var5.hasNext()) {
                    Object subTmp = var5.next();
                    Object obj = null;
                    if(String.class.isAssignableFrom(subType)) {
                        obj = subTmp;
                    } else if(Long.class.isAssignableFrom(subType)) {
                        obj = subTmp;
                    } else if(Integer.class.isAssignableFrom(subType)) {
                        obj = subTmp;
                    } else if(Boolean.class.isAssignableFrom(subType)) {
                        obj = subTmp;
                    } else if(Date.class.isAssignableFrom(subType)) {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                        try {
                            obj = format.parse(String.valueOf(subTmp));
                        } catch (ParseException var10) {
                            throw new CloudCangException(var10);
                        }
                    } else if(subTmp instanceof Map) {
                        Map<?, ?> subMap = (Map)subTmp;
                        obj = JsonConverter.this.fromJson(subMap, subType);
                    }

                    if(obj != null) {
                        listObjs.add(obj);
                    }
                }

                return listObjs;
            }
        });
    }
}
