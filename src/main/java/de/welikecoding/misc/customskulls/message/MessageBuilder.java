package de.welikecoding.misc.customskulls.message;

import de.welikecoding.misc.customskulls.CustomSkulls;
import de.welikecoding.misc.customskulls.utils.CC;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageBuilder {

    private final CustomSkulls main = CustomSkulls.getInstance();
    private String messageString;

    public MessageBuilder(Message message) {
        messageString = main.getMessageFileManager().getMessage(message);
    }

    public MessageBuilder withSender(CommandSender sender) {
        if(sender instanceof Player) {
            messageString = messageString.replace("{SENDER}", sender.getName());
        } else {
            messageString = messageString.replace("{SENDER}", main.getMessageFileManager().getMessage(Message.CONSOLE_NAME));
        }
        return this;
    }

    public MessageBuilder with(String target, String replacement) {
        messageString = messageString.replace(target, replacement);
        return this;
    }

    public String build() {
        return CC.translate(main.getMessageFileManager().getMessage(Message.PREFIX) + messageString);
    }

    public String buildWithoutPrefix() {
        messageString = messageString.replace(main.getMessageFileManager().getMessage(Message.PREFIX), "");
        return CC.translate(messageString);
    }

    public void send(CommandSender sender) {
        sender.sendMessage(build());
    }

    public void send(Player player) {
        player.sendMessage(build());
    }

}
