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
import wtf.cattyn.woo.mixin.mixins.IEntity;

public class NoWeb
extends Module {
    Setting.b bypass = this.registerB("2b2t Bypass", false);

    public NoWeb() {
        super("NoWeb", Module.Category.Movement);
    }

    @SubscribeEvent
    public void onUpdate(UpdateEvent event) {
        if (this.nullCheck()) {
            return;
        }
        float power = 0.5f;
        if (!((IEntity)this.mc.player).getIsInWeb()) return;
        if (this.bypass.getValue()) return;
        if (this.mc.player.onGround) return;
        if (!(this.mc.player.motionY <= 0.0)) return;
        this.mc.player.motionY -= (double)power;
    }
}

