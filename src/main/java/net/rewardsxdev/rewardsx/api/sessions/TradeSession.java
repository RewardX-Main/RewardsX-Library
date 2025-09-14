package net.rewardsxdev.rewardsx.api.sessions;

import lombok.Getter;
import lombok.Setter;
import net.rewardsxdev.rewardsx.api.API;
import net.rewardsxdev.rewardsx.api.gui.RewardsGUI;
import net.rewardsxdev.rewardsx.api.manager.TradeManager;
import net.rewardsxdev.rewardsx.api.player.RPlayer;
import net.rewardsxdev.rewardsx.api.spigot.configuration.Language;
import net.rewardsxdev.rewardsx.api.support.papi.SupportPAPI;
import net.rewardsxdev.rewardsx.api.utils.globalpaths.MessagesPath;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class TradeSession {
    @Getter
    private final Player owner;
    @Getter
    private final Player accepter;
    private final Plugin plugin;

    @Getter
    private final Map<Integer, ItemStack> ownerItems = new HashMap<>();
    @Getter
    public final Set<UUID> ignoreNextClose = new HashSet<>();
    @Getter
    private final Map<Integer, ItemStack> accepterItems = new HashMap<>();
    private final API api;

    // Metodi per settare accettazione
    @Getter
    @Setter
    private boolean ownerAccepted = false;
    @Getter
    @Setter
    private boolean accepterAccepted = false;

    public TradeSession(Player owner, Player accepter, Plugin plugin, API api) {
        this.owner = owner;
        this.accepter = accepter;
        this.plugin = plugin;
        this.api = api;
    }

    // True se entrambi hanno accettato
    public boolean isConfirmed() {
        return ownerAccepted && accepterAccepted;
    }

    // Aggiungi item
    public void putOwnerItem(int slot, ItemStack item) {
        if (item == null || item.getAmount() == 0) {
            ownerItems.remove(slot);
        } else {
            ownerItems.put(slot, item);
        }
    }

    public void putAccepterItem(int slot, ItemStack item) {
        if (item == null || item.getAmount() == 0) {
            accepterItems.remove(slot);
        } else {
            accepterItems.put(slot, item);
        }
    }

    // Reset della sessione (rimuovi tutti gli item e le accettazioni)
    public void reset() {
        ownerItems.clear();
        accepterItems.clear();
        ownerAccepted = false;
        accepterAccepted = false;
    }

    // Termina e trasferisci gli item agli utenti
    public void confirmTrade() {
        if (!isConfirmed()) return;

        Player owner = getOwner();
        Player accepter = getAccepter();

        RPlayer rOwner = api.getRserver().getSpigotRserver().getPlayer(owner.getUniqueId());
        RPlayer rAccepter = api.getRserver().getSpigotRserver().getPlayer(accepter.getUniqueId());

        // Ottieni la lista ordinata degli item offerti
        List<ItemStack> ownerItemList = getItems(true);
        List<ItemStack> accepterItemList = getItems(false);

        // Trasferisci gli oggetti dell'owner all'accepter
        for (ItemStack item : ownerItemList) {
            if (item == null) continue;
            HashMap<Integer, ItemStack> leftovers = accepter.getInventory().addItem(item.clone());
            for (ItemStack leftover : leftovers.values()) {
                accepter.getWorld().dropItemNaturally(accepter.getLocation(), leftover);
            }
        }

        // Trasferisci gli oggetti dell'accepter all'owner
        for (ItemStack item : accepterItemList) {
            if (item == null) continue;
            HashMap<Integer, ItemStack> leftovers = owner.getInventory().addItem(item.clone());
            for (ItemStack leftover : leftovers.values()) {
                owner.getWorld().dropItemNaturally(owner.getLocation(), leftover);
            }
        }

        owner.sendMessage(SupportPAPI.getSupportPAPI().replace(rOwner, Language.getMsg(rOwner, MessagesPath.MESSAGES_TRADE_REQUESTER_COMPLETED)));
        accepter.sendMessage(SupportPAPI.getSupportPAPI().replace(rAccepter, Language.getMsg(rAccepter, MessagesPath.MESSAGES_TRADE_TARGET_COMPLETED)));

        // Resetta la sessione


        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            owner.closeInventory();
            accepter.closeInventory();
            reset();
        }, 1L);

    }


    public List<ItemStack> getItems(boolean isOwner) {
        Map<Integer, ItemStack> itemsMap = isOwner ? ownerItems : accepterItems;
        List<ItemStack> items = new ArrayList<>();

        // Ordina gli slot per assicurare la stessa visualizzazione
        itemsMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> items.add(entry.getValue()));

        return items;
    }

    public void updateInventories(RewardsGUI gui) {
        // Chiamare la funzione che costruisce la GUI (devi averla a disposizione)
        // Esempio (adatta in base alla tua implementazione):
        ignoreNextClose.add(owner.getUniqueId());
        ignoreNextClose.add(accepter.getUniqueId());

        gui.openTradeInventory(owner, this, true);
        gui.openTradeInventory(accepter, this, false);
    }

}
