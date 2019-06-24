package com.yx.earthworm.executor.statement;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.yx.earthworm.mapping.BoundSql;
import com.yx.earthworm.mapping.MappedStatement;
import com.yx.earthworm.session.Configuration;

public class DefaultStatementHandler extends BaseStatementHandler {
  
  public DefaultStatementHandler(Configuration configuration, BoundSql boundSql, Object parameterObject, MappedStatement mappedStatement){
    super(configuration, boundSql, parameterObject, mappedStatement);
  }

  @Override
  public <E> List<E> doQuery(Statement statement) throws SQLException {
    PreparedStatement ps = (PreparedStatement) statement;
    ps.execute();
    return resultSetHandler.handleResultSet(statement);
  }

}
