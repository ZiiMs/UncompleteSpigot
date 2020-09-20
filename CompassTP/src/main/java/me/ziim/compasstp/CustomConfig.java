package me.ziim.compasstp;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class CustomConfig {

    private static File file;
    private static FileConfiguration customFile;

    public static void setup() {
        file = new File(CompassTP.getPlugin(CompassTP.class).getDataFolder(), "data.yml");
        if (!file.exists()) {
            System.out.print("File doesnt exist!");
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.print("Could not create the file.");
            }
        }
        customFile = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get() {
        return customFile;
    }

    public static void save() {
        try {
            customFile.save(file);
        } catch (IOException e) {
            System.out.print("Couldnt save file!");
        }
    }

    public static void reload() {
        customFile = YamlConfiguration.loadConfiguration(file);
    }
}
