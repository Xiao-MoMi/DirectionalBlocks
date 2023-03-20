package net.momirealms.directionalblock;

import dev.lone.itemsadder.api.CustomBlock;
import dev.lone.itemsadder.api.CustomStack;
import dev.lone.itemsadder.api.Events.CustomBlockBreakEvent;
import dev.lone.itemsadder.api.Events.CustomBlockPlaceEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;

import java.util.Arrays;
import java.util.HashSet;

public class PlaceListener implements Listener {

    private static final HashSet<Material> REPLACEABLE = new HashSet<>(Arrays
            .asList(Material.SNOW, Material.VINE, Material.GRASS, Material.TALL_GRASS, Material.SEAGRASS, Material.FERN,
                    Material.LARGE_FERN));

    private final DirectionalBlock plugin;

    public PlaceListener(DirectionalBlock plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlaceCustom(CustomBlockPlaceEvent event) {
        if (event.isCancelled() || !event.isCanBuild()) return;
        String block_id = event.getNamespacedID();
        Directions directions = plugin.getDirections(block_id);
        if (directions == null) return;
        final Player player = event.getPlayer();
        Block placed = event.getBlock();
        Block against = event.getPlacedAgainst();
        // Because of some unexpected arguments of the event
        // To prevent block duplication, this part would not be handled by Directional Blocks
        if (REPLACEABLE.contains(against.getType())) return;
        // cancel the event would make PlayerInteractEvent cancelled
        // Make anti-grief plugin lose protection
        // So a task is scheduled later for block replacement
        Bukkit.getScheduler().runTask(plugin, () -> {
            CustomBlock.remove(placed.getLocation());
            directions.place(player.getLocation().getPitch(), player.getLocation().getYaw(), against.getFace(placed), placed.getLocation());
        });
    }

    /**
     * I don't use CustomBlockBreakEvent because cancelling that event would remove the break sound & particles
     *  and make some anti-grief plugin lose protection
     */
    @EventHandler
    public void onBreakCustom(ItemSpawnEvent event) {
        if (event.isCancelled()) return;
        Item item = event.getEntity();
        CustomStack customStack = CustomStack.byItemStack(item.getItemStack());
        if (customStack == null) return;
        String origin = plugin.getOriginBlockId(customStack.getNamespacedID());
        if (origin == null) return;
        item.setItemStack(CustomStack.getInstance(origin).getItemStack());
    }
}
