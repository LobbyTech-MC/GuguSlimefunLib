package me.ddggdd135.guguslimefunlib.api.abstracts;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import com.xzavier0722.mc.plugin.slimefun4.storage.util.StorageCacheUtils;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineProcessHolder;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.core.machines.MachineProcessor;
import java.util.Objects;
import java.util.function.Consumer;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;
import me.ddggdd135.guguslimefunlib.api.CustomCraftingOperation;
import me.ddggdd135.guguslimefunlib.api.GuguMachineInfo;
import me.ddggdd135.guguslimefunlib.api.MachineMenu;
import me.ddggdd135.guguslimefunlib.api.MachineMenuWrapper;
import me.ddggdd135.guguslimefunlib.api.interfaces.InventoryBlock;
import me.ddggdd135.guguslimefunlib.script.ScriptEval;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class GuguMachineBlock extends TickingBlock
        implements InventoryBlock, MachineProcessHolder<CustomCraftingOperation> {
    @Getter
    private ScriptEval eval;

    @Getter
    private SlimefunItemStack slimefunItemStack;

    @Getter
    private MachineMenu machineMenu;

    @Getter
    private MachineProcessor<CustomCraftingOperation> machineProcessor;

    @Getter
    @Setter
    private boolean working;

    protected GuguMachineBlock(
            ItemGroup itemGroup,
            SlimefunItemStack item,
            RecipeType recipeType,
            ItemStack[] recipe,
            @Nullable MachineMenu machineMenu,
            @Nullable ScriptEval eval,
            int workingSlot) {
        super(itemGroup, item, recipeType, recipe);
        this.eval = eval;
        this.slimefunItemStack = item;
        this.machineMenu = machineMenu;
        this.machineProcessor = new MachineProcessor<>(this);

        if (eval != null) {
            this.eval.addThing("setWorking", (Consumer<Boolean>) b -> {
                working = b;
                eval.addThing("working", working);
            });
            this.eval.addThing("working", working);

            eval.doInit();

            addItemHandler(new BlockPlaceHandler(false) {
                @Override
                public void onPlayerPlace(@NotNull BlockPlaceEvent e) {
                    GuguMachineBlock.this.eval.evalFunction("onPlace", e);
                }
            });
        }

        if (machineMenu == null) return;
        if (workingSlot >= 0 && workingSlot < 54) {
            ChestMenu.MenuClickHandler menuClickHandler = machineMenu.getMenuClickHandlerDirectly(workingSlot);
            machineMenu.addMenuClickHandler(workingSlot, (player, slot, cursor, clickAction) -> {
                if (eval != null) {
                    working = true;
                    eval.addThing("working", true);
                }

                return menuClickHandler.onClick(player, slot, cursor, clickAction);
            });
        }
        this.machineProcessor.setProgressBar(machineMenu.getProgressBar());

        createPreset(this);
    }

    @Override
    public boolean isSynchronized() {
        return false;
    }

    @Override
    protected void tick(
            @Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull SlimefunBlockData slimefunBlockData) {
        if (eval != null) {
            BlockMenu blockMenu = StorageCacheUtils.getMenu(block.getLocation());
            GuguMachineInfo info =
                    new GuguMachineInfo(blockMenu, slimefunBlockData, this, slimefunItem, block, machineProcessor);
            eval.evalFunction("tick", info);
        }
    }

    @Override
    public int[] getInputSlots() {
        return machineMenu.getInputSlot();
    }

    @Override
    public int[] getOutputSlots() {
        return machineMenu.getOutputSlot();
    }

    @Override
    public void init(@Nonnull BlockMenuPreset preset) {}

    @Override
    public void newInstance(@Nonnull BlockMenu menu, @Nonnull Block block) {}

    public void createPreset(@Nonnull SlimefunItem item, @Nonnull String title) {
        new MachineMenuWrapper(Objects.requireNonNull(slimefunItemStack.getItem()), machineMenu) {
            @Override
            public void init() {
                super.init();
                GuguMachineBlock.this.init(this);
            }

            @Override
            public void newInstance(@Nonnull BlockMenu menu, @Nonnull Block block) {
                GuguMachineBlock.this.newInstance(menu, block);
            }

            @Override
            public int[] getSlotsAccessedByItemTransport(ItemTransportFlow itemTransportFlow) {
                if (itemTransportFlow == ItemTransportFlow.INSERT) return getInputSlots();
                else return getOutputSlots();
            }
        };
    }
}
