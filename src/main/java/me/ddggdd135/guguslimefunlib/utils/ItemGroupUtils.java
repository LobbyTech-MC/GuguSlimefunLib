package me.ddggdd135.guguslimefunlib.utils;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.groups.NestedItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.groups.SubItemGroup;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import javax.annotation.Nonnull;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public class ItemGroupUtils {
    @Nullable public static ItemGroup findItemGroup(NamespacedKey namespacedKey) {
        for (ItemGroup itemGroup : Slimefun.getRegistry().getAllItemGroups()) {
            if (itemGroup.getKey().equals(namespacedKey)) return itemGroup;
        }

        return null;
    }

    @Nonnull
    public static SubItemGroup createSubItemGroup(
            NamespacedKey parent, NamespacedKey namespacedKey, ItemStack itemStack, int tier) {
        ItemGroup parentItemGroup = findItemGroup(parent);
        if (!(parentItemGroup instanceof NestedItemGroup nestedItemGroup)) {
            throw new RuntimeException("父物品组不是NestedItemGroup");
        }

        return new SubItemGroup(namespacedKey, nestedItemGroup, itemStack, tier);
    }
}
