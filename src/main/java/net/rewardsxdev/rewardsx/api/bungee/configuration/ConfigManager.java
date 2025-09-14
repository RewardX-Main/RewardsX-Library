package net.rewardsxdev.rewardsx.api.bungee.configuration;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class ConfigManager {


    private final Plugin plugin;
    private File file;
    private Configuration yml;
    private final String name;
    private boolean firstTime = false;

    public ConfigManager(Plugin plugin, String name, String dirPath) {
        this.plugin = plugin;
        this.name = name;
        File dir = new File(dirPath);

        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                plugin.getLogger().log(Level.SEVERE, "Could not create directory: " + dirPath);
                return;
            }
        }

        this.file = new File(dir, name + ".yml");

        if (!file.exists()) {
            plugin.getLogger().info("Creating " + file.getPath());
            try (InputStream in = plugin.getResourceAsStream(name + ".yml")) {
                if (in != null) {
                    copy(in, file);
                } else {
                    if (!file.createNewFile()) {
                        plugin.getLogger().log(Level.SEVERE, "Failed to create config file: " + file.getPath());
                        return;
                    }
                }
                firstTime = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        load();
    }

    public void load() {
        try {
            this.yml = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addHeader(String headerText) {
        if (file == null || headerText == null) return;

        try {
            // Leggi il contenuto originale del file, se esiste
            List<String> originalLines = new ArrayList<>();
            if (file.exists()) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    originalLines = reader.lines().collect(Collectors.toList());
                }
            }

            // Prepara le righe dell'header con commento
            List<String> headerLines = Arrays.stream(headerText.split("\n"))
                    .map(line -> "# " + line)
                    .collect(Collectors.toList());

            // Controlla se l'header è già presente all'inizio del file
            boolean headerAlreadyPresent = originalLines.size() >= headerLines.size();
            if (headerAlreadyPresent) {
                for (int i = 0; i < headerLines.size(); i++) {
                    if (!originalLines.get(i).trim().equals(headerLines.get(i).trim())) {
                        headerAlreadyPresent = false;
                        break;
                    }
                }
            }

            if (headerAlreadyPresent) return; // Header già presente, non fare nulla

            // Scrivi l'header e poi il contenuto originale
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (String line : headerLines) {
                    writer.write(line);
                    writer.newLine();
                }
                writer.newLine(); // linea vuota dopo header

                for (String line : originalLines) {
                    writer.write(line);
                    writer.newLine();
                }
            }

        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Impossibile aggiungere l'header al file di configurazione: " + file.getPath(), e);
        }
    }

    public void reload() {
        load();
    }

    public void save() {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(yml, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Configuration getYml() {
        return yml;
    }

    public void set(String path, Object value) {
        yml.set(path, value);
        save();
    }

    public String getString(String path) {
        return yml.getString(path);
    }

    public int getInt(String path) {
        return yml.getInt(path);
    }

    public void addDefault(String path, Object value) {
        if (!getYml().contains(path)) {
            getYml().set(path, value);
            save();
        }
    }


    public boolean getBoolean(String path) {
        return yml.getBoolean(path);
    }

    public List<String> getStringList(String path) {
        return yml.getStringList(path);
    }

    public boolean isFirstTime() {
        return firstTime;
    }

    private void copy(InputStream in, File file) throws IOException {
        try (OutputStream out = new FileOutputStream(file)) {
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        }
    }



}
