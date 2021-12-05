package uk.co.breadhub.oriapi.api.database;

public interface IDBConnectionPool {

    void newConnectionPool(String pluginName, String Username, String Password, String HostName, int Port, String DatabaseName);

    void newConnectionPoolWithSize(String pluginName, String Username, String Password, String HostName, int Port, String DatabaseName, int maxPoolSize);
}
