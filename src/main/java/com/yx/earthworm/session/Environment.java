package com.yx.earthworm.session;
/*
 * 
 */

import javax.sql.DataSource;


import com.yx.earthworm.transaction.TransactionFactory;
/**
 * 
 * @author yangxin 2019年4月20日 下午6:40:00
 */
public class Environment {

  private TransactionFactory transactionFactory;
  private DataSource dataSource;
  
  private Environment(){}
  
  public static class Builder{
    Environment environment = new Environment();
    
    public Builder transactionFactory(TransactionFactory transactionFactory){
      environment.transactionFactory = transactionFactory;
      return this;
    }
    
    public Builder dataSource(DataSource dataSource){
      environment.dataSource = dataSource;
      return this;
    }
    
    public Environment build(){
      assert null != environment.dataSource;
      assert null != environment.transactionFactory;
      return environment;
    }
  }

  public TransactionFactory getTransactionFactory() {
    return transactionFactory;
  }

  public void setTransactionFactory(TransactionFactory transactionFactory) {
    this.transactionFactory = transactionFactory;
  }

  public DataSource getDataSource() {
    return dataSource;
  }

  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
  }
  
}
