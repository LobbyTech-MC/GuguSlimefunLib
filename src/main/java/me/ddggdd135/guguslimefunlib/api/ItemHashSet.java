package me.ddggdd135.guguslimefunlib.api;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import javax.annotation.Nonnull;
import me.ddggdd135.guguslimefunlib.items.ItemKey;
import org.bukkit.inventory.ItemStack;

public class ItemHashSet implements Set<ItemKey> {
    private final ItemHashMap<Boolean> map = new ItemHashMap<>();

    public boolean add(ItemStack e) {
        return map.put(e, Boolean.TRUE) == null;
    }

    @Override
    public boolean remove(Object e) {
        if (e instanceof ItemKey itemKey) return map.removeKey(itemKey);
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
    public boolean addAll(@Nonnull Collection<? extends ItemKey> c) {
        boolean modified = false;
        for (ItemKey e : c) {
            if (add(e)) {
                modified = true;
            }
        }
        return modified;
    }

    public boolean addAllItemStacks(@Nonnull Collection<? extends ItemStack> c) {
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
        Iterator<ItemKey> it = iterator();
        while (it.hasNext()) {
            ItemKey e = it.next();
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
    public Iterator<ItemStack> itemIterator() {
        return map.keySet().iterator(); // 使用 ConcurrentHashMap 的 keySet 作为迭代器
    }

    @Nonnull
    @Override
    public Iterator<ItemKey> iterator() {
        return map.sourceKeySet().iterator(); // 使用 ConcurrentHashMap 的 keySet 作为迭代器
    }

    @Nonnull
    @Override
    public Object[] toArray() {
        return map.sourceKeySet().toArray();
    }

    @Nonnull
    @Override
    public <T> T[] toArray(@Nonnull T[] a) {
        return map.sourceKeySet().toArray(a);
    }

    @Override
    public boolean add(ItemKey e) {
        return map.putKey(e, Boolean.TRUE) == null;
    }

    public void clear() {
        map.clear();
    }
}
