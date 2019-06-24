package com.yx.earthworm.jdbc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import javax.xml.stream.events.EndDocument;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Statement用例
 * 
 * @author yangxin 2019年1月14日 下午1:27:23
 */
public class StatementTest extends BaseJdbcTest {

  private Connection connection;
  private Statement statement;

  @Before
  public void setUp() {
    super.setUp();
    try {
      connection = DriverManager.getConnection(properties.getProperty("jdbc.url"),
          properties.getProperty("jdbc.username"), properties.getProperty("jdbc.password"));
      statement = connection.createStatement();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @After
  public void end() {
    try {
      if (null != connection) {
        connection.close();
      }
      if (null != statement) {
        statement.close();
      }
    } catch (Exception e2) {
      e2.printStackTrace();
    }
  }

  /**
   * 调用存储过程
   */
  @Test
  public void callableStatement_test() {
    CallableStatement cs = null;

    try {
      cs = connection.prepareCall("{call PRC_P_YWSL(?,?,?,?,?,?,?,?)}");
      cs.registerOutParameter(7, Types.VARCHAR);
      cs.registerOutParameter(8, Types.VARCHAR);
      cs.setString(1, "R01000");
      cs.setString(2, "9000039843");
      cs.setString(3, "1");
      cs.setString(4, "sys");
      cs.setString(5, "510399");
      cs.setString(6, "0");
      ResultSet rs = cs.executeQuery();
      System.out.println(cs.getString(7));
      System.out.println(cs.getString(8));
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (null != cs) {
        try {
          cs.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
  }

  /**
   * 写入clob字段
   */
  @Test
  public void blobInsert_test() {
    PreparedStatement ps = null;
    String path = "e:" + File.separator;
    try {
      ps = connection.prepareStatement("INSERT INTO t_20190117(book) VALUES(?)");
      ps.setBinaryStream(1, new FileInputStream(new File(path + "11.pdf")));
      ps.execute();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (null != ps) {
        try {
          ps.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
  }

  /**
   * 读取clob字段
   */
  @Test
  public void blobSlect_test() {
    String path = "e:" + File.separator;
    FileOutputStream fos = null;

    try {
      ResultSet rs = statement.executeQuery("select book from t_20190117 where rownum = 1");
      fos = new FileOutputStream(new File(path + "22.pdf"));
      while (rs.next()) {
        IOUtils.copy(rs.getBinaryStream(1), fos);
      }
    } catch (Exception e) {
    } finally {
      if (null != fos) {
        try {
          fos.flush();
          fos.close();
        } catch (IOException e) {
        }
      }
    }
  }

  @Test
  public void StatementBatch_test() {
    try {
      connection.setAutoCommit(false);
      statement.addBatch("insert into t_20190115(aac002) values('1')");
      statement.addBatch("insert into t_20190115(aac002) values('2')");
      for (int i : statement.executeBatch()) {
        System.out.println("更新行数：" + i);
      }
      Statement statement2 = connection.createStatement();
      statement2.addBatch("insert into t_20190115(aac002) values('3')");
      statement2.addBatch("insert into t_20190115(aac002) values('4')");
      for (int i : statement2.executeBatch()) {
        System.out.println("更新行数：" + i);
      }
      connection.commit();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void simpleStatement_test() {
    try {
      Assert.assertNotNull(statement);
      ResultSet rs = statement.executeQuery("select count(1) as n, 2 as n2, 3 as n3, sysdate as d from dual");
      Assert.assertNotNull(rs);
      while (rs.next()) {
        System.out.println(rs.getInt(1));
        System.out.println(rs.getInt(2));
        System.out.println(rs.getString("n3"));
        System.out.println(rs.getDate("d"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
