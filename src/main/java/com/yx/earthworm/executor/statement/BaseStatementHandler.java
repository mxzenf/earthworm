package com.yx.earthworm.executor.statement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.yx.earthworm.executor.resultset.ResultSetHandler;
import com.yx.earthworm.mapping.BoundSql;
import com.yx.earthworm.mapping.MappedStatement;
import com.yx.earthworm.session.Configuration;
/**
 * 
 * @author yangxin 2019年5月21日 上午10:32:30
 */
public abstract class BaseStatementHandler implements StatementHandler {
  
  protected Configuration configuration;
  protected BoundSql boundSql;
  protected Object parameterObject;
  
  protected ResultSetHandler resultSetHandler;
  private ParameterHandler parameterHandler;
  
  public BaseStatementHandler(){}
  
  public BaseStatementHandler(Configuration configuration, BoundSql boundSql, Object parameterObject, MappedStatement mappedStatement){
    this.configuration = configuration;
    this.boundSql = boundSql;
    this.parameterObject = parameterObject;
    this.resultSetHandler = configuration.newResultSetHandler(boundSql, mappedStatement);
    this.parameterHandler = new DefaultParameterHandler(configuration, boundSql, parameterObject);
  }

  @Override
  public Statement prepare(Connection connection) throws SQLException {
    String sql = boundSql.getSql();
    return connection.prepareStatement(sql);
  }

  @Override
  public void parameterize(Statement statement) throws SQLException {
    parameterHandler.setParameters((PreparedStatement)statement);
  }

  @Override
  public <E> List<E> query(Statement statement) throws SQLException {
    return doQuery(statement);
  }
  
  public abstract <E> List<E> doQuery(Statement statement) throws SQLException;

}
