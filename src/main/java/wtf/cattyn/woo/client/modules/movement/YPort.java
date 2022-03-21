//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\maywr\Documents\remapping\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package wtf.cattyn.woo.client.modules.movement;

import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wtf.cattyn.woo.api.event.UpdateEvent;
import wtf.cattyn.woo.api.module.Module;
import wtf.cattyn.woo.api.setting.Setting;
import wtf.cattyn.woo.api.util.EntityUtil;

public class YPort
extends Module {
    public static final YPort INSTANCE = new YPort();
    public Setting.d speed = this.registerD("Speed", 0.16, 0.0, 0.5);
    public Setting.b noJumpEffect = this.registerB("No Jump Effect", false);
    public double startY = 0.0;

    public YPort() {
        super("YPort", Module.Category.Movement);
    }

    @Override
    public void onDisable() {
        EntityUtil.setTimer(1.0f);
    }

    @SubscribeEvent
    public void onUpdate(UpdateEvent event) {
        if (this.nullCheck()) {
            return;
        }
        if (this.mc.player.onGround) {
            this.startY = this.mc.player.posY;
        }
        this.handleYPortSpeed();
    }

    private void handleYPortSpeed() {
        if (!EntityUtil.isMoving((EntityLivingBase)this.mc.player)) return;
        if (this.mc.player.isInWater()) {
            if (this.mc.player.isInLava()) return;
        }
        if (this.mc.player.collidedHorizontally) {
            return;
        }
        if (this.mc.player.onGround) {
            EntityUtil.setTimer(1.15f);
            this.mc.player.jump();
            EntityUtil.setSpeed((EntityLivingBase)this.mc.player, EntityUtil.getBaseMoveSpeed() + this.speed.getValue());
            return;
        }
        this.mc.player.motionY = -1.0;
        EntityUtil.resetTimer();
    }
}

