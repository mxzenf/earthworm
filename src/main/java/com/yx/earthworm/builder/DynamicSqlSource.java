package com.yx.earthworm.builder;

import com.yx.earthworm.mapping.BoundSql;
import com.yx.earthworm.mapping.SqlSource;
import com.yx.earthworm.session.Configuration;

/**
 * 
 * @author yangxin 2019年3月12日 下午8:21:57
 */
public class DynamicSqlSource implements SqlSource {

  private Configuration configuration;
  private String sql;

  public DynamicSqlSource(Configuration configuration, String sql) {
    this.configuration = configuration;
    this.sql = sql;
  }

  @Override
  public BoundSql getBoundSql(Object parameterObject) {
    SqlSourceBuilder sb = new SqlSourceBuilder(configuration);
    return sb.parse(sql, null == parameterObject ? Object.class : parameterObject.getClass(), parameterObject)
        .getBoundSql(parameterObject);
  }

}
