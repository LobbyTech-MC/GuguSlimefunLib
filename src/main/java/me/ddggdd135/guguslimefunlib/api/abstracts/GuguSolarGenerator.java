package me.ddggdd135.guguslimefunlib.api.abstracts;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.items.electric.generators.SolarGenerator;
import javax.annotation.Nonnull;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

public class GuguSolarGenerator extends SolarGenerator {
    @Getter
    private int lightLevel;

    public GuguSolarGenerator(
            ItemGroup itemGroup,
            int dayEnergy,
            int nightEnergy,
            SlimefunItemStack item,
            RecipeType recipeType,
            ItemStack[] recipe,
            int capacity) {
        super(itemGroup, dayEnergy, nightEnergy, item, recipeType, recipe, capacity);
    }

    public void setLightLevel(int lightLevel) {
        if (lightLevel > 15 || lightLevel < 0) {
            lightLevel = 15;
        }

        this.lightLevel = lightLevel;
    }

    public int getGeneratedOutput(@Nonnull Location location, @Nonnull SlimefunBlockData slimefunBlockData) {
        World world = location.getWorld();

        if (world.getEnvironment() != World.Environment.NORMAL) {
            return 0;
        } else {
            boolean isDaytime = isDaytime(world);

            if (!isDaytime && getNightEnergy() < 1) {
                return 0;
            } else if (!world.isChunkLoaded(location.getBlockX() >> 4, location.getBlockZ() >> 4)
                    || location.getBlock().getRelative(0, 1, 0).getLightFromSky() < lightLevel) {
                return 0;
            } else {
                return isDaytime ? getDayEnergy() : getNightEnergy();
            }
        }
    }

    private boolean isDaytime(World world) {
        long time = world.getTime();
        return !world.hasStorm() && !world.isThundering() && (time < 12300L || time > 23850L);
    }
}
