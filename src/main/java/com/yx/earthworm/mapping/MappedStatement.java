package com.yx.earthworm.mapping;

import com.yx.earthworm.builder.ResultMap;
import com.yx.earthworm.session.Configuration;

/**
 * 
 * @author yangxin 2019年2月12日 下午1:56:28
 */
public final class MappedStatement {

  private String id;
  private SqlCommandType sqlCommandType;
  private StatementType statementType;
  private SqlSource sqlSource;
  private Configuration configuration;
  private ResultMap resultMap;
  private ParameterMap parameterMap;
  
  MappedStatement(){}
  
  public static class Builder{
    MappedStatement mappedStatement = new MappedStatement();
    
    public Builder(String id, SqlSource sqlSource, SqlCommandType sqlCommandType, Configuration configuration) {
      mappedStatement.id = id;
      mappedStatement.sqlSource = sqlSource;
      mappedStatement.sqlCommandType = sqlCommandType;
      mappedStatement.configuration = configuration;
    }
    
    public Builder resultMap(ResultMap resultMap){
      mappedStatement.resultMap = resultMap;
      return this;
    }
    
    public Builder statementType(StatementType statementType){
      mappedStatement.statementType = statementType;
      return this;
    }
    
    public Builder parameterMap(ParameterMap parameterMap){
      mappedStatement.parameterMap = parameterMap;
      return this;
    }
    
    public MappedStatement build(){
      return mappedStatement;
    }
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public SqlCommandType getSqlCommandType() {
    return sqlCommandType;
  }

  public void setSqlCommandType(SqlCommandType sqlCommandType) {
    this.sqlCommandType = sqlCommandType;
  }

  public StatementType getStatementType() {
    return statementType;
  }

  public void setStatementType(StatementType statementType) {
    this.statementType = statementType;
  }

  public SqlSource getSqlSource() {
    return sqlSource;
  }

  public void setSqlSource(SqlSource sqlSource) {
    this.sqlSource = sqlSource;
  }

  public Configuration getConfiguration() {
    return configuration;
  }

  public void setConfiguration(Configuration configuration) {
    this.configuration = configuration;
  }

  public ResultMap getResultMap() {
    return resultMap;
  }

  public void setResultMap(ResultMap resultMap) {
    this.resultMap = resultMap;
  }

  public ParameterMap getParameterMap() {
    return parameterMap;
  }

  public void setParameterMap(ParameterMap parameterMap) {
    this.parameterMap = parameterMap;
  }
  
  
  
}
