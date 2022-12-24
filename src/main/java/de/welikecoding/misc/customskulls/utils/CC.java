package de.welikecoding.misc.customskulls.utils;

import net.md_5.bungee.api.ChatColor;

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

}