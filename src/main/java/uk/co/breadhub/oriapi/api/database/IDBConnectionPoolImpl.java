package uk.co.breadhub.oriapi.api.database;

import uk.co.breadhub.oriapi.api.modules.mysql.IDBPoolManager;
import uk.co.breadhub.oriapi.api.modules.mysql.IDBPoolManagerImpl;
import uk.co.breadhub.oriapi.utils.loggingUtil;

import java.sql.SQLException;

public class IDBConnectionPoolImpl implements IDBConnectionPool {

    public static IDBPoolManager idpm = new IDBPoolManagerImpl();

    @Override
    public void newConnectionPool(String pluginName, String Username, String Password, String HostName, int Port, String DatabaseName) {
        idpm.getPools().putIfAbsent(pluginName, new DataSourcePoolObject(pluginName, Username, Password, HostName, Port, DatabaseName));
        try {
            loggingUtil.doLog(String.format("Pool Created for &7[&d%s&7]&6, Status: %s", pluginName, idpm.getPools().get(pluginName).getConnection().isClosed() ? "&4Offline" : "&aOnline"), false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void newConnectionPoolWithSize(String pluginName, String Username, String Password, String HostName, int Port, String DatabaseName, int maxPoolSize) {
        idpm.getPools().putIfAbsent(pluginName, new DataSourcePoolObject(pluginName, Username, Password, HostName, Port, DatabaseName, maxPoolSize));
        try {
            loggingUtil.doLog(String.format("Pool with size %s Created for &7[&d%s&7]&6, Status: %s", maxPoolSize, pluginName, idpm.getPools().get(pluginName).getConnection().isClosed() ? "&4Offline" : "&aOnline"), false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}