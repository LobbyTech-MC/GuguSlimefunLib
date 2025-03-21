package me.ddggdd135.guguslimefunlib.items;

import de.tr7zw.changeme.nbtapi.utils.nmsmappings.ClassWrapper;
import de.tr7zw.changeme.nbtapi.utils.nmsmappings.ReflectionMethod;
import me.ddggdd135.guguslimefunlib.utils.ItemUtils;
import org.bukkit.inventory.ItemStack;

public class ItemKey {
    private ItemStack itemStack;
    private ItemType type;
    private int hash;

    public ItemKey(ItemStack itemStack) {
        itemStack = itemStack.asOne();

        if (ClassWrapper.CRAFT_ITEMSTACK.getClazz().isAssignableFrom(itemStack.getClass())) {
            this.itemStack = itemStack;
        } else {
            Object nmsStack = ReflectionMethod.ITEMSTACK_NMSCOPY.run(null, itemStack);
            this.itemStack = (ItemStack) ReflectionMethod.ITEMSTACK_BUKKITMIRROR.run(null, nmsStack);
        }

        this.type = ItemUtils.getItemType(itemStack);
        this.hash = type.hashCode();
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

        return that.itemStack.equals(itemStack);
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
