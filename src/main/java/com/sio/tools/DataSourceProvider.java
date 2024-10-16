package com.sio.tools;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;

public class DataSourceProvider {
    private static MysqlDataSource instance;
    private static final ConfigManager configManager = new ConfigManager();

    public static DataSource getDataSourceInstance() {
        if (instance == null) {
            instance = new MysqlDataSource();
            instance.setServerName(configManager.getProperty("db.servername"));
            instance.setPort(Integer.parseInt(configManager.getProperty("db.port")));
            instance.setDatabaseName(configManager.getProperty("db.database"));
            instance.setUser(configManager.getProperty("db.user"));
            instance.setPassword(configManager.getProperty("db.password"));
        }
        return instance;
    }
}
