package me.ddggdd135.guguslimefunlib.api;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.core.machines.MachineProcessor;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import me.ddggdd135.guguslimefunlib.api.abstracts.GuguMachineBlock;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;

public record GuguMachineInfo(
        @Nullable BlockMenu blockMenu,
        @Nonnull SlimefunBlockData data,
        @Nonnull GuguMachineBlock machine,
        @Nonnull SlimefunItem machineItem,
        @Nonnull Block block,
        @Nonnull MachineProcessor<CustomCraftingOperation> processor) {

    public Inventory getInventory() {
        if (blockMenu == null) {
            return null;
        }
        return blockMenu.getInventory();
    }
}
