//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\maywr\Documents\remapping\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  org.lwjgl.opengl.GL11
 */
package wtf.cattyn.woo.client.modules.client;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;
import wtf.cattyn.woo.Woo;
import wtf.cattyn.woo.api.event.Render2DEvent;
import wtf.cattyn.woo.api.module.Module;
import wtf.cattyn.woo.api.setting.Setting;
import wtf.cattyn.woo.api.util.ColorUtil;
import wtf.cattyn.woo.api.util.RenderUtil;

public class HUD
extends Module {
    int y;
    ScaledResolution sr;
    Setting.b rainbow;
    Setting.b watermark;
    Setting.i watermarkXpos;
    Setting.i watermarkYpos;
    Setting.b arrayList;
    Setting.b nixwatermark;
    Setting.d scalenixware;
    Setting.i nixwatermarkXpos;
    Setting.i nixwatermarkYpos;
    Setting.b predicttest;
    Setting.i predicttestXpos;
    Setting.i predicttestYpos;
    Setting.b coords;
    Setting.i coordsXpos;
    Setting.i coordsYpos;
    public String status;

    @Override
    public void onArtificialUpdate() {
        this.watermarkXpos.setHidden(this.watermark.getValue());
        this.watermarkYpos.setHidden(this.watermark.getValue());
        this.nixwatermarkXpos.setHidden(this.nixwatermark.getValue());
        this.nixwatermarkYpos.setHidden(this.nixwatermark.getValue());
        this.scalenixware.setHidden(this.nixwatermark.getValue());
        this.predicttestXpos.setHidden(this.predicttest.getValue());
        this.predicttestYpos.setHidden(this.predicttest.getValue());
    }

    public HUD() {
        super("HUD", Module.Category.Client);
        this.sr = new ScaledResolution(this.mc);
        this.rainbow = this.registerB("Rainbow", false);
        this.watermark = this.registerB("Watermark", true);
        this.watermarkXpos = this.registerI("Watermark X position", 0, 0, this.sr.getScaledWidth());
        this.watermarkYpos = this.registerI("Watermark Y position", 0, 0, this.sr.getScaledHeight());
        this.arrayList = this.registerB("ArrayList", true);
        this.nixwatermark = this.registerB("NixWare", true);
        this.scalenixware = this.registerD("Scale NixWare", 1.0, 0.0, 3.0);
        this.nixwatermarkXpos = this.registerI("NixWare X position", 0, 0, this.sr.getScaledWidth());
        this.nixwatermarkYpos = this.registerI("NixWare Y position", 0, 0, this.sr.getScaledHeight());
        this.predicttest = this.registerB("Predict Indicator", true);
        this.predicttestXpos = this.registerI("Predict Indicator X position", 0, 0, this.sr.getScaledWidth());
        this.predicttestYpos = this.registerI("Predict Indicator Y position", 0, 0, this.sr.getScaledHeight());
        this.coords = this.registerB("Coords", true);
        this.coordsXpos = this.registerI("coords X position", 0, 0, this.sr.getScaledWidth());
        this.coordsYpos = this.registerI("coords Y position", 0, 0, this.sr.getScaledHeight());
    }

    @Override
    @SubscribeEvent
    public void onRender2D(Render2DEvent event) {
        int y;
        int x;
        if (this.nullCheck()) {
            return;
        }
        ScaledResolution sr = new ScaledResolution(this.mc);
        this.nixwatermarkXpos.setMax(sr.getScaledWidth());
        this.nixwatermarkYpos.setMax(sr.getScaledHeight());
        this.watermarkXpos.setMax(sr.getScaledWidth());
        this.watermarkYpos.setMax(sr.getScaledHeight());
        this.predicttestXpos.setMax(sr.getScaledWidth());
        this.predicttestYpos.setMax(sr.getScaledHeight());
        if (this.coords.getValue()) {
            x = (int)this.mc.player.posX;
            y = (int)this.mc.player.posY;
            int z = (int)this.mc.player.posZ;
            String text = "OVER: [ X: " + x + " Y: " + y + " Z: " + z + " ] NETHER: [ X:" + x * 8 + " Z: " + z * 8 + " ]";
            Woo.fontManager.drawString(text, this.coordsXpos.getValue(), this.coordsYpos.getValue(), new Color(255, 255, 255, 255).getRGB());
        }
        if (this.nixwatermark.getValue()) {
            x = 5;
            y = 5;
            String text = "Woo v2 | " + this.mc.session.getUsername() + " | " + (this.mc.currentServerData != null ? this.mc.currentServerData.serverIP : "singleplayer") + " | " + (this.mc.currentServerData != null ? this.getPing() + "ms" : "0ms") + " | " + Minecraft.debugFPS + "fps";
            GL11.glPushMatrix();
            GL11.glScaled((double)this.scalenixware.getValue(), (double)this.scalenixware.getValue(), (double)this.scalenixware.getValue());
            RenderUtil.drawRect(this.nixwatermarkXpos.getValue() + x, this.nixwatermarkYpos.getValue() + y, this.nixwatermarkXpos.getValue() + x + Woo.fontManager.getStringWidth(text + 2), this.nixwatermarkYpos.getValue() + y + 15, new Color(1, 1, 1, 100).getRGB());
            RenderUtil.drawRect(this.nixwatermarkXpos.getValue() + x, this.nixwatermarkYpos.getValue() + y, this.nixwatermarkXpos.getValue() + x + Woo.fontManager.getStringWidth(text + 2), (float)(this.nixwatermarkYpos.getValue() + y) + 2.5f, this.rainbow.getValue() ? ColorUtil.rainbowInt(1, 0.7f, 0.7f) : new Color(68, 105, 250, 255).getRGB());
            Woo.fontManager.drawString(text, this.nixwatermarkXpos.getValue() + x + 3, this.nixwatermarkYpos.getValue() + y + 5, new Color(255, 255, 255, 255).getRGB());
            GL11.glPopMatrix();
        }
        if (this.watermark.getValue()) {
            Woo.fontManager.drawString("W" + ChatFormatting.WHITE + "oo", this.watermarkXpos.getValue() + 1, this.watermarkYpos.getValue() + 1, ColorUtil.rainbowInt(0, 0.6f, 1.0f));
        }
        this.y = 2;
        ArrayList<Module> mods = Woo.moduleManager.getEnabledAndDrawnModules();
        mods.sort(Comparator.comparing(m -> -Woo.fontManager.getStringWidth(ChatFormatting.WHITE + m.getName() + ChatFormatting.BOLD + " " + m.getHudInfo())));
        if (!this.arrayList.getValue()) return;
        Iterator<Module> iterator = mods.iterator();
        while (iterator.hasNext()) {
            Module m2 = iterator.next();
            String info = ChatFormatting.WHITE + m2.getName() + ChatFormatting.GRAY + (m2.getHudInfo() == "" ? "" : " ") + m2.getHudInfo();
            Woo.fontManager.drawString(info, sr.getScaledWidth() - Woo.fontManager.getStringWidth(info) - 3, this.y, -1);
            this.y += this.mc.fontRenderer.FONT_HEIGHT + 2;
        }
    }

    public int getPing() {
        int p = -1;
        if (this.mc.player == null) return p;
        if (this.mc.getConnection() == null) return p;
        if (this.mc.getConnection().getPlayerInfo(this.mc.player.getName()) != null) return Objects.requireNonNull(this.mc.getConnection().getPlayerInfo(this.mc.player.getName())).getResponseTime();
        return p;
    }
}

