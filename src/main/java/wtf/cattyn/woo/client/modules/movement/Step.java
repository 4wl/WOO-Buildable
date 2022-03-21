//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\maywr\Documents\remapping\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package wtf.cattyn.woo.client.modules.movement;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wtf.cattyn.woo.api.event.UpdateEvent;
import wtf.cattyn.woo.api.module.Module;
import wtf.cattyn.woo.api.setting.Setting;

public class Step
extends Module {
    public Setting.d height = this.registerD("Height", 2.0, 0.0, 3.0);

    public Step() {
        super("Step", Module.Category.Movement);
    }

    @Override
    public void onDisable() {
        if (this.nullCheck()) {
            return;
        }
        this.mc.player.stepHeight = 0.6f;
    }

    @SubscribeEvent
    public void onUpdate(UpdateEvent event) {
        if (this.nullCheck()) return;
        if (this.mc.player.isInWater()) return;
        if (this.mc.player.isInLava()) return;
        if (this.mc.player.isOnLadder()) {
            return;
        }
        if (!this.mc.player.onGround) return;
        this.mc.player.stepHeight = (float)this.height.getValue();
    }
}

