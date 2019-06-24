package com.yx.earthworm.builder;

import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.yx.earthworm.io.Resources;
import com.yx.earthworm.session.Configuration;
import com.yx.earthworm.session.ExecutorType;
import com.yx.earthworm.session.SqlSession;
import com.yx.earthworm.session.defaults.DefaultSqlSessionFactory;

public class XMLConfigBuilderTest {
  private XMLConfigBuilder builder;
  private String src = "com/yx/earthworm/builder/conf.xml";
  private Configuration configuration;
  
  @Before
  public void setUp(){
    builder = new XMLConfigBuilder(Resources.getResourceAsStream(src));
    try {
      builder.parse();
    } catch (Exception e) {
      e.printStackTrace();
    }
    configuration = builder.getConfiguration();
  }
  
  @Test
  public void parse() throws Exception{
    assertNotNull(builder);
    assertNotNull(configuration);
    
  }
  
  @Test
  public void select() throws SQLException{
    DefaultSqlSessionFactory dss = new DefaultSqlSessionFactory(configuration);
    SqlSession ss = dss.openSqlSession(ExecutorType.SIMPLE);
    List<?> res = ss.selectList("com.yx.earthworm.builder.AuthorMapper.getaz01", new HashMap());
    System.out.println(res);
  }
  
}
