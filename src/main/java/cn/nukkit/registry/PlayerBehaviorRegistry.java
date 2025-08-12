package cn.nukkit.registry;

import cn.nukkit.Player;
import cn.nukkit.PlayerBehaviors;
import cn.nukkit.entity.data.Skin;
import cn.nukkit.lang.TranslationContainer;
import cn.nukkit.level.Location;
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;

public final class PlayerBehaviorRegistry {
    private PlayerBehaviorRegistry() {}

    private static PlayerBehaviors.FirstSpawnController firstSpawn = new DefaultFirstSpawnController();
    private static PlayerBehaviors.ClientInitController  clientInit = new DefaultClientInitController();
    private static PlayerBehaviors.RespawnController    respawn    = new DefaultRespawnController();
    private static PlayerBehaviors.LeaveMessageController leaveMessage = new DefaultLeaveMessageController();
    private static PlayerBehaviors.SkinController vanillaSkin = new DefaultSkinController();
    private static PlayerBehaviors.SleepController sleep = new DefaultSleepController();
    private static PlayerBehaviors.StopSleepController stopSleep = new DefaultStopSleepController();
    private static PlayerBehaviors.OnUpdateController onUpdate = new DefaultOnUpdateController();
    private static PlayerBehaviors.ChatController chat = new DefaultChatController();

    // REGISTRIES
    public static void registerFirstSpawnController(PlayerBehaviors.FirstSpawnController c) { if (c != null) firstSpawn = c; }
    public static void registerClientInitController(PlayerBehaviors.ClientInitController c) { if (c != null) clientInit = c; }
    public static void registerRespawnController(PlayerBehaviors.RespawnController c) { if (c != null) respawn = c; }
    public static void registerLeaveMessageController(PlayerBehaviors.LeaveMessageController c) { if (c != null) leaveMessage = c; }
    public static void registerSkinController(PlayerBehaviors.SkinController c) { if (c != null) vanillaSkin = c; }
    public static void registerSleepController(PlayerBehaviors.SleepController c) { if (c != null) sleep = c; }
    public static void registerStopSleepController(PlayerBehaviors.StopSleepController c) { if (c != null) stopSleep = c; }
    public static void registerOnUpdateController(PlayerBehaviors.OnUpdateController c) { if (c != null) onUpdate = c; }
    public static void registerChatController(PlayerBehaviors.ChatController c) { if (c != null) chat = c; }

    // INTERFACES
    public static PlayerBehaviors.FirstSpawnController firstSpawn() { return firstSpawn; }
    public static PlayerBehaviors.ClientInitController clientInit() { return clientInit; }
    public static PlayerBehaviors.RespawnController    respawn()    { return respawn; }
    public static PlayerBehaviors.LeaveMessageController leaveMessage() { return leaveMessage; }
    public static PlayerBehaviors.SkinController vanillaSkin() { return vanillaSkin; }
    public static PlayerBehaviors.SleepController sleep() { return sleep; }
    public static PlayerBehaviors.StopSleepController stopSleep() { return stopSleep; }
    public static PlayerBehaviors.OnUpdateController onUpdate() { return onUpdate; }
    public static PlayerBehaviors.ChatController chat() { return chat; }

    // DEFAULTS
    private static final class DefaultFirstSpawnController implements PlayerBehaviors.FirstSpawnController {
        @Override public boolean handleFirstSpawn(Player p) { return false; }
        @Override public void beforeFirstSpawn(Player p) {}
        @Override public Location firstSpawnResolveTeleportTarget(Player p, Location vanillaTarget) { return vanillaTarget; }
        @Override public void afterFirstSpawn(Player p) {}
    }

    private static final class DefaultClientInitController implements PlayerBehaviors.ClientInitController {
        @Override public boolean handleClientInit(Player p) { return false; }
        @Override public void beforeClientInit(Player p) {}
        @Override public void afterClientInit(Player p) {}
    }

    private static final class DefaultRespawnController implements PlayerBehaviors.RespawnController {
        @Override public boolean handleRespawn(Player p) { return false; }
        @Override public void beforeRespawn(Player p) {}
        @Override public Position respawnResolvePosition(Player p, Position vanillaPosition) { return vanillaPosition; }
        @Override public void afterRespawn(Player p) {}
    }

    private static final class DefaultLeaveMessageController implements PlayerBehaviors.LeaveMessageController {
        @Override
        public TranslationContainer resolveLeaveMessage(Player p, TranslationContainer vanilla) { return vanilla; }
    }

    private static final class DefaultSkinController implements PlayerBehaviors.SkinController {
        @Override public boolean handleSetSkin(Player p, Skin skin) { return false; }
    }

    private static final class DefaultSleepController implements PlayerBehaviors.SleepController {
        @Override public boolean handleSleep(Player p, Vector3 bedPos) { return false; }
        @Override public void beforeSleep(Player p, Vector3 bedPos) {}
        @Override public Location sleepResolveTeleportTarget(Player p, Vector3 bedPos, Location vanillaTarget) { return vanillaTarget; }
        @Override public Position sleepResolveSpawnPosition(Player p, Vector3 bedPos, Position vanillaSpawn) { return vanillaSpawn; }
        @Override public void afterSleep(Player p, Vector3 bedPos, boolean success) {}
    }

    private static final class DefaultStopSleepController implements PlayerBehaviors.StopSleepController {
        @Override public boolean handleStopSleep(Player p) { return false; }
        @Override public void beforeStopSleep(Player p) {}
        @Override public void afterStopSleep(Player p) {}
    }

    private static final class DefaultOnUpdateController implements PlayerBehaviors.OnUpdateController {
        @Override public boolean handleUpdate(Player p, int currentTick) { return false; }
        @Override public void beforeUpdate(Player p, int currentTick) {}
        @Override public void afterUpdate(Player p, int currentTick, boolean result) {}
        @Override public void afterEntityBaseTick(Player p, int tickDiff) {}
        @Override public int regenerateAmount(Player p, int vanillaAmount) { return vanillaAmount; }
    }

    private static final class DefaultChatController implements PlayerBehaviors.ChatController {
        @Override public boolean handleChat(Player p, String message) { return false; }
        @Override public void beforeChat(Player p, String message) {}
        @Override public void afterChat(Player p, String message, boolean result) {}
    }
}
