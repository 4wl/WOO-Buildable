//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\maywr\Documents\remapping\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3i
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package wtf.cattyn.woo.client.modules.render;

import java.awt.Color;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wtf.cattyn.woo.api.event.Render3DEvent;
import wtf.cattyn.woo.api.module.Module;
import wtf.cattyn.woo.api.setting.Setting;
import wtf.cattyn.woo.api.util.BlockUtil;
import wtf.cattyn.woo.api.util.ColorUtil;
import wtf.cattyn.woo.api.util.RenderUtil;

public class HoleEsp
extends Module {
    public Setting.b renderOwn = this.registerB("Render Own", true);
    public Setting.b fov = this.registerB("In Fov", true);
    public Setting.b rainbow = this.registerB("Rainbow Hole Esp", false);
    public Setting.i range = this.registerI("Range X", 4, 0, 10);
    public Setting.i rangeY = this.registerI("Range Y", 4, 0, 10);
    public Setting.b box = this.registerB("Box", true);
    public Setting.b gradientBox = this.registerB("Gradient Box", true);
    public Setting.b invertGradientBox = this.registerB("Invert Gradient Box", true);
    public Setting.b outline = this.registerB("Outline", true);
    public Setting.b gradientOutline = this.registerB("Gradient Outline", true);
    public Setting.b invertGradientOutline = this.registerB("ReverseOutline", true);
    public Setting.d height = this.registerD("Height", 0.0, -2.0, 2.0);
    public Setting.i red = this.registerI("Red", 0, 0, 255);
    public Setting.i green = this.registerI("Green", 0, 0, 255);
    public Setting.i blue = this.registerI("Blue", 0, 0, 255);
    public Setting.i alpha = this.registerI("Alpha", 255, 0, 255);
    public Setting.i boxAlpha = this.registerI("Box Alpha", 255, 0, 255);
    public Setting.d lineWidth = this.registerD("Line Width", 1.0, 0.1, 5.0);
    public Setting.b safeColor = this.registerB("Bedrock Color", true);
    public Setting.i safeRed = this.registerI("Safe Red", 0, 0, 255);
    public Setting.i safeGreen = this.registerI("Safe Green", 0, 0, 255);
    public Setting.i safeBlue = this.registerI("Safe Blue", 0, 0, 255);
    public Setting.i safeAlpha = this.registerI("Safe Alpha", 255, 0, 255);
    public Setting.b customOutline = this.registerB("Custom Outline", true);
    public Setting.i cRed = this.registerI("OL Red", 0, 0, 255);
    public Setting.i cGreen = this.registerI("OL Green", 0, 0, 255);
    public Setting.i cBlue = this.registerI("OL Blue", 0, 0, 255);
    public Setting.i cAlpha = this.registerI("OL Alpha", 255, 0, 255);
    public Setting.i safecRed = this.registerI("OL Safe Red", 0, 0, 255);
    public Setting.i safecGreen = this.registerI("OL Safe Green", 0, 0, 255);
    public Setting.i safecBlue = this.registerI("OL Safe Blue", 0, 0, 255);
    public Setting.i safecAlpha = this.registerI("OL Safe Alpha", 255, 0, 255);
    private int currentAlpha = 0;

    public HoleEsp() {
        super("HoleEsp", Module.Category.Render);
    }

    @Override
    @SubscribeEvent
    public void onRender3D(Render3DEvent event) {
        assert (this.mc.renderViewEntity != null);
        Vec3i playerPos = new Vec3i(this.mc.renderViewEntity.posX, this.mc.renderViewEntity.posY, this.mc.renderViewEntity.posZ);
        int x = playerPos.getX() - this.range.getValue();
        block0: while (x < playerPos.getX() + this.range.getValue()) {
            int z = playerPos.getZ() - this.range.getValue();
            while (true) {
                if (z < playerPos.getZ() + this.range.getValue()) {
                } else {
                    ++x;
                    continue block0;
                }
                for (int y = playerPos.getY() + this.rangeY.getValue(); y > playerPos.getY() - this.rangeY.getValue(); --y) {
                    BlockPos pos = new BlockPos(x, y, z);
                    if (!this.mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR) || !this.mc.world.getBlockState(pos.add(0, 1, 0)).getBlock().equals(Blocks.AIR) || !this.mc.world.getBlockState(pos.add(0, 2, 0)).getBlock().equals(Blocks.AIR) || pos.equals((Object)new BlockPos(this.mc.player.posX, this.mc.player.posY, this.mc.player.posZ)) && !this.renderOwn.getValue() || !BlockUtil.isPosInFov(pos).booleanValue() && this.fov.getValue()) continue;
                    if (this.mc.world.getBlockState(pos.north()).getBlock() == Blocks.BEDROCK && this.mc.world.getBlockState(pos.east()).getBlock() == Blocks.BEDROCK && this.mc.world.getBlockState(pos.west()).getBlock() == Blocks.BEDROCK && this.mc.world.getBlockState(pos.south()).getBlock() == Blocks.BEDROCK && this.mc.world.getBlockState(pos.down()).getBlock() == Blocks.BEDROCK) {
                        RenderUtil.drawBoxESPw(pos, this.rainbow.getValue() ? ColorUtil.rainbow(1, 0.7f, 0.7f) : new Color(this.safeRed.getValue(), this.safeGreen.getValue(), this.safeBlue.getValue(), this.safeAlpha.getValue()), this.customOutline.getValue(), new Color(this.safecRed.getValue(), this.safecGreen.getValue(), this.safecBlue.getValue(), this.safecAlpha.getValue()), (float)this.lineWidth.getValue(), this.outline.getValue(), this.box.getValue(), this.boxAlpha.getValue(), true, this.height.getValue(), this.gradientBox.getValue(), this.gradientOutline.getValue(), this.invertGradientBox.getValue(), this.invertGradientOutline.getValue(), this.currentAlpha);
                        continue;
                    }
                    if (!BlockUtil.isBlockUnSafe(this.mc.world.getBlockState(pos.down()).getBlock()) || !BlockUtil.isBlockUnSafe(this.mc.world.getBlockState(pos.east()).getBlock()) || !BlockUtil.isBlockUnSafe(this.mc.world.getBlockState(pos.west()).getBlock()) || !BlockUtil.isBlockUnSafe(this.mc.world.getBlockState(pos.south()).getBlock()) || !BlockUtil.isBlockUnSafe(this.mc.world.getBlockState(pos.north()).getBlock())) continue;
                    RenderUtil.drawBoxESPw(pos, this.rainbow.getValue() ? ColorUtil.rainbow(1, 0.7f, 0.7f) : new Color(this.red.getValue(), this.green.getValue(), this.blue.getValue(), this.alpha.getValue()), this.customOutline.getValue(), new Color(this.cRed.getValue(), this.cGreen.getValue(), this.cBlue.getValue(), this.cAlpha.getValue()), (float)this.lineWidth.getValue(), this.outline.getValue(), this.box.getValue(), this.boxAlpha.getValue(), true, this.height.getValue(), this.gradientBox.getValue(), this.gradientOutline.getValue(), this.invertGradientBox.getValue(), this.invertGradientOutline.getValue(), this.currentAlpha);
                }
                ++z;
            }
        }
        return;
    }
}

