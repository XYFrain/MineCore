package com.xycm.frain.minecore.model;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

@ConfigSerializable
@Getter
public class ServerSpawn {
    private String world = "world";
    private double x = 0.5;
    private double y = 64.0;
    private double z = 0.5;
    private double yaw = 0.0;
    private double pitch = 0.0;

    public Location getLocation() {
        World worldInstance = Bukkit.getWorld(world);
        return new Location(worldInstance, x, y, z, (float) yaw, (float) pitch);
    }
}
