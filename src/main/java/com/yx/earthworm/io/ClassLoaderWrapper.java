package com.yx.earthworm.io;

import java.io.InputStream;

/**
 * classloader包装类
 * @author yangxin 2019年1月5日 下午7:29:33
 */
public class ClassLoaderWrapper {

  private ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
  private ClassLoader defaultClassLoader;
  
  ClassLoaderWrapper(){}
  
  ClassLoader[] getClassLoaders(){
    return new ClassLoader[]{
        defaultClassLoader,
        Thread.currentThread().getContextClassLoader(),
        getClass().getClassLoader(),
        systemClassLoader
    };
  }
  
  public Class<?> classForName(String className){
    for (ClassLoader cl : getClassLoaders()) {
      if (null != cl) {
        try {
          Class<?> c = Class.forName(className, true, cl);
          if (null != c) {
            return c;
          }
        } catch (ClassNotFoundException e) {
//          e.printStackTrace();
        }
      }
    }
    return null;
  }
  
  InputStream getResourceAsStream(String name){
    for (ClassLoader cl : getClassLoaders()) {
      if ( null != cl ) {
        InputStream is = cl.getResourceAsStream(name);
        if (null == is) {
          throw new RuntimeException("无法加载指定资源");
        }
        return is;
      }
    }
    return null;
  }
}
