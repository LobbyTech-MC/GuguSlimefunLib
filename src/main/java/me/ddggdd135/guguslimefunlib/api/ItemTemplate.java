package me.ddggdd135.guguslimefunlib.api;

import com.google.common.base.Preconditions;
import de.tr7zw.changeme.nbtapi.NBTItem;
import io.papermc.paper.inventory.ItemRarity;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.event.HoverEventSource;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemTemplate
        implements Cloneable, HoverEventSource<HoverEvent.ShowItem>, net.kyori.adventure.translation.Translatable {
    private ItemStack handle;
    private NBTItem nbt_handle;
    private boolean isAir;

    protected ItemTemplate() {
        handle = new ItemStack(Material.STONE, 1);
        isAir = true;
    }

    public ItemTemplate(@Nonnull Material type) {
        handle = new ItemStack(type, 1);
        if (type.isAir()) {
            handle.setType(Material.STONE);
            isAir = true;
        }
        if (!isAir) nbt_handle = new NBTItem(handle, true);
    }

    public ItemTemplate(@Nonnull ItemStack stack) throws IllegalArgumentException {
        Preconditions.checkArgument(stack != null, "Cannot copy null stack");
        handle = stack.clone();
        if (stack.getType().isAir()) {
            handle.setType(Material.STONE);
            isAir = true;
        }
        if (!isAir) {
            handle.setAmount(1);
            nbt_handle = new NBTItem(handle, true);
        }
    }

    public @Nonnull Material getType() {
        return handle.getType();
    }

    public void setType(@Nonnull Material type) {
        Preconditions.checkArgument(type != null, "Material cannot be null");
        if (type.isAir()) isAir = true;
        else isAir = false;
        handle.setType(isAir ? Material.STONE : type);
    }

    public int getAmount() {
        return 1;
    }

    public void setAmount(int amount) {}

    public int getMaxStackSize() {
        return handle.getMaxStackSize();
    }

    public String toString() {
        StringBuilder toString = (new StringBuilder("ItemStack{"))
                .append(this.getType().name())
                .append(" x ")
                .append(this.getAmount());
        if (this.hasItemMeta()) {
            toString.append(", ").append(this.getItemMeta());
        }

        return toString.append('}').toString();
    }

    public boolean equals(Object obj) {
        ItemStack itemStack;
        if (this == obj) {
            return true;
        } else if (!(obj instanceof ItemStack)) {
            if (obj instanceof ItemTemplate template) {
                if (isAir != template.isAir) return false;
                if (isAir && template.isAir) return true;
                itemStack = template.getHandle();
            } else return false;
        } else itemStack = (ItemStack) obj;

        return this.isSimilar(itemStack);
    }

    public boolean isSimilar(@Nullable ItemStack stack) {
        if (stack == null) return false;
        else {
            if (isAir != stack.getType().isAir()) return false;
            if (isAir && stack.getType().isAir()) return true;
            if (nbt_handle == null) nbt_handle = new NBTItem(handle, true);
            return handle.getType() == stack.getType()
                    && stack.getAmount() != 0
                    && new NBTItem(stack, true).toString().equals(nbt_handle.toString());
        }
    }

    @Override
    public @Nonnull ItemTemplate clone() {
        return new ItemTemplate(handle);
    }

    public int hashCode() {
        if (nbt_handle == null && !isAir) {
            nbt_handle = new NBTItem(handle, true);
            return nbt_handle.hashCode() + 31 * handle.getType().hashCode();
        } else if (nbt_handle != null && !isAir) {
            return nbt_handle.hashCode() + 31 * handle.getType().hashCode();
        } else {
            return 0;
        }
    }

    public boolean containsEnchantment(@Nonnull Enchantment ench) {
        return handle.containsEnchantment(ench);
    }

    public int getEnchantmentLevel(@Nonnull Enchantment ench) {
        return handle.getEnchantmentLevel(ench);
    }

    public @Nonnull Map<Enchantment, Integer> getEnchantments() {
        return handle.getEnchantments();
    }

    public void addEnchantments(@Nonnull Map<Enchantment, Integer> enchantments) {
        handle.addEnchantments(enchantments);
    }

    public void addEnchantment(@Nonnull Enchantment ench, int level) {
        handle.addEnchantment(ench, level);
    }

    public void addUnsafeEnchantments(@Nonnull Map<Enchantment, Integer> enchantments) {
        handle.addUnsafeEnchantments(enchantments);
    }

    public void addUnsafeEnchantment(@Nonnull Enchantment ench, int level) {
        handle.addUnsafeEnchantment(ench, level);
    }

    public int removeEnchantment(@Nonnull Enchantment ench) {
        return handle.removeEnchantment(ench);
    }

    public @Nonnull Map<String, Object> serialize() {
        return handle.serialize();
    }

    public boolean editMeta(@Nonnull Consumer<? super ItemMeta> consumer) {
        return handle.editMeta(consumer);
    }

    public <M extends ItemMeta> boolean editMeta(@Nonnull Class<M> metaClass, @Nonnull Consumer<? super M> consumer) {
        return handle.editMeta(metaClass, consumer);
    }

    public ItemMeta getItemMeta() {
        return handle.getItemMeta();
    }

    public boolean hasItemMeta() {
        return true;
    }

    public boolean setItemMeta(@Nullable ItemMeta itemMeta) {
        return handle.setItemMeta(itemMeta);
    }

    public @Nonnull ItemStack enchantWithLevels(int levels, boolean allowTreasure, @Nonnull Random random) {
        return handle.enchantWithLevels(levels, allowTreasure, random);
    }

    public @Nonnull HoverEvent<HoverEvent.ShowItem> asHoverEvent(@Nonnull UnaryOperator<HoverEvent.ShowItem> op) {
        return handle.asHoverEvent(op);
    }

    public @Nonnull Component displayName() {
        return handle.displayName();
    }

    /** @deprecated */
    @Deprecated
    public @Nullable List<String> getLore() {
        return handle.getLore();
    }

    public @Nullable List<Component> lore() {
        return handle.lore();
    }

    /** @deprecated */
    @Deprecated
    public void setLore(@Nullable List<String> lore) {
        handle.setLore(lore);
    }

    public void lore(@Nullable List<? extends Component> lore) {
        handle.lore(lore);
    }

    public void addItemFlags(ItemFlag... itemFlags) {
        handle.addItemFlags(itemFlags);
    }

    public void removeItemFlags(ItemFlag... itemFlags) {
        handle.removeItemFlags(itemFlags);
    }

    public @Nonnull Set<ItemFlag> getItemFlags() {
        return handle.getItemFlags();
    }

    public boolean hasItemFlag(@Nonnull ItemFlag flag) {
        return handle.hasItemFlag(flag);
    }

    public @Nonnull String translationKey() {
        return handle.translationKey();
    }

    public @Nonnull ItemRarity getRarity() {
        return handle.getRarity();
    }

    public boolean isRepairableBy(@Nonnull ItemStack repairMaterial) {
        return handle.isRepairableBy(repairMaterial);
    }

    public boolean canRepair(@Nonnull ItemStack toBeRepaired) {
        return handle.canRepair(toBeRepaired);
    }

    public @Nonnull ItemStack damage(int amount, @Nonnull LivingEntity livingEntity) {
        return handle.damage(amount, livingEntity);
    }

    public boolean isEmpty() {
        return handle.getType().isAir();
    }

    public ItemStack getHandle() {
        if (!isAir) return handle.clone();
        return new ItemStack(Material.AIR);
    }

    public void setHandle(@Nonnull ItemStack handle) {
        this.handle = handle.clone();
        this.handle.setAmount(1);
        nbt_handle = new NBTItem(this.handle, true);
    }
}
