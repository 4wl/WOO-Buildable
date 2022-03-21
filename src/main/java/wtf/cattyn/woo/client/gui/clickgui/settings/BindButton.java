/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package wtf.cattyn.woo.client.gui.clickgui.settings;

import java.awt.Color;
import org.lwjgl.input.Keyboard;
import wtf.cattyn.woo.Woo;
import wtf.cattyn.woo.api.module.Module;
import wtf.cattyn.woo.client.gui.clickgui.CategoryButton;
import wtf.cattyn.woo.client.gui.clickgui.SettingButton;

public class BindButton
extends SettingButton {
    boolean binding;

    public BindButton(CategoryButton parentCategory, Module module, int x, int y, int w, int h) {
        super(parentCategory, module, x, y, w, h);
    }

    @Override
    public void render(int mouseX, int mouseY) {
        Woo.fontManager.drawString("Bind", this.x + 5, this.y + 5, new Color(255, 255, 255).getRGB());
        if (this.binding) {
            Woo.fontManager.drawString("[ ... ]", this.x + this.w - 5 - Woo.fontManager.getStringWidth("[ ... ]"), this.y + 5, new Color(255, 255, 255).getRGB());
            return;
        }
        try {
            Woo.fontManager.drawString("[ " + Keyboard.getKeyName((int)this.module.getBind()) + " ]", this.x + this.w - 5 - Woo.fontManager.getStringWidth("[ " + Keyboard.getKeyName((int)this.module.getBind()) + " ]"), this.y + 5, new Color(255, 255, 255).getRGB());
            return;
        }
        catch (Exception e) {
            Woo.fontManager.drawString("[ NONE ]", this.x + this.w - 10 - Woo.fontManager.getStringWidth("[ NONE ]"), this.y + 5, new Color(255, 255, 255).getRGB());
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (!this.isHover(this.x, this.y, this.w, this.h - 1, mouseX, mouseY)) return;
        this.binding = !this.binding;
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        if (!this.binding) return;
        if (keyCode != 211 && keyCode != 14) {
            this.module.setBind(keyCode);
            this.binding = false;
            return;
        }
        this.module.setBind(0);
        this.binding = false;
    }
}

