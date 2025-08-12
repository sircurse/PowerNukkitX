package cn.nukkit;

import cn.nukkit.entity.data.Skin;
import cn.nukkit.lang.TranslationContainer;
import cn.nukkit.level.Location;
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.PlayerSkinPacket;
import lombok.extern.slf4j.Slf4j;

/**
 * Central namespace for pluggable Player behaviors.
 * Keep these interfaces small and stable; implementations live in plugins.
 */
@Slf4j
public final class PlayerBehaviors {
    private PlayerBehaviors() {}

    /**
     * Controls the first‑spawn flow executed after the login sequence.
     * You can either take full control (handleFirstSpawn == true) or
     * let vanilla run and intercept key points via the hook methods.
     */
    public static interface FirstSpawnController {

        /**
         * If you return true, the core will SKIP the vanilla doFirstSpawn() body.
         * You are responsible for doing everything (syncs, packets, teleport, etc).
         */
        boolean handleFirstSpawn(Player p);

        /**
         * Called right before vanilla doFirstSpawn() starts (only if handleFirstSpawn returned false).
         * Use this to pre‑set flags, clear state, preload data, etc.
         */
        default void beforeFirstSpawn(Player p) {}

        /**
         * Vanilla computes a Location target; you can replace it here.
         * Return the location to actually use for teleport.
         */
        default Location firstSpawnResolveTeleportTarget(Player p, Location vanillaTarget) { return vanillaTarget; }

        /**
         * Called after vanilla finishes (only if handleFirstSpawn returned false).
         * Good place to push client‑synced props, scoreboard, etc.
         */
        default void afterFirstSpawn(Player p) {}
    }

    /**
     * Called when the client has finished local initialization (UI/world ready).
     * Lets plugins replace or extend the vanilla flow.
     */
    public static interface ClientInitController {
            /**
             * Return true to fully handle the initialization yourself.
             * If true, core will SKIP the vanilla onPlayerLocallyInitialized() body.
             */
            boolean handleClientInit(Player p);

            /** Runs right before vanilla logic (only if handleClientInit returned false). */
            default void beforeClientInit(Player p) {}

            /** Runs right after vanilla logic (only if handleClientInit returned false). */
            default void afterClientInit(Player p) {}
    }

    /**
     * Full control over the respawn pipeline.
     */
    public static interface RespawnController {
        /**
         * Return true to fully handle respawn yourself (skip vanilla body).
         */
        boolean handleRespawn(Player p);

        /**
         * Called before vanilla respawn logic (only if handleRespawn returned false).
         */
        default void beforeRespawn(Player p) {}

        /**
         * Adjust the final respawn position after events/validation.
         */
        default Position respawnResolvePosition(Player p, Position vanillaPosition) { return vanillaPosition; }

        /**
         * Called after vanilla respawn logic finishes.
         */
        default void afterRespawn(Player p) {}
    }

    /**
     * Return the message to broadcast when the player leaves.
     */
    public static interface LeaveMessageController {
        /**
         * You receive the vanilla message; return it unchanged or replace it.
         */
        TranslationContainer resolveLeaveMessage(Player p, TranslationContainer vanilla);
    }

    /**
     * Controls the skin set by the client.
     */
    public static interface SkinController {
        boolean handleSetSkin(Player p, Skin skin);

        default void beforeSetSkin(Player p, Skin skin) {}

        /** Return false to suppress the broadcast while still applying the skin. */
        default boolean allowBroadcast(Player p, Skin skin) { return true; }

        /** Last chance to edit the packet (or log) before it’s sent. */
        default void beforeBroadcast(Player p, Skin skin, PlayerSkinPacket pk) {}

        /** Called after the packet is sent (or skipped). */
        default void afterBroadcast(Player p, Skin skin, PlayerSkinPacket pk) {}

        /** Runs after everything (apply + optional broadcast). */
        default void afterSetSkin(Player p, Skin skin) {}
    }

    /**
     * Hook around the "sleep in bed" flow.
     * You can fully take over (handleSleep == true) or just tweak via hooks/resolvers.
     */
    public static interface SleepController {
        /**
         * If you return true, the core will SKIP the vanilla sleepOn() body.
         * You are responsible for doing everything (checks, events, teleport, spawn, flags, etc).
         */
        default boolean handleSleep(Player p, Vector3 bedPos) { return false; }

        /** Runs right before vanilla logic (only if handleSleep returned false). */
        default void beforeSleep(Player p, Vector3 bedPos) {}

        /** Replace or keep the vanilla teleport target used when the player lies down. */
        default Location sleepResolveTeleportTarget(Player p, Vector3 bedPos, Location vanillaTarget) { return vanillaTarget; }

        /** Replace or keep the spawn Position stored when sleeping (SpawnPointType stays BLOCK). */
        default Position sleepResolveSpawnPosition(Player p, Vector3 bedPos, Position vanillaSpawn) { return vanillaSpawn; }

        /** success=true iff the player actually entered sleeping state (or your handler returned true). */
        default void afterSleep(Player p, Vector3 bedPos, boolean success) {}
    }

    /**
     * Hook around waking up / stopping sleep.
     * You can fully take over (handleStopSleep == true) or just tweak via hooks/resolvers.
     */
    public static interface StopSleepController {
        /** Return true to fully handle stopSleep yourself (skip vanilla body). */
        default boolean handleStopSleep(Player p) { return false; }

        /** Runs right before vanilla logic (only if handleStopSleep returned false). */
        default void beforeStopSleep(Player p) {}

        /** success=true iff the player actually transitioned out of sleeping. */
        default void afterStopSleep(Player p) {}
    }

    /**
     * Hooks around the per-tick player update.
     * You can fully take over (handleUpdate == true) or just tweak via hooks.
     */
    public static interface OnUpdateController {
        /** Return true to fully handle onUpdate() yourself (skip vanilla body). */
        default boolean handleUpdate(Player p, int currentTick) { return false; }

        /** Runs right before vanilla logic (only if handleUpdate returned false). */
        default void beforeUpdate(Player p, int currentTick) {}

        /** Called after the method determines a result, right before returning. */
        default void afterUpdate(Player p, int currentTick, boolean result) {}

        // Mid-pipeline hooks (no-ops by default)
        default void afterEntityBaseTick(Player p, int tickDiff) {}
        /**
         * Natural regeneration amount per tick (vanilla = 1).
         * Return 0 or negative to skip healing.
         */
        default int regenerateAmount(Player p, int vanillaAmount) { return vanillaAmount; }
    }

    /**
     * Minimal hooks for player chat.
     * You can fully take over (handleChat == true) or just run before/after.
     */
    public static interface ChatController {
        /** Return true to fully handle chat yourself (skip vanilla body). */
        default boolean handleChat(Player p, String message) { return false; }

        /** Runs right before vanilla logic (only if handleChat returned false). */
        default void beforeChat(Player p, String message) {}

        /** Called once at the end with the final result. */
        default void afterChat(Player p, String message, boolean result) {}
    }


























}





