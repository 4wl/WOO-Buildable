//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\maywr\Documents\remapping\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemPickaxe
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayerDigging
 *  net.minecraft.network.play.client.CPacketPlayerDigging$Action
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.World
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 */
package wtf.cattyn.woo.client.modules.player;

import java.awt.Color;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import wtf.cattyn.woo.api.accessor.IPlayerControllerMP;
import wtf.cattyn.woo.api.event.EventPlayerDamageBlock;
import wtf.cattyn.woo.api.event.Render3DEvent;
import wtf.cattyn.woo.api.module.Module;
import wtf.cattyn.woo.api.setting.Setting;
import wtf.cattyn.woo.api.util.RenderUtil;
import wtf.cattyn.woo.api.util.Timers;
import wtf.cattyn.woo.api.util.Wrapper;

public class InstantMine
extends Module {
    public Setting.i delay = this.registerI("Delay", 20, 0, 500);
    Setting.i hue = this.registerI("Hue", 0, 0, 360);
    Setting.d sat = this.registerD("Saturation", 0.8, 0.0, 1.0);
    Setting.d bright = this.registerD("Bright", 1.0, 0.0, 1.0);
    private final Timers timer = new Timers();
    private BlockPos renderBlock;
    private BlockPos lastBlock;
    private EnumFacing direction;

    public InstantMine() {
        super("InstantMine", Module.Category.Player);
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event) {
        if (this.nullCheck()) {
            return;
        }
        if (this.renderBlock != null && this.timer.passedMs(this.delay.getValue())) {
            if (this.mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() != Items.DIAMOND_PICKAXE) {
                return;
            }
            if (this.mc.world.getBlockState(this.renderBlock).getBlock() != Blocks.AIR) {
                this.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.renderBlock, this.direction));
            }
        }
        try {
            ((IPlayerControllerMP)this.mc).setBlockHitDelay(0);
            return;
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    @SubscribeEvent
    public void OnDamageBlock(EventPlayerDamageBlock event) {
        if (!this.canBreak(event.getPos())) return;
        if (this.lastBlock != null && (event.getPos().getX() != this.lastBlock.getX() || event.getPos().getY() != this.lastBlock.getY() || event.getPos().getZ() != this.lastBlock.getZ())) {
            this.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, event.getPos(), event.getDirection()));
        }
        this.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, event.getPos(), event.getDirection()));
        this.mc.player.swingArm(EnumHand.MAIN_HAND);
        this.renderBlock = event.getPos();
        this.lastBlock = event.getPos();
        this.direction = event.getDirection();
        this.timer.reset();
        this.timer.reset();
        event.setCanceled(true);
    }

    @Override
    @SubscribeEvent
    public void onRender3D(Render3DEvent event) {
        if (this.renderBlock == null) return;
        RenderUtil.drawBoxESP(this.renderBlock, Color.getHSBColor((float)this.hue.getValue() / 360.0f, (float)this.sat.getValue(), (float)this.bright.getValue()), false, new Color(0, 0, 0), 1.0f, true, true, 100, false);
    }

    private boolean canBreak(BlockPos pos) {
        IBlockState blockState = this.mc.world.getBlockState(pos);
        Block block = blockState.getBlock();
        if (block.getBlockHardness(blockState, (World)this.mc.world, pos) == -1.0f) return false;
        return true;
    }

    private int findPickaxeInHotbar() {
        int slot = -1;
        int i2 = 0;
        while (i2 < 9) {
            Item item;
            ItemStack stack = Wrapper.mc.player.inventory.getStackInSlot(i2);
            if (stack != ItemStack.EMPTY && (item = stack.getItem()) instanceof ItemPickaxe) {
                return i2;
            }
            ++i2;
        }
        return slot;
    }
}

