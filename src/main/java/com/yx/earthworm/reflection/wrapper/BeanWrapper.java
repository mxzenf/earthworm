package com.yx.earthworm.reflection.wrapper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.yx.earthworm.reflection.MetaClass;
import com.yx.earthworm.reflection.invoker.Invoker;

public class BeanWrapper implements ObjectWrapper {

  private final static Object[] NO_ARGUMENTS = new Object[0];
  private Object obj;
  private MetaClass metaClass;

  public BeanWrapper(Object obj) {
    this.obj = obj;
    metaClass = MetaClass.forClass(obj.getClass());
  }

  @Override
  public Object get(String propertyName) {
    return getBeanProperty(propertyName);
  }

  private Object getBeanProperty(String propertyName) {
    try {
      return metaClass.getGetInvoker(propertyName).invoke(obj, NO_ARGUMENTS);
    } catch (Throwable e) {
      throw new RuntimeException("获取属性[" + propertyName + "]错误");
    }
  }

  @Override
  public void set(String propertyName, Object val) {
    try {
      metaClass.getSetInvoker(propertyName).invoke(obj, new Object[] { val });
    } catch (Throwable e) {
      throw new RuntimeException("设置属性[" + propertyName + "]错误");
    }
  }

  @Override
  public void add(Object val) {
    throw new UnsupportedOperationException("不支持该方法");
  }

  @Override
  public Class<?> getGetterType(String name) {
    try {
      Invoker invoker = metaClass.getGetInvoker(name);
      Field method = invoker.getClass().getDeclaredField("method");
      method.setAccessible(true);
      return ((Method)method.get(invoker)).getReturnType();
    } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
      e.printStackTrace();
    } 
    return null;
  }

  @Override
  public boolean hasSetter(String name) {
    return metaClass.hasSetter(name);
  }

}
