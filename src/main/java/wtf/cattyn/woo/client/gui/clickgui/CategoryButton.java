//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\maywr\Documents\remapping\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package wtf.cattyn.woo.client.gui.clickgui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import wtf.cattyn.woo.Woo;
import wtf.cattyn.woo.api.module.Module;
import wtf.cattyn.woo.client.gui.clickgui.ClickGUI;
import wtf.cattyn.woo.client.gui.clickgui.ModuleButton;
import wtf.cattyn.woo.client.modules.client.ClickGuiMod;

public class CategoryButton {
    private final List<ModuleButton> buttons = new ArrayList<ModuleButton>();
    private final ResourceLocation image;
    private final Module.Category category;
    private int x;
    private int y;
    private final int w;
    private final int h;
    private boolean selected;
    private int scrollModule;

    public CategoryButton(Module.Category category, int x, int y, int w, int h, ResourceLocation image1, int windowX, int windowY) {
        this.category = category;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.selected = false;
        int iMod = 0;
        this.image = image1;
        Iterator<Module> iterator = Woo.moduleManager.getModules(category).iterator();
        while (iterator.hasNext()) {
            Module module = iterator.next();
            ModuleButton button = new ModuleButton(module, windowX, windowY + 40 + 20 * iMod, 80, 20, this, windowX, windowY);
            if (ClickGUI.lastMod != null && button.getModule().equals(ClickGUI.lastMod)) {
                button.setSelected(true);
            }
            this.buttons.add(button);
            ++iMod;
        }
    }

    public void render(int mouseX, int mouseY, int scrollWheel, int windowX, int windowY) {
        GlStateManager.enableBlend();
        Minecraft.getMinecraft().getTextureManager().bindTexture(this.image);
        if (this.selected) {
            GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)((float)ClickGuiMod.INSTANCE.getAlpha() / 255.0f));
        } else {
            GlStateManager.color((float)0.5f, (float)0.5f, (float)0.5f, (float)((float)ClickGuiMod.INSTANCE.getAlpha() / 255.0f));
        }
        CategoryButton.drawCompleteImage(this.x + 10, this.y + 8, 16, 16);
        GlStateManager.disableBlend();
        for (int i2 = 0; i2 < this.buttons.size(); ++i2) {
            ModuleButton button = this.buttons.get(i2);
            button.setX(windowX + 2);
            button.setY(windowY + 40 + 20 * i2);
        }
        if (!this.selected) return;
        this.doScroll(mouseX, mouseY, scrollWheel, windowX, windowY);
        int iMod = 0;
        int modIndex = 0;
        Iterator<ModuleButton> iterator = this.buttons.iterator();
        while (iterator.hasNext()) {
            ModuleButton button2 = iterator.next();
            if (modIndex < this.scrollModule) {
                ++modIndex;
                if (!button2.isSelected()) continue;
                button2.render(mouseX, mouseY, scrollWheel, windowX, windowY, false);
                continue;
            }
            if (iMod >= 10) {
                if (!button2.isSelected()) continue;
                button2.render(mouseX, mouseY, scrollWheel, windowX, windowY, false);
                continue;
            }
            button2.setY(this.y + 37 + 15 * iMod);
            button2.render(mouseX, mouseY, scrollWheel, windowX, windowY, true);
            ++iMod;
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (this.isHover(this.x, this.y + 10, this.w, this.h - 10, mouseX, mouseY) && mouseButton == 0) {
            for (CategoryButton btn : ClickGUI.buttons) {
                btn.selected = false;
            }
            this.selected = true;
        }
        if (!this.selected) return;
        ClickGUI.lastCat = this.category;
        int iMod = 0;
        int modIndex = 0;
        Iterator<ModuleButton> iterator = this.buttons.iterator();
        while (iterator.hasNext()) {
            ModuleButton button2 = iterator.next();
            if (modIndex < this.scrollModule) {
                ++modIndex;
                button2.mouseClicked(mouseX, mouseY, mouseButton, false);
                continue;
            }
            if (iMod >= 10) {
                button2.mouseClicked(mouseX, mouseY, mouseButton, false);
                continue;
            }
            button2.mouseClicked(mouseX, mouseY, mouseButton, true);
            ++iMod;
        }
    }

    public void mouseReleased(int mouseX, int mouseY) {
        if (!this.selected) return;
        this.buttons.forEach(button -> button.mouseReleased(mouseX, mouseY));
    }

    public void keyTyped(char typedChar, int keyCode) {
        if (!this.selected) return;
        this.buttons.forEach(button -> button.keyTyped(typedChar, keyCode));
    }

    private void doScroll(int mouseX, int mouseY, int scrollWheel, int windowX, int windowY) {
        if (scrollWheel < 0) {
            if (!this.isHover(windowX, windowY + 40, 80, 180, mouseX, mouseY)) return;
            if (this.scrollModule >= this.buttons.size() - 10) {
                return;
            }
            ++this.scrollModule;
            return;
        }
        if (scrollWheel <= 0) return;
        if (!this.isHover(windowX, windowY + 40, 80, 180, mouseX, mouseY)) return;
        if (this.scrollModule <= 0) {
            return;
        }
        --this.scrollModule;
    }

    private boolean isHover(int X, int Y, int W, int H, int mX, int mY) {
        if (mX < X) return false;
        if (mX > X + W) return false;
        if (mY < Y) return false;
        if (mY > Y + H) return false;
        return true;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Module.Category getCategory() {
        return this.category;
    }

    public List<ModuleButton> getButtons() {
        return this.buttons;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public static void drawCompleteImage(float posX, float posY, int width, int height) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)posX, (float)posY, (float)0.0f);
        GL11.glBegin((int)7);
        GL11.glTexCoord2f((float)0.0f, (float)0.0f);
        GL11.glVertex3f((float)0.0f, (float)0.0f, (float)0.0f);
        GL11.glTexCoord2f((float)0.0f, (float)1.0f);
        GL11.glVertex3f((float)0.0f, (float)height, (float)0.0f);
        GL11.glTexCoord2f((float)1.0f, (float)1.0f);
        GL11.glVertex3f((float)width, (float)height, (float)0.0f);
        GL11.glTexCoord2f((float)1.0f, (float)0.0f);
        GL11.glVertex3f((float)width, (float)0.0f, (float)0.0f);
        GL11.glEnd();
        GL11.glPopMatrix();
    }
}

