/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.common.MinecraftForge
 */
package wtf.cattyn.woo.client.gui.clickgui;

import net.minecraftforge.common.MinecraftForge;
import wtf.cattyn.woo.api.module.Module;
import wtf.cattyn.woo.client.gui.clickgui.CategoryButton;

public class SettingButton {
    protected final CategoryButton parentCategory;
    protected final Module module;
    protected int x;
    protected int y;
    protected final int w;
    protected final int h;
    protected boolean hidden;

    public SettingButton(CategoryButton parentCategory, Module module, int x, int y, int w, int h) {
        MinecraftForge.EVENT_BUS.register((Object)this);
        this.parentCategory = parentCategory;
        this.module = module;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.hidden = false;
    }

    public void render(int mouseX, int mouseY) {
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
    }

    public void mouseReleased(int mouseX, int mouseY) {
    }

    public void keyTyped(char typedChar, int keyCode) {
    }

    protected boolean isHover(int X, int Y, int W, int H, int mX, int mY) {
        if (mX < X) return false;
        if (mX > X + W) return false;
        if (mY < Y) return false;
        if (mY > Y + H) return false;
        return true;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}

