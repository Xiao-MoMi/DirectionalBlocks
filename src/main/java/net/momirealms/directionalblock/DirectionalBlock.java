package net.momirealms.directionalblock;

import dev.lone.itemsadder.api.CustomBlock;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;

public final class DirectionalBlock extends JavaPlugin {

    private static DirectionalBlock plugin;
    private PlaceListener listener;

    private final HashMap<String, Directions> blocks;

    public DirectionalBlock() {
        this.blocks = new HashMap<>();
    }

    @Override
    public void onEnable() {
        plugin = this;
        this.listener = new PlaceListener(this);
        this.registerCommands();
        this.reload();
        this.getLogger().info("Plugin Enabled");
    }

    @Override
    public void onDisable() {
        this.unload();
    }

    private void registerCommands() {
        PluginCommand pluginCommand = Bukkit.getPluginCommand("directionalblocks");
        DBCommand dbCommand = new DBCommand(this);
        if (pluginCommand != null) {
            pluginCommand.setTabCompleter(dbCommand);
            pluginCommand.setExecutor(dbCommand);
        }
    }

    public void load() {
        this.loadConfig();
        Bukkit.getPluginManager().registerEvents(this.listener,this);
    }

    public void unload() {
        HandlerList.unregisterAll(this.listener);
        blocks.clear();
    }

    public void reload() {
        this.unload();
        YamlConfiguration config = getConfig("config.yml");
        if (!config.getBoolean("enable-plugin")) return;
        this.load();
    }

    public void loadConfig() {
        this.load3();
        this.load4();
        this.load6();
        this.getLogger().info("Loaded " + blocks.size() + " directional blocks");
    }

    private void load3() {
        File three_file = new File(plugin.getDataFolder() + File.separator + "3-variables-blocks");
        if (!three_file.exists()) {
            if (!three_file.mkdir()) return;
            plugin.saveResource("3-variables-blocks" + File.separator + "example.yml", false);
        }
        File[] files = three_file.listFiles();
        if (files == null) return;
        for (File file : files) {
            if (!file.getName().endsWith(".yml")) continue;
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            for (String key : config.getKeys(false)) {
                Directions3 directions = new Directions3(
                        config.getString(key + ".x"),
                        config.getString(key + ".y"),
                        config.getString(key + ".z")
                );
                blocks.put(key, directions);
            }
        }
    }

    private void load4() {
        File four_file = new File(plugin.getDataFolder() + File.separator + "4-variables-blocks");
        if (!four_file.exists()) {
            if (!four_file.mkdir()) return;
            plugin.saveResource("4-variables-blocks" + File.separator + "example.yml", false);
        }
        File[] files = four_file.listFiles();
        if (files == null) return;
        for (File file : files) {
            if (!file.getName().endsWith(".yml")) continue;
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            for (String key : config.getKeys(false)) {
                Directions4 directions = new Directions4(
                        config.getString(key + ".north"),
                        config.getString(key + ".south"),
                        config.getString(key + ".east"),
                        config.getString(key + ".west")
                );
                blocks.put(key, directions);
            }
        }
    }

    private void load6() {
        File six_file = new File(plugin.getDataFolder() + File.separator + "6-variables-blocks");
        if (!six_file.exists()) {
            if (!six_file.mkdir()) return;
            plugin.saveResource("6-variables-blocks" + File.separator + "example.yml", false);
        }
        File[] files = six_file.listFiles();
        if (files == null) return;
        for (File file : files) {
            if (!file.getName().endsWith(".yml")) continue;
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            for (String key : config.getKeys(false)) {
                Directions6 directions = new Directions6(
                        config.getString(key + ".north"),
                        config.getString(key + ".south"),
                        config.getString(key + ".east"),
                        config.getString(key + ".west"),
                        config.getString(key + ".up"),
                        config.getString(key + ".down")
                );
                blocks.put(key, directions);
            }
        }
    }

    public static DirectionalBlock getPlugin() {
        return plugin;
    }

    public Directions getDirections(String id) {
        return blocks.get(id);
    }

    public static void placeBlock(String blockID, Location location) {
        if (isVanillaItem(blockID)) {
            location.getBlock().setType(Material.valueOf(blockID));
        }
        else {
            CustomBlock customBlock = CustomBlock.place(blockID, location);
            customBlock.playPlaceSound();
        }
    }

    public static boolean isVanillaItem(String item) {
        char[] chars = item.toCharArray();
        for (char character : chars) {
            if ((character < 65 || character > 90) && character != 95) {
                return false;
            }
        }
        return true;
    }

    public YamlConfiguration getConfig(String configName) {
        File file = new File(this.getDataFolder(), configName);
        if (!file.exists()) this.saveResource(configName, false);
        return YamlConfiguration.loadConfiguration(file);
    }
}
