package net.rewardsxdev.rewardsx.api.addon;

import net.rewardsxdev.rewardsx.api.API;
import net.rewardsxdev.rewardsx.api.instance.PlatformAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ServiceLoader;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

/**
 * Carica dinamicamente tutti i JAR presenti nella cartella Addons
 * e registra le implementazioni di {@link RewardsXAddon} trovate
 * tramite {@link ServiceLoader}.
 *
 * <p>La classe è stateless: tutti i metodi sono statici.</p>
 */
public final class AddonLoader {
    private final API api;
    private static PlatformAdapter adapter;
    private static final String JAR_GLOB = "*.jar";

    public AddonLoader(API api) {
        this.api = api;
        adapter = api.getInstance();
    }

    /* ------------------------------------------------------------------ */
    /* API pubblica                                                       */
    /* ------------------------------------------------------------------ */

    /**
     * Scansiona <strong>una sola volta</strong> la cartella ricevuta e,
     * per ciascun JAR trovato, ne tenta il caricamento.
     *
     * @param addonsRoot percorso di <code>…/RewardsX/Addons</code>
     */
    public static void loadAll(Path addonsRoot) {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(addonsRoot, JAR_GLOB)) {
            for (Path jar : stream) {
                adapter.debug("Loading add-on JAR: " + jar.getFileName());
                loadSingleJar(jar);
            }
        } catch (IOException ioe) {
            adapter.error("Cannot list add-on directory " + addonsRoot + ioe);
        }
    }

    /* ------------------------------------------------------------------ */
    /* Implementazione interna                                            */
    /* ------------------------------------------------------------------ */

    /**
     * Carica un singolo JAR, ne cerca le implementazioni di
     * {@link RewardsXAddon} tramite il meccanismo SPI e le registra.
     *
     * @param jar percorso al file .jar dell’add-on
     */
    private static void loadSingleJar(Path jar) {
        adapter.debug("[AddonLoader] Scanning " + jar.getFileName());

        try {
            URLClassLoader cl = new URLClassLoader(
                    new URL[]{ jar.toUri().toURL() },
                    AddonLoader.class.getClassLoader()
            );
            adapter.debug("[AddonLoader] ClassLoader created: " + cl);

            // STEP 1: Apri il jar
            try (JarInputStream jarInput = new JarInputStream(Files.newInputStream(jar))) {
                adapter.debug("[AddonLoader] Opened JAR: " + jar.getFileName());

                JarEntry entry;
                int classCount = 0;
                while ((entry = jarInput.getNextJarEntry()) != null) {
                    if (entry.getName().endsWith(".class")) {
                        classCount++;
                        String className = entry.getName()
                                .replace("/", ".")
                                .replace(".class", "");

                        adapter.debug("[AddonLoader] Found class: " + className);

                        try {
                            Class<?> clazz = cl.loadClass(className);
                            adapter.debug("[AddonLoader] Loaded class: " + clazz.getName());

                            if (RewardsXAddon.class.isAssignableFrom(clazz)) {
                                adapter.debug("[AddonLoader] Class " + clazz.getName() + " implements RewardsXAddon");

                                if (!Modifier.isAbstract(clazz.getModifiers())) {
                                    adapter.debug("[AddonLoader] Class " + clazz.getName() + " is concrete, instantiating...");

                                    RewardsXAddon addon = (RewardsXAddon) clazz.getDeclaredConstructor().newInstance();
                                    adapter.debug("[AddonLoader] Instantiated addon: " + addon.getAddonName());

                                    AddonRegistry.register(addon);
                                    adapter.debug("[AddonLoader] Registered addon: " + addon.getAddonName());
                                    return;
                                } else {
                                    adapter.debug("[AddonLoader] Class " + clazz.getName() + " is abstract, skipping");
                                }
                            }

                        } catch (ClassNotFoundException e) {
                            adapter.debug("[AddonLoader] Classe " + className + " non trovata" + e);
                        } catch (NoSuchMethodException e) {
                            adapter.debug("[AddonLoader] Nessun costruttore vuoto in " + className + e);
                        } catch (InvocationTargetException e) {
                            adapter.debug("[AddonLoader] Errore nel costruttore di " + className + e.getCause());
                        } catch (Throwable t) {
                            adapter.debug("[AddonLoader] Impossibile caricare " + className + t);
                        }

                    }
                }

                adapter.debug("[AddonLoader] Total classes scanned: " + classCount);
                adapter.info("[AddonLoader] No RewardsXAddon implementation found in " + jar.getFileName());
            }

        } catch (Exception ex) {
            adapter.info("[AddonLoader] Cannot load add-on " + jar.getFileName() + ": "+ ex.getMessage() + ex);
        }
    }


}

