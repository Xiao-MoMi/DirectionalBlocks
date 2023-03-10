package net.momirealms.directionalblock;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DBCommand implements TabExecutor {

    private final DirectionalBlock plugin;

    public DBCommand(DirectionalBlock plugin) {
        this.plugin = plugin;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            return Stream.of("reload").filter(s -> s.startsWith(args[0])).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length < 1) return true;
        if (args[0].equalsIgnoreCase("reload")) {
            plugin.reload();
            if (sender instanceof Player) {
                Player player = (Player) sender;
                player.sendRawMessage("[DirectionalBlocks] reloaded");
            } else {
                plugin.getLogger().info("reloaded");
            }
        }
        return true;
    }
}
