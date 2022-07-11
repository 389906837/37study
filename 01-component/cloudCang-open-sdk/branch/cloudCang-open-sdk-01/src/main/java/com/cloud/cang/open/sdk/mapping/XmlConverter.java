package com.cloud.cang.open.sdk.mapping;

import com.cloud.cang.open.sdk.exception.CloudCangException;
import com.cloud.cang.open.sdk.mapping.json.ResponseParseItem;
import com.cloud.cang.open.sdk.request.BaseRequest;
import com.cloud.cang.open.sdk.response.BaseResponse;
import com.cloud.cang.open.sdk.util.BaseEncrypt;
import com.cloud.cang.open.sdk.util.SignItem;
import com.cloud.cang.open.sdk.utils.Base64;
import com.cloud.cang.open.sdk.utils.XmlUtils;
import org.w3c.dom.Element;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @version 1.0
 * @Description: xml 解析转换器
 * @Author: zhouhong
 * @Date: 2018/9/3 16:21
 */
public class XmlConverter implements Converter {
    public XmlConverter() {
    }

    @Override
    public <T extends BaseResponse> T toResponse(String scontent, Class<T> tClass) throws CloudCangException {
        Element root = XmlUtils.getRootElementFromString(scontent);
        return (T) this.getModelFromXML(root, tClass);
    }

    public SignItem getSignItem(BaseRequest<?> request, String responseBody) throws CloudCangException {
        if(StringUtils.isEmpty(responseBody)) {
            return null;
        } else {
            SignItem signItem = new SignItem();
            String sign = this.getSign(responseBody);
            signItem.setSign(sign);
            String signSourceData = this.getSignSourceData(request, responseBody);
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
        int signDataStartIndex = indexOfRootNode + rootNode.length() + 1;
        int indexOfSign = body.indexOf("<sign");
        return indexOfSign < 0?null:body.substring(signDataStartIndex, indexOfSign);
    }

    private String getSign(String body) {
        String signNodeName = "<sign>";
        String signEndNodeName = "</sign>";
        int indexOfSignNode = body.indexOf(signNodeName);
        int indexOfSignEndNode = body.indexOf(signEndNodeName);
        return indexOfSignNode >= 0 && indexOfSignEndNode >= 0?body.substring(indexOfSignNode + signNodeName.length(), indexOfSignEndNode):null;
    }


    @Override
    public String encryptSourceData(BaseRequest<?> request, String responseBody, String format, String encryptType, String encryptKey, String charset) throws CloudCangException {
        ResponseParseItem respSignSourceData = this.getXMLSignSourceData(request, responseBody);
        String bodyIndexContent = responseBody.substring(0, respSignSourceData.getStartIndex());
        String bodyEndContent = responseBody.substring(respSignSourceData.getEndIndex());
        return bodyIndexContent + BaseEncrypt.decryptContent(respSignSourceData.getEncryptContent(), encryptType, encryptKey, charset) + bodyEndContent;
    }

    private ResponseParseItem getXMLSignSourceData(BaseRequest<?> request, String body) {
        String rootNode = request.getApiMethodName().replace('.', '_') + "_response";
        String errorRootNode = "error_response";
        int indexOfRootNode = body.indexOf(rootNode);
        int indexOfErrorRoot = body.indexOf(errorRootNode);
        return indexOfRootNode > 0?this.parseXMLSignSourceData(body, rootNode, indexOfRootNode):(indexOfErrorRoot > 0?this.parseXMLSignSourceData(body, errorRootNode, indexOfErrorRoot):null);
    }

    private ResponseParseItem parseXMLSignSourceData(String body, String rootNode, int indexOfRootNode) {
        int signDataStartIndex = indexOfRootNode + rootNode.length() + 1;
        String xmlStartNode = "<response_encrypted>";
        String xmlEndNode = "</response_encrypted>";
        int indexOfSign = body.indexOf(xmlEndNode);
        if(indexOfSign < 0) {
            return new ResponseParseItem(0, 0, "");
        } else {
            int signDataEndIndex = indexOfSign + xmlEndNode.length();
            String encryptBizContent = body.substring(signDataStartIndex, signDataEndIndex);
            String bizContent = encryptBizContent.substring(xmlStartNode.length(), encryptBizContent.length() - xmlEndNode.length());
            return new ResponseParseItem(signDataStartIndex, signDataEndIndex, bizContent);
        }
    }
    private <T> T getModelFromXML(final Element element, Class<T> clazz) throws CloudCangException {
        return element == null?null:Converters.convert(clazz, new Reader() {
            public boolean hasReturnField(Object name) {
                Element childE = XmlUtils.getChildElement(element, (String)name);
                return childE != null;
            }

            public Object getPrimitiveObject(Object name) {
                return XmlUtils.getElementValue(element, (String)name);
            }

            public Object getObject(Object name, Class<?> type) throws CloudCangException {
                Element childE = XmlUtils.getChildElement(element, (String)name);
                return childE != null?XmlConverter.this.getModelFromXML(childE, type):null;
            }

            public List<?> getListObjects(Object listName, Object itemName, Class<?> subType) throws CloudCangException {
                List<Object> list = null;
                Element listE = XmlUtils.getChildElement(element, (String)listName);
                if(listE != null) {
                    list = new ArrayList();
                    List<Element> itemEs = XmlUtils.getChildElements(listE, (String)itemName);
                    Iterator var7 = itemEs.iterator();

                    while(var7.hasNext()) {
                        Element itemE = (Element)var7.next();
                        Object obj = null;
                        String value = XmlUtils.getElementValue(itemE);
                        if(String.class.isAssignableFrom(subType)) {
                            obj = value;
                        } else if(Long.class.isAssignableFrom(subType)) {
                            obj = Long.valueOf(value);
                        } else if(Integer.class.isAssignableFrom(subType)) {
                            obj = Integer.valueOf(value);
                        } else if(Boolean.class.isAssignableFrom(subType)) {
                            obj = Boolean.valueOf(value);
                        } else if(Date.class.isAssignableFrom(subType)) {
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                            try {
                                obj = format.parse(value);
                            } catch (ParseException var13) {
                                throw new CloudCangException(var13);
                            }
                        } else {
                            obj = XmlConverter.this.getModelFromXML(itemE, subType);
                        }

                        if(obj != null) {
                            list.add(obj);
                        }
                    }
                }

                return list;
            }
        });
    }
}
