package net.momirealms.directionalblock;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;

public class Directions6 implements Directions {

    private final String north;
    private final String south;
    private final String east;
    private final String west;
    private final String up;
    private final String down;

    public Directions6(String north, String south, String east, String west, String up, String down) {
        this.north = north;
        this.south = south;
        this.east = east;
        this.west = west;
        this.up = up;
        this.down = down;
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

    public String getDown() {
        return down;
    }

    public String getUp() {
        return up;
    }

    private String getBlock(BlockFace blockFace) {
        switch (blockFace) {
            case UP: {
                return getUp();
            }
            case EAST: {
                return getEast();
            }
            case NORTH: {
                return getNorth();
            }
            case WEST: {
                return getWest();
            }
            case DOWN: {
                return getDown();
            }
            case SOUTH: {
                return getSouth();
            }
            default: {
                return "AIR";
            }
        }
    }

    @Override
    public void place(float pitch, float yaw, BlockFace blockFace, Location location) {
        switch (blockFace){
            case DOWN:
            case UP: {
                if (pitch < 45 && pitch > -45) {
                    if (yaw < 45 && yaw > -45) {
                        DirectionalBlock.placeBlock(getNorth(), location);
                    } else if (yaw < -45 && yaw > -135) {
                        DirectionalBlock.placeBlock(getWest(), location);
                    } else if (yaw > 45 && yaw < 135) {
                        DirectionalBlock.placeBlock(getEast(), location);
                    } else {
                        DirectionalBlock.placeBlock(getSouth(), location);
                    }
                } else {
                    DirectionalBlock.placeBlock(getBlock(blockFace), location);
                }
                break;
            }
            case NORTH:
            case WEST:
            case SOUTH:
            case EAST: {
                if (pitch < 45 && pitch > -45) {
                    DirectionalBlock.placeBlock(getBlock(blockFace), location);
                } else if(pitch >= 45){
                    DirectionalBlock.placeBlock(getUp(), location);
                } else {
                    DirectionalBlock.placeBlock(getDown(), location);
                }
                break;
            }
        }
    }
}
