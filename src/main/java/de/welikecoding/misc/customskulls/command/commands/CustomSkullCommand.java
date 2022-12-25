package de.welikecoding.misc.customskulls.command.commands;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import de.welikecoding.misc.customskulls.command.Command;
import de.welikecoding.misc.customskulls.customs.CustomSkull;
import de.welikecoding.misc.customskulls.message.Message;
import de.welikecoding.misc.customskulls.message.MessageBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.util.StringUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomSkullCommand extends Command {

    public CustomSkullCommand() {
        super(
                "customskull",
                new String[]{"cs", "cskull"},
                "Get an defined skull from customskulls.yml",
                "/customskull <key>",
                "customskulls.cs.use",
                false);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if(args.length == 0 || args.length > 2) {
            new MessageBuilder(Message.CS_COMMAND_USAGE).send(sender);
            return;
        }

        CustomSkull customSkull = main.getCustomSkullFileManager().getSkull(args[0]);

        if(args.length == 1 && (sender instanceof Player)) {
            Player player = (Player) sender;
            giveSkull(player, customSkull);
            new MessageBuilder(Message.CS_CREATED).with("{SKULLKEY}", customSkull.getKey()).send(player);
            return;
        }

        if(args.length == 2) {
            Player target = Bukkit.getPlayerExact(args[1]);
            if(target == null || !target.isOnline()) {
                new MessageBuilder(Message.TARGET_NOT_ONLINE).with("{TARGET}", args[1]).send(sender);
                return;
            }

            giveSkull(target, customSkull);
            new MessageBuilder(Message.CS_SENT).with("{SKULLKEY}", customSkull.getKey()).with("{TARGET}", target.getName()).send(sender);
            new MessageBuilder(Message.CS_RECEIVED).with("{SKULLKEY}", customSkull.getKey()).withSender(sender).send(target);
            return;
        }

        new MessageBuilder(Message.REQUIRES_PLAYER).send(sender);

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {

        if(args.length == 1) {
            return StringUtil.copyPartialMatches(args[0], main.getCustomSkullFileManager().getKeys(), new ArrayList<>());
        }

        if (args.length != 2) {
            return new ArrayList<>();
        }

        List<String> onlinePlayers = new ArrayList<>();
        Bukkit.getOnlinePlayers().forEach(player -> onlinePlayers.add(player.getName()));
        return StringUtil.copyPartialMatches(args[1], onlinePlayers, new ArrayList<>());

    }

    private void giveSkull(Player player, CustomSkull customSkull) {
        ItemStack customSkullStack = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta customSkullMeta = (SkullMeta) customSkullStack.getItemMeta();

        if(customSkullMeta == null) return;
        customSkullMeta.setDisplayName(customSkull.getDisplayName());

        GameProfile gameProfile = new GameProfile(customSkull.getUUID(), null);
        gameProfile.getProperties().put("textures", new Property("textures", customSkull.getValue()));
        Field field;

        try {
            field = customSkullMeta.getClass().getDeclaredField("profile");
            field.setAccessible(true);
            field.set(customSkullMeta, gameProfile);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        if(customSkullMeta.getDisplayName().equals(new MessageBuilder(Message.CS_MISSING_TEXTURE_DISPLAYNAME).with("{SKULLKEY}", customSkull.getKey()).buildWithoutPrefix())) {
            customSkullMeta.setDisplayName(new MessageBuilder(Message.CS_MISSING_TEXTURE_DISPLAYNAME).with("{SKULLKEY}", customSkull.getKey()).buildWithoutPrefix());
            customSkullMeta.setLore(Collections.singletonList(new MessageBuilder(Message.CS_MISSING_TEXTURE_LORE).with("{SKULLKEY}", customSkull.getKey()).buildWithoutPrefix()));
            customSkullStack.setItemMeta(customSkullMeta);
            player.getInventory().addItem(customSkullStack);
            return;
        }
        customSkullMeta.setDisplayName(new MessageBuilder(Message.CS_DISPLAYNAME).with("{SKULLKEY}", customSkull.getKey()).buildWithoutPrefix());
        customSkullStack.setItemMeta(customSkullMeta);
        player.getInventory().addItem(customSkullStack);
    }


}
