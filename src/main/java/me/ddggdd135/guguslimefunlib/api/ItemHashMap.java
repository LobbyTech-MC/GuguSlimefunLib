package me.ddggdd135.guguslimefunlib.api;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import me.ddggdd135.guguslimefunlib.items.ItemKey;
import org.bukkit.inventory.ItemStack;

public class ItemHashMap<V> implements Map<ItemStack, V> {
    private final Map<ItemKey, V> map;

    public ItemHashMap() {
        this.map = new ConcurrentHashMap<>();
    }

    public ItemHashMap(@Nonnull Map<ItemStack, V> m) {
        this.map = new ConcurrentHashMap<>();
        putAll(m);
    }

    public ItemHashMap(@Nonnull ItemHashMap<V> m) {
        this.map = new ConcurrentHashMap<>();
        putAll(m);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        if (key instanceof ItemStack) {
            return map.containsKey(new ItemKey((ItemStack) key));
        }

        if (key instanceof ItemKey itemKey) {
            return map.containsKey(itemKey);
        }

        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        if (key instanceof ItemStack) {
            return map.get(new ItemKey((ItemStack) key));
        }
        return null;
    }

    public V getKey(ItemKey key) {
        return map.get(key);
    }

    @Override
    public V put(ItemStack key, V value) {
        return map.put(new ItemKey(key), value);
    }

    public V putKey(ItemKey key, V value) {
        return map.put(key, value);
    }

    @Override
    public V remove(Object key) {
        if (key instanceof ItemStack) {
            return map.remove(new ItemKey((ItemStack) key));
        }
        return null;
    }

    public V removeKey(ItemKey key) {
        return map.remove(key);
    }

    @Override
    public void putAll(Map<? extends ItemStack, ? extends V> m) {
        for (Map.Entry<? extends ItemStack, ? extends V> entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    public void putAll(ItemHashMap<V> m) {
        for (Map.Entry<? extends ItemKey, ? extends V> entry : m.keyEntrySet()) {
            putKey(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Set<ItemStack> keySet() {
        return new AbstractSet<ItemStack>() {
            @Override
            public Iterator<ItemStack> iterator() {
                return new Iterator<ItemStack>() {
                    private final Iterator<ItemKey> wrapperIterator =
                            map.keySet().iterator();

                    @Override
                    public boolean hasNext() {
                        return wrapperIterator.hasNext();
                    }

                    @Override
                    public ItemStack next() {
                        return wrapperIterator.next().getItemStack();
                    }

                    @Override
                    public void remove() {
                        wrapperIterator.remove();
                    }
                };
            }

            @Override
            public int size() {
                return map.size();
            }

            @Override
            public boolean contains(Object o) {
                return ItemHashMap.this.containsKey(o);
            }

            @Override
            public boolean remove(Object o) {
                return ItemHashMap.this.remove(o) != null;
            }
        };
    }

    @Override
    public Collection<V> values() {
        return map.values();
    }

    @Override
    public Set<Map.Entry<ItemStack, V>> entrySet() {
        return new AbstractSet<Map.Entry<ItemStack, V>>() {
            @Override
            public Iterator<Map.Entry<ItemStack, V>> iterator() {
                return new Iterator<Map.Entry<ItemStack, V>>() {
                    private final Iterator<Map.Entry<ItemKey, V>> entryIterator =
                            map.entrySet().iterator();

                    @Override
                    public boolean hasNext() {
                        return entryIterator.hasNext();
                    }

                    @Override
                    public Map.Entry<ItemStack, V> next() {
                        Map.Entry<ItemKey, V> entry = entryIterator.next();
                        return new AbstractMap.SimpleEntry<>(entry.getKey().getItemStack(), entry.getValue());
                    }

                    @Override
                    public void remove() {
                        entryIterator.remove();
                    }
                };
            }

            @Override
            public int size() {
                return map.size();
            }

            @Override
            public boolean contains(Object o) {
                if (o instanceof Map.Entry) {
                    Map.Entry<?, ?> entry = (Map.Entry<?, ?>) o;
                    if (entry.getKey() instanceof ItemStack) {
                        ItemKey wrapper = new ItemKey((ItemStack) entry.getKey());
                        return map.containsKey(wrapper) && Objects.equals(map.get(wrapper), entry.getValue());
                    }
                }
                return false;
            }

            @Override
            public boolean remove(Object o) {
                if (o instanceof Map.Entry) {
                    Map.Entry<?, ?> entry = (Map.Entry<?, ?>) o;
                    if (entry.getKey() instanceof ItemStack) {
                        ItemKey wrapper = new ItemKey((ItemStack) entry.getKey());
                        if (Objects.equals(map.get(wrapper), entry.getValue())) {
                            map.remove(wrapper);
                            return true;
                        }
                    }
                }
                return false;
            }
        };
    }

    public Set<Map.Entry<ItemKey, V>> keyEntrySet() {
        return map.entrySet();
    }

    public Set<ItemKey> sourceKeySet() {
        return map.keySet();
    }

    public V getOrDefault(ItemKey key, V defaultValue) {
        V v;

        return (((v = getKey(key)) != null) || containsKey(key)) ? v : defaultValue;
    }

    @Override
    public String toString() {
        return map.entrySet().stream()
                .map(entry -> entry.getKey().getItemStack() + "=" + entry.getValue())
                .collect(Collectors.joining(", ", "{", "}"));
    }
}
