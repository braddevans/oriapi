package uk.co.breadhub.oriapi.api.database;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import lombok.Data;
import uk.co.breadhub.oriapi.utils.loggingUtil;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;

// DataSource Pool Object Not a PoolToy Object
@Data
public class DataSourcePoolObject {

    // jdbc:mysql://
    private String JDBCConnectionUrlBase = "jdbc:";
    private String JDBCConnectionUrl = "";

    // Creds
    private String PluginName;
    private String DBUsername;
    private String DBPassword;
    private String DBHostname;
    private String DBName;
    private int DBPort = 3306;
    private int maxPoolSize = 20;
    private ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
    private Savepoint jdbcConnSavePoint;

    public DataSourcePoolObject(String pluginName, String username, String password, String hostname, int port, String databaseName) {
        this.PluginName = pluginName;
        this.DBUsername = username;
        this.DBPassword = password;
        this.DBHostname = hostname;
        this.DBPort = port;
        this.DBName = databaseName;

        try {
            setupPoolObj();
        } catch (Exception e) {
            loggingUtil.doLog(e.getMessage(), true);
        }
    }

    public DataSourcePoolObject(String pluginName, String username, String password, String hostname, int port, String databaseName, int maxConnPoolSize) {
        this.PluginName = pluginName;
        this.DBUsername = username;
        this.DBPassword = password;
        this.DBHostname = hostname;
        this.DBPort = port;
        this.DBName = databaseName;
        this.maxPoolSize = maxConnPoolSize;

        try {
            setupPoolObj();
        } catch (Exception e) {
            loggingUtil.doLog(e.getMessage(), true);
        }
    }

    // Required to be run
    public void setupPoolObj() throws PropertyVetoException {
        this.comboPooledDataSource.setDriverClass("com.mysql.jdbc.Driver");
        this.JDBCConnectionUrl = (this.getJDBCConnectionUrlBase() + "mysql://" + this.DBHostname + ":" + this.DBPort + "/" + this.DBName);
        this.comboPooledDataSource.setJdbcUrl(this.JDBCConnectionUrl);
        this.comboPooledDataSource.setUser(this.DBUsername);
        this.comboPooledDataSource.setPassword(this.DBPassword);
        this.comboPooledDataSource.setMaxPoolSize(this.maxPoolSize);
        this.comboPooledDataSource.setInitialPoolSize(5);
        this.comboPooledDataSource.setMinPoolSize(5);
        this.comboPooledDataSource.setAcquireIncrement(5);
    }

    public Connection getConnection() throws SQLException {
        return this.comboPooledDataSource.getConnection();
    }

    public void undoLastExecute() {
        try {
            getConnection().rollback(this.jdbcConnSavePoint);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void undoUsingCustomSavepoint(Savepoint point) {
        try {
            getConnection().rollback(point);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setCustomSavepointNow() {
        try {
            this.jdbcConnSavePoint = getConnection().setSavepoint();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ComboPooledDataSource getDataSource() {
        return this.comboPooledDataSource;
    }

    @Override
    public String toString() {
        return "DataSourcePoolObject{" +
                "JDBCConnectionUrlBase='" + JDBCConnectionUrlBase + '\'' +
                ", JDBCConnectionUrl='" + JDBCConnectionUrl + '\'' +
                ", PluginName='" + PluginName + '\'' +
                ", DBUsername='" + DBUsername + '\'' +
                ", DBPassword='" + DBPassword + '\'' +
                ", DBHostname='" + DBHostname + '\'' +
                ", DBName='" + DBName + '\'' +
                ", DBPort=" + DBPort +
                ", maxPoolSize=" + maxPoolSize +
                ", comboPooledDataSource=" + comboPooledDataSource +
                ", jdbcConnSavePoint=" + jdbcConnSavePoint +
                '}';
    }
}