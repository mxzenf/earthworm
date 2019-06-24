package com.yx.earthworm.builder;

import java.io.InputStream;

import com.yx.earthworm.datasource.DataSourceFactory;
import com.yx.earthworm.io.Resources;
import com.yx.earthworm.parser.XNode;
import com.yx.earthworm.parser.XPathParser;
import com.yx.earthworm.session.Configuration;
import com.yx.earthworm.session.Environment;
import com.yx.earthworm.transaction.TransactionFactory;

/**
 * 
 * @author yangxin 2019年4月19日 下午3:39:42
 */
public class XMLConfigBuilder extends BaseBuilder {

  private XPathParser parser;
  private String environment;
  
  public XMLConfigBuilder(InputStream input){
    this(new XPathParser(input), null);
  }
  
  public XMLConfigBuilder(XPathParser parser, String environment){
    super(new Configuration());
    this.parser = parser;
    this.environment = environment;
  }
  
  public Configuration parse() throws Exception{
    parseConfiguration(parser.evalNode("/configuration"));
    
    return configuration;
  }

  private void parseConfiguration(XNode root) throws Exception {
    mappedElement(root.evalNode("mappers"));    
    environmentElement(root.evalNode("environments"));
  }

  private void environmentElement(XNode context) throws Exception {
    if (null == environment) {
      environment = context.getStringAttribute("default");
    }
    for (XNode xn : context.getChildren()) {
      if (isUseEnviroment(xn)) {
        TransactionFactory transactionFactory = transactionFactoryElement(xn.evalNode("transactionManager"));
        DataSourceFactory dsFactory = dataSourceFactoryElement(xn.evalNode("dataSource"));
        Environment.Builder builder = new Environment.Builder();
        builder.transactionFactory(transactionFactory);
        builder.dataSource(dsFactory.getDataSource());
        configuration.setEnvironment(builder.build());
      }
    }
  }

  private DataSourceFactory dataSourceFactoryElement(XNode context) throws InstantiationException, IllegalAccessException {
    String type = context.getStringAttribute("type");
    DataSourceFactory dsFactory = (DataSourceFactory) resolveClass(type).newInstance();
    dsFactory.setProperties(context.getChildrenAsProperties());
    return dsFactory;
  }

  private TransactionFactory transactionFactoryElement(XNode context) throws InstantiationException, IllegalAccessException {
    String type = context.getStringAttribute("type");
    TransactionFactory transactionFactory = (TransactionFactory) resolveClass(type).newInstance();
    return transactionFactory;
  }

  private boolean isUseEnviroment(XNode xn) {
    String id = xn.getStringAttribute("id");
    return null != id && environment.equals(id);
  }

  private void mappedElement(XNode context) {
    for (XNode mapper : context.evalNodes("mapper")) {
      String src = mapper.getStringAttribute("src");
      XMLMapperBuilder builder = new XMLMapperBuilder(Resources.getResourceAsStream(src), configuration);
      builder.parse();
    }
  }
}
