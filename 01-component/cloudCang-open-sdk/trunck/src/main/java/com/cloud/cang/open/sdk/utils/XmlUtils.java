package com.cloud.cang.open.sdk.utils;

import com.cloud.cang.open.sdk.exception.CloudCangException;
import com.cloud.cang.open.sdk.mapping.StringUtils;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

/**
 * @version 1.0
 * @Description: xml 操作类
 * @Author: zhouhong
 * @Date: 2018/9/3 16:22
 */
public class XmlUtils {
    
    private static final String XMLNS_XSI = "xmlns:xsi";
    private static final String XSI_SCHEMA_LOCATION = "xsi:schemaLocation";
    private static final String LOGIC_YES = "yes";
    private static final String DEFAULT_ENCODE = "UTF-8";
    private static final String REG_INVALID_CHARS = "&#\\d+;";

    public XmlUtils() {
    }

    public static Document newDocument() throws CloudCangException {
        Document doc = null;

        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            documentBuilderFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            documentBuilderFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            documentBuilderFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            documentBuilderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            documentBuilderFactory.setXIncludeAware(false);
            documentBuilderFactory.setExpandEntityReferences(false);
            doc = documentBuilderFactory.newDocumentBuilder().newDocument();
            return doc;
        } catch (ParserConfigurationException var2) {
            throw new CloudCangException(var2);
        }
    }

    public static Document getDocument(File file) throws CloudCangException {
        InputStream in = getInputStream(file);
        return getDocument(in);
    }

    public static Document getDocument(InputStream in) throws CloudCangException {
        Document doc = null;

        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            documentBuilderFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            documentBuilderFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            documentBuilderFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            documentBuilderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            documentBuilderFactory.setXIncludeAware(false);
            documentBuilderFactory.setExpandEntityReferences(false);

            DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
            doc = builder.parse(in);
        } catch (ParserConfigurationException var12) {
            throw new CloudCangException(var12);
        } catch (SAXException var13) {
            throw new CloudCangException("XML_PARSE_ERROR", var13);
        } catch (IOException var14) {
            throw new CloudCangException("XML_READ_ERROR", var14);
        } finally {
            if(in != null) {
                try {
                    in.close();
                } catch (IOException var11) {
                    ;
                }
            }

        }

        return doc;
    }

    public static Element createRootElement(String tagName) throws CloudCangException {
        Document doc = newDocument();
        Element root = doc.createElement(tagName);
        doc.appendChild(root);
        return root;
    }

    public static Element getRootElementFromStream(InputStream in) throws CloudCangException {
        return getDocument(in).getDocumentElement();
    }

    public static Element getRootElementFromFile(File file) throws CloudCangException {
        return getDocument(file).getDocumentElement();
    }

    private static String getEncoding(String text) {
        String result = "UTF-8";
        String xml = text.trim();
        if(xml.startsWith("<?xml")) {
            int end = xml.indexOf("?>");
            String sub = xml.substring(0, end);
            StringTokenizer tokens = new StringTokenizer(sub, " =\"'");

            while(tokens.hasMoreTokens()) {
                String token = tokens.nextToken();
                if("encoding".equals(token)) {
                    if(tokens.hasMoreTokens()) {
                        result = tokens.nextToken();
                    }
                    break;
                }
            }
        }

        return result;
    }

    public static Element getRootElementFromString(String payload) throws CloudCangException {
        if(payload != null && payload.trim().length() >= 1) {
            Object var1 = null;

            byte[] bytes;
            try {
                payload = StringUtils.stripNonValidXMLCharacters(payload);
                String encodeString = getEncoding(payload);
                bytes = payload.getBytes(encodeString);
            } catch (UnsupportedEncodingException var3) {
                throw new CloudCangException("XML_ENCODE_ERROR", var3);
            }

            InputStream in = new ByteArrayInputStream(bytes);
            return getDocument((InputStream)in).getDocumentElement();
        } else {
            throw new CloudCangException("XML_PAYLOAD_EMPTY");
        }
    }

    public static List<Element> getElements(Element parent, String tagName) {
        NodeList nodes = parent.getElementsByTagName(tagName);
        List<Element> elements = new ArrayList();

        for(int i = 0; i < nodes.getLength(); ++i) {
            Node node = nodes.item(i);
            if(node instanceof Element) {
                elements.add((Element)node);
            }
        }

        return elements;
    }

    public static Element getElement(Element parent, String tagName) {
        List<Element> children = getElements(parent, tagName);
        return children.isEmpty()?null:(Element)children.get(0);
    }

    public static List<Element> getChildElements(Element parent, String tagName) {
        NodeList nodes = parent.getElementsByTagName(tagName);
        List<Element> elements = new ArrayList();

        for(int i = 0; i < nodes.getLength(); ++i) {
            Node node = nodes.item(i);
            if(node instanceof Element && node.getParentNode() == parent) {
                elements.add((Element)node);
            }
        }

        return elements;
    }

    public static Element getChildElement(Element parent, String tagName) {
        List<Element> children = getChildElements(parent, tagName);
        return children.isEmpty()?null:(Element)children.get(0);
    }

    public static String getElementValue(Element parent, String tagName) {
        Element element = getChildElement(parent, tagName);
        if(element != null) {
            NodeList nodes = element.getChildNodes();
            if(nodes != null && nodes.getLength() > 0) {
                for(int i = 0; i < nodes.getLength(); ++i) {
                    Node node = nodes.item(i);
                    if(node instanceof Text) {
                        return ((Text)node).getData();
                    }
                }
            }
        }

        return null;
    }

    public static String getElementValue(Element element) {
        if(element != null) {
            NodeList nodes = element.getChildNodes();
            if(nodes != null && nodes.getLength() > 0) {
                for(int i = 0; i < nodes.getLength(); ++i) {
                    Node node = nodes.item(i);
                    if(node instanceof Text) {
                        return ((Text)node).getData();
                    }
                }
            }
        }

        return null;
    }

    public static Element appendElement(Element parent, String tagName) {
        Element child = parent.getOwnerDocument().createElement(tagName);
        parent.appendChild(child);
        return child;
    }

    public static Element appendElement(Element parent, String tagName, String value) {
        Element child = appendElement(parent, tagName);
        child.appendChild(child.getOwnerDocument().createTextNode(value));
        return child;
    }

    public static void appendElement(Element parent, Element child) {
        Node tmp = parent.getOwnerDocument().importNode(child, true);
        parent.appendChild(tmp);
    }

    public static Element appendCDATAElement(Element parent, String tagName, String value) {
        Element child = appendElement(parent, tagName);
        if(value == null) {
            value = "";
        }

        Node cdata = child.getOwnerDocument().createCDATASection(value);
        child.appendChild(cdata);
        return child;
    }

    public static String childNodeToString(Node node) throws CloudCangException {
        String payload = null;

        try {
            Transformer tf = TransformerFactory.newInstance().newTransformer();
            Properties props = tf.getOutputProperties();
            props.setProperty("omit-xml-declaration", "yes");
            tf.setOutputProperties(props);
            StringWriter writer = new StringWriter();
            tf.transform(new DOMSource(node), new StreamResult(writer));
            payload = writer.toString();
            payload = payload.replaceAll("&#\\d+;", " ");
            return payload;
        } catch (TransformerException var5) {
            throw new CloudCangException("XML_TRANSFORM_ERROR", var5);
        }
    }

    public static String nodeToString(Node node) throws CloudCangException {
        String payload = null;

        try {
            Transformer tf = TransformerFactory.newInstance().newTransformer();
            Properties props = tf.getOutputProperties();
            props.setProperty("indent", "yes");
            props.setProperty("encoding", "UTF-8");
            tf.setOutputProperties(props);
            StringWriter writer = new StringWriter();
            tf.transform(new DOMSource(node), new StreamResult(writer));
            payload = writer.toString();
            payload = payload.replaceAll("&#\\d+;", " ");
            return payload;
        } catch (TransformerException var5) {
            throw new CloudCangException("XML_TRANSFORM_ERROR", var5);
        }
    }

    public static String xmlToString(File file) throws CloudCangException {
        Element root = getRootElementFromFile(file);
        return nodeToString(root);
    }

    public static String xmlToString(InputStream in) throws CloudCangException {
        Element root = getRootElementFromStream(in);
        return nodeToString(root);
    }

    public static void saveToXml(Node doc, File file) throws CloudCangException {
        OutputStream out = null;

        try {
            Transformer tf = TransformerFactory.newInstance().newTransformer();
            Properties props = tf.getOutputProperties();
            props.setProperty("method", "xml");
            props.setProperty("indent", "yes");
            tf.setOutputProperties(props);
            DOMSource dom = new DOMSource(doc);
            out = getOutputStream(file);
            Result result = new StreamResult(out);
            tf.transform(dom, result);
        } catch (TransformerException var14) {
            throw new CloudCangException("XML_TRANSFORM_ERROR", var14);
        } finally {
            if(out != null) {
                try {
                    out.close();
                } catch (IOException var13) {
                    ;
                }
            }

        }

    }

    public static void validateXml(Node doc, File schemaFile) throws CloudCangException {
        validateXml(doc, getInputStream(schemaFile));
    }

    public static void validateXml(Node doc, InputStream schemaStream) throws CloudCangException {
        try {
            Source source = new StreamSource(schemaStream);
            Schema schema = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema").newSchema(source);
            Validator validator = schema.newValidator();
            validator.validate(new DOMSource(doc));
        } catch (SAXException var13) {
            throw new CloudCangException("XML_VALIDATE_ERROR", var13);
        } catch (IOException var14) {
            throw new CloudCangException("XML_READ_ERROR", var14);
        } finally {
            if(schemaStream != null) {
                try {
                    schemaStream.close();
                } catch (IOException var12) {
                    ;
                }
            }

        }

    }

    public static String xmlToHtml(String payload, File xsltFile) throws CloudCangException {
        String result = null;

        try {
            Source template = new StreamSource(xsltFile);
            Transformer transformer = TransformerFactory.newInstance().newTransformer(template);
            Properties props = transformer.getOutputProperties();
            props.setProperty("omit-xml-declaration", "yes");
            transformer.setOutputProperties(props);
            StreamSource source = new StreamSource(new StringReader(payload));
            StreamResult sr = new StreamResult(new StringWriter());
            transformer.transform(source, sr);
            result = sr.getWriter().toString();
            return result;
        } catch (TransformerException var8) {
            throw new CloudCangException("XML_TRANSFORM_ERROR", var8);
        }
    }

    public static void setNamespace(Element element, String namespace, String schemaLocation) {
        element.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", namespace);
        element.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
        element.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "xsi:schemaLocation", schemaLocation);
    }

    public static String encodeXml(String payload) throws CloudCangException {
        Element root = createRootElement("xml");
        root.appendChild(root.getOwnerDocument().createTextNode(payload));
        return childNodeToString(root.getFirstChild());
    }

    private static InputStream getInputStream(File file) throws CloudCangException {
        FileInputStream in = null;

        try {
            in = new FileInputStream(file);
            return in;
        } catch (FileNotFoundException var3) {
            throw new CloudCangException("FILE_NOT_FOUND", var3);
        }
    }

    private static OutputStream getOutputStream(File file) throws CloudCangException {
        FileOutputStream in = null;

        try {
            in = new FileOutputStream(file);
            return in;
        } catch (FileNotFoundException var3) {
            throw new CloudCangException("FILE_NOT_FOUND", var3);
        }
    }
}
