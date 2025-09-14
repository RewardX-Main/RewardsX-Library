package net.rewardsxdev.rewardsx.api.utils.mojang;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class PremiumUUID {
    /**
     * Restituisce l'UUID premium di username, oppure null se non è premium/inesistente.
     */
    public static CompletableFuture<UUID> lookupPremiumUUID(String username) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                HttpURLConnection con = (HttpURLConnection)
                        new URL("https://api.mojang.com/users/profiles/minecraft/"
                                + URLEncoder.encode(username, StandardCharsets.UTF_8))
                                .openConnection();

                con.setConnectTimeout(5000);
                con.setReadTimeout(5000);

                if (con.getResponseCode() != 200) {   // 204 ⇒ non-premium
                    return null;
                }

                String json;
                try (InputStream in = con.getInputStream()) {
                    json = new String(in.readAllBytes(), StandardCharsets.UTF_8);
                }

                JsonObject obj = JsonParser.parseString(json).getAsJsonObject();
                String raw = obj.get("id").getAsString();        // senza trattini
                return UUID.fromString(raw.replaceFirst(
                        "(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})",
                        "$1-$2-$3-$4-$5"));
            } catch (IOException e) {
                e.printStackTrace();
                return null;          // rete KO → trattalo come non-premium
            }
        });
    }

}
