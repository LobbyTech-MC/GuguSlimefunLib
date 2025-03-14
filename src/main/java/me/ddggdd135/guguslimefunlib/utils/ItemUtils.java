package me.ddggdd135.guguslimefunlib.utils;

import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.iface.ReadableNBT;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import me.ddggdd135.guguslimefunlib.api.ItemHashMap;
import me.ddggdd135.guguslimefunlib.items.ItemType;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Container;
import org.bukkit.block.Furnace;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ItemUtils {
    @Nonnull
    public static ItemStack[] createItems(@Nonnull ItemStack template, int amount) {
        List<ItemStack> itemStacks = new ArrayList<>();
        int rest = amount;
        while (true) {
            if (rest <= template.getMaxStackSize()) {
                ItemStack itemStack = template.asQuantity(rest);
                itemStacks.add(itemStack);
                break;
            } else {
                rest -= template.getMaxStackSize();
                ItemStack itemStack = template.asQuantity(template.getMaxStackSize());
                itemStacks.add(itemStack);
            }
        }
        return itemStacks.toArray(new ItemStack[0]);
    }

    @Nonnull
    public static ItemStack[] createItems(@Nonnull Map<ItemStack, Integer> storage) {
        List<ItemStack> itemStacks = new ArrayList<>();
        for (ItemStack itemStack : storage.keySet()) {
            itemStacks.addAll(List.of(createItems(itemStack, storage.get(itemStack))));
        }
        return itemStacks.toArray(new ItemStack[0]);
    }

    @Nonnull
    public static ItemStack[] trimItems(@Nonnull ItemStack[] itemStacks) {
        List<ItemStack> itemStackList = new ArrayList<>();
        for (ItemStack itemStack : itemStacks) {
            if (itemStack.getAmount() > 0) {
                itemStackList.add(itemStack);
            }
        }
        return itemStackList.toArray(new ItemStack[0]);
    }

    @Nonnull
    public static ItemHashMap<Integer> getAmounts(@Nonnull ItemStack[] itemStacks) {
        ItemHashMap<Integer> storage = new ItemHashMap<>();
        for (ItemStack itemStack : itemStacks) {
            if (itemStack == null || itemStack.getType().isAir()) continue;
            if (storage.containsKey(itemStack)) {
                storage.put(itemStack, storage.get(itemStack) + itemStack.getAmount());
            } else {
                storage.put(itemStack, itemStack.getAmount());
            }
        }
        return storage;
    }

    @Nonnull
    public static Map<ItemStack, Integer> takeItems(
            @Nonnull Map<ItemStack, Integer> source, @Nonnull Map<ItemStack, Integer> toTake) {
        Map<ItemStack, Integer> storage = new ItemHashMap<>(source);
        for (ItemStack itemStack : toTake.keySet()) {
            if (storage.containsKey(itemStack)) {
                storage.put(itemStack, storage.get(itemStack) - toTake.get(itemStack));
            } else {
                storage.put(itemStack, -toTake.get(itemStack));
            }
        }
        return storage;
    }

    @Nonnull
    public static ItemHashMap<Integer> addItems(
            @Nonnull Map<ItemStack, Integer> source, @Nonnull Map<ItemStack, Integer> toAdd) {
        ItemHashMap<Integer> storage = new ItemHashMap<>(source);
        for (ItemStack itemStack : toAdd.keySet()) {
            if (storage.containsKey(itemStack)) {
                storage.put(itemStack, storage.get(itemStack) + toAdd.get(itemStack));
            } else {
                storage.put(itemStack, toAdd.get(itemStack));
            }
        }
        return storage;
    }

    public static void trim(@Nonnull Map<ItemStack, Integer> storage) {
        List<ItemStack> toRemove = new ArrayList<>();
        for (ItemStack itemStack : storage.keySet()) {
            if (itemStack == null || itemStack.getType().isAir() || storage.get(itemStack) <= 0)
                toRemove.add(itemStack);
        }
        for (ItemStack itemStack : toRemove) {
            storage.remove(itemStack);
        }
    }

    public static boolean contains(BlockMenu inv, int[] slots, ItemStack[] itemStacks) {
        Map<ItemStack, Integer> toTake = getAmounts(itemStacks);

        for (ItemStack itemStack : toTake.keySet()) {
            if (toTake.get(itemStack) > getItemAmount(inv, slots, itemStack)) {
                return false;
            }
        }
        return true;
    }

    public static int getItemAmount(BlockMenu inv, int[] slots, ItemStack itemStack) {
        int founded = 0;
        for (int slot : slots) {
            ItemStack item = inv.getItemInSlot(slot);
            if (item == null || item.getType().isAir()) continue;
            if (SlimefunUtils.isItemSimilar(item, itemStack, true, false)) {
                founded += item.getAmount();
            }
        }
        return founded;
    }

    @NotNull private static ItemStack[] getVanillaItemStacks(Block block) {
        Container container = (Container) block.getState();
        ItemStack[] items = new ItemStack[0];
        if (container instanceof Furnace furnace) {
            FurnaceInventory furnaceInventory = furnace.getInventory();
            items = new ItemStack[] {furnaceInventory.getResult()};
        } else if (container instanceof Chest chest) {
            Inventory inventory = chest.getBlockInventory();
            items = inventory.getContents();
        }
        return items;
    }

    public static <T extends SlimefunItem> T setRecipeOutput(@Nonnull T item, @Nonnull ItemStack output) {
        item.setRecipeOutput(output);
        return item;
    }

    public static ItemStack[] tryTakeItem(
            @Nonnull BlockMenu blockMenu, @Nonnull ItemHashMap<Integer> items, int... slots) {
        ItemHashMap<Integer> amounts = new ItemHashMap<>(items);
        ItemHashMap<Integer> found = new ItemHashMap<>();

        for (ItemStack itemStack : amounts.keySet()) {
            for (int slot : slots) {
                ItemStack item = blockMenu.getItemInSlot(slot);
                if (item == null || item.getType().isAir()) continue;
                if (SlimefunUtils.isItemSimilar(item, itemStack, true, false)) {
                    if (item.getAmount() > amounts.get(itemStack)) {
                        found.put(itemStack, found.getOrDefault(itemStack, 0) + amounts.get(itemStack));
                        int rest = item.getAmount() - amounts.get(itemStack);
                        item.setAmount(rest);
                        blockMenu.markDirty();
                        break;
                    } else {
                        found.put(itemStack, found.getOrDefault(itemStack, 0) + item.getAmount());
                        blockMenu.replaceExistingItem(slot, null);
                        int rest = amounts.get(itemStack) - item.getAmount();
                        if (rest != 0) amounts.put(itemStack, rest);
                        else break;
                    }
                }
            }
        }

        return createItems(found);
    }

    @Nullable public static String getSFId(@Nonnull ItemStack itemStack) {
        return NBT.get(itemStack, x -> {
            ReadableNBT pdc = x.getCompound("PublicBukkitValues");
            if (pdc == null) return null;

            return pdc.getString("slimefun:slimefun_item");
        });
    }

    @Nonnull
    public static ItemType getItemType(@Nonnull ItemStack itemStack) {
        if (itemStack.getType().isAir() || itemStack.getAmount() == 0) return new ItemType(false, null, Material.AIR);
        if (itemStack instanceof SlimefunItemStack sfis) {
            return new ItemType(true, sfis.getItemId(), sfis.getType());
        }

        String id = getSFId(itemStack);

        if (id != null) {
            return new ItemType(true, id, itemStack.getType());
        }

        return new ItemType(false, null, itemStack.getType());
    }
}
