package me.ddggdd135.guguslimefunlib.api;

import io.github.thebusybiscuit.slimefun4.implementation.operations.CraftingOperation;
import io.github.thebusybiscuit.slimefun4.libraries.commons.lang.Validate;
import javax.annotation.Nonnull;
import lombok.Getter;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;

public class CustomCraftingOperation extends CraftingOperation {
    @Getter
    private MachineRecipe recipe;

    public CustomCraftingOperation(@Nonnull MachineRecipe recipe) {
        super(recipe.getInput(), recipe.getOutput(), recipe.getTicks());
        Validate.isTrue(
                recipe.getTicks() >= 0,
                "The amount of total ticks must be a positive integer or zero, received: " + recipe.getTicks());
        this.recipe = recipe;
    }

    public int getTotalTicks() {
        return recipe.getTicks();
    }
}
