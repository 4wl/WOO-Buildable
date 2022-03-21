//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\maywr\Documents\remapping\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 */
package wtf.cattyn.woo.client.gui.clickgui.settings;

import java.awt.Color;
import java.io.IOException;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import wtf.cattyn.woo.Woo;
import wtf.cattyn.woo.api.module.Module;
import wtf.cattyn.woo.api.setting.Setting;
import wtf.cattyn.woo.api.util.RenderUtil;
import wtf.cattyn.woo.api.util.Wrapper;
import wtf.cattyn.woo.client.gui.clickgui.CategoryButton;
import wtf.cattyn.woo.client.gui.clickgui.ClickGUI;
import wtf.cattyn.woo.client.gui.clickgui.SettingButton;

public class ColorButton
extends SettingButton {
    private final Setting.c setting;
    private boolean dragging;

    public ColorButton(CategoryButton parentCategory, Module module, int x, int y, int w, int h, Setting.c setting) {
        super(parentCategory, module, x, y, w, h);
        this.setting = setting;
    }

    @Override
    public void render(int mouseX, int mouseY) {
        Woo.fontManager.drawString(this.setting.getName(), this.x + 5, this.y + 5, -1);
        RenderUtil.drawFakeRect(this.x + 179, this.y + 4, 12, 12, new Color(80, 80, 80).getRGB());
        RenderUtil.drawFakeRect(this.x + 180, this.y + 5, 10, 10, this.setting.getValue().getRGB());
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (!this.isHover(this.x, this.y, this.w, this.h - 1, mouseX, mouseY)) return;
        Wrapper.mc.displayGuiScreen((GuiScreen)new ColorGUI(this.setting, this.setting.getParent()));
        this.dragging = true;
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY) {
        this.dragging = false;
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent e) {
        this.hidden = this.setting.getHidden();
    }

    private static class ColorGUI
    extends GuiScreen {
        public int hue;
        private int x = 100;
        private int y = 100;
        private final int w;
        private final int h;
        private final Setting.c setting;
        private final Module module;
        private int dragX;
        private int dragY;
        private boolean dragging;
        private boolean hueDragging;
        private int hueSliderWidth;

        public ColorGUI(Setting.c setting, Module module) {
            this.w = 250;
            this.h = 100;
            this.setting = setting;
            this.module = module;
            this.hue = (int)Color.RGBtoHSB(setting.getValue().getRed(), setting.getValue().getGreen(), setting.getValue().getBlue(), null)[0];
        }

        public void drawScreen(int mouseX, int mouseY, float partialTicks) {
            this.updateHueSlider(mouseX);
            if (this.dragging) {
                this.x = this.dragX + mouseX;
                this.y = this.dragY + mouseY;
            }
            RenderUtil.drawFakeRect(this.x, this.y, this.w, this.h, new Color(44, 44, 44).getRGB());
            RenderUtil.drawFakeRect(this.x + 155, this.y + 5, this.h - 10, this.h - 10, this.setting.getValue().getRGB());
            RenderUtil.drawFakeRect(this.x + 5, this.y + 13, 145, 2, new Color(80, 80, 80).getRGB());
            RenderUtil.drawFakeRect(this.x + 4, this.y + 13, this.hueSliderWidth + 1, 2, this.hue);
        }

        protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
            if (this.isHover(this.x, this.y, this.w, 10, mouseX, mouseY) && mouseButton == 0) {
                this.dragging = true;
                this.dragX = this.x - mouseX;
                this.dragY = this.y - mouseY;
            }
            if (!this.isHover(this.x + 4, this.y + 10, 147, 6, mouseX, mouseY)) return;
            if (mouseButton != 0) return;
            this.hueDragging = true;
        }

        private void updateHueSlider(int mouseX) {
            double diff = Math.min(this.w - 10, Math.max(0, mouseX - this.x - 5));
            this.hueSliderWidth = (int)((float)(this.w - 10) * Color.RGBtoHSB(this.setting.getValue().getRed(), this.setting.getValue().getGreen(), this.setting.getValue().getBlue(), null)[0] / 360.0f);
            if (!this.hueDragging) return;
            if (diff == 0.0) {
                this.hue = 0;
                return;
            }
            if (diff == (double)(this.w - 10)) {
                this.hue = 360;
                return;
            }
            this.hue = (int)(diff / (double)(this.w - 10) * 360.0);
        }

        protected void mouseReleased(int mouseX, int mouseY, int state) {
            this.dragging = false;
        }

        protected void keyTyped(char typedChar, int keyCode) throws IOException {
            if (keyCode != 1) return;
            this.mc.displayGuiScreen((GuiScreen)new ClickGUI());
        }

        public void onGuiClosed() {
            this.dragging = false;
            this.hueDragging = false;
        }

        public boolean doesGuiPauseGame() {
            return false;
        }

        private boolean isHover(int X, int Y, int W, int H, int mX, int mY) {
            if (mX < X) return false;
            if (mX > X + W) return false;
            if (mY < Y) return false;
            if (mY > Y + H) return false;
            return true;
        }
    }
}

