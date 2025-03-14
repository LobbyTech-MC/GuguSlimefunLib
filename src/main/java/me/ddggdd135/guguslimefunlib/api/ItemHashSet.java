package me.ddggdd135.guguslimefunlib.api;

import java.util.*;
import javax.annotation.Nonnull;
import org.bukkit.inventory.ItemStack;

public class ItemHashSet implements Set<ItemStack> {
    private final ItemHashMap<Boolean> map = new ItemHashMap<>();

    @Override
    public boolean add(ItemStack e) {
        return map.put(e, Boolean.TRUE) == null;
    }

    @Override
    public boolean remove(Object e) {
        return map.remove(e) != null;
    }

    @Override
    public boolean contains(Object e) {
        return map.containsKey(e);
    }

    @Override
    public boolean containsAll(@Nonnull Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(@Nonnull Collection<? extends ItemStack> c) {
        boolean modified = false;
        for (ItemStack e : c) {
            if (add(e)) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean retainAll(@Nonnull Collection<?> c) {
        boolean modified = false;
        Iterator<ItemStack> it = iterator();
        while (it.hasNext()) {
            ItemStack e = it.next();
            if (!c.contains(e)) {
                it.remove();
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean removeAll(@Nonnull Collection<?> c) {
        boolean modified = false;
        for (Object o : c) {
            if (remove(o)) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Nonnull
    @Override
    public Iterator<ItemStack> iterator() {
        return map.keySet().iterator(); // 使用 ConcurrentHashMap 的 keySet 作为迭代器
    }

    @Nonnull
    @Override
    public Object[] toArray() {
        return map.keySet().toArray();
    }

    @Nonnull
    @Override
    public <T> T[] toArray(@Nonnull T[] a) {
        return map.keySet().toArray(a);
    }

    public void clear() {
        map.clear();
    }
}
