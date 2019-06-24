package com.yx.earthworm.mapping;

import java.util.List;

import com.yx.earthworm.session.Configuration;

/**
 * 
 * @author yangxin 2019年2月27日 下午1:49:30
 */
public class BoundSql {

  private String sql;
  private List<ParameterMapping> parameterMappings;
  private Object parameterObject;
  
  public BoundSql(Configuration configuration,String sql, List<ParameterMapping> parameterMappings, Object parameterObject) {
    this.sql = sql;
    this.parameterMappings = parameterMappings;
    this.parameterObject = parameterObject;
  }

  public String getSql() {
    return sql;
  }

  public void setSql(String sql) {
    this.sql = sql;
  }

  public List<ParameterMapping> getParameterMappings() {
    return parameterMappings;
  }

  public void setParameterMappings(List<ParameterMapping> parameterMappings) {
    this.parameterMappings = parameterMappings;
  }

  public Object getParameterObject() {
    return parameterObject;
  }

  public void setParameterObject(Object parameterObject) {
    this.parameterObject = parameterObject;
  }
  
  
}
