package com.yx.earthworm.parser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * xml xpath解析器
 * 
 * @author yangxin 2019年1月5日 下午8:21:00
 */
public class XPathParser {

  private XPath xpath;
  private Document document;

  public XPathParser() {
  }

  public XPathParser(InputSource is) {
    commonConstructor();
    this.document = createDocument(is);
  }

  public XPathParser(InputStream is) {
    this(new InputSource(is));
  }

  private void commonConstructor() {
    XPathFactory xPathFactory = XPathFactory.newInstance();
    this.xpath = xPathFactory.newXPath();
  }

  private Object evaluate(String expression, Object item, QName returnType) {
    try {
      return xpath.evaluate(expression, item, returnType);
    } catch (XPathExpressionException e) {
      e.printStackTrace();
    }
    return null;
  }
  
  public XNode evaluate(String expression, Object context) {
    return new XNode((Node) evaluate(expression, context, XPathConstants.NODE), this);
  }

  public Object evaluate(String expression, QName returnType) {
    return evaluate(expression, document, returnType);
  }

  public XNode evalNode(String expression) {
    return new XNode((Node) evaluate(expression, XPathConstants.NODE), this);
  }
  
  public List<XNode> evaluateNodes(String expression) {
    return evaluateNodes(document, expression);
  }

  public List<XNode> evaluateNodes(Object root, String expression) {
    List<XNode> nodes = new ArrayList<XNode>();
    NodeList nl = (NodeList) evaluate(expression, root, XPathConstants.NODESET);

    if (null != nl && nl.getLength() > 0) {
      for (int i = 0; i < nl.getLength(); i++) {
        nodes.add(new XNode(nl.item(i), this));
      }
    }

    return nodes;
  }

  private Document createDocument(InputSource inputSource) {
    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder documentBuilder;
    try {
      documentBuilder = documentBuilderFactory.newDocumentBuilder();
      return documentBuilder.parse(inputSource);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
