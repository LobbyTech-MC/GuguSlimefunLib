package me.ddggdd135.guguslimefunlib.items;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.core.attributes.DistinctiveItem;
import me.ddggdd135.guguslimefunlib.utils.ItemUtils;
import me.matl114.matlib.nmsMirror.impl.CraftBukkit;
import me.matl114.matlib.nmsMirror.impl.NMSItem;
import org.bukkit.inventory.ItemStack;

public class ItemKey {
    private ItemStack itemStack;
    private ItemType type;
    private Object nms;
    private int hash;

    public ItemKey(ItemStack itemStack) {
        try {
            itemStack = itemStack.asOne();
            this.type = ItemUtils.getItemType(itemStack);
            if (this.type.getIsSlimefun() && this.type.getId() != null) {
                SlimefunItem sfItem = SlimefunItem.getById(this.type.getId());
                if (sfItem != null && !(sfItem instanceof DistinctiveItem)) {
                    ItemStack canonical = sfItem.getItem().asOne();
                    itemStack = canonical;
                }
            }
            this.nms = CraftBukkit.ITEMSTACK.unwrapToNMS(itemStack);
            this.itemStack = CraftBukkit.ITEMSTACK.asCraftMirror(nms);
            this.hash = NMSItem.ITEMSTACK.customHashcode(nms);
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public ItemType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemKey that = (ItemKey) o;

        try {
            return NMSItem.ITEMSTACK.matchItem(nms, that.nms, false, false);
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }

    @Override
    public int hashCode() {
        return hash;
    }

    @Override
    public String toString() {
        return "ItemKey{" + "itemStack=" + itemStack + '}';
    }
}
