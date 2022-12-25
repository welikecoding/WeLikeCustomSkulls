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
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

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
            new MessageBuilder(Message.PS_COMMAND_USAGE).send(sender);
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
            playerSkullMeta.setDisplayName(new MessageBuilder(Message.PS_DISPLAYNAME).with("{OWNER}", owner.getName()).buildWithoutPrefix());
        }
        playerSkull.setItemMeta(playerSkullMeta);

        if(args.length == 1 && (!(sender instanceof Player))) {
            new MessageBuilder(Message.REQUIRES_PLAYER).send(sender);
        } else if(args.length == 1) {

            Player player = (Player) sender;

            player.getInventory().addItem(playerSkull);
            new MessageBuilder(Message.PS_CREATED).with("{OWNER}", owner.getName()).send(sender);

        } else {

            Player target = Bukkit.getPlayerExact(args[1]);
            if(target == null || !target.isOnline()) {
                new MessageBuilder(Message.TARGET_NOT_ONLINE).with("{TARGET}", args[1]).send(sender);
                return;
            }

            target.getInventory().addItem(playerSkull);
            new MessageBuilder(Message.PS_SENT).with("{TARGET}", target.getName()).with("{OWNER}", owner.getName()).send(sender);
            new MessageBuilder(Message.PS_RECEIVED).withSender(sender).with("{OWNER}", owner.getName()).send(target);

        }

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {

        if(args.length == 2) {
            List<String> onlinePlayers = new ArrayList<>();
            Bukkit.getOnlinePlayers().forEach(player -> onlinePlayers.add(player.getName()));
            return StringUtil.copyPartialMatches(args[1], onlinePlayers, new ArrayList<>());
        }

        return new ArrayList<>();
    }
}
