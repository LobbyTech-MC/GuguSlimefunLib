package me.ddggdd135.guguslimefunlib.items;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.World;
import org.bukkit.block.Biome;

@Setter
@Getter
public class SupplyInfo {
    private int defaultNormalSupply = 0;
    private Map<Biome, Integer> normalSupply = new HashMap<>();
    private int defaultNetherSupply = 0;
    private Map<Biome, Integer> netherSupply = new HashMap<>();
    private int defaultTheEndSupply = 0;
    private Map<Biome, Integer> theEndSupply = new HashMap<>();

    public void addNormalSupply(Biome biome, int count) {
        normalSupply.put(biome, count);
    }

    public void addNetherSupply(Biome biome, int count) {
        netherSupply.put(biome, count);
    }

    public void addTheEndSupply(Biome biome, int count) {
        theEndSupply.put(biome, count);
    }

    public int getDefaultSupply(@Nonnull World.Environment environment, @Nonnull Biome biome) {
        if (environment == World.Environment.NORMAL) {
            if (normalSupply.containsKey(biome)) return normalSupply.get(biome);
            return defaultNormalSupply;
        } else if (environment == World.Environment.NETHER) {
            if (netherSupply.containsKey(biome)) return netherSupply.get(biome);
            return defaultNetherSupply;
        } else if (environment == World.Environment.THE_END) {
            if (theEndSupply.containsKey(biome)) return theEndSupply.get(biome);
            return defaultTheEndSupply;
        }

        return 0;
    }
}
