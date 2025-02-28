package me.ddggdd135.guguslimefunlib.api;

import java.util.*;
import javax.annotation.Nonnull;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemHashSet implements Set<ItemStack> {
    private final Map<Material, Set<ItemStack>> bukkits = new HashMap<>();

    @Override
    public int size() {
        int size = 0;

        for (Map.Entry<Material, Set<ItemStack>> entry : bukkits.entrySet()) {
            size += entry.getValue().size();
        }

        return size;
    }

    @Override
    public boolean isEmpty() {
        if (bukkits.isEmpty()) return true;
        for (Map.Entry<Material, Set<ItemStack>> entry : bukkits.entrySet()) {
            if (!entry.getValue().isEmpty()) return false;
        }

        return true;
    }

    @Override
    public boolean contains(Object o) {
        if (!(o instanceof ItemStack itemStack)) return false;
        Set<ItemStack> bukkit = bukkits.get(itemStack.getType());
        if (bukkit == null) return false;
        return bukkit.contains(o);
    }

    @Nonnull
    @Override
    public Iterator<ItemStack> iterator() {
        return bukkits.values().stream().flatMap(Collection::stream).iterator();
    }

    @Nonnull
    @Override
    public Object[] toArray() {
        return bukkits.values().stream().flatMap(Collection::stream).toArray();
    }

    @Nonnull
    @Override
    public <T> T[] toArray(@Nonnull T[] a) {
        return bukkits.values().stream().flatMap(Collection::stream).toList().toArray(a);
    }

    @Override
    public boolean add(ItemStack itemStack) {
        Set<ItemStack> bukkit = bukkits.computeIfAbsent(itemStack.getType(), k -> new HashSet<>());
        return bukkit.add(itemStack);
    }

    @Override
    public boolean remove(Object o) {
        if (!(o instanceof ItemStack itemStack)) return false;
        Set<ItemStack> bukkit = bukkits.get(itemStack.getType());
        return bukkit.remove(itemStack);
    }

    @Override
    public boolean containsAll(@Nonnull Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) return false;
        }

        return true;
    }

    @Override
    public boolean addAll(@Nonnull Collection<? extends ItemStack> c) {
        for (ItemStack o : c) {
            if (!add(o)) return false;
        }

        return true;
    }

    @Override
    public boolean retainAll(@Nonnull Collection<?> c) {
        return false;
    }

    @Override
    public boolean removeAll(@Nonnull Collection<?> c) {
        for (Object o : c) {
            if (!remove(o)) return false;
        }
        return true;
    }

    @Override
    public void clear() {
        bukkits.clear();
    }
}
