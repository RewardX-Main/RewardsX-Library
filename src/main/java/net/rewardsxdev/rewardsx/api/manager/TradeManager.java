package net.rewardsxdev.rewardsx.api.manager;

import lombok.Getter;
import lombok.Setter;
import net.rewardsxdev.rewardsx.api.sessions.TradeSession;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
public class TradeManager {
    // Mappa UUID giocatore -> TradeSession attiva
    private final Map<UUID, TradeSession> activeTrades = new ConcurrentHashMap<>();

    // Mappa UUID destinatario (target) -> UUID richiedente (requester)
    private final Map<UUID, UUID> pendingRequests = new HashMap<>();

    // Aggiunge o aggiorna una sessione di trade per un giocatore
    public void addSession(UUID playerUUID, TradeSession session) {
        activeTrades.put(playerUUID, session);
    }

    // Rimuove la sessione di un giocatore
    public void removeSession(UUID playerUUID) {
        activeTrades.remove(playerUUID);
    }

    // Recupera la sessione di trade attiva di un giocatore, null se non presente
    public TradeSession getSession(UUID playerUUID) {
        return activeTrades.get(playerUUID);
    }

    // Verifica se un giocatore è in una sessione di trade
    public boolean isTrading(UUID playerUUID) {
        return activeTrades.containsKey(playerUUID);
    }

    // --------- METODI DI GESTIONE RICHIESTE TRADE ---------

    // Aggiunge una richiesta pending: target riceve richiesta da requester
    public void addTradeRequest(UUID target, UUID requester) {
        pendingRequests.put(target, requester);
    }

    // Rimuove una richiesta pending
    public void removeTradeRequest(UUID target) {
        pendingRequests.remove(target);
    }

    // Verifica se target ha una richiesta pending da requester
    public boolean isPendingRequest(UUID target, UUID requester) {
        return pendingRequests.get(target) != null && pendingRequests.get(target).equals(requester);
    }

    // Verifica se target ha qualunque richiesta pending
    public boolean hasPendingRequest(UUID target) {
        return pendingRequests.containsKey(target);
    }

    // Ottieni il richiedente per una richiesta pending (se esiste)
    public UUID getRequesterOfPendingRequest(UUID target) {
        return pendingRequests.get(target);
    }
}
