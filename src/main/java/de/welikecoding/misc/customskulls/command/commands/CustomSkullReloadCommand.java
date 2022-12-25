package de.welikecoding.misc.customskulls.command.commands;

import de.welikecoding.misc.customskulls.command.Command;
import de.welikecoding.misc.customskulls.message.Message;
import de.welikecoding.misc.customskulls.message.MessageBuilder;
import org.bukkit.command.CommandSender;

public class CustomSkullReloadCommand extends Command {

    public CustomSkullReloadCommand() {
        super(
                "csreload:customskulls",
                new String[]{"csr:skulls", "csr:cs"},
                "Reload the custom skulls file.",
                "/csreload:customskulls",
                "customskulls.reload.customskulls",
                false
        );
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        main.getCustomSkullFileManager().reload();
        new MessageBuilder(Message.CUSTOM_SKULLS_FILE_RELOADED).send(sender);

    }
}
