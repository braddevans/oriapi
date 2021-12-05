package uk.co.breadhub.oriapi.utils;

import org.bukkit.ChatColor;

public class loggingUtil {
    public static void doLog(String logItem, boolean isError) {
        if (!isError) {
            System.out.println(
                    ChatColor.translateAlternateColorCodes('&', "&7[&5OriAPI&7] &2[LOG] &6" + logItem + "&r")
            );
        } else {
            System.out.println(
                    ChatColor.translateAlternateColorCodes('&', "&7[&5OriAPI&7] &4[ERROR] &6" + logItem + "&r")
            );
        }
    }
}