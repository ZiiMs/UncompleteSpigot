package me.ziim.mineenhance;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

public class EnchantmentWrapper extends Enchantment {
    private final String name;
    private final int maxLvl;
    private final NamespacedKey Foundkey;

    public EnchantmentWrapper(String key, String name, int maxLvl) {
        super(NamespacedKey.minecraft(key));
        Foundkey = NamespacedKey.minecraft(key);
        this.name = name;
        this.maxLvl = maxLvl;
    }

    @Override
    public NamespacedKey getKey() {
        return Foundkey;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getMaxLevel() {
        return maxLvl;
    }

    @Override
    public int getStartLevel() {
        return 0;
    }

    @Override
    public EnchantmentTarget getItemTarget() {
        return null;
    }

    @Override
    public boolean isTreasure() {
        return false;
    }

    @Override
    public boolean isCursed() {
        return false;
    }

    @Override
    public boolean conflictsWith(Enchantment other) {
        return false;
    }

    @Override
    public boolean canEnchantItem(ItemStack item) {
        return false;
    }
}
