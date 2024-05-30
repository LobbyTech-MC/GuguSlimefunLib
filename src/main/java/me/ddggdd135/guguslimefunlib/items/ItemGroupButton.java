package me.ddggdd135.guguslimefunlib.items;

import io.github.thebusybiscuit.slimefun4.api.items.groups.NestedItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.groups.SubItemGroup;
import me.ddggdd135.guguslimefunlib.libraries.Colors.CMIChatColor;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ItemGroupButton extends SubItemGroup {
    private final String[] actions;

    public ItemGroupButton(
            NamespacedKey key, NestedItemGroup parent, ItemStack item, int tier, String... actions) {
        super(key, parent, item, tier);

        this.actions = actions;
    }

    public void run(Player p) {
        if (actions != null) {
            for (String action : actions) {
                if (action.split(" ").length < 2) {
                    Bukkit.getConsoleSender().sendMessage(org.bukkit.ChatColor.YELLOW + "在" + getKey().getKey() + "物品组按钮中发现未知的操作格式: " + action);
                    continue;
                }

                int index = action.indexOf(" ");
                String type = action.substring(0, index);
                String content = action.substring(index + 1);
                switch (type) {
                    case "link" -> {
                        p.sendMessage(CMIChatColor.translate("&e单击此处: "));
                        TextComponent link = new TextComponent(content);
                        link.setColor(ChatColor.GRAY);

                        ClickEvent spigotClickEvent = new ClickEvent(ClickEvent.Action.OPEN_URL, content);
                        link.setClickEvent(spigotClickEvent);

                        p.sendMessage(link);
                    }
                    case "console" -> {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), content.replaceAll("%player%", p.getName()));
                    }
                    default -> Bukkit.getConsoleSender().sendMessage( org.bukkit.ChatColor.YELLOW + getKey().getKey() + "物品组按钮中发现未知的操作类型: " + action);
                }
            }
        }
    }
}
