package net.rewardsxdev.rewardsx.api.spigot.configuration;

import lombok.Getter;
import lombok.Setter;
import net.rewardsxdev.rewardsx.api.API;
import net.rewardsxdev.rewardsx.api.events.PlayerLangChangeEvent;
import net.rewardsxdev.rewardsx.api.player.RPlayer;
import net.rewardsxdev.rewardsx.api.utils.color.Color;
import net.rewardsxdev.rewardsx.api.utils.globalpaths.MessagesPath;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static net.rewardsxdev.rewardsx.api.API.MESSAGES_PATH;

public class Language extends ConfigManager {
    /**
     * -- GETTER --
     *  Get language iso code.
     */
    @Getter
    private final String iso;
    @Getter
    private final API api;
    @Setter
    private String prefix = "";
    @Setter
    private static String prefixStatic = "";
    @Getter
    private static final HashMap<UUID, Language> langByPlayer = new HashMap<>();
    /**
     * -- GETTER --
     *  Get loaded languages list.
     */
    @Getter
    private static final List<Language> languages = new ArrayList<>();
    /**
     * -- GETTER --
     *  Get server default language.
     */
    @Getter
    @Setter
    private static Language defaultLanguage;

    /**
     * Create a new configuration file.
     *
     * @param plugin config owner.
     * @param iso   config name. Do not include .yml in it.
     */
    public Language(Plugin plugin, String iso, API api) {
        super(plugin, iso, plugin.getDataFolder().getPath() + MESSAGES_PATH);
        this.iso = iso;
        this.api = api;
        languages.add(this);
    }
    /**
     * Get language display name.
     */
    public String getLangName() {
        return getYml().getString(MessagesPath.MESSAGES_NAME);
    }

    /**
     * Get message in player's language.
     */
    public static String getMsg(RPlayer player, String path) {
        if (player == null) {
            return getDefaultLanguage().m(path);
        }
        return langByPlayer.getOrDefault(player.getUniqueId(), getDefaultLanguage())
                .m(path).replace("{prefix}", (prefixStatic == null ? "" : prefixStatic));
    }

    /**
     * Retrieve a player language.
     */
    public static Language getPlayerLanguage(@NotNull RPlayer player) {
        return langByPlayer.getOrDefault(player.getUniqueId(), getDefaultLanguage());
    }

    public static Language getPlayerLanguage(UUID p) {
        return langByPlayer.getOrDefault(p, getDefaultLanguage());
    }

    /**
     * Check if a message was set.
     */
    public boolean exists(String path) {
        return getYml().get(path) != null;
    }

    /**
     * Get a string list in player's language.
     */
    public static List<String> getList(@NotNull RPlayer player, String path) {
        return langByPlayer.getOrDefault(player.getUniqueId(), getDefaultLanguage()).l(path);
    }

    @SuppressWarnings("unused")
    public void relocate(String from, String to) {
        Object fromData = getYml().get(from);
        if (null != fromData) {
            this.getYml().set(to, fromData);
            this.getYml().set(from, null);
        }
    }

    /**
     * Save a value to file if not exists.
     */
    public static void saveIfNotExists(String path, Object data) {
        for (Language l : languages) {
            if (l.getYml().get(path) == null) {
                l.set(path, data);
            }
        }
    }

    /**
     * Get a color translated message.
     */
    public String m(String path) {
        String message = getYml().getString(path);
        if (message == null) {
            System.err.println("Missing message key " + path + " in language " + iso);
            message = "MISSING_LANG";
        }

        return Color.safeTranslate(message.replace("{prefix}", (prefix == null ? "" : prefix)));
    }


    /**
     * Get a color translated list.
     */
    public List<String> l(String path) {
        List<String> lines = getYml().getStringList(path);
        if (lines == null) {
            System.err.println("Missing message list key " + path + " in language " + iso);
            lines = Collections.emptyList();
        }
        return Color.safeTranslate(lines);
    }

    /**
     * Check if a language exists.
     */
    public static boolean isLanguageExist(String iso) {
        for (Language l : languages) {
            if (l.iso.equalsIgnoreCase(iso)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get language with given info.
     *
     * @return null if you could not find.
     */
    public static Language getLang(String iso) {
        for (Language l : languages) {
            if (l.iso.equalsIgnoreCase(iso)) {
                return l;
            }
        }
        return getDefaultLanguage();
    }

    /**
     * Change a player language and refresh
     * scoreboard and custom join items.
     */
    public static boolean setPlayerLanguage(UUID uuid, String iso) {
        if (iso == null) {
            if (langByPlayer.containsKey(uuid)) {
                Player player = Bukkit.getPlayer(uuid);
                if (player != null && player.isOnline()) {
                    PlayerLangChangeEvent e = new PlayerLangChangeEvent(player, langByPlayer.get(uuid).iso, getDefaultLanguage().iso);
                    Bukkit.getPluginManager().callEvent(e);
                    if (e.isCancelled()) return false;
                }
            }
            langByPlayer.remove(uuid);
            return true;
        }

        Language newLang = Language.getLang(iso);
        if (newLang == null) return false;
        Language oldLang = Language.getPlayerLanguage(uuid);
        if (oldLang.getIso().equals(newLang.getIso())) return false;

        Player player = Bukkit.getPlayer(uuid);
        if (player != null && player.isOnline()) {
            PlayerLangChangeEvent e = new PlayerLangChangeEvent(player, oldLang.getIso(), newLang.getIso());
            Bukkit.getPluginManager().callEvent(e);
            if (e.isCancelled()) return false;
        }

        if (Language.getDefaultLanguage().getIso().equals(newLang.getIso())) {
            langByPlayer.remove(uuid);
            return true;
        }

        if (langByPlayer.containsKey(uuid)) {
            langByPlayer.replace(uuid, newLang);
        } else {
            langByPlayer.put(uuid, newLang);
        }
        return true;
    }

}
