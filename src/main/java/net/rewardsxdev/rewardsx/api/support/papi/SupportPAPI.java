package net.rewardsxdev.rewardsx.api.support.papi;

import lombok.Getter;
import lombok.Setter;
import me.clip.placeholderapi.PlaceholderAPI;
import net.rewardsxdev.rewardsx.api.player.RPlayer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.List;

public class SupportPAPI {

    @Getter
    @Setter
    private static supp supportPAPI = new noPAPI();

    public interface supp {
        String replace(RPlayer p, String s);

        List<String> replace(RPlayer p, List<String> strings);
    }

    public static class noPAPI implements supp {

        @Override
        public String replace(RPlayer p, String s) {
            return s;
        }

        @Override
        public List<String> replace(RPlayer p, List<String> strings) {
            return strings;
        }
    }

    public static class withPAPI implements supp {

        @Override
        public String replace(RPlayer p, String s) {
            OfflinePlayer player = getPlayer(p);
            return PlaceholderAPI.setPlaceholders(player, s);
        }

        @Override
        public List<String> replace(RPlayer p, List<String> strings) {
            OfflinePlayer player = getPlayer(p);
            return PlaceholderAPI.setPlaceholders(player, strings);
        }
    }

    public static OfflinePlayer getPlayer(RPlayer p){
        return Bukkit.getOfflinePlayer(p.getUniqueId());
    }

}
