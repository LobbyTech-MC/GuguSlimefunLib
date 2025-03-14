package me.ddggdd135.guguslimefunlib;

import de.tr7zw.changeme.nbtapi.utils.nmsmappings.ClassWrapper;
import de.tr7zw.changeme.nbtapi.utils.nmsmappings.ReflectionMethod;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import me.ddggdd135.guguslimefunlib.utils.ItemUtils;
import org.bukkit.inventory.ItemStack;

public class ItemKey {
    private ItemStack itemStack;
    private int hash;

    public ItemKey(ItemStack itemStack) {
        if (ClassWrapper.CRAFT_ITEMSTACK.getClazz().isAssignableFrom(itemStack.getClass())) {
            this.itemStack = itemStack;
        } else {
            Object nmsStack = ReflectionMethod.ITEMSTACK_NMSCOPY.run((Object) null, new Object[] {itemStack});
            this.itemStack =
                    (ItemStack) ReflectionMethod.ITEMSTACK_BUKKITMIRROR.run((Object) null, new Object[] {nmsStack});
        }

        this.hash = ItemUtils.getItemType(itemStack).hashCode();
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
        return hash;
    }

    @Override
    public String toString() {
        return "ItemKey{" + "itemStack=" + itemStack + '}';
    }
}
