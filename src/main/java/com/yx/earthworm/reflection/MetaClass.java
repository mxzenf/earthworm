package com.yx.earthworm.reflection;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.yx.earthworm.reflection.invoker.Invoker;
import com.yx.earthworm.reflection.invoker.MethodInvoker;

/**
 * 
 * @author yangxin 2019年2月18日 下午3:50:20
 */
public class MetaClass {
  
  private Class<?> type;
  private Map<String, Invoker> getInvokers = new HashMap<String, Invoker>();
  private Map<String, Invoker> setInvokers = new HashMap<String, Invoker>();

  private MetaClass(Class<?> type){
    this.type = type;
    addGetAndSetMethods(type);
  }
  
  public boolean hasSetter(String name) {
    return setInvokers.containsKey(name);
  }
  
  private void addGetAndSetMethods(Class<?> clazz) {
    for (Method method : clazz.getMethods()) {
      String name = method.getName();
      
      if (name.startsWith("get")) {
        if (method.getParameterTypes() == null || method.getParameterTypes().length == 0) {
          addGetMethod(name.substring(3).toLowerCase(), method);
        }
      } else if (name.startsWith("set")) {
        if (method.getParameterTypes().length == 1) {
          addSetMethod(name.substring(3).toLowerCase(), method);
        }
      }
    }
    
  }

  private void addGetMethod(String name, Method method) {
    getInvokers.put(name, new MethodInvoker(method));
  }
  
  private void addSetMethod(String name, Method method) {
    setInvokers.put(name, new MethodInvoker(method));
  }

  public static MetaClass forClass(Class<?> clazz){
    return new MetaClass(clazz);
  }
  
  public Invoker getGetInvoker(String propertyName){
    return getInvokers.get(propertyName);
  }
  
  public Invoker getSetInvoker(String propertyName){
    return setInvokers.get(propertyName);
  }
}
