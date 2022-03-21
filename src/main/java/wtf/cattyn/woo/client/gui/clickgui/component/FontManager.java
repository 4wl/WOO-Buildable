//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\maywr\Documents\remapping\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.math.MathHelper
 */
package wtf.cattyn.woo.client.gui.clickgui.component;

import java.awt.Font;
import net.minecraft.util.math.MathHelper;
import wtf.cattyn.woo.api.util.Timers;
import wtf.cattyn.woo.api.util.Wrapper;
import wtf.cattyn.woo.client.gui.clickgui.component.CFontRenderer;

public class FontManager
extends Wrapper {
    public CFontRenderer fontRenderer = new CFontRenderer(new Font("Verdana", 0, 16), true, true);
    public CFontRenderer fontRendererBIG = new CFontRenderer(new Font("Verdana", 1, 30), true, true);
    private boolean customFont = false;
    private final Timers idleTimer = new Timers();
    private boolean idling;
    public int scaledWidth;
    public int scaledHeight;
    public int scaleFactor;

    public void setCustomFont(boolean bool) {
        this.customFont = bool;
    }

    public int drawString(String text, float x, float y, int color) {
        if (!this.customFont) return Wrapper.mc.fontRenderer.drawStringWithShadow(text, x, y, color);
        return (int)this.fontRenderer.drawStringWithShadow(text, x, y, color);
    }

    public int drawString(String text, float x, float y, int color, boolean big) {
        if (this.customFont && !big) {
            return (int)this.fontRenderer.drawStringWithShadow(text, x, y, color);
        }
        if (!big) return Wrapper.mc.fontRenderer.drawStringWithShadow(text, x, y, color);
        return (int)this.fontRendererBIG.drawStringWithShadow(text, x, y, color);
    }

    public void updateResolution() {
        this.scaledWidth = FontManager.mc.displayWidth;
        this.scaledHeight = FontManager.mc.displayHeight;
        this.scaleFactor = 1;
        boolean flag = mc.isUnicode();
        int i2 = FontManager.mc.gameSettings.guiScale;
        if (i2 == 0) {
            i2 = 1000;
        }
        while (this.scaleFactor < i2 && this.scaledWidth / (this.scaleFactor + 1) >= 320 && this.scaledHeight / (this.scaleFactor + 1) >= 240) {
            ++this.scaleFactor;
        }
        if (flag && this.scaleFactor % 2 != 0 && this.scaleFactor != 1) {
            --this.scaleFactor;
        }
        double scaledWidthD = this.scaledWidth / this.scaleFactor;
        double scaledHeightD = this.scaledHeight / this.scaleFactor;
        this.scaledWidth = MathHelper.ceil((double)scaledWidthD);
        this.scaledHeight = MathHelper.ceil((double)scaledHeightD);
    }

    public String getIdleSign() {
        if (this.idleTimer.passedMs(500L)) {
            this.idling = !this.idling;
            this.idleTimer.reset();
        }
        if (!this.idling) return "";
        return "_";
    }

    public int drawCenteredString(String text, float x, float y, int color, boolean shadow) {
        if (!this.customFont) return Wrapper.mc.fontRenderer.drawString(text, x - (float)this.getStringWidth(text) / 2.0f, y, color, shadow);
        return (int)this.fontRenderer.drawStringWithShadow(text, x - (float)this.getStringWidth(text) / 2.0f, y, color);
    }

    public int drawStringNoShadow(String text, float x, float y, int color, boolean big) {
        if (this.customFont && !big) {
            return (int)this.fontRenderer.drawString(text, x, y, color, false);
        }
        if (!big) return Wrapper.mc.fontRenderer.drawString(text, x, y, color, false);
        return (int)this.fontRendererBIG.drawString(text, x, y, color, false);
    }

    public int getStringHeight() {
        if (!this.customFont) return 9;
        return this.fontRenderer.getStringHeight("A");
    }

    public int getStringWidth(String text) {
        if (!this.customFont) return Wrapper.mc.fontRenderer.getStringWidth(text);
        return this.fontRenderer.getStringWidth(text);
    }
}

