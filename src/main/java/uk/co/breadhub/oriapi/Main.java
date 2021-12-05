package uk.co.breadhub.oriapi;

import org.bukkit.plugin.java.JavaPlugin;
import uk.co.breadhub.oriapi.api.database.DataSourcePoolObject;
import uk.co.breadhub.oriapi.api.modules.mysql.IDBPoolManager;
import uk.co.breadhub.oriapi.api.modules.mysql.IDBPoolManagerImpl;
import uk.co.breadhub.oriapi.utils.loggingUtil;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public final class Main extends JavaPlugin {

    //Origami Api

    //parameters
    public static Main instance;
    public static IDBPoolManager iDBpoolManager = new IDBPoolManagerImpl();
    public static HashMap<String, Boolean> activeModules = new HashMap<>();

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        if (!new File(getDataFolder() + "config.yml").exists()) {
            saveDefaultConfig();
        }


        modules();
        loggingUtil.doLog("API Now: &aOnline", false);

    }

    private void modules() {
        List<String> modules = getConfig().getStringList("plugin.modules");
        for (String module : modules) {
            if (getConfig().getBoolean("plugin.modules." + module + ".enabled")) {
                activeModules.putIfAbsent(module, true);
            }
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        iDBpoolManager.shutdown();
    }
}
