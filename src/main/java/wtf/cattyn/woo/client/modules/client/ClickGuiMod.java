//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\maywr\Documents\remapping\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiScreen
 */
package wtf.cattyn.woo.client.modules.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import wtf.cattyn.woo.api.module.Module;
import wtf.cattyn.woo.api.setting.Setting;
import wtf.cattyn.woo.client.gui.clickgui.ClickGUI;

public class ClickGuiMod
extends Module {
    public static ClickGuiMod INSTANCE;
    public Setting.i r = this.registerI("Red", 255, 0, 255);
    public Setting.i g = this.registerI("Green", 0, 0, 255);
    public Setting.i b = this.registerI("Blue", 255, 0, 255);
    public Setting.b playerHud = this.registerB("Second Panel", true);
    public Setting.b animation = this.registerB("Animation", true);
    public static int alpha;

    public ClickGuiMod() {
        super("ClickGui", Module.Category.Client);
        this.setBind(210);
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
        if (this.nullCheck()) {
            return;
        }
        alpha = 1;
        this.mc.displayGuiScreen((GuiScreen)new ClickGUI());
    }

    public static void updateAlpha() {
        float fps = 1.0f / (float)Minecraft.getDebugFPS();
        if ((alpha = (int)((float)alpha + 980.7693f * fps)) < 255) return;
        alpha = 255;
    }

    public int getAlpha() {
        if (!this.animation.getValue()) return 255;
        int n = alpha;
        return n;
    }
}

