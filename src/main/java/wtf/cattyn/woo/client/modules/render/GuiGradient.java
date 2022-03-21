//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\maywr\Documents\remapping\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiChat
 *  net.minecraft.client.gui.GuiConfirmOpenLink
 *  net.minecraft.client.gui.GuiControls
 *  net.minecraft.client.gui.GuiCustomizeSkin
 *  net.minecraft.client.gui.GuiGameOver
 *  net.minecraft.client.gui.GuiIngameMenu
 *  net.minecraft.client.gui.GuiOptions
 *  net.minecraft.client.gui.GuiScreenOptionsSounds
 *  net.minecraft.client.gui.GuiVideoSettings
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.gui.inventory.GuiContainer
 *  net.minecraft.client.gui.inventory.GuiEditSign
 *  net.minecraftforge.fml.client.GuiModList
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package wtf.cattyn.woo.client.modules.render;

import java.awt.Color;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiConfirmOpenLink;
import net.minecraft.client.gui.GuiControls;
import net.minecraft.client.gui.GuiCustomizeSkin;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreenOptionsSounds;
import net.minecraft.client.gui.GuiVideoSettings;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraftforge.fml.client.GuiModList;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wtf.cattyn.woo.api.event.Render2DEvent;
import wtf.cattyn.woo.api.module.Module;
import wtf.cattyn.woo.api.setting.Setting;
import wtf.cattyn.woo.api.util.RenderUtil;
import wtf.cattyn.woo.client.modules.client.ClickGuiMod;

public class GuiGradient
extends Module {
    public Setting.i r = this.registerI("Red", 255, 0, 255);
    public Setting.i g = this.registerI("Green", 0, 0, 255);
    public Setting.i b = this.registerI("Blue", 255, 0, 255);
    public Setting.i alpha = this.registerI("Alpha", 30, 0, 255);
    public Setting.i r2 = this.registerI("Red - 2", 255, 0, 255);
    public Setting.i g2 = this.registerI("Green - 2", 0, 0, 255);
    public Setting.i b2 = this.registerI("Blue - 2", 255, 0, 255);
    public Setting.i alpha2 = this.registerI("Alpha - 2", 150, 0, 255);
    private int startcolor;
    private int endcolor;

    public GuiGradient() {
        super("GuiGradient", Module.Category.Render);
    }

    @Override
    @SubscribeEvent
    public void onRender2D(Render2DEvent event) {
        if (this.nullCheck()) {
            return;
        }
        if (this.mc.world == null) return;
        this.startcolor = new Color(this.r.getValue(), this.g.getValue(), this.b.getValue(), this.alpha.getValue()).getRGB();
        this.endcolor = new Color(this.r2.getValue(), this.g2.getValue(), this.b2.getValue(), this.alpha2.getValue()).getRGB();
        ScaledResolution sr = new ScaledResolution(this.mc);
        if (!(ClickGuiMod.INSTANCE.isToggled() || this.mc.currentScreen instanceof GuiContainer || this.mc.currentScreen instanceof GuiChat || this.mc.currentScreen instanceof GuiConfirmOpenLink || this.mc.currentScreen instanceof GuiEditSign || this.mc.currentScreen instanceof GuiGameOver || this.mc.currentScreen instanceof GuiOptions || this.mc.currentScreen instanceof GuiIngameMenu || this.mc.currentScreen instanceof GuiVideoSettings || this.mc.currentScreen instanceof GuiScreenOptionsSounds || this.mc.currentScreen instanceof GuiControls || this.mc.currentScreen instanceof GuiCustomizeSkin || this.mc.currentScreen instanceof GuiModList)) {
            if (!(this.mc.currentScreen instanceof GuiModList)) return;
        }
        RenderUtil.drawGradientRect(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), this.startcolor, this.endcolor);
    }
}

