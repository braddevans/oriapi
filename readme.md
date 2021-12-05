
### Example Code:

plugin.yml

```yaml
depend: [ OriApi ]
```

DatabaseImpl.java

```java
public class DatabaseImpl {
    public static Connection getConnectionOrCreate() throws SQLException {
        if (pools.containsKey(PluginName)) {
            return pools.get(PluginName).getConnection();
        }
        else if (! pools.containsKey(PluginName)) {
            connectionPoolApi.newConnectionPool(PluginName, username, password, hostname, port, database, DatabaseType);
        }

        return pools.get(PluginName).getConnection();
    }

    // Database Methods for inserting getting selecting like normal below
    // I Recommend using PreparedStatements

    // to undo the last database execute run "connectionPoolApi.rollbackDatasourceFromLastExecute()"

    public ResultSet runPreparedStatementSql(String sql) {
        Connection connection;
        PreparedStatement pstmt;
        ResultSet resultSet;
        try {
            connection = getConnectionOrCreate();
            pstmt = connection.prepareStatement(sql);

            resultSet = pstmt.executeQuery();
        }
        catch (Exception e) {
            pools.get(PluginName).undoLastExecute();
            e.printStackTrace();
        }
        return resultSet;
    }
}
```

main class

```java

public final class MainClass extends JavaPlugin {
    // instance of the plugin
    private static MainClass instance;

    public static MainClass getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        // Plugin startup logic

        // create initial pool connection
        try {
            DatabaseImpl.getConnection();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        logingUtil.doLog("Example Plugin Pooling Module Started.", false);
    }
}
```