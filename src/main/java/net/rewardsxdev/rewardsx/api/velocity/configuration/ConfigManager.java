package net.rewardsxdev.rewardsx.api.velocity.configuration;

import com.velocitypowered.api.plugin.PluginContainer;
import lombok.Getter;
import net.md_5.bungee.config.Configuration;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.file.Path;
import java.util.*;
import org.slf4j.Logger;
import java.util.stream.Collectors;

public class ConfigManager {

    private final PluginContainer plugin;
    private final Logger logger;
    private File file;
    @Getter
    private Map<String, Object> yml;
    private final String name;
    private boolean firstTime = false;

    public ConfigManager(PluginContainer plugin, Logger logger, String name, Path dirPath) {
        this.plugin = plugin;
        this.logger = logger;
        this.name = name;
        File dir = dirPath.toFile();

        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                logger.error("Could not create directory: " + dirPath);
                return;
            }
        }

        this.file = new File(dir, name + ".yml");

        if (!file.exists()) {
            logger.info("Creating " + file.getPath());
            try (InputStream in = getResourceAsStream(name + ".yml")) {
                if (in != null) {
                    copy(in, file);
                } else {
                    if (!file.createNewFile()) {
                        logger.error("Failed to create config file: " + file.getPath());
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

    public void addHeader(String headerText) {
        if (file == null || headerText == null) return;

        try {
            // Leggi il contenuto originale del file
            List<String> originalLines = new ArrayList<>();
            if (file.exists()) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    originalLines = reader.lines().collect(Collectors.toList());
                }
            }

            // Prepara le righe dell'header con il simbolo di commento YAML
            List<String> headerLines = Arrays.stream(headerText.split("\n"))
                    .map(line -> "# " + line)
                    .collect(Collectors.toList());

            // Controlla se l'header è già presente
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
            logger.error("Impossibile aggiungere l'header al file di configurazione: " + file.getPath());
            e.printStackTrace();
        }
    }



    private InputStream getResourceAsStream(String resource) {
        // Sostituisci con il metodo corretto per caricare risorse dal jar del plugin Velocity
        return getClass().getClassLoader().getResourceAsStream(resource);
    }

    public void load() {
        try (InputStream input = new FileInputStream(file)) {
            Yaml yaml = new Yaml();
            this.yml = yaml.load(input);
            if (this.yml == null) this.yml = new HashMap<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reload() {
        load();
    }

    public void save() {
        try (Writer writer = new FileWriter(file)) {
            Yaml yaml = new Yaml();
            yaml.dump(yml, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object get(String path) {
        String[] keys = path.split("\\.");
        Map<String, Object> section = yml;
        for (int i = 0; i < keys.length - 1; i++) {
            Object next = section.get(keys[i]);
            if (!(next instanceof Map)) return null;
            section = (Map<String, Object>) next;
        }
        return section.get(keys[keys.length - 1]);
    }

    public String getString(String path) {
        Object val = get(path);
        return val != null ? val.toString() : null;
    }

    public int getInt(String path) {
        Object val = get(path);
        if (val instanceof Number) return ((Number) val).intValue();
        try { return Integer.parseInt(val.toString()); } catch (Exception e) { return 0; }
    }



    public boolean getBoolean(String path) {
        Object val = get(path);
        if (val instanceof Boolean) return (Boolean) val;
        if (val != null) return Boolean.parseBoolean(val.toString());
        return false;
    }

    public List<String> getStringList(String path) {
        Object val = get(path);
        if (val instanceof List) {
            List<?> list = (List<?>) val;
            List<String> strList = new ArrayList<>();
            for (Object o : list) strList.add(o.toString());
            return strList;
        }
        return Collections.emptyList();
    }

    public void set(String path, Object value) {
        String[] keys = path.split("\\.");
        Map<String, Object> section = yml;
        for (int i = 0; i < keys.length - 1; i++) {
            section = (Map<String, Object>) section.computeIfAbsent(keys[i], k -> new HashMap<>());
        }
        section.put(keys[keys.length - 1], value);
        save();
    }

    public void addDefault(String path, Object value) {
        if (get(path) == null) set(path, value);
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
