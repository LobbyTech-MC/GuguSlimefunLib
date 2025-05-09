package me.ddggdd135.guguslimefunlib.items;

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

            if (CraftBukkit.ITEMSTACK.isCraftItemStack(itemStack)) {
                this.itemStack = itemStack;
                nms = CraftBukkit.ITEMSTACK.unwrapToNMS(itemStack);
            } else {
                Object nmsStack = CraftBukkit.ITEMSTACK.asNMSCopy(itemStack);
                this.itemStack = CraftBukkit.ITEMSTACK.asCraftMirror(nmsStack);
                nms = nmsStack;
            }

            this.type = ItemUtils.getItemType(itemStack);
            this.hash = type.hashCode();
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
