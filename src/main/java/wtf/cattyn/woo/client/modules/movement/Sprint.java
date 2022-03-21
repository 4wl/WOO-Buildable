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

public class Sprint
extends Module {
    public Sprint() {
        super("Sprint", Module.Category.Movement);
    }

    @SubscribeEvent
    public void onUpdate(UpdateEvent event) {
        if (this.nullCheck()) {
            return;
        }
        if (this.mc.player.moveForward == 0.0f && this.mc.player.moveStrafing == 0.0f) {
            this.setHudInfo("Not Sprinting");
            return;
        }
        this.mc.player.setSprinting(true);
        this.setHudInfo("Sprinting");
    }
}

