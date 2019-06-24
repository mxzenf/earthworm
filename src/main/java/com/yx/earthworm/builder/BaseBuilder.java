package com.yx.earthworm.builder;

import com.yx.earthworm.io.Resources;
import com.yx.earthworm.mapping.ParameterMode;
import com.yx.earthworm.session.Configuration;
import com.yx.earthworm.type.JdbcType;
import com.yx.earthworm.type.TypeHandler;
import com.yx.earthworm.type.TypeHandlerRegistry;

/**
 * 
 * @author yangxin 2019年2月27日 下午1:45:19
 */
public abstract class BaseBuilder {

  protected final Configuration configuration;
  protected final TypeHandlerRegistry typeHandlerRegistry;

  public BaseBuilder(Configuration configuration) {
    this.configuration = configuration;
    this.typeHandlerRegistry = configuration.getTypeHandlerRegistry();
  }

  protected JdbcType resolveJdbcType(String alias) {
    if (null == alias) {
      return JdbcType.NULL;
    }

    return JdbcType.valueOf(alias);
  }

  protected ParameterMode resolveParameterMode(String alias) {
    if (null == alias) {
      return null;
    }
    return ParameterMode.valueOf(alias);
  }
  
  protected Class<?> resolveClass(String type){
    return Resources.classForName(type);
  }

  protected TypeHandler<?> resolveTypeHandler(Class<?> javaType, Class<? extends TypeHandler<?>> handlerType) {
    if (null == handlerType) {
      return null;
    }
    
    TypeHandler<?> handler = typeHandlerRegistry.getMappingTypeHandler(handlerType);
    if (null == handler) {
      handler = typeHandlerRegistry.getInstance(javaType, handlerType);
    }
    
    return handler;
  }

  public Configuration getConfiguration() {
    return configuration;
  }
  
}
