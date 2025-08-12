package me.ddggdd135.guguslimefunlib.nms;

import org.bukkit.inventory.ItemStack;

public class ItemNMS {
    public static final java.lang.invoke.VarHandle API_ITEM_STACK_CRAFT_DELEGATE_FIELD;

    static {
        try {
            API_ITEM_STACK_CRAFT_DELEGATE_FIELD = java.lang.invoke.MethodHandles.privateLookupIn(
                            ItemStack.class, java.lang.invoke.MethodHandles.lookup())
                    .findVarHandle(ItemStack.class, "craftDelegate", ItemStack.class);
        } catch (final IllegalAccessException | NoSuchFieldException exception) {
            throw new RuntimeException(exception);
        }
    }
}
