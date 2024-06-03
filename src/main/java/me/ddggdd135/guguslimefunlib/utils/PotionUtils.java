package me.ddggdd135.guguslimefunlib.utils;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class PotionUtils {
    public static PotionEffect parse(String potionEffect) {
        String[] split = potionEffect.split(" ");
        if (split.length != 2) {
            throw new IllegalArgumentException("错误的药水效果");
        }
        String effectName = split[0];
        int amplifier = Integer.parseInt(split[1]);

        PotionEffectType type = PotionEffectType.getByName(effectName);
        if (type == null) {
            throw new IllegalArgumentException("错误的药水效果类型");
        }

        if (amplifier <= 0) {
            throw new IllegalArgumentException("错误的药水效果等级 应为1-255");
        }

        return new PotionEffect(type, Integer.MAX_VALUE, amplifier);
    }

    public static PotionEffect[] parseAll(String... potionEffects) {
        List<PotionEffect> result = new ArrayList<>();
        for (String potionEffect : potionEffects) {
            result.add(parse(potionEffect));
        }

        return result.toArray(PotionEffect[]::new);
    }
}
