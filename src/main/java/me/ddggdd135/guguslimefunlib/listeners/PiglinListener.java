package me.ddggdd135.guguslimefunlib.listeners;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import me.ddggdd135.guguslimefunlib.items.GuguSlimefunItem;
import org.bukkit.entity.Piglin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class PiglinListener implements Listener {
    @EventHandler
    public void onPiglinDropItem(EntityDropItemEvent e) {
        if (e.getEntity() instanceof Piglin) {
            List<ItemStack> drops = Slimefun.getRegistry().getEnabledSlimefunItems().stream().map(SlimefunItem::getItem).toList();

            /*
             * NOTE: Getting a new random number each iteration because multiple items could have the same
             * % chance to drop, and if one fails all items with that number will fail.
             * Getting a new random number will allow multiple items with the same % chance to drop.
             */

            for (ItemStack is : drops) {
                SlimefunItem sfi = SlimefunItem.getByItem(is);
                // Check the getBarteringLootChance and compare against a random number 0-100,
                // if the random number is greater then replace the item.
                if (sfi instanceof GuguSlimefunItem guguSlimefunItem && guguSlimefunItem.getPiglinTradeChance() != 0) {
                    int chance = guguSlimefunItem.getPiglinTradeChance();

                    if (chance >= 100) {
                        sfi.warn("The Piglin Bartering chance must be between 1-99% on item: " + sfi.getId());
                    } else if (chance > ThreadLocalRandom.current().nextInt(100)) {
                        e.getItemDrop().setItemStack(sfi.getRecipeOutput());
                        return;
                    }
                }
            }
        }
    }
}
