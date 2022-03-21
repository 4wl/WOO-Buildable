/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 */
package wtf.cattyn.woo.client.gui.clickgui.settings;

import java.awt.Color;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import wtf.cattyn.woo.Woo;
import wtf.cattyn.woo.api.module.Module;
import wtf.cattyn.woo.api.setting.Setting;
import wtf.cattyn.woo.api.util.RenderUtil;
import wtf.cattyn.woo.client.gui.clickgui.CategoryButton;
import wtf.cattyn.woo.client.gui.clickgui.SettingButton;

public class ModeSettingButton
extends SettingButton {
    private final Setting.mode setting;
    private int modeIndex;

    public ModeSettingButton(CategoryButton parentCategory, Module module, int x, int y, int w, int h, Setting.mode setting) {
        super(parentCategory, module, x, y, w, h);
        this.setting = setting;
    }

    @Override
    public void render(int mouseX, int mouseY) {
        Woo.fontManager.drawString(this.setting.getName(), this.x + 5, this.y + 5, -1);
        RenderUtil.drawFakeRect(this.x + this.w - 6 - Woo.fontManager.getStringWidth(this.setting.getValue()), this.y + 5, Woo.fontManager.getStringWidth(this.setting.getValue()), 8, new Color(80, 80, 80).getRGB());
        Woo.fontManager.drawString(this.setting.getValue(), this.x + this.w - 5 - Woo.fontManager.getStringWidth(this.setting.getValue()), this.y + 5, new Color(255, 255, 255).getRGB());
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (!this.isHover(this.x, this.y, this.w, this.h - 1, mouseX, mouseY)) return;
        if (mouseButton == 0) {
            int maxIndex = this.setting.getModes().size() - 1;
            ++this.modeIndex;
            if (this.modeIndex > maxIndex) {
                this.modeIndex = 0;
            }
            this.setting.setValue(this.setting.getModes().get(this.modeIndex));
            return;
        }
        if (mouseButton != 1) return;
        int maxIndex = this.setting.getModes().size() - 1;
        --this.modeIndex;
        if (this.modeIndex < 0) {
            this.modeIndex = maxIndex;
        }
        this.setting.setValue(this.setting.getModes().get(this.modeIndex));
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent e) {
        this.hidden = this.setting.getHidden();
    }
}

