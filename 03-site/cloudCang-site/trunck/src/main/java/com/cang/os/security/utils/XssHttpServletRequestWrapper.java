package com.cang.os.security.utils;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.cang.os.common.utils.StringUtil;

public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
	
	private Map<Object, Object> parameterMap = new HashMap<Object, Object>();
	
	final static String END_WITH_JSON = "_json";
	
	
	public XssHttpServletRequestWrapper( HttpServletRequest request ) {
		super( request ) ;
		init() ;
	}
	
	
	@SuppressWarnings({ "unchecked"}) 
	private void init(){
		HttpServletRequest request = (HttpServletRequest)getRequest();
		if(request != null){
			Map<String, String[]> map = request.getParameterMap();
			for(Entry entry : map.entrySet()){
				Object key = entry.getKey();
				Object value = entry.getValue();
				
				if( key != null &&  StringUtil.isNotBlank( key.toString() ) ) {
					if(value != null){
						
						if( key.toString().endsWith( END_WITH_JSON ) ) {
							parameterMap.put(key, value) ;
						} else {
							boolean special = false; 
							if(key.toString().equals("from")){
								special = true;
							}
							
							if(value instanceof String[]){
								int length = ((String[])value).length;
								String[] strs = new String[length];
								for(int i = 0 ; i < length ; i++){
									strs[i] = HtmlClean.htmlSecurityEscape( ((String[])value)[i] ) ;
									if(special){
										strs[i] = HtmlClean.specaialCharReplace( strs[i] ) ; 
									}
								}
								parameterMap.put(key, strs);
							} else if(value instanceof String){
								String temp = HtmlClean.htmlSecurityEscape( (String)value);
								if(special){
									temp =  HtmlClean.specaialCharReplace( temp )  ; 
								}
								parameterMap.put(key,  temp) ; 
							} else{
								String temp =  HtmlClean.htmlSecurityEscape( value.toString());
								if(special){
									temp =  HtmlClean.specaialCharReplace( temp ) ; 
								}
								parameterMap.put(key, temp ); 
							}
						}
					}
				}
			}
				
				
		}
	}
	
	@Override
	public String getParameter(String name) {
		Object object = parameterMap.get(name);
		if(object != null){
			if(object instanceof String[]){ 
				String[] array = (String[])object;
				if(array.length > 0){
					return array[0];
				}
			}
			else if(object instanceof String){
				return (String)object;
			}
			else{
				return object.toString();
			}
		}
		return this.getRequest().getParameter(name);
	}
	
	
	
	@SuppressWarnings("rawtypes")
	@Override
	public Enumeration getParameterNames() {
		return Collections.enumeration(parameterMap.keySet());
	}

	
	@Override
	public String[] getParameterValues(String name) {
		
		Object object = parameterMap.get(name);
		if(object != null){
			if(object instanceof String[]){ 
				return (String[])object;
			} else if(object instanceof String){
				return new String[]{(String)object};
			} else {
				return new String[]{object.toString()};
			}
		}
		return null;
	}
	
	
	@SuppressWarnings("rawtypes")
	@Override
	public Map getParameterMap() {
		return parameterMap;
	}

	
	
	
	
	
}
