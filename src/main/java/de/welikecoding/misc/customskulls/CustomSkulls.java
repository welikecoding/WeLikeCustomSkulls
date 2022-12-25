package de.welikecoding.misc.customskulls;

import de.welikecoding.misc.customskulls.command.commands.CustomSkullCommand;
import de.welikecoding.misc.customskulls.command.commands.CustomSkullReloadCommand;
import de.welikecoding.misc.customskulls.command.commands.MessagesReloadCommand;
import de.welikecoding.misc.customskulls.command.commands.PlayerSkullCommand;
import de.welikecoding.misc.customskulls.customs.CustomSkullFileManager;
import de.welikecoding.misc.customskulls.message.MessageFileManager;
import de.welikecoding.misc.customskulls.utils.VersionChecker;
import lombok.Getter;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public final class CustomSkulls extends JavaPlugin {

    @Getter private static CustomSkulls instance;

    @Getter private MessageFileManager messageFileManager;
    @Getter private CustomSkullFileManager customSkullFileManager;

    @Override
    public void onLoad() {
        instance = this;
        messageFileManager = new MessageFileManager(this);
        customSkullFileManager = new CustomSkullFileManager(this);
    }


    @Override
    public void onEnable() {

        customSkullFileManager.loadSkulls();

        new PlayerSkullCommand();
        new MessagesReloadCommand();
        new CustomSkullCommand();
        new CustomSkullReloadCommand();

        Metrics metrics = new Metrics(this, 17176);

        new VersionChecker().checkForUpdate();

    }


}
