package com.yx.earthworm.session;

import java.io.InputStream;

import com.yx.earthworm.builder.XMLConfigBuilder;
import com.yx.earthworm.session.defaults.DefaultSqlSessionFactory;

/**
 * 
 * @author yangxin 2019年4月17日 下午4:45:48
 */
public final class SqlSessionFactoryBuilder {
  
  public SqlSessionFactory build(InputStream input) throws Exception{
    XMLConfigBuilder builder = new XMLConfigBuilder(input);
    return build(builder.parse());
  }

  public SqlSessionFactory build(Configuration configuration){
    return new DefaultSqlSessionFactory(configuration);
  }
  
}
