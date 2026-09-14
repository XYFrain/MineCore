package com.xycm.frain.minecore.model;

import lombok.Data;
import lombok.NonNull;
import org.bukkit.Location;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Data
public class PlayerData {

    @NonNull
    private final UUID uuid;
    private final Map<String, Location> homes = new LinkedHashMap<>();
    private Location backLocation;
}
