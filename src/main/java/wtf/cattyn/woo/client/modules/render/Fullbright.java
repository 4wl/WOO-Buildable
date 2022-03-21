//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\maywr\Documents\remapping\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$PlayerTickEvent
 */
package wtf.cattyn.woo.client.modules.render;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import wtf.cattyn.woo.api.module.Module;

public class Fullbright
extends Module {
    boolean saved = false;
    double oldGamma = 1.0;

    public Fullbright() {
        super("Fullbright", Module.Category.Render);
    }

    @SubscribeEvent
    public void onTick(TickEvent.PlayerTickEvent e) {
        if (this.nullCheck()) {
            return;
        }
        if (!this.saved) {
            this.oldGamma = this.mc.gameSettings.gammaSetting;
            this.saved = true;
        }
        this.mc.gameSettings.gammaSetting = 999.0f;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        this.mc.gameSettings.gammaSetting = (int)this.oldGamma;
        this.saved = false;
    }
}

