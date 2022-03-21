//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\maywr\Documents\remapping\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 */
package wtf.cattyn.woo.client.gui.clickgui.settings;

import java.awt.Color;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import wtf.cattyn.woo.Woo;
import wtf.cattyn.woo.api.module.Module;
import wtf.cattyn.woo.api.setting.Setting;
import wtf.cattyn.woo.api.util.RenderUtil;
import wtf.cattyn.woo.client.gui.clickgui.CategoryButton;
import wtf.cattyn.woo.client.gui.clickgui.ClickGUI;
import wtf.cattyn.woo.client.gui.clickgui.SettingButton;
import wtf.cattyn.woo.client.modules.client.ClickGuiMod;

public class DoubleSettingButton
extends SettingButton {
    private final Setting.d setting;
    private boolean dragging;
    private int sliderWidth;

    public DoubleSettingButton(CategoryButton parentCategory, Module module, int x, int y, int w, int h, Setting.d setting) {
        super(parentCategory, module, x, y, w, h);
        this.setting = setting;
    }

    @Override
    public void render(int mouseX, int mouseY) {
        this.updateSlider(mouseX);
        if (this.setting.getValue() > this.setting.getMax()) {
            this.setting.setValue(this.setting.getMax());
        }
        RenderUtil.drawRoundedFakeRect(this.x + 5, this.y + 13, this.w - 10, 2, new Color(80, 80, 80, ClickGuiMod.INSTANCE.getAlpha()).getRGB());
        RenderUtil.drawRoundedFakeRect(this.x + 4, this.y + 13, this.sliderWidth + 1, 2, ClickGUI.guicolor);
        Woo.fontManager.drawString(this.setting.getName(), this.x + 5, this.y + 1, -1, false);
        GlStateManager.pushMatrix();
        GlStateManager.scale((float)0.9f, (float)0.9f, (float)0.9f);
        Woo.fontManager.drawCenteredString(String.format("%.2f", this.setting.getValue()).replace(".00", ""), ((float)this.x + (float)this.w / 2.0f) / 0.9f * 1.0f, (float)(this.y + 16) / 0.9f * 1.0f, -1, false);
        GlStateManager.scale((float)1.0f, (float)1.0f, (float)1.0f);
        GlStateManager.popMatrix();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (!this.isHover(this.x, this.y, this.w, this.h - 1, mouseX, mouseY)) return;
        this.dragging = true;
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY) {
        this.dragging = false;
    }

    private void updateSlider(int mouseX) {
        double diff = Math.min(this.w - 10, Math.max(0, mouseX - this.x - 5));
        double minimum = this.setting.getMin();
        double maximum = this.setting.getMax();
        this.sliderWidth = (int)((double)(this.w - 10) * (this.setting.getValue() - minimum) / (maximum - minimum));
        if (!this.dragging) return;
        if (diff == 0.0) {
            this.setting.setValue((int)minimum);
            return;
        }
        if (diff == (double)(this.w - 10)) {
            this.setting.setValue((int)maximum);
            return;
        }
        this.setting.setValue(diff / (double)(this.w - 10) * (maximum - minimum) + minimum);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent e) {
        this.hidden = this.setting.getHidden();
    }
}

