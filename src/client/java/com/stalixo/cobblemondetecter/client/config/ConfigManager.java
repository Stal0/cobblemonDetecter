package com.stalixo.cobblemondetecter.client.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = new File("config/CobbleDetecterConfig.json");

    public static Config loadConfig() {
        if (!CONFIG_FILE.exists()) {
            return new Config(100, 10.0); // Valores padrão
        }
        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            return GSON.fromJson(reader, Config.class);
        } catch (IOException e) {
            e.printStackTrace();
            return new Config(100, 10.0); // Valores padrão em caso de erro
        }
    }

    public static void saveConfig(Config config) {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(config, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
