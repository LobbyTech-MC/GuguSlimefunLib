package me.ddggdd135.guguslimefunlib.api;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.*;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemHashMap<T> implements Map<ItemStack, T> {
    private final Object2ObjectOpenHashMap<Material, Map<ItemStack, T>> bukkits = new Object2ObjectOpenHashMap<>();

    public ItemHashMap(@Nonnull Map<ItemStack, T> items) {
        putAll(items);
    }

    public ItemHashMap() {}

    @Override
    public int size() {
        int size = 0;

        for (Map.Entry<Material, Map<ItemStack, T>> entry : bukkits.entrySet()) {
            size += entry.getValue().size();
        }

        return size;
    }

    @Override
    public boolean isEmpty() {
        if (bukkits.isEmpty()) return true;
        for (Map.Entry<Material, Map<ItemStack, T>> entry : bukkits.entrySet()) {
            if (!entry.getValue().isEmpty()) return false;
        }

        return true;
    }

    @Override
    public boolean containsKey(Object key) {
        if (!(key instanceof ItemStack itemStack)) return false;
        Map<ItemStack, T> bukkit = bukkits.get(itemStack.getType());
        if (bukkit == null) return false;
        return bukkit.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        for (Map.Entry<Material, Map<ItemStack, T>> entry : bukkits.entrySet()) {
            if (entry.getValue().containsValue(value)) return true;
        }

        return false;
    }

    @Override
    public T get(Object key) {
        if (!(key instanceof ItemStack itemStack)) throw new IllegalArgumentException();
        Map<ItemStack, T> bukkit = bukkits.get(itemStack.getType());
        if (bukkit == null) return null;
        return bukkit.get(key);
    }

    @Nullable @Override
    public T put(ItemStack key, T value) {
        Map<ItemStack, T> bukkit = bukkits.computeIfAbsent(key.getType(), k -> new HashMap<>());
        return bukkit.put(key, value);
    }

    @Override
    public T remove(Object key) {
        if (!(key instanceof ItemStack itemStack)) return null;
        Map<ItemStack, T> bukkit = bukkits.get(itemStack.getType());
        return bukkit.remove(key);
    }

    @Override
    public void putAll(@Nonnull Map<? extends ItemStack, ? extends T> m) {
        m.forEach(this::put);
    }

    @Override
    public void clear() {
        bukkits.clear();
    }

    @Nonnull
    @Override
    public Set<ItemStack> keySet() {
        Set<ItemStack> itemStacks = new HashSet<>();
        for (Map.Entry<Material, Map<ItemStack, T>> entry : bukkits.entrySet()) {
            itemStacks.addAll(entry.getValue().keySet());
        }

        return itemStacks;
    }

    @Nonnull
    @Override
    public Collection<T> values() {
        Set<T> values = new HashSet<>();
        for (Map.Entry<Material, Map<ItemStack, T>> entry : bukkits.entrySet()) {
            values.addAll(entry.getValue().values());
        }

        return values;
    }

    @Nonnull
    @Override
    public Set<Entry<ItemStack, T>> entrySet() {
        return bukkits.entrySet().stream()
                .flatMap(x -> x.getValue().entrySet().stream())
                .map(x -> new Entry<ItemStack, T>() {
                    @Override
                    public ItemStack getKey() {
                        return x.getKey();
                    }

                    @Override
                    public T getValue() {
                        return x.getValue();
                    }

                    @Override
                    public T setValue(T value) {
                        return x.setValue(value);
                    }
                })
                .collect(Collectors.toSet());
    }
}
