package com.yx.earthworm.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;

/**
 * 资源读取类
 * @author yangxin 2019年1月5日 下午7:56:59
 */
public class Resources {
  private static ClassLoaderWrapper classLoaderWrapper = new ClassLoaderWrapper();
  
  public static InputStream getResourceAsStream(String name){
    return classLoaderWrapper.getResourceAsStream(name);
  }
  
  public static Reader getResourceAsReader(String name){
    return new InputStreamReader(getResourceAsStream(name));
  }
  
  public static Properties getResourceAsProperties(String name) throws IOException{
    Properties properties = new Properties();
    properties.load(getResourceAsReader(name));
    return properties;
  }
  
  public static Class<?> classForName(String className){
    return classLoaderWrapper.classForName(className);
  }
}
