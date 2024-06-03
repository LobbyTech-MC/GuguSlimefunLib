package me.ddggdd135.guguslimefunlib.items;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MenuItems {
    public static final SlimefunItemStack Empty = new SlimefunItemStack(
            "_GUGU_MN_EMPTY_", new AdvancedCustomItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE, " "));
    public static final ItemStack MULTI_INPUT_ITEM =
            new CustomItemStack(Material.LIME_STAINED_GLASS_PANE, "&a多物品输入", "", "&7> 单击查看");
    public static final ItemStack MULTI_OUTPUT_ITEM =
            new CustomItemStack(Material.LIME_STAINED_GLASS_PANE, "&a多物品输出", "", "&7> 单击查看");
}
