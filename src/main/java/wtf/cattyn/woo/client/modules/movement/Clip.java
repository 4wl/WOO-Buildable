//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\maywr\Documents\remapping\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayer$Position
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package wtf.cattyn.woo.client.modules.movement;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wtf.cattyn.woo.api.event.UpdateEvent;
import wtf.cattyn.woo.api.module.Module;
import wtf.cattyn.woo.api.setting.Setting;

public class Clip
extends Module {
    public Setting.d powers = this.registerD("Power", 0.1, 0.0, 0.5);
    Setting.b custom = this.registerB("Custom", false);
    Setting.b mini = this.registerB("Mini Power", false);
    Setting.b packet = this.registerB("Use Packet", false);

    public Clip() {
        super("Clip", Module.Category.Movement);
    }

    @SubscribeEvent
    public void onUpdate(UpdateEvent event) {
        if (this.nullCheck()) {
            return;
        }
        this.setHudInfo("Power " + this.powers.getValue());
        double yaw = (double)this.mc.player.rotationYaw * 0.017453292;
        double power = this.powers.getValue();
        if (!this.mc.gameSettings.keyBindSneak.pressed) return;
        if (this.custom.getValue()) {
            this.mc.player.setPositionAndUpdate(this.mc.player.posX - Math.sin(yaw) * power, this.mc.player.posY + (double)0.03f, this.mc.player.posZ + Math.cos(yaw) * power);
        }
        if (this.packet.getValue()) {
            this.mc.getConnection().sendPacket((Packet)new CPacketPlayer.Position(this.mc.player.posX - Math.sin(yaw) * power + (double)0.03f, this.mc.player.posY - (double)0.003f, this.mc.player.posZ + Math.cos(yaw) * power + 0.02, false));
        }
        if (!this.mini.getValue()) return;
        this.mc.player.setPositionAndUpdate(this.mc.player.posX - Math.sin(yaw) * 0.001, this.mc.player.posY + (double)0.01f, this.mc.player.posZ + Math.cos(yaw) * 0.001);
    }
}

