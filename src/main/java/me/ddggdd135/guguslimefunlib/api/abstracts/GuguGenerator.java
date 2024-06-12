package me.ddggdd135.guguslimefunlib.api.abstracts;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import com.xzavier0722.mc.plugin.slimefun4.storage.util.StorageCacheUtils;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetProvider;
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineProcessHolder;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.core.machines.MachineProcessor;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.operations.FuelOperation;
import io.github.thebusybiscuit.slimefun4.libraries.commons.lang.Validate;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.github.thebusybiscuit.slimefun4.utils.NumberUtils;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import io.github.thebusybiscuit.slimefun4.utils.itemstack.ItemStackWrapper;
import java.util.*;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;
import me.ddggdd135.guguslimefunlib.api.MachineMenu;
import me.ddggdd135.guguslimefunlib.api.MachineMenuWrapper;
import me.ddggdd135.guguslimefunlib.api.interfaces.InventoryBlock;
import me.ddggdd135.guguslimefunlib.items.AdvancedCustomItemStack;
import me.ddggdd135.guguslimefunlib.items.GuguSlimefunItem;
import me.ddggdd135.guguslimefunlib.libraries.colors.CMIChatColor;
import me.ddggdd135.guguslimefunlib.script.ScriptEval;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ClickAction;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineFuel;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GuguGenerator extends GuguSlimefunItem
        implements InventoryBlock, MachineProcessHolder<FuelOperation>, EnergyNetProvider, RecipeDisplayItem {
    private static final int[] border =
            new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 13, 31, 36, 37, 38, 39, 40, 41, 42, 43, 44};
    private static final int[] border_in = new int[] {9, 10, 11, 12, 18, 21, 27, 28, 29, 30};
    private static final int[] border_out = new int[] {14, 15, 16, 17, 23, 26, 32, 33, 34, 35};

    @Getter
    private ScriptEval eval;

    @Getter
    @Setter
    private int capacity;

    @Getter
    @Setter
    private int energyProduction;

    @Getter
    @Nullable private MachineMenu machineMenu;

    @Getter
    private Set<MachineFuel> fuelTypes = new HashSet<>();

    @Getter
    private MachineProcessor<FuelOperation> machineProcessor;

    public GuguGenerator(
            ItemGroup itemGroup,
            SlimefunItemStack item,
            RecipeType recipeType,
            ItemStack[] recipe,
            @Nullable MachineMenu machineMenu,
            @Nullable ScriptEval eval) {
        super(itemGroup, item, recipeType, recipe);
        this.eval = eval;
        this.machineMenu = machineMenu;
        this.machineProcessor = new MachineProcessor<>(this);

        if (eval != null) {

            eval.doInit();

            addItemHandler(new BlockPlaceHandler(false) {
                @Override
                public void onPlayerPlace(@Nonnull BlockPlaceEvent e) {
                    GuguGenerator.this.eval.evalFunction("onPlace", e);
                }
            });
        }
        addItemHandler(new BlockBreakHandler(false, false) {
            @Override
            public void onPlayerBreak(
                    @Nonnull BlockBreakEvent blockBreakEvent,
                    @Nonnull ItemStack itemStack,
                    @Nonnull List<ItemStack> drops) {
                Block block = blockBreakEvent.getBlock();
                Location loc = block.getLocation();
                BlockMenu blockMenu = StorageCacheUtils.getMenu(loc);
                if (blockMenu != null) {
                    blockMenu.dropItems(loc, getInputSlots());
                    blockMenu.dropItems(loc, getOutputSlots());
                }

                machineProcessor.endOperation(block);

                if (eval != null) {
                    eval.evalFunction("onBreak", blockBreakEvent, itemStack, drops);
                }
            }
        });

        if (machineMenu == null) createPreset(this);
        else
            new MachineMenuWrapper(this, machineMenu) {
                @Override
                public void init() {
                    super.init();
                    GuguGenerator.this.init(this);
                }

                @Override
                public void newInstance(@Nonnull BlockMenu menu, @Nonnull Block block) {
                    super.newInstance(menu, block);
                    GuguGenerator.this.newInstance(menu, block);
                }

                @Override
                public int[] getSlotsAccessedByItemTransport(ItemTransportFlow itemTransportFlow) {
                    if (itemTransportFlow == ItemTransportFlow.INSERT) return getInputSlots();
                    else return getOutputSlots();
                }
            };
    }

    @Override
    public int[] getInputSlots() {
        if (machineMenu != null) return machineMenu.getInputSlot();
        return new int[] {19, 20};
    }

    @Override
    public int[] getOutputSlots() {
        if (machineMenu != null) return machineMenu.getOutputSlot();
        return new int[] {24, 25};
    }

    @Override
    public void init(@Nonnull BlockMenuPreset preset) {
        if (preset instanceof MachineMenuWrapper) return;
        for (int i : border) {
            preset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }

        for (int i : border_in) {
            preset.addItem(i, ChestMenuUtils.getInputSlotTexture(), ChestMenuUtils.getEmptyClickHandler());
        }

        for (int i : border_out) {
            preset.addItem(i, ChestMenuUtils.getOutputSlotTexture(), ChestMenuUtils.getEmptyClickHandler());
        }

        for (int i : getOutputSlots()) {
            preset.addMenuClickHandler(i, new ChestMenu.AdvancedMenuClickHandler() {

                @Override
                public boolean onClick(Player p, int slot, ItemStack cursor, ClickAction action) {
                    return false;
                }

                @Override
                public boolean onClick(
                        InventoryClickEvent e, Player p, int slot, ItemStack cursor, ClickAction action) {
                    return cursor == null || cursor.getType() == null || cursor.getType() == Material.AIR;
                }
            });
        }

        preset.addItem(
                22,
                new AdvancedCustomItemStack(Material.BLACK_STAINED_GLASS_PANE, " "),
                ChestMenuUtils.getEmptyClickHandler());
    }

    @Override
    public void newInstance(@Nonnull BlockMenu menu, @Nonnull Block block) {}

    public int getGeneratedOutput(Location location, SlimefunBlockData data) {
        BlockMenu blockMenu = StorageCacheUtils.getMenu(location);
        if (blockMenu == null) return 0;
        FuelOperation operation = machineProcessor.getOperation(location);

        if (operation != null) {
            if (!operation.isFinished()) {
                machineProcessor.updateProgressBar(blockMenu, 22, operation);

                if (isChargeable()) {
                    int charge = getCharge(location, data);

                    if (getCapacity() - charge >= getEnergyProduction()) {
                        operation.addProgress(1);
                        return getEnergyProduction();
                    }

                    return 0;
                } else {
                    operation.addProgress(1);
                    return getEnergyProduction();
                }
            } else {
                ItemStack fuel = operation.getIngredient();

                if (isBucket(fuel)) {
                    blockMenu.pushItem(new ItemStack(Material.BUCKET), getOutputSlots());
                }

                blockMenu.replaceExistingItem(22, new AdvancedCustomItemStack(Material.BLACK_STAINED_GLASS_PANE, " "));

                machineProcessor.endOperation(location);
                return 0;
            }
        } else {
            Map<Integer, Integer> found = new HashMap<>();
            MachineFuel fuel = findRecipe(blockMenu, found);

            if (fuel != null) {
                for (Map.Entry<Integer, Integer> entry : found.entrySet()) {
                    blockMenu.consumeItem(entry.getKey(), entry.getValue());
                }

                machineProcessor.startOperation(location, new FuelOperation(fuel));
            }

            return 0;
        }
    }

    public void registerFuel(@Nonnull MachineFuel fuel) {
        Validate.notNull(fuel, "Machine Fuel cannot be null!");
        fuelTypes.add(fuel);
    }

    private boolean isBucket(@Nullable ItemStack item) {
        if (item == null) {
            return false;
        }

        ItemStackWrapper wrapper = ItemStackWrapper.wrap(item);
        return item.getType() == Material.LAVA_BUCKET
                || SlimefunUtils.isItemSimilar(wrapper, SlimefunItems.FUEL_BUCKET, true)
                || SlimefunUtils.isItemSimilar(wrapper, SlimefunItems.OIL_BUCKET, true);
    }

    private MachineFuel findRecipe(BlockMenu menu, Map<Integer, Integer> found) {
        for (MachineFuel fuel : fuelTypes) {
            for (int slot : getInputSlots()) {
                if (fuel.test(menu.getItemInSlot(slot))) {
                    found.put(slot, fuel.getInput().getAmount());
                    return fuel;
                }
            }
        }

        return null;
    }

    @Override
    public List<ItemStack> getDisplayRecipes() {
        List<ItemStack> list = new ArrayList<>();

        for (MachineFuel fuel : fuelTypes) {
            ItemStack item = fuel.getInput().clone();
            ItemMeta im = item.getItemMeta();
            List<String> lore = new ArrayList<>();
            lore.add("&8\u21E8 &7持续时间 " + NumberUtils.getTimeLeft(fuel.getTicks() / 2));
            lore.add("&8\u21E8 &e\u26A1 &7" + getEnergyProduction() * 2 + " J/s");
            lore.add("&8\u21E8 &e\u26A1 &7最大储存量: "
                    + NumberUtils.getCompactDouble((double) fuel.getTicks() * getEnergyProduction())
                    + " J");
            im.setLore(CMIChatColor.translate(lore));
            item.setItemMeta(im);
            list.add(item);
        }

        return list;
    }
}
