package de.welikecoding.misc.customskulls.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ItemBuilder {

    private final ItemStack itemStack;
    private final ItemMeta itemMeta;

    /**
     * @param material The material the item should be
     * @param amount The amount of items you want to have
     */
    public ItemBuilder(Material material, int amount) {

        this.itemStack = new ItemStack(material, amount);
        this.itemMeta = this.itemStack.getItemMeta();

    }

    /**
     * @param displayName The name the item should have
     */
    public ItemBuilder setDisplayName(String displayName) {
        this.itemMeta.setDisplayName(displayName);
        return this;
    }

    /**
     * @param lines The strings for the item lore
     */
    public ItemBuilder setLore(String... lines) {
        this.itemMeta.setLore(Arrays.asList(lines));
        return this;
    }

    /**
     * @return The created item as an itemstack
     */
    public ItemStack build() {
        this.updateMeta();
        return this.itemStack;
    }

    private void updateMeta() {
        this.itemStack.setItemMeta(this.itemMeta);
    }

}
