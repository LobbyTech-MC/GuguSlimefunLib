package me.ddggdd135.guguslimefunlib.items;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

public class GuguSlimefunItemFactory {
    public static GuguSlimefunItem createItem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack... recipe) {
        return new GuguSlimefunItem(itemGroup, item, recipeType, recipe);
    }

    public static GuguSlimefunItem createItem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, @Nullable ItemStack recipeOutput, ItemStack... recipe) {
        return new GuguSlimefunItem(itemGroup, item, recipeType, recipeOutput, recipe);
    }

    public static GuguSlimefunItem createItem(ItemGroup itemGroup, ItemStack item, String id, RecipeType recipeType, ItemStack... recipe) {
        return new GuguSlimefunItem(itemGroup, item, id, recipeType, recipe);
    }
}
