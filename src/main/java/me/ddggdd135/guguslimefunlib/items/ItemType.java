package me.ddggdd135.guguslimefunlib.items;

import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.bukkit.Material;

public class ItemType {
    private boolean isSlimefun;
    private String sfid;
    private Material material;

    public ItemType() {}

    public ItemType(boolean isSlimefun, @Nullable String sfid, @Nonnull Material material) {
        this.isSlimefun = isSlimefun;
        this.sfid = sfid;
        this.material = material;
    }

    public void setId(@Nullable String sfid) {
        this.sfid = sfid;
    }

    public void setSlimefun(boolean slimefun) {
        isSlimefun = slimefun;
    }

    public void setMaterial(@Nonnull Material material) {
        this.material = material;
    }

    public boolean getIsSlimefun() {
        return isSlimefun;
    }

    @Nullable public String getId() {
        return sfid;
    }

    @Nonnull
    public Material getMaterial() {
        return material;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemType itemType = (ItemType) o;
        return isSlimefun == itemType.isSlimefun
                && Objects.equals(sfid, itemType.sfid)
                && material == itemType.material;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isSlimefun, sfid, material);
    }
}
