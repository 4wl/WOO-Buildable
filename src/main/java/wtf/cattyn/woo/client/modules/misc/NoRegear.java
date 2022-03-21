//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\maywr\Documents\remapping\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.BlockShulkerBox
 *  net.minecraft.init.Items
 *  net.minecraft.item.ItemShulkerBox
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketHeldItemChange
 *  net.minecraft.network.play.client.CPacketPlayerDigging
 *  net.minecraft.network.play.client.CPacketPlayerDigging$Action
 *  net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.BlockPos
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package wtf.cattyn.woo.client.modules.misc;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import net.minecraft.block.BlockShulkerBox;
import net.minecraft.init.Items;
import net.minecraft.item.ItemShulkerBox;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wtf.cattyn.woo.api.event.PacketEvent;
import wtf.cattyn.woo.api.event.UpdateEvent;
import wtf.cattyn.woo.api.module.Module;
import wtf.cattyn.woo.api.setting.Setting;
import wtf.cattyn.woo.api.util.BlockUtil;
import wtf.cattyn.woo.api.util.ItemUtil;

public class NoRegear
extends Module {
    Setting.i range = this.registerI("Range", 6, 0, 6);
    private final Set<BlockPos> shulkerBlackList = new HashSet<BlockPos>();

    public NoRegear() {
        super("NoRegear", "is shit?", Module.Category.Misc);
    }

    @SubscribeEvent
    public void onUpdate(UpdateEvent event) {
        Iterator<BlockPos> iterator = BlockUtil.getSphere(this.range.getValue()).iterator();
        while (iterator.hasNext()) {
            BlockPos pos = iterator.next();
            if (!(this.mc.world.getBlockState(pos).getBlock() instanceof BlockShulkerBox) || this.shulkerBlackList.contains(pos)) continue;
            this.mc.player.swingArm(EnumHand.MAIN_HAND);
            int lastSlot = -1;
            if (this.mc.player.getHeldItemMainhand().getItem() != Items.DIAMOND_PICKAXE) {
                lastSlot = this.mc.player.inventory.currentItem;
                int pickSlot = ItemUtil.getItemFromHotbar(Items.DIAMOND_PICKAXE);
                if (pickSlot != -1) {
                    this.mc.getConnection().sendPacket((Packet)new CPacketHeldItemChange(ItemUtil.getItemFromHotbar(Items.DIAMOND_PICKAXE)));
                }
            }
            this.mc.getConnection().sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, EnumFacing.UP));
            this.mc.getConnection().sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, EnumFacing.UP));
            if (lastSlot == -1) continue;
            this.mc.getConnection().sendPacket((Packet)new CPacketHeldItemChange(lastSlot));
        }
    }

    @SubscribeEvent
    public void onPacketSend(PacketEvent.Send event) {
        if (!(event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock)) return;
        CPacketPlayerTryUseItemOnBlock packet = (CPacketPlayerTryUseItemOnBlock)event.getPacket();
        if (!(this.mc.player.getHeldItem(packet.getHand()).getItem() instanceof ItemShulkerBox)) return;
        this.shulkerBlackList.add(packet.getPos().offset(packet.getDirection()));
    }
}

