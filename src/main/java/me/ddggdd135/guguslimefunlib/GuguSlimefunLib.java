package me.ddggdd135.guguslimefunlib;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import lombok.Getter;
import me.ddggdd135.guguslimefunlib.listeners.BlockListener;
import me.ddggdd135.guguslimefunlib.listeners.InventoryListener;
import me.ddggdd135.guguslimefunlib.listeners.PiglinListener;
import me.ddggdd135.guguslimefunlib.listeners.WitherListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class GuguSlimefunLib extends JavaPlugin implements SlimefunAddon {
    @Getter
    private static GuguSlimefunLib instance;

    @Override
    public void onEnable() {
        instance = this;
        // Plugin startup logic
        Bukkit.getServer().getPluginManager().registerEvents(new BlockListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new InventoryListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PiglinListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new WitherListener(), this);
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
