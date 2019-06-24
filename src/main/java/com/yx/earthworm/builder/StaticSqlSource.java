package com.yx.earthworm.builder;

import java.util.List;

import com.yx.earthworm.mapping.BoundSql;
import com.yx.earthworm.mapping.ParameterMapping;
import com.yx.earthworm.mapping.SqlSource;
import com.yx.earthworm.session.Configuration;
/**
 * 
 * @author yangxin 2019年2月27日 下午1:58:55
 */
public class StaticSqlSource implements SqlSource {

  private List<ParameterMapping> parameterMappings;
  private String sql;
  private Configuration configuration;
  
  public StaticSqlSource(Configuration configuration, String sql, List<ParameterMapping> parameterMappings) {
    this.sql = sql;
    this.parameterMappings = parameterMappings;
    this.configuration = configuration;
  }

  @Override
  public BoundSql getBoundSql(Object parameterObject) {
    return new BoundSql(configuration, sql, parameterMappings, parameterObject);
  }

}
