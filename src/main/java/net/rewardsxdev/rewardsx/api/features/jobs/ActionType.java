package net.rewardsxdev.rewardsx.api.features.jobs;

public enum ActionType {
    BREAK_BLOCK,         // Distruggere blocchi
    BREAK,               // Distruggere blocchi (BlockBreakEvent)
    TNT_BREAK,           // Distruggere blocchi con TNT o Minecart TNT
    PLACE_BLOCK,         // Piazzare blocchi
    PLACE,               // Piazzare blocchi (BlockPlaceEvent)
    KILL_ENTITY,         // Uccidere creature o giocatori
    KILL,                // Uccidere tramite EntityDeathEvent
    MM_KILL,             // Uccidere Mythic Mobs (plugin MythicMobs)
    FISHING,             // Pescare pesci (PlayerFishEvent)
    CRAFT_ITEM,          // Craftare un item
    CRAFT,               // Craftare tramite CraftItemEvent
    EAT_ITEM,            // Mangiare cibo
    EAT,                 // Mangiare tramite PlayerItemConsumeEvent
    SMELT_ITEM,          // Smeltare oggetti (richiede workaround)
    BREW_ITEM,           // Preparare pozioni (richiede workaround)
    ENCHANT_ITEM,        // Incantare oggetti
    ENCHANT,             // Incantare tramite EnchantItemEvent
    REPAIR_ITEM,         // Riparare oggetti
    BREED_ENTITY,        // Far accoppiare animali
    BREED,               // Far accoppiare tramite EntityBreedEvent
    TAME_ENTITY,         // Addomesticare animali
    TAME,                // Addomesticare tramite EntityTameEvent
    MILK_COW,            // Mungere mucche
    MILK,                // Mungere tramite PlayerInteractEntityEvent
    DYE_ARMOR,           // Tingere armatura
    SHEAR_SHEEP,         // Tosare pecore
    SHEAR,               // Tosare tramite PlayerShearEntityEvent
    EXPLORE,             // Esplorare mappa
    CUSTOM_KILL,         // Uccidere con professione specifica
    VILLAGER_TRADE,      // Commerciare con villagers
    COLLECT_ITEM,        // Raccogliere qualcosa
    BAKE_ITEM,           // Cuocere/cucinare
    STRIP_LOGS,          // Scortecciare
    WALK                 // Camminare (PlayerMoveEvent)
}
