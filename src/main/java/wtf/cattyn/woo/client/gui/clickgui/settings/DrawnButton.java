/*
 * Decompiled with CFR 0.152.
 */
package wtf.cattyn.woo.client.gui.clickgui.settings;

import java.awt.Color;
import wtf.cattyn.woo.Woo;
import wtf.cattyn.woo.api.module.Module;
import wtf.cattyn.woo.api.util.RenderUtil;
import wtf.cattyn.woo.client.gui.clickgui.CategoryButton;
import wtf.cattyn.woo.client.gui.clickgui.ClickGUI;
import wtf.cattyn.woo.client.gui.clickgui.SettingButton;

public class DrawnButton
extends SettingButton {
    public DrawnButton(CategoryButton parentCategory, Module module, int x, int y, int w, int h) {
        super(parentCategory, module, x, y, w, h);
    }

    @Override
    public void render(int mouseX, int mouseY) {
        Woo.fontManager.drawString("Drawn", this.x + 5, this.y + 5, new Color(255, 255, 255).getRGB());
        RenderUtil.drawFakeRect(this.x + 180, this.y + 5, 12, 7, new Color(25, 25, 25).getRGB());
        if (this.module.isDrawn()) {
            RenderUtil.drawFakeRect(this.x + 186, this.y + 6, 5, 5, ClickGUI.guicolor);
            return;
        }
        RenderUtil.drawFakeRect(this.x + 181, this.y + 6, 5, 5, new Color(60, 60, 60).getRGB());
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (!this.isHover(this.x, this.y, this.w, this.h - 1, mouseX, mouseY)) return;
        this.module.setDrawn(!this.module.isDrawn());
    }
}

