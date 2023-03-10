package net.momirealms.directionalblock;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;

public class Directions4 implements Directions {

    private final String north;
    private final String south;
    private final String east;
    private final String west;

    public Directions4(String north, String south, String east, String west) {
        this.north = north;
        this.south = south;
        this.east = east;
        this.west = west;
    }

    public String getEast() {
        return east;
    }

    public String getNorth() {
        return north;
    }

    public String getSouth() {
        return south;
    }

    public String getWest() {
        return west;
    }

    @Override
    public void place(float pitch, float yaw, BlockFace blockFace, Location location) {
        if (yaw < 45 && yaw > -45) {
            DirectionalBlock.placeBlock(getSouth(), location);
        } else if (yaw < -45 && yaw > -135) {
            DirectionalBlock.placeBlock(getEast(), location);
        } else if (yaw > 45 && yaw < 135) {
            DirectionalBlock.placeBlock(getWest(), location);
        } else {
            DirectionalBlock.placeBlock(getNorth(), location);
        }
    }
}
