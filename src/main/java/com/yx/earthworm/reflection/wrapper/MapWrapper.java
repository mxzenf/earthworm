package com.yx.earthworm.reflection.wrapper;

import java.util.Map;
/**
 * 
 * @author yangxin 2019年2月14日 下午3:22:41
 */
public class MapWrapper implements ObjectWrapper {
  
  private Map<String, Object> map;
  
  public MapWrapper(Map<String, Object> map) {
    this.map = map;
  }

  @Override
  public Object get(String propertyName) {
    return map.get(propertyName);
  }

  @Override
  public void set(String propertyName, Object val) {
    map.put(propertyName, val);
  }

  @Override
  public void add(Object val) {
    throw new UnsupportedOperationException("不支持该方法");
  }

  @Override
  public Class<?> getGetterType(String name) {
    if (null != map.get(name)) {
      return map.get(name).getClass();
    }
    return Object.class;
  }

  @Override
  public boolean hasSetter(String name) {
    return true;
  }

}
