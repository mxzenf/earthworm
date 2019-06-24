package com.yx.earthworm.jdbc;

import java.io.IOException;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;
import java.util.Properties;

import com.yx.earthworm.io.Resources;

/**
 * jdbc回顾
 * @author yangxin 2019年1月13日 下午4:20:10
 */
public class BaseJdbcTest {
  
  protected Properties properties;
  private final String JDBC_FILE = "com/yx/earthworm/jdbc/jdbc.properties";
  /**
   * 加载jdbc.properties配置文件
   * @return
   */
  private Properties load_jdbc_properties() {
    try {
      return Resources.getResourceAsProperties(JDBC_FILE);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
  /**
   * 加载驱动
   * @param properties
   */
  private void load_drivers(Properties properties){
    Enumeration<Driver> drivers = DriverManager.getDrivers();
    System.out.println("调用Class.forName前系统存在的drivers：");
    while (drivers.hasMoreElements()) {
      Driver driver = (Driver) drivers.nextElement();
      System.out.println(driver.toString());
    }
    try {
      Class.forName(properties.getProperty("jdbc.driverClassName"));
    } catch (ClassNotFoundException e) {
      throw new RuntimeException("加载驱动出错");
    }
  }
  /**
   * 初始化
   */
  protected void setUp(){
    properties = this.load_jdbc_properties();
    this.load_drivers(properties);
  }
  
}
