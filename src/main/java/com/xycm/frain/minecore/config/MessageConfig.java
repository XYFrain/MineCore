package com.xycm.frain.minecore.config;

import com.xycm.frain.minecore.MineCore;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.loader.HeaderMode;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;
import lombok.Getter;
import java.io.File;


@ConfigSerializable
@Getter
public class MessageConfig {

    @Getter
    private static MessageConfig instance = new MessageConfig();
    private MessageConfig() {}

    //------------------------------------------------------------------
    // 前缀
    private String prefix = "";
    // 通用消息
    private String reloadSuccess = "";
    private String noPermission = "";
    private String playerOnly = "";
    private String invalidArgument = "";
    private String playerNotFound = "";
    private String backNoLocation = "";
    // 治疗
    private String healSuccess = "";
    private String healOthers = "";
    // 自杀
    private String suicideSuccess = "";
    // 飞行
    private String flyEnabled = "";
    private String flyDisabled = "";
    private String flyEnabledOthers = "";
    private String flyDisabledOthers = "";
    // 无敌
    private String godEnabled = "";
    private String godDisabled = "";
    private String godEnabledOthers = "";
    private String godDisabledOthers = "";
    // 游戏模式
    private String gamemodeChanged = "";
    private String gamemodeOthers = "";
    private String modeSurvival = "";
    private String modeCreative = "";
    private String modeAdventure = "";
    private String modeSpectator = "";
    // 隐身
    private String vanishEnabled = "";
    private String vanishDisabled = "";
    private String vanishEnabledOthers = "";
    private String vanishDisabledOthers = "";
    // 用法
    private String usage = "";
    // 家
    private String homeSet = "";
    private String homeDeleted = "";
    private String homeNotFound = "";
    private String noHomes = "";
    private String teleportedToHome = "";
    // 传送点
    private String warpSet = "";
    private String warpDeleted = "";
    private String warpNotFound = "";
    private String noWarps = "";
    private String teleportedToWarp = "";
    // 传送
    private String tpHereSuccess = "";
    private String spawnSuccess = "";
    private String spawnOthers = "";
    private String teleportNotice = "";
    // 异常消息
    private String exceptionUnknownCommand = "";
    private String exceptionMissingArgument = "";
    private String exceptionInvalidInteger = "";
    private String exceptionInvalidDecimal = "";
    private String exceptionCommandError = "";
    //------------------------------------------------------------------

    public static void load(File file) {
        try {
            YamlConfigurationLoader loader = YamlConfigurationLoader.builder()
                    .path(file.toPath())
                    .headerMode(HeaderMode.PRESERVE)
                    .build();
            ConfigurationNode root = loader.load();
            MessageConfig loaded = root.get(MessageConfig.class);
            if (loaded != null) instance = loaded;
        } catch (ConfigurateException e) {
            MineCore.getInstance().getLogger().severe(e.getMessage());
        }
    }
}
