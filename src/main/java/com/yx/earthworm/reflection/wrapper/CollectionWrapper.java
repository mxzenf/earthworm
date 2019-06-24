package com.yx.earthworm.reflection.wrapper;

import java.util.Collection;

public class CollectionWrapper implements ObjectWrapper {
  
  private Collection<Object> collection;
  
  public CollectionWrapper(Collection<Object> collection) {
    this.collection = collection;
  }

  @Override
  public Object get(String propertyName) {
    throw new UnsupportedOperationException("不支持该方法");
  }

  @Override
  public void set(String propertyName, Object val) {
    throw new UnsupportedOperationException("不支持该方法");
  }

  @Override
  public void add(Object val) {
    collection.add(val);
  }

  @Override
  public Class<?> getGetterType(String name) {
    throw new UnsupportedOperationException("集合不支持此操作");
  }

  @Override
  public boolean hasSetter(String name) {
    throw new UnsupportedOperationException("集合不支持此操作");
  }

}
