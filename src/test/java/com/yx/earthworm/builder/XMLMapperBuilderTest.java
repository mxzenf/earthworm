package com.yx.earthworm.builder;


import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.List;

import com.yx.earthworm.session.Configuration;
import com.yx.earthworm.session.ExecutorType;
import com.yx.earthworm.session.SqlSession;
import com.yx.earthworm.session.defaults.DefaultSqlSessionFactory;

/**
 * 
 * @author yangxin 2019年4月12日 上午9:54:34
 */
public class XMLMapperBuilderTest {

  private XMLMapperBuilder builder;
  private static final String resource = "com/yx/earthworm/builder/AuthorMapper.xml";
  @Before
  public void setUp(){
    builder = new XMLMapperBuilder(resource, new Configuration());
  }
  @SuppressWarnings("all")
  @Test
  public void parse(){
    assertNotNull(builder);
    builder.parse();
  }
  
}
