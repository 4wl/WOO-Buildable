/*
 * Decompiled with CFR 0.152.
 */
package wtf.cattyn.woo.client.gui.clickgui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import wtf.cattyn.woo.Woo;
import wtf.cattyn.woo.api.module.Module;
import wtf.cattyn.woo.api.setting.Setting;
import wtf.cattyn.woo.client.gui.clickgui.CategoryButton;
import wtf.cattyn.woo.client.gui.clickgui.ClickGUI;
import wtf.cattyn.woo.client.gui.clickgui.SettingButton;
import wtf.cattyn.woo.client.gui.clickgui.settings.BindButton;
import wtf.cattyn.woo.client.gui.clickgui.settings.BooleanSettingButton;
import wtf.cattyn.woo.client.gui.clickgui.settings.ColorButton;
import wtf.cattyn.woo.client.gui.clickgui.settings.DoubleSettingButton;
import wtf.cattyn.woo.client.gui.clickgui.settings.DrawnButton;
import wtf.cattyn.woo.client.gui.clickgui.settings.IntegerSettingButton;
import wtf.cattyn.woo.client.gui.clickgui.settings.ModeSettingButton;
import wtf.cattyn.woo.client.modules.client.ClickGuiMod;

public class ModuleButton {
    private final List<SettingButton> buttonList = new ArrayList<SettingButton>();
    private CategoryButton parent;
    private Module module;
    private int x;
    private int y;
    private int w;
    private int h;
    private boolean selected;
    private int scrollSetting;

    public ModuleButton(Module module, int x, int y, int w, int h, CategoryButton parent, int windowX, int windowY) {
        ClickGUI.buttonListHidden = new ArrayList<SettingButton>();
        this.module = module;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.parent = parent;
        this.buttonList.add(new BindButton(parent, module, windowX + 80, windowY + 40, 200, 20));
        this.buttonList.add(new DrawnButton(parent, module, windowX + 80 + 100, windowY + 40, 200, 20));
        int iSet = 1;
        Iterator<Setting> iterator = Woo.settingManager.getSettingsForMod(module).iterator();
        while (iterator.hasNext()) {
            Setting setting = iterator.next();
            SettingButton button = null;
            switch (setting.getType()) {
                case B: {
                    button = new BooleanSettingButton(parent, module, windowX + 80, windowY + 40 + iSet * 20, 200, 20, (Setting.b)setting);
                    break;
                }
                case I: {
                    button = new IntegerSettingButton(parent, module, windowX + 80, windowY + 40 + iSet * 20, 200, 20, (Setting.i)setting);
                    break;
                }
                case D: {
                    button = new DoubleSettingButton(parent, module, windowX + 80, windowY + 40 + iSet * 20, 200, 20, (Setting.d)setting);
                    break;
                }
                case M: {
                    button = new ModeSettingButton(parent, module, windowX + 80, windowY + 40 + iSet * 20, 200, 20, (Setting.mode)setting);
                    break;
                }
                case C: {
                    button = new ColorButton(parent, module, windowX + 80, windowY + 40 + iSet * 20, 200, 20, (Setting.c)setting);
                    break;
                }
            }
            this.buttonList.add(button);
            ++iSet;
        }
    }

    public void render(int mouseX, int mouseY, int scrollWheel, int windowX, int windowY, boolean self) {
        if (self) {
            Woo.fontManager.drawString((this.selected ? "| " : "") + this.module.getName(), this.x, this.y + 7, this.module.isToggled() ? new Color(255, 255, 255, ClickGuiMod.INSTANCE.getAlpha()).getRGB() : new Color(147, 147, 147, ClickGuiMod.INSTANCE.getAlpha()).getRGB());
        }
        int iSet1 = 0;
        for (SettingButton button : this.buttonList) {
            button.setX(windowX + 80);
            button.setY(windowY + 40 + iSet1 * 20);
            ++iSet1;
        }
        if (!this.selected) return;
        this.doScroll(mouseX, mouseY, scrollWheel, windowX, windowY);
        int iSet2 = 0;
        int setIndex = 0;
        Iterator<SettingButton> iterator = this.buttonList.iterator();
        while (iterator.hasNext()) {
            SettingButton button2 = iterator.next();
            if (setIndex < this.scrollSetting) {
                ++setIndex;
                continue;
            }
            if (iSet2 >= 7 || button2.hidden) continue;
            button2.setY(windowY + 40 + 20 * iSet2);
            button2.render(mouseX, mouseY);
            ++iSet2;
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton, boolean self) {
        if (this.isHover(this.x, this.y, this.w, this.h, mouseX, mouseY) && self) {
            if (mouseButton == 0) {
                this.module.toggle();
            }
            if (mouseButton == 1 || mouseButton == 0) {
                for (ModuleButton moduleButton : this.parent.getButtons()) {
                    moduleButton.selected = false;
                }
                this.selected = true;
            }
        }
        if (!this.selected) return;
        ClickGUI.lastMod = this.module;
        this.buttonList.forEach(settingButton -> settingButton.mouseClicked(mouseX, mouseY, mouseButton));
    }

    public void mouseReleased(int mouseX, int mouseY) {
        if (!this.selected) return;
        ClickGUI.buttonListHidden.clear();
        this.buttonList.forEach(button -> {
            button.mouseReleased(mouseX, mouseY);
            if (button.hidden) return;
            ClickGUI.buttonListHidden.add((SettingButton)button);
        });
    }

    public void keyTyped(char typedChar, int keyCode) {
        if (!this.selected) return;
        this.buttonList.forEach(button -> button.keyTyped(typedChar, keyCode));
    }

    private void doScroll(int mouseX, int mouseY, int scrollWheel, int windowX, int windowY) {
        if (scrollWheel < 0) {
            if (!this.isHover(windowX + 80, windowY + 40, 200, 180, mouseX, mouseY)) return;
            if (this.scrollSetting >= ClickGUI.buttonListHidden.size() - 7) {
                return;
            }
            ++this.scrollSetting;
            return;
        }
        if (scrollWheel <= 0) return;
        if (!this.isHover(windowX + 80, windowY + 40, 200, 180, mouseX, mouseY)) return;
        if (this.scrollSetting <= 0) {
            return;
        }
        --this.scrollSetting;
    }

    private boolean isHover(int X, int Y, int W, int H, int mX, int mY) {
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

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Module getModule() {
        return this.module;
    }
}

