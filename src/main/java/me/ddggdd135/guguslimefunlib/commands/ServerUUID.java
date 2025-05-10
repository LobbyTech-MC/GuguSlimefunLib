package me.ddggdd135.guguslimefunlib.commands;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import me.ddggdd135.guguslimefunlib.GuguSlimefunLib;
import me.ddggdd135.guguslimefunlib.libraries.colors.CMIChatColor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class ServerUUID implements TabExecutor {
    private static List<String> WHITE_LIST = List.of("JWJUN233233", "balugaq");

    @Override
    public boolean onCommand(
            @Nonnull CommandSender commandSender,
            @Nonnull Command command,
            @Nonnull String s,
            @Nonnull String[] strings) {
        if (!commandSender.hasPermission("guguslimefunlib.server_uuid")) {
            if (!(commandSender instanceof Player player && WHITE_LIST.contains(player.getName()))) {
                commandSender.sendMessage(CMIChatColor.translate("&a你没有权限使用这个命令"));
                return false;
            }
        }

        TextComponent textComponent = Component.text(
                        CMIChatColor.translate("&eUUID: " + GuguSlimefunLib.getServerUUID()))
                .clickEvent(ClickEvent.clickEvent(
                        ClickEvent.Action.COPY_TO_CLIPBOARD,
                        GuguSlimefunLib.getServerUUID().toString()))
                .hoverEvent(HoverEvent.showText(Component.text(CMIChatColor.translate("&e点击复制"))));
        commandSender.sendMessage(textComponent);

        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(
            @Nonnull CommandSender commandSender,
            @Nonnull Command command,
            @Nonnull String s,
            @Nonnull String[] strings) {
        return new ArrayList<>();
    }
}
