//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\maywr\Documents\remapping\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.init.MobEffects
 *  net.minecraft.network.play.server.SPacketPlayerPosLook
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 */
package wtf.cattyn.woo.client.modules.movement;

import net.minecraft.entity.Entity;
import net.minecraft.init.MobEffects;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import wtf.cattyn.woo.api.event.MoveEvent;
import wtf.cattyn.woo.api.event.PacketEvent;
import wtf.cattyn.woo.api.module.Module;
import wtf.cattyn.woo.api.setting.Setting;

public class Strafe
extends Module {
    private Setting.b timer = this.registerB("Use Timer", true);
    private Setting.d timerSpeed = this.registerD("Timer Speed", 1.1, 0.0, 2.0);
    private Setting.d speed = this.registerD("Speed", 2.6, 0.0, 10.0);
    private Setting.b jump = this.registerB("Jump", true);
    private Setting.b liquid = this.registerB("Liquid", false);
    private double moveSpeed = 0.0;
    private double lastDist = 0.0;
    private int stage = 4;

    public Strafe() {
        super("Strafe", Module.Category.Movement);
    }

    @Override
    public void onDisable() {
        if (!this.timer.getValue()) return;
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event) {
        if (this.nullCheck()) {
            return;
        }
        if (!this.liquid.getValue()) {
            if (this.mc.player.isInLava()) return;
            if (this.mc.player.isInWater()) {
                return;
            }
        }
        this.lastDist = Math.sqrt(Math.pow(this.mc.player.posX - this.mc.player.prevPosX, 2.0) + Math.pow(this.mc.player.posZ - this.mc.player.prevPosZ, 2.0));
    }

    @SubscribeEvent
    public void onPlayerMove(MoveEvent event) {
        int amplifier;
        if (this.mc.player.moveForward != 0.0f || this.mc.player.moveStrafing != 0.0f) {
            if (this.mc.player.onGround) {
                this.stage = 2;
            }
            if (this.timer.getValue()) {
                // empty if block
            }
        }
        if (this.stage == 1 && (this.mc.player.moveForward != 0.0f || this.mc.player.moveStrafing != 0.0f)) {
            this.stage = 2;
            this.moveSpeed = 1.38 * (this.speed.getValue() / 10.0);
            if (this.mc.player.isPotionActive(MobEffects.SPEED)) {
                amplifier = this.mc.player.getActivePotionEffect(MobEffects.SPEED).getAmplifier();
                this.moveSpeed *= 1.0 + 0.2 * (double)(amplifier + 1);
            }
        } else if (this.stage == 2) {
            this.stage = 3;
            if (this.jump.getValue()) {
                this.mc.player.motionY = 0.3995f;
                event.motionY = 0.3995f;
            }
            this.moveSpeed *= 2.149;
            if (this.mc.player.isPotionActive(MobEffects.SPEED)) {
                amplifier = this.mc.player.getActivePotionEffect(MobEffects.SPEED).getAmplifier();
                this.moveSpeed *= 1.0 + 0.2 * (double)(amplifier + 1);
            }
        } else if (this.stage == 3) {
            this.stage = 4;
            this.moveSpeed = this.lastDist - 0.66 * (this.lastDist - this.speed.getValue() / 10.0);
            if (this.mc.player.isPotionActive(MobEffects.SPEED)) {
                amplifier = this.mc.player.getActivePotionEffect(MobEffects.SPEED).getAmplifier();
                this.moveSpeed *= 1.0 + 0.2 * (double)(amplifier + 1);
            }
        } else {
            if (this.mc.world.getCollisionBoxes((Entity)this.mc.player, this.mc.player.getEntityBoundingBox().offset(0.0, this.mc.player.motionY, 0.0)).size() > 0 || this.mc.player.collidedVertically) {
                this.stage = 1;
            }
            this.moveSpeed = this.lastDist - this.lastDist / 159.0;
        }
        this.moveSpeed = Math.min(Math.max(this.moveSpeed, this.speed.getValue() / 10.0), 0.551);
        float forward = this.mc.player.movementInput.moveForward;
        float strafe = this.mc.player.movementInput.moveStrafe;
        float yaw = this.mc.player.rotationYaw;
        if ((double)this.mc.player.moveForward == 0.0 && (double)this.mc.player.moveStrafing == 0.0) {
            event.motionX = 0.0;
            event.motionZ = 0.0;
        } else if (forward != 0.0f) {
            if (strafe >= 1.0f) {
                yaw += (float)(forward > 0.0f ? -45 : 45);
                strafe = 0.0f;
            } else if (strafe <= -1.0f) {
                yaw += (float)(forward > 0.0f ? 45 : -45);
                strafe = 0.0f;
            }
            if (forward > 0.0f) {
                forward = 1.0f;
            } else if (forward < 0.0f) {
                forward = -1.0f;
            }
        }
        double sin = Math.sin(Math.toRadians(yaw + 90.0f));
        double cos = Math.cos(Math.toRadians(yaw + 90.0f));
        event.motionX = (double)forward * this.moveSpeed * cos + (double)strafe * this.moveSpeed * sin;
        event.motionZ = (double)forward * this.moveSpeed * sin - (double)strafe * this.moveSpeed * cos;
        if ((double)this.mc.player.moveForward != 0.0) return;
        if ((double)this.mc.player.moveStrafing != 0.0) return;
        event.motionX = 0.0;
        event.motionZ = 0.0;
    }

    @SubscribeEvent
    public void onPacketReceive(PacketEvent.Receive e) {
        if (!(e.getPacket() instanceof SPacketPlayerPosLook)) return;
        this.moveSpeed = 0.0;
        this.lastDist = 0.0;
        this.stage = 4;
    }
}

