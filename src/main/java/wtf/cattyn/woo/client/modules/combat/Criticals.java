//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\maywr\Documents\remapping\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayer$Position
 *  net.minecraft.network.play.client.CPacketUseEntity
 *  net.minecraft.network.play.client.CPacketUseEntity$Action
 *  net.minecraft.world.World
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package wtf.cattyn.woo.client.modules.combat;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wtf.cattyn.woo.api.event.PacketEvent;
import wtf.cattyn.woo.api.module.Module;

public class Criticals
extends Module {
    public Criticals() {
        super("Criticals", Module.Category.Combat);
    }

    @SubscribeEvent
    public void onPacketSend(PacketEvent.Send event) {
        if (this.nullCheck()) {
            return;
        }
        if (!(event.getPacket() instanceof CPacketUseEntity)) return;
        CPacketUseEntity packet = (CPacketUseEntity)event.getPacket();
        if (packet.getAction() != CPacketUseEntity.Action.ATTACK) return;
        if (!this.mc.player.onGround) return;
        if (this.mc.gameSettings.keyBindJump.isKeyDown()) return;
        if (!(packet.getEntityFromWorld((World)this.mc.world) instanceof EntityLivingBase)) return;
        if (this.mc.player.isInWater()) return;
        if (this.mc.player.isInLava()) return;
        this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY + (double)0.1f, this.mc.player.posZ, false));
        this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY, this.mc.player.posZ, false));
    }
}

