//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\maywr\Documents\remapping\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.init.Blocks
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayer$Position
 *  net.minecraft.util.math.BlockPos
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package wtf.cattyn.woo.client.modules.combat;

import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wtf.cattyn.woo.api.event.UpdateEvent;
import wtf.cattyn.woo.api.module.Module;
import wtf.cattyn.woo.api.setting.Setting;
import wtf.cattyn.woo.api.util.BlockUtil;
import wtf.cattyn.woo.api.util.ItemUtil;
import wtf.cattyn.woo.api.util.TextUtil;

public class SelfFill
extends Module {
    Setting.d heights = this.registerD("Height", 5.0, -10.0, 10.0);
    Setting.b enderChestMode = this.registerB("Ender Chest", false);
    Setting.b unbreakable = this.registerB("Unbreakable", false);
    Setting.b servermode = this.registerB("ServerMode", false);
    private BlockPos position;
    int enablePosX;
    int enablePosY;
    int enablePosZ;

    public SelfFill() {
        super("SelfFill", Module.Category.Combat);
    }

    @Override
    public void onEnable() {
        if (this.nullCheck()) {
            return;
        }
        this.position = new BlockPos(this.mc.player.posX, Math.ceil(this.mc.player.posY), this.mc.player.posZ);
        this.enablePosX = (int)this.mc.player.posX;
        this.enablePosY = (int)this.mc.player.posY;
        this.enablePosZ = (int)this.mc.player.posZ;
    }

    @SubscribeEvent
    public void onUpdate(UpdateEvent event) {
        if (this.nullCheck()) {
            return;
        }
        if (!this.unbreakable.getValue()) {
            this.initSelfFill(this.position, this.heights.getValue(), this.enderChestMode.getValue());
            this.disable();
            return;
        }
        if (this.mc.player.posX > (double)this.enablePosX || this.mc.player.posY > (double)this.enablePosY || this.mc.player.posZ > (double)this.enablePosZ) {
            this.disable();
            return;
        }
        if (this.unbreakable.getValue()) {
            if (this.mc.world.getBlockState(this.position).getBlock() != Blocks.AIR) return;
        }
        this.initSelfFill(this.position, this.heights.getValue(), this.enderChestMode.getValue());
    }

    public void initSelfFill(BlockPos pos, double height, boolean enderchest) {
        if (!this.servermode.getValue()) {
            int startSlot = this.mc.player.inventory.currentItem;
            int obsidianSlot = ItemUtil.findHotbarBlock(Blocks.OBSIDIAN);
            int chestSlot = ItemUtil.findHotbarBlock(Blocks.ENDER_CHEST);
            if (!enderchest) {
                ItemUtil.switchToHotbarSlot(obsidianSlot, false);
                if (obsidianSlot == -1) {
                    TextUtil.sendMessage("[SF] no obsidian, disabled..", true);
                    this.disable();
                    return;
                }
            } else {
                ItemUtil.switchToHotbarSlot(chestSlot, false);
                if (chestSlot == -1) {
                    TextUtil.sendMessage("[SF] no ender chest, disabled..", true);
                    this.disable();
                    return;
                }
            }
            this.mc.getConnection().sendPacket((Packet)new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY + 0.419, this.mc.player.posZ, true));
            this.mc.getConnection().sendPacket((Packet)new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY + 0.75319998, this.mc.player.posZ, true));
            this.mc.getConnection().sendPacket((Packet)new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY + 1.00013597, this.mc.player.posZ, true));
            this.mc.getConnection().sendPacket((Packet)new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY + 1.16610926, this.mc.player.posZ, true));
            BlockUtil.placeBlock(pos);
            this.mc.getConnection().sendPacket((Packet)new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY + height, this.mc.player.posZ, false));
            if (startSlot == -1) return;
            ItemUtil.switchToHotbarSlot(startSlot, false);
            return;
        }
        if (!this.servermode.getValue()) return;
        if (!this.enderChestMode.getValue()) {
            this.mc.player.sendChatMessage("/setblock ~ ~ ~ obsidian");
            return;
        }
        this.mc.player.sendChatMessage("/setblock ~ ~ ~ ender_chest");
    }
}

