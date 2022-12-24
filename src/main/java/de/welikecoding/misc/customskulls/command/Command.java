package de.welikecoding.misc.customskulls.command;

import de.welikecoding.misc.customskulls.CustomSkulls;
import de.welikecoding.misc.customskulls.message.Message;
import de.welikecoding.misc.customskulls.message.MessageBuilder;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Command extends BukkitCommand {

    protected final CustomSkulls main = CustomSkulls.getInstance();

    private final boolean requiresPlayer;

    public Command(String name, String[] aliases, String description, String usage, String permission, boolean requiresPlayer) {
        super(name);

        this.setAliases(Arrays.asList(aliases));
        this.setDescription(description);
        this.setUsage(usage);
        this.setPermission(permission);

        this.requiresPlayer = requiresPlayer;

        try {
            Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            field.setAccessible(true);
            CommandMap commandMap = (CommandMap) field.get(Bukkit.getServer());
            commandMap.register(name, this);
        } catch (NoSuchFieldException | IllegalAccessException exception) {
            exception.printStackTrace();
        }

    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {

        if (!Objects.requireNonNull(getPermission()).isEmpty()) {
            if (!sender.hasPermission(getPermission())) {
                new MessageBuilder(Message.NOT_ENOUGH_PERMISSIONS).send(sender);
                return false;
            }
        }

        if (requiresPlayer) {
            if (!(sender instanceof Player)) {
                new MessageBuilder(Message.REQUIRES_PLAYER).send(sender);
                return false;
            }
            execute((Player) sender, args);
            return true;
        }

        execute(sender, args);
        return true;
    }

    public void execute(Player player, String[] args) {}
    public void execute(CommandSender sender, String[] args) {}

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {return onTabComplete(sender, args);}
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}