package wtf.cattyn.woo;

import java.io.File;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;
import wtf.cattyn.woo.api.command.CommandManager;
import wtf.cattyn.woo.api.event.WooEvent;
import wtf.cattyn.woo.api.managers.FriendManager;
import wtf.cattyn.woo.api.managers.RotationManager;
import wtf.cattyn.woo.api.module.Module;
import wtf.cattyn.woo.api.module.ModuleManager;
import wtf.cattyn.woo.api.setting.SettingManager;
import wtf.cattyn.woo.api.util.ConfigUtil;
import wtf.cattyn.woo.api.util.SoundUtil;
import wtf.cattyn.woo.client.gui.clickgui.component.FontManager;
import wtf.cattyn.woo.client.modules.client.Font;

@Mod(modid="woo", name="Woo", version="v2")
public class Woo {
    public static final Woo INSTANCE = new Woo();
    public static final String MODID = "woo";
    public static final String NAME = "Woo";
    public static final String VERSION = "v2";
    int dominika = 7;
    private final File directory;
    public static SettingManager settingManager;
    public static ModuleManager moduleManager;
    public static CommandManager commandManager;
    public static FontManager fontManager;
    public static FriendManager friendManager;
    public static RotationManager rotationManager;
    public static ConfigUtil configManager;
    public static Thread artificialTick;
    public static final Logger log;

    public Woo() {
        this.directory = new File(Minecraft.getMinecraft().gameDir, MODID);
    }

    public static void load() {
        settingManager = new SettingManager();
        moduleManager = new ModuleManager();
        commandManager = new CommandManager();
        fontManager = new FontManager();
        configManager = new ConfigUtil();
        friendManager = new FriendManager();
        rotationManager = new RotationManager();
        configManager.loadConfig();
        log.info("Managers loaded");
        MinecraftForge.EVENT_BUS.register((Object)new WooEvent());
        fontManager.setCustomFont(Font.INSTANCE.isToggled());
        Runtime.getRuntime().addShutdownHook(new ConfigUtil());
        artificialTick = new Thread(() -> {
            while (true) {
                try {
                    while (true) {
                        if (Minecraft.getMinecraft().player != null) {
                            for (Module m : moduleManager.getModules()) {
                                if (!m.isToggled()) continue;
                                m.onArtificialUpdate();
                            }
                        }
                        Thread.sleep(10L);
                    }
                }
                catch (Exception exception) {
                    continue;
                }
            }
        });
        artificialTick.start();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        Woo.load();
        friendManager.init(new File(this.directory, "Friends.json"));
        System.out.println("Woo initialized!");
        Display.setTitle("Woo v2");
        SoundUtil.playSound(new ResourceLocation("audio/seal.wav"));
    }

    static {
        log = LogManager.getLogger("[Woo]");
    }
}

