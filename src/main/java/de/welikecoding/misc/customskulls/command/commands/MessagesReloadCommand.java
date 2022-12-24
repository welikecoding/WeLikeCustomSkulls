package de.welikecoding.misc.customskulls.command.commands;

import de.welikecoding.misc.customskulls.command.Command;
import de.welikecoding.misc.customskulls.message.Message;
import de.welikecoding.misc.customskulls.message.MessageBuilder;
import org.bukkit.command.CommandSender;

public class MessagesReloadCommand extends Command {

    public MessagesReloadCommand() {
        super(
                "csreload:messages",
                new String[]{"csr:messages", "csr:msg"},
                "Reload the messages file.",
                "/csreload:messages",
                "customskulls.reload.messages",
                false
        );
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        main.getMessageFileManager().reload();
        new MessageBuilder(Message.MESSAGE_FILE_RELOADED).send(sender);

    }
}
