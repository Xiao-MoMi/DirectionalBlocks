package net.momirealms.directionalblock;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;

public class Directions3 implements Directions {

    private final String x;
    private final String y;
    private final String z;

    public Directions3(String x, String y, String z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public String getX() {
        return x;
    }

    public String getY() {
        return y;
    }

    public String getZ() {
        return z;
    }

    @Override
    public void place(float pitch, float yaw, BlockFace blockFace, Location location) {
        if (blockFace == BlockFace.NORTH || blockFace == BlockFace.SOUTH) {
            DirectionalBlock.placeBlock(getZ(), location);
        } else if (blockFace == BlockFace.EAST || blockFace == BlockFace.WEST) {
            DirectionalBlock.placeBlock(getX(), location);
        } else {
            DirectionalBlock.placeBlock(getY(), location);
        }
    }
}
