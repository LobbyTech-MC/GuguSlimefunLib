package me.ddggdd135.guguslimefunlib.api;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import me.ddggdd135.guguslimefunlib.script.ScriptEval;
import me.ddggdd135.guguslimefunlib.utils.ReflectionUtils;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.inventory.ItemStack;

public class MachineMenu extends ChestMenu {
    @Getter
    @Setter
    private ScriptEval eval;

    @Getter
    @Setter
    private int[] inputSlot;

    @Getter
    @Setter
    private int[] outputSlot;

    @Getter
    @Setter
    private ItemStack progressBar;
    @Getter
    @Setter
    private int progressSlot = 22;

    private final Map<Integer, MenuClickHandler> sourceHandlers = new HashMap<>();

    public MachineMenu(String title, BlockMenuPreset preset) {
        super(title);
        setPlayerInventoryClickable(true);

        if (preset.isSizeAutomaticallyInferred()) {
            addItem(getSize() - 1, null);
        } else setSize(getSize());

        for (int slot : preset.getPresetSlots()) {
            addItem(slot, getItemInSlot(slot));
        }

        for (int slot = 0; slot < 54; slot++) {
            if (getMenuClickHandler(slot) != null) {
                addMenuClickHandler(slot, getMenuClickHandler(slot));
            }
        }

        addMenuOpeningHandler(getMenuOpeningHandler());
        addMenuCloseHandler(getMenuCloseHandler());
        init();
    }

    public MachineMenu(String title) {
        super(title);
        setPlayerInventoryClickable(true);
        init();
    }

    public MachineMenu(String title, int size) {
        super(title, size);
        setPlayerInventoryClickable(true);
        init();
    }

    public void init() {}

    @Override
    public MachineMenu addMenuClickHandler(int slot, MenuClickHandler handler) {
        sourceHandlers.put(slot, handler);
        super.addMenuClickHandler(slot, (player, i, cursor, clickAction) -> {
            if (eval != null) {
                eval.evalFunction("onClick", player, i, cursor, clickAction);
            }

            return handler.onClick(player, i, cursor, clickAction);
        });
        return this;
    }

    public MenuClickHandler getMenuClickHandlerDirectly(int slot) {
        return sourceHandlers.get(slot);
    }

    public String getTitle() {
        try {
            return ReflectionUtils.getField(this, "title");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return "";
        }
    }

    public MachineMenu addItem(ItemStack item, int... slots) {
        for(int slot : slots) addItem(slot, item);
        return this;
    }

    public MachineMenu addItem(ItemStack item, MenuClickHandler clickHandler, int... slots) {
        for(int slot : slots) addItem(slot, item, clickHandler);
        return this;
    }
}
