package net.momirealms.directionalblock;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;

public interface Directions {

    void place(float pitch, float yaw, BlockFace blockFace, Location location);
}
