package me.ddggdd135.guguslimefunlib.items;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemConsumptionHandler;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public class GuguSlimefunFood extends GuguSlimefunItem{
    public GuguSlimefunFood(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack... recipe) {
        super(itemGroup, item, recipeType, recipe);

        addItemHandler(getItemHandler());
    }

    public GuguSlimefunFood(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, @Nullable ItemStack recipeOutput, ItemStack... recipe) {
        super(itemGroup, item, recipeType, recipeOutput, recipe);

        addItemHandler(getItemHandler());
    }

    protected GuguSlimefunFood(ItemGroup itemGroup, ItemStack item, String id, RecipeType recipeType, ItemStack... recipe) {
        super(itemGroup, item, id, recipeType, recipe);

        addItemHandler(getItemHandler());
    }

    public ItemConsumptionHandler getItemHandler() {
        return (e, p, i) -> {
            if (getEval() != null) {
                getEval().evalFunction("onEat", e, p, i);
            }
        };
    }
}
