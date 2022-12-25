package de.welikecoding.misc.customskulls.customs;

import de.welikecoding.misc.customskulls.CustomSkulls;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.*;

public class CustomSkullFileManager {

    private final File file = new File("plugins/CustomSkulls/customskulls.yml");
    private Configuration configuration ;

    private final List<String> keys = new ArrayList<>();
    private final Map<String, CustomSkull> customSkulls = new HashMap<>();

    public CustomSkullFileManager(CustomSkulls main) {
        if (!file.exists()) {
            main.saveResource(file.getName(), false);
        }
        configuration = YamlConfiguration.loadConfiguration(file);
    }

    public void loadSkulls() {
        if(configuration.getConfigurationSection("Skulls") == null) {
            return;
        }
        for(String key : Objects.requireNonNull(configuration.getConfigurationSection("Skulls")).getKeys(false)) {
            keys.add(key.toUpperCase());
            customSkulls.put(key.toUpperCase(), new CustomSkull(key));
        }
    }
    public boolean isKeyValid(String key) {
        if(configuration.get("Skulls." + key + ".Value") == null) return false;
        int valueLength = Objects.requireNonNull(configuration.getString("Skulls." + key + ".Value")).length();
        return (valueLength == 176 || valueLength == 180);
    }

    public List<String> getKeys() {
        return keys;
    }

    public Map<String, CustomSkull> getCustomSkulls() {
        return customSkulls;
    }

    public CustomSkull getSkull(String key) {
        if(!customSkulls.containsKey(key.toUpperCase())) {
            keys.add(key.toUpperCase());
            customSkulls.put(key.toUpperCase(), new CustomSkull(key));
        }
        return customSkulls.get(key.toUpperCase());
    }

    public String getValue(String key) {
        return configuration.getString("Skulls." + key + ".Value");
    }

    public void reload() {
        configuration = YamlConfiguration.loadConfiguration(file);
        keys.clear();
        customSkulls.clear();
        loadSkulls();
    }

}
