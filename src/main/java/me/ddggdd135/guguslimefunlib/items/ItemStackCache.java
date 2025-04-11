package me.ddggdd135.guguslimefunlib.items;

import java.util.Objects;
import javax.annotation.Nonnull;
import org.bukkit.inventory.ItemStack;

public class ItemStackCache {
    private ItemStack itemStack;
    private ItemKey itemKey;

    public ItemStackCache(@Nonnull ItemStack itemStack) {
        this.itemStack = itemStack;
        this.itemKey = new ItemKey(itemStack);
    }

    @Nonnull
    public ItemStack getItemStack() {
        return itemStack;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemStackCache that = (ItemStackCache) o;
        return Objects.equals(itemKey, that.itemKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemKey);
    }
}
