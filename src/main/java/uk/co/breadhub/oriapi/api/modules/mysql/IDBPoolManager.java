package uk.co.breadhub.oriapi.api.modules.mysql;

import uk.co.breadhub.oriapi.api.database.DataSourcePoolObject;
import uk.co.breadhub.oriapi.api.database.IDBConnectionPool;

import java.util.HashMap;

public interface IDBPoolManager {
    IDBConnectionPool getConnectionPoolApi();

    HashMap<String, DataSourcePoolObject> getPools();

    void shutdown();
}
