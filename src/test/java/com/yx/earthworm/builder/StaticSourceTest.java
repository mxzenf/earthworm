package com.yx.earthworm.builder;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.yx.earthworm.mapping.BoundSql;
import com.yx.earthworm.mapping.SqlSource;
import com.yx.earthworm.session.Configuration;

/**
 * 
 * @author yangxin 2019年3月18日 下午7:23:47
 */
public class StaticSourceTest {
  
  private Configuration configuration;
  
  @Before
  public void setUp() {
    configuration = new Configuration();
  }

  @Test
  public void sqlSourceBuilder_test(){
    SqlSourceBuilder ssb = new SqlSourceBuilder(configuration);
    Map<String, String> param = new HashMap<String, String>(){
      {
        put("aac001", "1");
      }
    };
    String sql = "select aac002,aac001 from ac01 where aac001 = #{aac001, javaType=java.lang.String}";
    SqlSource ss = ssb.parse(sql, String.class,param);
    BoundSql bs = ss.getBoundSql(param);
    System.out.println(bs.getSql());
  }  
}
