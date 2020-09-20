package me.ziim.mineenhance;

import org.bukkit.enchantments.Enchantment;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

public class CustomEnchants {
    public static final Enchantment TELEPATHY = new EnchantmentWrapper("telepathy", "Telepathy", 1);
    public static final Enchantment VEINMINER = new EnchantmentWrapper("veinminer", "Veinminer", 1);
    public static final Enchantment AREAMINE = new EnchantmentWrapper("areamine", "Areamine", 2);
    public static final Enchantment MAKERAIN = new EnchantmentWrapper("makerain", "Makerain", 1);
    public static final Enchantment SPAWNERTOUCH = new EnchantmentWrapper("spawnertouch", "Spawner Touch", 1);


    public static void register() {
        boolean telepathy = Arrays.stream(Enchantment.values()).collect(Collectors.toList()).contains(CustomEnchants.TELEPATHY);
        boolean veinminer = Arrays.stream(Enchantment.values()).collect(Collectors.toList()).contains(CustomEnchants.VEINMINER);
        boolean areamine = Arrays.stream(Enchantment.values()).collect(Collectors.toList()).contains(CustomEnchants.AREAMINE);
        boolean makerain = Arrays.stream(Enchantment.values()).collect(Collectors.toList()).contains(CustomEnchants.MAKERAIN);
        boolean spawnertouch = Arrays.stream(Enchantment.values()).collect(Collectors.toList()).contains(CustomEnchants.SPAWNERTOUCH);

        if (!telepathy)
            registerEnchantment(TELEPATHY);
        if (!veinminer)
            registerEnchantment(VEINMINER);
        if (!areamine)
            registerEnchantment(AREAMINE);
        if (!makerain)
            registerEnchantment(MAKERAIN);
        if (!spawnertouch)
            registerEnchantment(SPAWNERTOUCH);
    }

    public static void registerEnchantment(Enchantment enchant) {
        boolean registered = true;
        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
            Enchantment.registerEnchantment(enchant);
        } catch (Exception e) {
            registered = false;
            e.printStackTrace();
        }
        if (registered) {
            System.out.print("Enchantment registered!!");
        }
    }
}
