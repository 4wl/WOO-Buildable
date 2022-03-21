//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\maywr\Documents\remapping\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.init.Items
 *  net.minecraft.item.ItemExpBottle
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayer$Rotation
 *  net.minecraft.util.EnumHand
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package wtf.cattyn.woo.client.modules.player;

import net.minecraft.init.Items;
import net.minecraft.item.ItemExpBottle;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wtf.cattyn.woo.api.event.UpdateEvent;
import wtf.cattyn.woo.api.module.Module;
import wtf.cattyn.woo.api.util.ItemUtil;

public class XPDown
extends Module {
    public XPDown() {
        super("XPDown", Module.Category.Player);
    }

    @SubscribeEvent
    public void onUpdate(UpdateEvent event) {
        if (this.nullCheck()) {
            return;
        }
        boolean mainHand = this.mc.player.getHeldItemMainhand().getItem() == Items.EXPERIENCE_BOTTLE;
        if (!ItemUtil.holdingItem(ItemExpBottle.class)) return;
        if (this.mc.player.getActiveHand() != EnumHand.MAIN_HAND) return;
        if (!mainHand) return;
        this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(-90.0f, this.mc.player.rotationPitch, this.mc.player.onGround));
    }
}

