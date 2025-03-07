package me.ddggdd135.guguslimefunlib;

import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import java.util.Objects;
import me.ddggdd135.guguslimefunlib.utils.ItemUtils;
import org.bukkit.inventory.ItemStack;

public class ItemKey {
    private final ItemStack itemStack;

    public ItemKey(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemKey that = (ItemKey) o;
        // 自定义比较逻辑：只比较物品类型和数量
        return SlimefunUtils.isItemSimilar(itemStack, that.itemStack, true, false);
    }

    @Override
    public int hashCode() {
        String sfId = ItemUtils.getSFId(itemStack);
        if (sfId == null) return Objects.hash(itemStack.getType());
        return Objects.hash(itemStack.getType(), sfId);
    }

    @Override
    public String toString() {
        return "ItemKey{" + "itemStack=" + itemStack + '}';
    }
}
