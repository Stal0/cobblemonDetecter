package com.stalixo.cobblemondetecter.client.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_DIR = Paths.get("config");
    private static final Path CONFIG_FILE = CONFIG_DIR.resolve("CobbleDetecterConfig.json");

    public static Config loadConfig() {
        if (Files.notExists(CONFIG_FILE)) {
            System.out.println("Config file does not exist, creating default config.");
            saveConfig(new Config(100, 10.0)); // Salva a configuração padrão se o arquivo não existir
            return new Config(100, 10.0); // Retorna valores padrão
        }
        try (FileReader reader = new FileReader(CONFIG_FILE.toFile())) {
            System.out.println("Config file found, loading config.");
            return GSON.fromJson(reader, Config.class);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading config file, using default values.");
            return new Config(100, 10.0); // Valores padrão em caso de erro
        }
    }

    public static void saveConfig(Config config) {
        try {
            if (Files.notExists(CONFIG_DIR)) {
                Files.createDirectories(CONFIG_DIR); // Cria o diretório de configuração se não existir
            }
            try (FileWriter writer = new FileWriter(CONFIG_FILE.toFile())) {
                GSON.toJson(config, writer);
                System.out.println("Config file saved.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error saving config file.");
        }
    }
}
