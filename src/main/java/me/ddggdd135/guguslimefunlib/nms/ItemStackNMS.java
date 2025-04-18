package me.ddggdd135.guguslimefunlib.nms;

import java.lang.reflect.Method;
import lombok.Getter;
import me.ddggdd135.guguslimefunlib.api.InitializeProvider;
import me.ddggdd135.guguslimefunlib.api.InitializeSafeProvider;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class ItemStackNMS {
    public static final ItemStack DEFAULT_ITEMSTACK = new ItemStack(Material.STONE);
    private static final ThreadLocal<Inventory> threadLocalInventory =
            ThreadLocal.withInitial(() -> Bukkit.createInventory(
                    new InventoryHolder() {
                        Inventory inv;

                        @Override
                        public Inventory getInventory() {
                            return inv;
                        }
                    },
                    InventoryType.CHEST));
    public static final ItemStack craftItemStack = new InitializeSafeProvider<>(ItemStack.class, () -> {
                try {
                    Inventory a = threadLocalInventory.get();
                    a.setItem(0, DEFAULT_ITEMSTACK);
                    return a.getItem(0);
                } catch (Throwable e) {
                    return null;
                }
            })
            .v();

    @Getter
    private static final Class<?> craftItemStackClass =
            new InitializeProvider<Class<?>>(() -> craftItemStack != null ? craftItemStack.getClass() : null).v();

    public static final Method asNMSCopy = new InitializeSafeProvider<>(() -> {
                Method method = craftItemStackClass.getMethod("asNMSCopy", ItemStack.class);
                method.setAccessible(true);
                return method;
            })
            .v();
    public static final Object nmsItemStack = new InitializeSafeProvider<>(() -> {
                try {
                    return asNMSCopy.invoke(null, craftItemStack);
                } catch (Throwable e) {
                    return null;
                }
            })
            .v();

    @Getter
    private static final Class<?> nmsItemStackClass =
            new InitializeProvider<Class<?>>(() -> nmsItemStack != null ? nmsItemStack.getClass() : null).v();

    public static final Method asCraftMirror = new InitializeSafeProvider<>(() -> {
                Method method = craftItemStackClass.getMethod("asCraftMirror", nmsItemStackClass);
                method.setAccessible(true);
                return method;
            })
            .v();
    public static final Method copyNMSStack = new InitializeSafeProvider<>(() -> {
                Method method = craftItemStackClass.getMethod("copyNMSStack", nmsItemStackClass, int.class);
                method.setAccessible(true);
                return method;
            })
            .v();
    public static final java.lang.invoke.VarHandle CRAFT_ITEM_STACK_HANDLE_FILED = new InitializeSafeProvider<>(() -> {
                try {
                    return java.lang.invoke.MethodHandles.privateLookupIn(
                                    craftItemStackClass, java.lang.invoke.MethodHandles.lookup())
                            .findVarHandle(craftItemStackClass, "handle", nmsItemStackClass);
                } catch (final IllegalAccessException | NoSuchFieldException exception) {
                    return null;
                }
            })
            .v();
    public static final Method matches = new InitializeSafeProvider<>(() -> {
                Method method = nmsItemStackClass.getMethod("a", nmsItemStackClass, nmsItemStackClass);
                method.setAccessible(true);
                return method;
            })
            .v();
}
