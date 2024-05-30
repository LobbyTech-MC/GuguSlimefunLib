package me.ddggdd135.guguslimefunlib.listeners;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.ddggdd135.guguslimefunlib.items.GuguSlimefunItem;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

public class WitherListener implements Listener {
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onWitherDestroy(EntityChangeBlockEvent e) {
        if (e.getEntity().getType() == EntityType.WITHER) {
            var controller = Slimefun.getDatabaseManager().getBlockDataController();
            var block = e.getBlock();
            var blockData = controller.getBlockDataFromCache(block.getLocation());
            var item = blockData == null ? null : SlimefunItem.getById(blockData.getSfId());

            // Hardened Glass is excluded from here
            if (item instanceof GuguSlimefunItem guguSlimefunItem && guguSlimefunItem.isAntiWither()
                    && !item.getId().equals(SlimefunItems.HARDENED_GLASS.getItemId())) {
                e.setCancelled(true);
            }
        }
    }
}
