package uk.co.breadhub.oriapi.api.modules.mysql;

import uk.co.breadhub.oriapi.api.database.DataSourcePoolObject;
import uk.co.breadhub.oriapi.api.database.IDBConnectionPool;
import uk.co.breadhub.oriapi.api.database.IDBConnectionPoolImpl;

import java.util.HashMap;

public class IDBPoolManagerImpl implements IDBPoolManager {

    public static IDBConnectionPool connectionPoolApi = new IDBConnectionPoolImpl();
    private final HashMap<String, DataSourcePoolObject> pools = new HashMap<>();

    @Override
    public void shutdown() {
        for (DataSourcePoolObject dspo : getPools().values()) {
            dspo.getComboPooledDataSource().close();
        }
    }

    @Override
    public IDBConnectionPool getConnectionPoolApi() {
        return connectionPoolApi;
    }

    @Override
    public HashMap<String, DataSourcePoolObject> getPools() {
        return pools;
    }
}
