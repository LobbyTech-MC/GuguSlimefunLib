package me.ddggdd135.guguslimefunlib.items;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.Radioactivity;
import lombok.Getter;
import lombok.Setter;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

public class GuguSlimefunItem extends SlimefunItem {
    @Getter
    @Setter
    private @Nullable RainbowType rainbowType = null;
    @Getter
    @Setter
    private boolean antiWither = false;
    @Getter
    @Setter
    private int piglinTradeChance = 0;
    @Getter
    @Setter
    private @Nullable String dropFrom = null;
    @Getter
    @Setter
    private int dropChance = 0;
    @Getter
    @Setter
    private int dropAmount = 0;

    private int state = 0;

    public static Set<GuguSlimefunItem> allGuguSlimefunItems = new HashSet<>();
    public GuguSlimefunItem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack... recipe) {
        super(itemGroup, item, recipeType, recipe);

        addItemHandler(createTicker());
    }

    public GuguSlimefunItem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, @Nullable ItemStack recipeOutput, ItemStack... recipe) {
        super(itemGroup, item, recipeType, recipe, recipeOutput);

        addItemHandler(createTicker());
    }

    protected GuguSlimefunItem(ItemGroup itemGroup, ItemStack item, String id, RecipeType recipeType, ItemStack...recipe) {
        super(itemGroup, item, id, recipeType, recipe);

        addItemHandler(createTicker());
    }

    public BlockTicker createTicker() {
        return new BlockTicker() {
            @Override
            public boolean isSynchronized() {
                return false;
            }

            @Override
            public void tick(Block block, SlimefunItem item, SlimefunBlockData data) {
                GuguSlimefunItem.this.tick(block, item, data);
            }
        };
    }

    protected void tick(Block block, SlimefunItem item, SlimefunBlockData data) {
        if (rainbowType != null) {
            if (!block.getType().isAir()) {
                int i = state % rainbowType.asList().size();
                Material material = rainbowType.asList().get(i);
                block.setType(material);
            }
        }
        state++;
    }

    public void setVanilla(boolean value) {
        setUseableInWorkbench(value);
    }

    @Override
    public void register(SlimefunAddon slimefunAddon) {
        super.register(slimefunAddon);
        allGuguSlimefunItems.add(this);
    }
}
