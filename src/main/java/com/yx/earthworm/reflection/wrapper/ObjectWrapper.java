package com.yx.earthworm.reflection.wrapper;
/**
 * 
 * @author yangxin 2019年2月14日 下午3:07:59
 */
public interface ObjectWrapper {

  Object get(String propertyName);
  
  void set(String propertyName, Object val);
  
  void add(Object val);
  
  Class<?> getGetterType(String name);
  
  boolean hasSetter(String name);
  
}
