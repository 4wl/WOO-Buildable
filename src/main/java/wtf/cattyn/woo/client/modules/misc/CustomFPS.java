//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\maywr\Documents\remapping\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package wtf.cattyn.woo.client.modules.misc;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wtf.cattyn.woo.api.event.UpdateEvent;
import wtf.cattyn.woo.api.module.Module;
import wtf.cattyn.woo.api.setting.Setting;

public class CustomFPS
extends Module {
    public Setting.i cfps = this.registerI("Max Fps", 500, 20, 3000);

    public CustomFPS() {
        super("CustomFPS", Module.Category.Misc);
    }

    @SubscribeEvent
    public void onUpdate(UpdateEvent event) {
        this.mc.gameSettings.limitFramerate = this.cfps.getValue();
        this.setHudInfo("" + this.cfps.getValue());
    }
}

