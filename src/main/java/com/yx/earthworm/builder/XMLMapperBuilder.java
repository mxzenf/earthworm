package com.yx.earthworm.builder;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.yx.earthworm.io.Resources;
import com.yx.earthworm.mapping.MappedStatement;
import com.yx.earthworm.mapping.ParameterMap;
import com.yx.earthworm.mapping.ParameterMapping;
import com.yx.earthworm.mapping.SqlCommandType;
import com.yx.earthworm.mapping.SqlSource;
import com.yx.earthworm.parser.XNode;
import com.yx.earthworm.parser.XPathParser;
import com.yx.earthworm.session.Configuration;

/**
 * 
 * @author yangxin 2019年3月26日 下午1:21:40
 */
public class XMLMapperBuilder extends BaseBuilder {

  private String resource;
  private XPathParser parser;
  private String currentNameSpace;
  
  public XMLMapperBuilder(String resource, Configuration configuration) {
    this(resource, configuration, new XPathParser(Resources.getResourceAsStream(resource)));
  }
  
  public XMLMapperBuilder(InputStream input, Configuration configuration) {
    super(configuration);
    this.parser = new XPathParser(input);
  }
  
  public XMLMapperBuilder(String resource, Configuration configuration, XPathParser parser){
    super(configuration);
    this.parser = parser;
    this.resource = resource;
  }
  
  public void parse(){
    if (!configuration.isResourceLoaded(this.resource)) {
      configurationElement(parser.evalNode("/mapper"));
    }
  }

  private void configurationElement(XNode context) {
    String namespace = context.getStringAttribute("namespace");
    if (null == namespace || 0 == namespace.length()) {
      throw new BuilderException("mapper命名空间不能为空");
    }
    setCurrentNameSpace(namespace);
    buildMappedStatmentsForContext(context.evalNodes("select|insert|update|delete"));
  }

  private void buildMappedStatmentsForContext(List<XNode> nodes) {

    for (XNode context : nodes) {
      String id = context.getStringAttribute("id");
      String nodeName = context.getNode().getNodeName();
      SqlCommandType sqlCommandType = SqlCommandType.valueOf(nodeName.toUpperCase());
      String parameterType = context.getStringAttribute("parameterType");
      String resultType = context.getStringAttribute("resultType");
      addMappedStatment(id, sqlCommandType, parameterType, resultType, context);
    }
    
  }

  private void addMappedStatment(String id, SqlCommandType sqlCommandType, String parameterType, String resultType, XNode node) {

    id = applyCurrentNameSpace(id);
    SqlSourceBuilder sqlSourceBuilder = new SqlSourceBuilder(super.configuration);
    Class<?> parameterTypeClass = resolveClass(parameterType);
    SqlSource sqlSource = sqlSourceBuilder.parse(node.getStringBody(), parameterTypeClass, new HashMap<String, Object>());
    
    ResultMap.Builder resultMapBuilder = new ResultMap.Builder(id, resolveClass(resultType));
    ParameterMap.Builder parameterMapBuilder = new ParameterMap.Builder(id, parameterTypeClass, new ArrayList<ParameterMapping>());
    
    MappedStatement.Builder builder = new MappedStatement.Builder(id, sqlSource, sqlCommandType, this.configuration);
    builder.parameterMap(parameterMapBuilder.build());
    builder.resultMap(resultMapBuilder.build());
    super.configuration.addMappedStatement(builder.build());
  }

  private String applyCurrentNameSpace(String id) {
    return this.currentNameSpace + "." + id;
  }

  public String getResource() {
    return resource;
  }

  public void setResource(String resource) {
    this.resource = resource;
  }

  public XPathParser getParser() {
    return parser;
  }

  public void setParser(XPathParser parser) {
    this.parser = parser;
  }

  public String getCurrentNameSpace() {
    return currentNameSpace;
  }

  public void setCurrentNameSpace(String currentNameSpace) {
    this.currentNameSpace = currentNameSpace;
  }
  
}
