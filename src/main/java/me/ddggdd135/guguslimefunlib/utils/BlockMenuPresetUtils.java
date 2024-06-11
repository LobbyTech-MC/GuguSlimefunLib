package me.ddggdd135.guguslimefunlib.utils;

import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;

import javax.annotation.Nullable;

public class BlockMenuPresetUtils {
    @Nullable
    public static BlockMenuPreset findBlockMenuPreset(String id) {
        return Slimefun.getRegistry().getMenuPresets().get(id);
    }
}
