package com.yx.earthworm.datasource.druid;

import java.util.Properties;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSource;
import com.yx.earthworm.datasource.DataSourceFactory;
import com.yx.earthworm.reflection.MetaObject;

public class DruidDataSourceFactory implements DataSourceFactory {
  
  private DruidDataSource dataSource;
  
  public DruidDataSourceFactory(){
    dataSource = new DruidDataSource();
  }

  @Override
  public DataSource getDataSource() {
    return dataSource;
  }

  @Override
  public void setProperties(Properties props) {

    dataSource.configFromPropety(props);
  }

}
