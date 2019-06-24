package com.yx.earthworm.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DriverManagerTest extends BaseJdbcTest {

  @Before
  public void setUp() {
    super.setUp();
  }
  /**
   * 测试配置文件是否加载成功
   */
  @Test
  public void properties_test(){
    Assert.assertNotNull(properties);
  }
  /**
   * connection是否成功建立
   */
  @Test
  public void connection_test(){
    Connection connection = null;
    try {
      connection = DriverManager.getConnection(properties.getProperty("jdbc.url"), properties.getProperty("jdbc.username"), properties.getProperty("jdbc.password"));
      Assert.assertNotNull(connection);
      System.out.println("当前连接是否关闭：" + connection.isClosed());
      System.out.println("数据库默认类型对应的jave类别：" + connection.getTypeMap());
      System.out.println("是否自动提交：" + connection.getAutoCommit());
      System.out.println("当前连接是否有效：" + connection.isValid(1));
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      if (null != connection) {
        try {
          connection.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
