package me.ddggdd135.guguslimefunlib.api;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.Getter;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MachineMenuWrapper extends BlockMenuPreset {
    @Getter
    private MachineMenu machineMenu;

    public MachineMenuWrapper(@Nonnull SlimefunItem slimefunItem, @Nonnull MachineMenu machineMenu) {
        super(slimefunItem.getId(), machineMenu.getTitle());
        this.machineMenu = machineMenu;
        init();
    }

    @Override
    public void init() {}

    @Override
    public boolean canOpen(@Nonnull Block block, @Nonnull Player player) {
        if (player.hasPermission("slimefun.inventory.bypass")) {
            return true;
        } else {
            return getSlimefunItem().canUse(player, false)
                    && Slimefun.getProtectionManager()
                            .hasPermission(player, block.getLocation(), Interaction.INTERACT_BLOCK);
        }
    }

    @Override
    public int[] getSlotsAccessedByItemTransport(ItemTransportFlow itemTransportFlow) {
        if (itemTransportFlow == ItemTransportFlow.INSERT) return machineMenu.getInputSlot();
        else return machineMenu.getOutputSlot();
    }

    @Override
    public int[] getSlotsAccessedByItemTransport(DirtyChestMenu menu, ItemTransportFlow flow, ItemStack item) {
        return getSlotsAccessedByItemTransport(flow);
    }

    @Override
    public void replaceExistingItem(int slot, ItemStack item) {
        throw new UnsupportedOperationException("BlockMenuPreset does not support this method.");
    }

    @Override
    public ChestMenu addItem(int slot, @Nullable ItemStack item) {
        return machineMenu.addItem(slot, item);
    }

    @Override
    public ChestMenu addMenuClickHandler(int slot, MenuClickHandler handler) {
        return machineMenu.addMenuClickHandler(slot, handler);
    }

    @Nonnull
    public ChestMenu setSize(int size) {
        return machineMenu.setSize(size);
    }

    public int getSize() {
        return machineMenu.getSize();
    }

    public String getTitle() {
        return machineMenu.getTitle();
    }

    @Nonnull
    public Set<Integer> getInventorySlots() {
        Set<Integer> emptySlots = new HashSet<>();
        Set<Integer> occupiedSlots = getOccupiedSlots();

        if (isSizeAutomaticallyInferred()) {
            for (int i = 0; i < toInventory().getSize(); i++) {
                if (!occupiedSlots.contains(i)) {
                    emptySlots.add(i);
                }
            }
        } else {
            for (int i = 0; i < getSize(); i++) {
                if (!occupiedSlots.contains(i)) {
                    emptySlots.add(i);
                }
            }
        }

        return emptySlots;
    }

    protected void clone(@Nonnull ChestMenu menu) {
        menu.setPlayerInventoryClickable(true);

        if (isSizeAutomaticallyInferred()) {
            menu.addItem(getSize() - 1, null);
        } else menu.setSize(getSize());

        for (int slot : getOccupiedSlots()) {
            menu.addItem(slot, getItemInSlot(slot));
        }

        if (menu instanceof BlockMenu blockMenu) {
            newInstance(blockMenu, blockMenu.getLocation());
        }

        for (int slot = 0; slot < 54; slot++) {
            if (getMenuClickHandler(slot) != null) {
                menu.addMenuClickHandler(slot, getMenuClickHandler(slot));
            }
        }

        menu.addMenuOpeningHandler(getMenuOpeningHandler());
        menu.addMenuCloseHandler(getMenuCloseHandler());
    }

    public Set<Integer> getOccupiedSlots() {
        Set<Integer> occupiedSlots = new HashSet<>();
        for (int i = 0; i < getSize(); i++) {
            ItemStack itemStack = getItemInSlot(i);
            if (itemStack != null && !itemStack.getType().isAir()) occupiedSlots.add(i);
        }

        return occupiedSlots;
    }
}
