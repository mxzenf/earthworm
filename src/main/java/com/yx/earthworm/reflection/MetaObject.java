package com.yx.earthworm.reflection;

import java.util.Collection;
import java.util.Map;

import com.yx.earthworm.reflection.wrapper.BeanWrapper;
import com.yx.earthworm.reflection.wrapper.CollectionWrapper;
import com.yx.earthworm.reflection.wrapper.MapWrapper;
import com.yx.earthworm.reflection.wrapper.ObjectWrapper;

/**
 * 
 * @author yangxin 2019年2月14日 下午3:01:30
 */
public class MetaObject {

  private Object originalObject;
  private ObjectWrapper objectWrapper;

  private MetaObject(Object obj) {
    this.originalObject = obj;
    if (obj instanceof ObjectWrapper) {
      this.objectWrapper = (ObjectWrapper) obj;
    } else if (obj instanceof Map) {
      this.objectWrapper = new MapWrapper((Map) obj);
    } else if (obj instanceof Collection) {
      this.objectWrapper = new CollectionWrapper((Collection) obj);
    } else {
      this.objectWrapper = new BeanWrapper(obj);
    }
  }

  public static MetaObject forObject(Object obj) {
    return new MetaObject(obj);
  }

  public Object getValue(String propertyName) {
    return objectWrapper.get(propertyName);
  }

  public void setValue(String propertyName, Object val) {
    objectWrapper.set(propertyName, val);
  }

  public void add(Object o) {
    objectWrapper.add(o);
  }

  public Object getOriginalObject() {
    return originalObject;
  }

  public void setOriginalObject(Object originalObject) {
    this.originalObject = originalObject;
  }

  public Class<?> getGetterType(String name) {
    return objectWrapper.getGetterType(name);
  }
  
  public boolean hasSetter(String name) {
    return objectWrapper.hasSetter(name);
  }

}
