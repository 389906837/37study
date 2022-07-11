package com.cang.os.security.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Administrator
 *
 */
/**
 * @author Administrator
 *
 */
public class HtmlClean {
	
	
	/**
	 * Antispam Xss过滤
	 * @param input
	 * @return
	 */
	public static String htmlSecurityEscape( String input ){
		StringBuilder result = null;
		if( input != null ){
			result = new StringBuilder();
			char[] characters = input.toCharArray();
			int length = characters.length;
			int i = 0;
			boolean special;
			while( i < length ){
				switch (characters[i]) {
				case '\'':
					result.append("&#39;");
					break;
				case '\"':
					result.append("&quot;");
					break;
				case '\\':
					special = false;
					if( i < ( length -1)  ){
						char next = characters[ i+1 ];
						if( next == 'x' || next == 'u' || next == 'U' || next == 'X' ||	 (next >= '0' && next <= '9')){
							special = true;
						}
					}
					if( special ){
						result.append("&#92;") ;
					} else {
						result.append("\\");
					}
					break;
				case '<':
					result.append("&lt;");
					break;
				case '>':
					result.append("&gt;");
					break;
//				case '&'://防止二次转义 同时防止 &..&情况出现
//					special = false;
//					int position = position(characters, ';', length, (i+1) , 9 );
//					if( position != -1  ){
//						position = position(characters, '&', length, ( i+1) , position );
//						if( position == -1 ){
//							special = true;
//						}
//					}
//					if( special ){
//						result.append("&");
//					} else {
//						result.append("&amp;");
//					}
//					break;
				case '/':
					if( ( i < length - 1 ) && characters[ i+1 ] == '*' ){
						result.append("&#47;&#42;");
						i++;
					} else {
						result.append("/");
					}
					break;
				case '*':
					if ( ( i < length - 1) && characters[i+1] == '/'){
						result.append("&#42;&#47;");
						i++;
					} else {
						result.append("*");
					}
					break;
				default:
					if( !( Character.getDirectionality(characters[i]) == Character.DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC 
							|| Character.getDirectionality(characters[i]) == Character.DIRECTIONALITY_RIGHT_TO_LEFT_EMBEDDING 
							|| Character.getDirectionality(characters[i]) == Character.DIRECTIONALITY_RIGHT_TO_LEFT_OVERRIDE ) ){
						result.append(characters[i]);
					}
					break;
				}
				i++;
			}
		}
		return result != null ? result.toString() : null ;
	}
	
	public static String specaialCharReplace(String input){
		int dotBeforePortVal = dotBeforePort(input);
		
		StringBuilder result = null;
		if( input != null ){
			result = new StringBuilder();
			char[] characters = input.toCharArray();
			int length = characters.length;
			int i = 0;
			while( i < length ){
				switch (characters[i]) {
				case '(':
					result.append("&#40;");
					break;
				case ')':
					result.append("&#41;");
					break;
//				case ':':
//					if(input.indexOf("baidu.com") >=0 && input.indexOf("from=") >=0 && input.indexOf("word=") >=0){
//						if(input.startsWith("http://") && i == 4){
//							result.append(":");
//							break;
//						}else if(input.startsWith("https://") && i == 5){
//							result.append(":");
//							break;
//						}else if(i == dotBeforePortVal){
//							result.append(":");
//							break;
//						}
//						result.append("&#58;");
//					}else{
//						result.append(":");
//					}
//					break;
//				case ';':
//					result.append("&#59;");
//					break;					
				default:
					result.append(characters[i]);
					break;
				}
				i++;
			}
		}
		return result != null ? result.toString().replaceAll("\n", "").replaceAll("\r", "") : null ;
	}
	
	/**
	 * 返回 从begin  开始 end 结束 查询到某个字符得位置
	 * @param characters
	 * @param c
	 * @param length
	 * @param begin
	 * @param end
	 * @return
	 */
	private static int position(char[] characters , char c ,  int length ,  int begin , int end ){
		if( begin >= length  ){
			return -1;
		}
		end = ( begin + end ) > length ? length : (begin + end );
		for( int i = begin ; i < end ; i++ ){
			if ( characters[i] == c) {
				return ( i -  begin + 1 );
			}
		}
		return -1;
	}

	static String s = "https?://.*?(:)\\d{1,5}/";
	static Pattern p = Pattern.compile(s, Pattern.CASE_INSENSITIVE);
	
	//端口前的冒号不过滤
	private static  int dotBeforePort(String input){
		Matcher m = p.matcher(input);
		if(m.find()){
			return m.end(1)-1;
		}
		return -1;
	}
	
	public static void main(String[] args ) {
		
		System.out.println( htmlSecurityEscape("\"onmouce=alert") ) ;
		
	}
	
	

}
