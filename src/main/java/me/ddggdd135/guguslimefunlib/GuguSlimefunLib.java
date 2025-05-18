package me.ddggdd135.guguslimefunlib;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import lombok.Getter;
import me.ddggdd135.guguslimefunlib.commands.ServerUUID;
import me.ddggdd135.guguslimefunlib.listeners.InventoryListener;
import me.ddggdd135.guguslimefunlib.utils.UUIDUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class GuguSlimefunLib extends JavaPlugin implements SlimefunAddon {
    @Getter
    private static GuguSlimefunLib instance;

    @Getter
    private static UUID serverUUID;

    @Override
    public void onEnable() {
        instance = this;
        // Plugin startup logic
        File uuidFile = new File(getDataFolder(), "server-uuid");
        if (uuidFile.exists()) {
            try {
                serverUUID = UUID.nameUUIDFromBytes(Files.readAllBytes(Path.of(uuidFile.getPath())));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            serverUUID = UUID.randomUUID();
            try {
                getDataFolder().mkdirs();
                uuidFile.createNewFile();
                Files.write(Path.of(uuidFile.getPath()), UUIDUtils.toByteArray(serverUUID));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        Bukkit.getServer().getPluginManager().registerEvents(new InventoryListener(), this);
        ServerUUID serverUUIDCommand = new ServerUUID();
        getCommand("server_uuid").setTabCompleter(serverUUIDCommand);
        getCommand("server_uuid").setExecutor(serverUUIDCommand);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        instance = null;
    }

    @NotNull @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }

    @Nullable @Override
    public String getBugTrackerURL() {
        return null;
    }
}
