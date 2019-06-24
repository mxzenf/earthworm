package com.yx.earthworm.type;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author yangxin 2019年3月9日 下午2:17:42
 */
public class TypeHandlerRegistry {

  private final static Map<Type, Map<JdbcType, TypeHandler<?>>> TYPE_HANDLER_MAP = new HashMap<>();
  private final static Map<JdbcType, TypeHandler<?>> JDBC_TYPE_HANDLER_MAP = new HashMap<JdbcType, TypeHandler<?>>();
  private static TypeHandler<Object> UNKNOW_TYPE_HANDLER;
  private final Map<Class<?>, TypeHandler<?>> ALL_TYPE_HANDLERS_MAP = new HashMap<Class<?>, TypeHandler<?>>();

  public TypeHandlerRegistry() {
    UNKNOW_TYPE_HANDLER = new UnknowTypeHandler(this);
    register(Object.class, UNKNOW_TYPE_HANDLER);
    
    register(String.class, new StringTypeHandler());
    register(String.class, JdbcType.CHAR, new StringTypeHandler());
    register(String.class, JdbcType.VARCHAR, new StringTypeHandler());
  }

  public void register(Class<?> type, TypeHandler<?> handler) {
    register((Type) type, null, handler);
  }

  public void register(Class<?> type, JdbcType jdbcType, TypeHandler<?> handler) {
    register((Type) type, jdbcType, handler);
  }

  private void register(Type type, JdbcType jdbcType, TypeHandler<?> handler) {
    Map<JdbcType, TypeHandler<?>> map = TYPE_HANDLER_MAP.get(type);
    if (null == map) {
      map = new HashMap<JdbcType, TypeHandler<?>>();
      TYPE_HANDLER_MAP.put(type, map);
    }
    map.put(jdbcType, handler);
    ALL_TYPE_HANDLERS_MAP.put(handler.getClass(), handler);
  }

  public void register(JdbcType jdbcType, TypeHandler<?> handler) {
    JDBC_TYPE_HANDLER_MAP.put(jdbcType, handler);
  }

  public TypeHandler<?> getTypeHandler(Class<?> javaType) {
    return getTypeHandler(javaType, null);
  }

  public TypeHandler<?> getTypeHandler(Class<?> javaType, JdbcType jdbcType) {
    TypeHandler<?> typeHandler = null;

    Map<JdbcType, TypeHandler<?>> jdbcTypeMap = TYPE_HANDLER_MAP.get(javaType);
    if (null != jdbcTypeMap) {
      typeHandler = jdbcTypeMap.get(jdbcType);
      if (null == typeHandler) {
        typeHandler = jdbcTypeMap.get(null);
      }
    }

    return typeHandler;
  }
  
  public TypeHandler<?> getMappingTypeHandler(Class<? extends TypeHandler<?>> handlerType) {
    return ALL_TYPE_HANDLERS_MAP.get(handlerType);
  }

  public <T> TypeHandler<T> getInstance(Class<?> javaType, Class<? extends TypeHandler<?>> handlerType) {
    try {
      if (null == javaType) {
        return (TypeHandler<T>)handlerType.getConstructor().newInstance();
      } else {
        return (TypeHandler<T>)handlerType.getConstructor(Class.class).newInstance(javaType);
      }
      
    } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
        | NoSuchMethodException | SecurityException e) {
      e.printStackTrace();
      throw new UnknownError("");
    }
    
  }
  
  public boolean hasTypeHandler(Class<?> javaType){
    return hasTypeHandler(javaType, null);
  }
  
  public boolean hasTypeHandler(Class<?> javaType, JdbcType jdbcType){
    return null!=javaType && null!=getTypeHandler(javaType, jdbcType);
  }
}
