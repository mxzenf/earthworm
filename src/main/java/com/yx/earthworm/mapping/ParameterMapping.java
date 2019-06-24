package com.yx.earthworm.mapping;

import com.yx.earthworm.session.Configuration;
import com.yx.earthworm.type.JdbcType;
import com.yx.earthworm.type.TypeHandler;

/**
 * 
 * @author yangxin 2019年2月27日 下午1:50:44
 */
public class ParameterMapping {

  private Configuration configuration;
  private ParameterMode mode;
  private String property;
  private Class<?> javaType;
  private JdbcType jdbcType;
  private TypeHandler<?> typeHandler;

  private ParameterMapping() {
  }

  public static class Builder {
    private ParameterMapping parameterMapping = new ParameterMapping();
    
    public Builder(Configuration configuration) {
      parameterMapping.configuration = configuration;
    }

    public Builder mode(ParameterMode mode) {
      parameterMapping.mode = mode;
      return this;
    }

    public Builder javaType(Class<?> javaType) {
      parameterMapping.javaType = javaType;
      return this;
    }

    public Builder jdbcType(JdbcType jdbcType) {
      parameterMapping.jdbcType = jdbcType;
      return this;
    }

    public Builder typeHandler(TypeHandler<?> typeHandler) {
      parameterMapping.typeHandler = typeHandler;
      return this;
    }

    public ParameterMapping build(String property) {
      parameterMapping.property = property;
      resolveTypeHandler();
      return parameterMapping;
    }

    private void resolveTypeHandler() {
      if (null == parameterMapping.typeHandler && null != parameterMapping.javaType) {
        parameterMapping.typeHandler = parameterMapping.configuration.getTypeHandlerRegistry()
            .getTypeHandler(parameterMapping.javaType);
      }
    }
  }

  public ParameterMode getMode() {
    return mode;
  }

  public void setMode(ParameterMode mode) {
    this.mode = mode;
  }

  public String getProperty() {
    return property;
  }

  public void setProperty(String property) {
    this.property = property;
  }

  public Class<?> getJavaType() {
    return javaType;
  }

  public void setJavaType(Class<?> javaType) {
    this.javaType = javaType;
  }

  public JdbcType getJdbcType() {
    return jdbcType;
  }

  public void setJdbcType(JdbcType jdbcType) {
    this.jdbcType = jdbcType;
  }

  public TypeHandler<?> getTypeHandler() {
    return typeHandler;
  }

  public void setTypeHandler(TypeHandler<?> typeHandler) {
    this.typeHandler = typeHandler;
  }

}
