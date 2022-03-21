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
import wtf.cattyn.woo.client.gui.clickgui.ClickGUI;
import wtf.cattyn.woo.client.gui.clickgui.SettingButton;
import wtf.cattyn.woo.client.modules.client.ClickGuiMod;

public class BooleanSettingButton
extends SettingButton {
    private final Setting.b setting;

    public BooleanSettingButton(CategoryButton parentCategory, Module module, int x, int y, int w, int h, Setting.b setting) {
        super(parentCategory, module, x, y, w, h);
        this.setting = setting;
    }

    @Override
    public void render(int mouseX, int mouseY) {
        Woo.fontManager.drawString(this.setting.getName(), this.x + 5, this.y + 5, new Color(255, 255, 255).getRGB());
        RenderUtil.drawFakeRect(this.x + 180, this.y + 5, 12, 7, new Color(25, 25, 25, ClickGuiMod.INSTANCE.getAlpha()).getRGB());
        if (this.setting.getValue()) {
            RenderUtil.drawFakeRect(this.x + 186, this.y + 6, 5, 5, ClickGUI.guicolor);
            return;
        }
        RenderUtil.drawFakeRect(this.x + 181, this.y + 6, 5, 5, new Color(60, 60, 60, ClickGuiMod.INSTANCE.getAlpha()).getRGB());
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (!this.isHover(this.x, this.y, this.w, this.h - 1, mouseX, mouseY)) return;
        if (mouseButton != 0) return;
        this.setting.setValue(!this.setting.getValue());
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent e) {
        this.hidden = this.setting.getHidden();
    }
}

