package net.rewardsxdev.rewardsx.api.db.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
public class User {
    private int id;                 // nuovo campo per chiave primaria autonoma
    private UUID player;
    private String playerName;
    private String lang;
    private Timestamp lastLogin;
    private String refCode;
    private Timestamp playTime;

    // Costruttore completo
    public User(int id, UUID player, String playerName, String lang,
                Timestamp lastLogin, String refCode, Timestamp playTime) {
        this.id = id;
        this.player = player;
        this.playerName = playerName;
        this.lang = lang;
        this.lastLogin = lastLogin;
        this.refCode = refCode;
        this.playTime = playTime;
    }

    // Costruttore ausiliario (senza id, se non serve)
    public User(UUID player, String playerName, String lang,
                Timestamp lastLogin, String refCode, Timestamp playTime) {
        this.player = player;
        this.playerName = playerName;
        this.lang = lang;
        this.lastLogin = lastLogin;
        this.refCode = refCode;
        this.playTime = playTime;
    }
}

