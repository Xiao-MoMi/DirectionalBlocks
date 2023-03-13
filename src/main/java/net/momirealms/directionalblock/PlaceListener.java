package net.momirealms.directionalblock;

import dev.lone.itemsadder.api.CustomBlock;
import dev.lone.itemsadder.api.Events.CustomBlockPlaceEvent;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

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
        if (REPLACEABLE.contains(against.getType())) return;
        event.setCancelled(true);
        ItemStack blockItem = event.getItemInHand();
        if (player.getGameMode() != GameMode.CREATIVE) blockItem.setAmount(Math.max(blockItem.getAmount() - 1, 0));
        directions.place(player.getLocation().getPitch(), player.getLocation().getYaw(), against.getFace(placed), placed.getLocation());
    }
}
