package de.welikecoding.misc.customskulls.command.commands;

import de.welikecoding.misc.customskulls.command.Command;
import de.welikecoding.misc.customskulls.message.Message;
import de.welikecoding.misc.customskulls.message.MessageBuilder;
import de.welikecoding.misc.customskulls.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class PlayerSkullCommand extends Command {

    public PlayerSkullCommand() {
        super(
                "playerskull",
                new String[]{"pskull", "ps"},
                "Easy way to get any player head you want!",
                "/playerskull <player> (target)",
                "customskulls.ps.use",
                false
        );
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if(args.length == 0 || args.length > 2) {
            new MessageBuilder(Message.COMMAND_USAGE).send(sender);
            return;
        }

        OfflinePlayer owner = Bukkit.getOfflinePlayer(args[0]);

        if(owner.getName() == null) {
            return;
        }

        ItemStack playerSkull = new ItemBuilder(Material.PLAYER_HEAD, 1).build();
        SkullMeta playerSkullMeta = (SkullMeta) playerSkull.getItemMeta();
        if (playerSkullMeta != null) {
            playerSkullMeta.setOwningPlayer(owner);
            playerSkullMeta.setDisplayName(new MessageBuilder(Message.PLAYER_HEAD_DISPLAYNAME).withOwner(owner).buildWithoutPrefix());
        }
        playerSkull.setItemMeta(playerSkullMeta);

        if(args.length == 1 && (!(sender instanceof Player))) {
            new MessageBuilder(Message.REQUIRES_PLAYER).send(sender);
        } else if(args.length == 1) {

            Player player = (Player) sender;

            player.getInventory().addItem(playerSkull);
            new MessageBuilder(Message.PLAYER_HEAD_CREATED).withOwner(owner).send(sender);

        } else {

            Player target = Bukkit.getPlayerExact(args[1]);
            if(target == null || !target.isOnline()) {
                new MessageBuilder(Message.TARGET_NOT_ONLINE).withTarget(args[1]).send(sender);
                return;
            }

            target.getInventory().addItem(playerSkull);
            new MessageBuilder(Message.PLAYER_HEAD_SENT).withTarget(target).withOwner(owner).send(sender);
            new MessageBuilder(Message.PLAYER_HEAD_RECEIVED).withSender(sender).withOwner(owner).send(target);

        }

    }
}
