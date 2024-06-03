package me.ddggdd135.guguslimefunlib.listeners;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import com.xzavier0722.mc.plugin.slimefun4.storage.util.StorageCacheUtils;
import java.util.concurrent.ThreadLocalRandom;
import me.ddggdd135.guguslimefunlib.items.GuguSlimefunItem;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent e) {
        Material item = e.getBlock().getType();
        SlimefunBlockData blockData = StorageCacheUtils.getBlock(e.getBlock().getLocation());
        String block =
                blockData != null ? blockData.getSfId() : item.getKey().value().toUpperCase();

        for (GuguSlimefunItem guguSlimefunItem : GuguSlimefunItem.allGuguSlimefunItems) {
            if (guguSlimefunItem.getDropFrom() != null
                    && guguSlimefunItem.getDropFrom().equals(block)) {
                int chance = guguSlimefunItem.getDropChance();

                if (chance >= 100) {
                    guguSlimefunItem.warn("The Block-Breaking drop chance must be between 1-99% on item: "
                            + guguSlimefunItem.getId());
                } else if (chance > ThreadLocalRandom.current().nextInt(100)) {
                    e.getBlock()
                            .getWorld()
                            .dropItemNaturally(
                                    e.getBlock().getLocation(),
                                    guguSlimefunItem.getItem().asQuantity(guguSlimefunItem.getDropAmount()));
                    return;
                }
            }
        }
    }
}
