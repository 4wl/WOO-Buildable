//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\maywr\Documents\remapping\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.entity.Entity
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package wtf.cattyn.woo.client.modules.combat;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wtf.cattyn.woo.api.event.UpdateEvent;
import wtf.cattyn.woo.api.module.Module;
import wtf.cattyn.woo.api.setting.Setting;
import wtf.cattyn.woo.api.util.BlockUtil;
import wtf.cattyn.woo.api.util.ItemUtil;
import wtf.cattyn.woo.api.util.TextUtil;
import wtf.cattyn.woo.api.util.Timers;

public class Surround
extends Module {
    public Setting.i blocksPerTick = this.registerI("Block Tick", 8, 1, 20);
    public Setting.b center = this.registerB("Auto Center", false);
    public Setting.b helpingBlocks = this.registerB("Helping Block", true);
    public Setting.b intelligent = this.registerB("Intelligent", false);
    public Setting.b antiPedo = this.registerB("Always Help", false);
    public Setting.b floor = this.registerB("Floor", false);
    public Setting.i retryDelay = this.registerI("Retry Delay", 200, 1, 2500);
    private final Map<BlockPos, Integer> retries = new HashMap<BlockPos, Integer>();
    private final Timers timer = new Timers();
    private final Timers retryTimer = new Timers();
    private boolean didPlace = false;
    private int placements = 0;
    private int obbySlot = -1;
    public BlockPos startPos = null;
    double posY;

    public Surround() {
        super("Surround", Module.Category.Combat);
    }

    @Override
    public void onEnable() {
        if (this.nullCheck()) {
            this.disable();
            return;
        }
        this.startPos = new BlockPos(this.mc.player.getPositionVector());
        if (this.center.getValue()) {
            this.mc.player.setPosition((double)this.startPos.getX() + 0.5, (double)this.startPos.getY(), (double)this.startPos.getZ() + 0.5);
        }
        this.didPlace = false;
        this.retries.clear();
        this.retryTimer.reset();
        this.timer.reset();
        this.posY = this.mc.player.posY;
    }

    @SubscribeEvent
    public void onUpdate(UpdateEvent event) {
        boolean onEChest;
        if (this.posY < this.mc.player.posY) {
            this.disable();
            return;
        }
        if (this.check()) {
            return;
        }
        boolean bl = onEChest = this.mc.world.getBlockState(new BlockPos(this.mc.player.getPositionVector())).getBlock() == Blocks.ENDER_CHEST;
        if (this.mc.player.posY - (double)((int)this.mc.player.posY) < 0.7) {
            onEChest = false;
        }
        if (!BlockUtil.isSafe((Entity)this.mc.player, onEChest ? 1 : 0, this.floor.getValue())) {
            this.placeBlocks(this.mc.player.getPositionVector(), BlockUtil.getUnsafeBlockArray((Entity)this.mc.player, onEChest ? 1 : 0, this.floor.getValue()), this.helpingBlocks.getValue(), false);
            return;
        }
        if (BlockUtil.isSafe((Entity)this.mc.player, onEChest ? 0 : -1, false)) return;
        if (!this.antiPedo.getValue()) return;
        this.placeBlocks(this.mc.player.getPositionVector(), BlockUtil.getUnsafeBlockArray((Entity)this.mc.player, onEChest ? 0 : -1, false), false, false);
    }

    private boolean placeBlocks(Vec3d pos, Vec3d[] vec3ds, boolean hasHelpingBlocks, boolean isHelping) {
        int helpings = 0;
        if (this.obbySlot == -1) {
            return false;
        }
        if (this.mc.player == null) {
            return false;
        }
        int lastSlot = this.mc.player.inventory.currentItem;
        ItemUtil.switchToHotbarSlot(this.obbySlot, false);
        Vec3d[] vec3dArray = vec3ds;
        int n = vec3dArray.length;
        int n2 = 0;
        while (true) {
            if (n2 >= n) {
                ItemUtil.switchToHotbarSlot(lastSlot, false);
                return false;
            }
            Vec3d vec3d = vec3dArray[n2];
            boolean gotHelp = true;
            if (isHelping && !this.intelligent.getValue() && ++helpings > 1) {
                return false;
            }
            BlockPos position = new BlockPos(pos).add(vec3d.x, vec3d.y, vec3d.z);
            switch (BlockUtil.isPositionPlaceable(position, true)) {
                case -1: {
                    break;
                }
                case 2: {
                    if (!hasHelpingBlocks) break;
                    gotHelp = this.placeBlocks(pos, BlockUtil.getHelpingBlocks(vec3d), false, true);
                }
                case 3: {
                    if (gotHelp) {
                        this.placeBlock(position);
                    }
                    if (!isHelping) break;
                    return true;
                }
            }
            ++n2;
        }
    }

    private boolean check() {
        if (this.nullCheck()) {
            return true;
        }
        this.didPlace = false;
        this.placements = 0;
        this.obbySlot = ItemUtil.findHotbarBlock(Blocks.OBSIDIAN);
        if (this.retryTimer.passedMs(this.retryDelay.getValue())) {
            this.retries.clear();
            this.retryTimer.reset();
        }
        if (this.obbySlot != -1) return false;
        this.obbySlot = ItemUtil.findHotbarBlock(Blocks.ENDER_CHEST);
        if (this.obbySlot != -1) return false;
        TextUtil.sendMessage(ChatFormatting.RED + "<AutoFeetPlace> No obsidian.", false);
        this.disable();
        return true;
    }

    private void placeBlock(BlockPos pos) {
        if (this.placements >= this.blocksPerTick.getValue()) return;
        BlockUtil.placeBlock(pos);
        this.timer.reset();
        ++this.placements;
    }
}

