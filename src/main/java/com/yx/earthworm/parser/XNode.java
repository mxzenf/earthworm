package com.yx.earthworm.parser;
/**
 * 
 * @author yangxin 2019年1月6日 下午6:26:47
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.CharacterData;

public class XNode {

  private Node node;
  private XPathParser xpath;
  private Properties attributes;
  private String body;

  public XNode() {
  }

  public XNode(Node node, XPathParser xpath) {
    this.node = node;
    this.xpath = xpath;
    this.attributes = parseAttributes();
    this.body = parseBody(node);
  }

  private String parseBody(Node node) {
    String data = getBody(node);
    if (data == null) {
      NodeList children = node.getChildNodes();
      for (int i = children.getLength() - 1; i >= 0; i--) {
        data = getBody(children.item(i));
        if (null != data) {
          return data;
        }
      }
    }
    return null;
  }

  private String getBody(Node node) {
    if (node.getNodeType() == Node.CDATA_SECTION_NODE || node.getNodeType() == Node.TEXT_NODE) {
      return ((CharacterData) node).getData();
    }
    return null;
  }

  public String getStringBody() {
    return body;
  }

  public Node getNode() {
    return node;
  }

  public void setNode(Node node) {
    this.node = node;
  }

  public Properties getAttributes() {
    return attributes;
  }

  public void setAttributes(Properties attributes) {
    this.attributes = attributes;
  }

  private Properties parseAttributes() {
    Properties attributes = new Properties();

    NamedNodeMap nnm = this.node.getAttributes();
    if (null != nnm) {
      Node n = null;
      for (int i = 0; i < nnm.getLength(); i++) {
        n = nnm.item(i);
        attributes.put(n.getNodeName(), n.getNodeValue());
      }
    }

    return attributes;
  }

  public String getStringAttribute(String name) {
    return getStringAttribute(name, null);
  }

  public String getStringAttribute(String name, String def) {
    String value = attributes.getProperty(name);
    if (null == value) {
      return def;
    }
    return value;
  }

  public Properties getChildrenAsProperties() {
    Properties props = new Properties();
    for (XNode xn : getChildren()) {
      props.put(xn.getStringAttribute("name"), xn.getStringAttribute("value"));
    }
    return props;
  }

  public List<XNode> getChildren() {
    List<XNode> children = new ArrayList<XNode>();
    NodeList nodes = node.getChildNodes();
    if (null != nodes) {
      for (int i = nodes.getLength() - 1; i >= 0; i--) {
        if (nodes.item(i).getNodeType() != Node.CDATA_SECTION_NODE && 
            nodes.item(i).getNodeType() != Node.TEXT_NODE) {
          children.add(new XNode(nodes.item(i), xpath));
        }

      }
    }

    return children;
  }

  public List<XNode> evalNodes(String expression) {
    return xpath.evaluateNodes(node, expression);
  }

  public XNode evalNode(String expression) {
    return xpath.evaluate(expression, node);
  }

}
