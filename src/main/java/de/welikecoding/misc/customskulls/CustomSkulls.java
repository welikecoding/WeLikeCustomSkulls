package de.welikecoding.misc.customskulls;

import de.welikecoding.misc.customskulls.command.commands.PlayerSkullCommand;
import de.welikecoding.misc.customskulls.command.commands.MessagesReloadCommand;
import de.welikecoding.misc.customskulls.message.MessageFileManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class CustomSkulls extends JavaPlugin {

    @Getter private static CustomSkulls instance;
    @Getter private MessageFileManager messageFileManager;

    @Override
    public void onLoad() {
        instance = this;
        messageFileManager = new MessageFileManager(this);
    }

    @Override
    public void onEnable() {
        new PlayerSkullCommand();
        new MessagesReloadCommand();
    }

}
