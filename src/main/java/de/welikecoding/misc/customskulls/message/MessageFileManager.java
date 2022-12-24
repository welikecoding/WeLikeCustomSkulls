package de.welikecoding.misc.customskulls.message;

import de.welikecoding.misc.customskulls.CustomSkulls;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class MessageFileManager {

    private final File file = new File("plugins/CustomSkulls/messages.yml");
    private Configuration configuration ;

    public MessageFileManager(CustomSkulls main) {
        if(!file.exists()) {
            main.saveResource(file.getName(), false);
        }
        configuration = YamlConfiguration.loadConfiguration(file);
    }

    public String getMessage(Message message) {
        return configuration.getString(message.name());
    }

    public void reload() {
        configuration = YamlConfiguration.loadConfiguration(file);
    }

}
