package me.ddggdd135.guguslimefunlib.items;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.libraries.dough.skins.PlayerHead;
import io.github.thebusybiscuit.slimefun4.libraries.dough.skins.PlayerSkin;
import me.ddggdd135.guguslimefunlib.libraries.Colors.CMIChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class AdvancedCustomItemStack extends CustomItemStack {
    public AdvancedCustomItemStack(ItemStack item) {
        super(item);
    }

    public AdvancedCustomItemStack(Material type) {
        super(type);
    }

    public AdvancedCustomItemStack(ItemStack item, Consumer<ItemMeta> meta) {
        super(item, meta);
    }

    public AdvancedCustomItemStack(Material type, Consumer<ItemMeta> meta) {
        super(type, meta);
    }

    public AdvancedCustomItemStack(ItemStack item, String name, String... lore) {
        super(item, CMIChatColor.translate(name), CMIChatColor.translate(lore));
    }

    public AdvancedCustomItemStack(ItemStack item, Color color, String name, String... lore) {
        super(item, color, CMIChatColor.translate(name), CMIChatColor.translate(lore));
    }

    public AdvancedCustomItemStack(Material type, String name, String... lore) {
        super(type, CMIChatColor.translate(name), CMIChatColor.translate(lore));
    }

    public AdvancedCustomItemStack(Material type, String name, List<String> lore) {
        super(type, CMIChatColor.translate(name), lore);
    }

    public AdvancedCustomItemStack(ItemStack item, List<String> list) {
        super(item);
        setLore(CMIChatColor.translate(list));
    }

    public AdvancedCustomItemStack(Material type, List<String> list) {
        super(type);
        setLore(CMIChatColor.translate(list));
    }

    public AdvancedCustomItemStack(ItemStack item, int amount) {
        super(item, amount);
    }

    public AdvancedCustomItemStack(ItemStack item, Material type) {
        super(item, type);
    }

    @Nonnull
    public static AdvancedCustomItemStack fromHashCode(String hashCode, String name, String... lore) {
        PlayerSkin skin = PlayerSkin.fromHashCode(hashCode);
        return new AdvancedCustomItemStack(PlayerHead.getItemStack(skin), (ItemMeta meta) -> {
            meta.setDisplayName(name);
            meta.setLore(Arrays.stream(lore).toList());
        });
    }
    @Nonnull
    public static AdvancedCustomItemStack fromBase64(String base64, String name, String... lore) {
        PlayerSkin skin = PlayerSkin.fromBase64(base64);
        return new AdvancedCustomItemStack(PlayerHead.getItemStack(skin), (ItemMeta meta) -> {
            meta.setDisplayName(name);
            meta.setLore(Arrays.stream(lore).toList());
        });
    }
    @Nonnull
    public static AdvancedCustomItemStack fromURL(String url, String name, String... lore) {
        PlayerSkin skin = PlayerSkin.fromURL(url);
        return new AdvancedCustomItemStack(PlayerHead.getItemStack(skin), (ItemMeta meta) -> {
            meta.setDisplayName(name);
            meta.setLore(Arrays.stream(lore).toList());
        });
    }
    public AdvancedCustomItemStack doGlow() {
        addUnsafeEnchantment(Enchantment.LUCK, 1);
        addItemFlags(ItemFlag.HIDE_ENCHANTS);

        return this;
    }
    public AdvancedCustomItemStack setCustomModelData(int modelId) {
        ItemMeta meta = getItemMeta();
        meta.setCustomModelData(modelId);
        setItemMeta(meta);

        return this;
    }
    @Nonnull
    public AdvancedCustomItemStack asQuantity(int count) {
        AdvancedCustomItemStack itemStack = new AdvancedCustomItemStack(this);
        itemStack.setAmount(count);
        return itemStack;
    }

    @Nonnull
    public static AdvancedCustomItemStack parse(String yaml) {
        YamlConfiguration configuration = new YamlConfiguration();
        try {
            configuration.loadFromString(yaml);
            return new AdvancedCustomItemStack(configuration.getItemStack("item"));
        } catch (InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    @Nonnull
    public static AdvancedCustomItemStack fromSlimefunItem(String sfId, String name, String... lore) {
        SlimefunItem sfItem = SlimefunItem.getById(sfId);
        if (sfItem != null) {
            return new AdvancedCustomItemStack(sfItem.getItem().clone(), name, lore);
        }
        throw new RuntimeException("无法找到" + sfId);
    }

    @Nonnull
    public static AdvancedCustomItemStack fromSlimefunItem(String sfId, String name) {
        SlimefunItem sfItem = SlimefunItem.getById(sfId);
        if (sfItem != null) {
            return new AdvancedCustomItemStack(sfItem.getItem().clone(), itemMeta -> itemMeta.setDisplayName(name));
        }
        throw new RuntimeException("无法找到" + sfId);
    }

    @Nonnull
    public static AdvancedCustomItemStack fromSlimefunItem(String sfId, String... lore) {
        SlimefunItem sfItem = SlimefunItem.getById(sfId);
        if (sfItem != null) {
            return new AdvancedCustomItemStack(sfItem.getItem().clone(), Arrays.stream(lore).toList());
        }
        throw new RuntimeException("无法找到" + sfId);
    }

    public static AdvancedCustomItemStack fromLore(ItemStack itemStack, String... lore) {
        return new AdvancedCustomItemStack(itemStack, Arrays.stream(lore).toList());
    }
}
