package de.welikecoding.misc.customskulls.utils;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CC {

    private static final Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");

    public static String translate(String input) {
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            String color = input.substring(matcher.start(), matcher.end());
            input = input.replace(color, ChatColor.of(color) + "");
            matcher = pattern.matcher(input);
        }
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    public static void log(LogType logType, String input) {
        String prefix = "[WeLikeCustomSkulls] ";
        switch (logType) {
            case INFO:
                Bukkit.getConsoleSender().sendMessage(CC.translate(prefix + input));
                break;
            case SUCCESS:
                Bukkit.getConsoleSender().sendMessage(CC.translate(prefix + "&a" + input));
                break;
            case WARNING:
                Bukkit.getConsoleSender().sendMessage(CC.translate(prefix + "&e" + input));
                break;
            case ERROR:
                Bukkit.getConsoleSender().sendMessage(CC.translate(prefix + "&c" + input));
                break;
            case FATAL:
                Bukkit.getConsoleSender().sendMessage(CC.translate(prefix + "&c&l" + input));
        }
    }

}