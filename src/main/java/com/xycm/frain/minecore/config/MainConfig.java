package com.xycm.frain.minecore.config;

import com.xycm.frain.minecore.MineCore;
import com.xycm.frain.minecore.model.ServerSpawn;

import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.loader.HeaderMode;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import lombok.Getter;
import java.io.File;

@ConfigSerializable
@Getter
public class MainConfig {
    @Getter
    private static MainConfig instance = new MainConfig();
    private MainConfig() {}
    //------------------------------------------------------------------------------------------------------------------
    private String language = "zh_CN";
    private ServerSpawn spawn = new ServerSpawn();
    //------------------------------------------------------------------------------------------------------------------
    public static void load(File file) {
        try {
            YamlConfigurationLoader loader = YamlConfigurationLoader.builder()
                    .path(file.toPath())
                    .headerMode(HeaderMode.PRESERVE)
                    .build();
            ConfigurationNode root = loader.load();
            MainConfig loaded = root.get(MainConfig.class);
            if (loaded != null) instance = loaded;
        } catch (ConfigurateException e) {
            MineCore.getInstance().getLogger().severe(e.getMessage());
        }
    }
}
