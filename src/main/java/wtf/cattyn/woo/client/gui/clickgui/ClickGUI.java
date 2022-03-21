//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\maywr\Documents\remapping\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.OpenGlHelper
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.entity.RenderManager
 *  net.minecraft.entity.Entity
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.input.Mouse
 */
package wtf.cattyn.woo.client.gui.clickgui;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Color;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import wtf.cattyn.woo.Woo;
import wtf.cattyn.woo.api.module.Module;
import wtf.cattyn.woo.api.util.ColorUtil;
import wtf.cattyn.woo.api.util.RenderUtil;
import wtf.cattyn.woo.client.gui.clickgui.CategoryButton;
import wtf.cattyn.woo.client.gui.clickgui.SettingButton;
import wtf.cattyn.woo.client.gui.clickgui.component.Snow;
import wtf.cattyn.woo.client.modules.client.ClickGuiMod;

public class ClickGUI
extends GuiScreen {
    public static int startX = 0;
    public static int startY = 0;
    public static Module.Category lastCat = null;
    public static Module lastMod = null;
    public static List<SettingButton> buttonListHidden = null;
    public static final ClickGUI INSTANCE = new ClickGUI();
    private int x;
    private int y;
    private int w;
    private int h;
    public static List<CategoryButton> buttons;
    private ArrayList<Snow> snowList = new ArrayList();
    private int dragX;
    private int dragY;
    private boolean dragging;
    public static int guicolor;

    public ClickGUI() {
        buttons = new ArrayList<CategoryButton>();
        this.x = startX;
        this.y = startY;
        this.w = 280;
        this.h = 180;
        Random random = new Random();
        for (int i2 = 0; i2 < 100; ++i2) {
            for (int y = 0; y < 3; ++y) {
                Snow snow = new Snow(25 * i2, y * -50, random.nextInt(3) + 1, random.nextInt(2) + 1);
                this.snowList.add(snow);
            }
        }
        Module.Category[] values = Module.Category.values();
        int i3 = 0;
        while (i3 < values.length) {
            Module.Category category = values[i3];
            ResourceLocation image = null;
            ResourceLocation combat = new ResourceLocation("gui/combat.png");
            ResourceLocation movement = new ResourceLocation("gui/move.png");
            ResourceLocation render = new ResourceLocation("gui/render.png");
            ResourceLocation misc = new ResourceLocation("gui/misc.png");
            ResourceLocation component = new ResourceLocation("gui/component.png");
            ResourceLocation client = new ResourceLocation("gui/client.png");
            switch (values[i3]) {
                case Combat: {
                    image = combat;
                    break;
                }
                case Misc: {
                    image = misc;
                    break;
                }
                case Movement: {
                    image = movement;
                    break;
                }
                case Render: {
                    image = render;
                    break;
                }
                case Player: {
                    image = component;
                    break;
                }
                case Client: {
                    image = client;
                    break;
                }
            }
            CategoryButton button = new CategoryButton(category, this.x + i3 * 40, this.y, 40, 40, image, this.x, this.y);
            buttons.add(button);
            if (lastCat != null) {
                if (button.getCategory().equals((Object)lastCat)) {
                    button.setSelected(true);
                }
            } else if (button.getCategory().equals((Object)Module.Category.Combat)) {
                button.setSelected(true);
            }
            ++i3;
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution resolution = new ScaledResolution(this.mc);
        ClickGuiMod.updateAlpha();
        if (!this.snowList.isEmpty()) {
            this.snowList.forEach(snow -> snow.Update(resolution));
        }
        if (this.dragging) {
            int screenWidth = resolution.getScaledWidth();
            int screenHeight = resolution.getScaledHeight();
            this.x = this.dragX + mouseX;
            this.y = this.dragY + mouseY;
            if (this.x < 0) {
                this.x = 0;
            }
            if (this.y < 0) {
                this.y = 0;
            }
            if (this.x + this.w > screenWidth) {
                this.x = screenWidth - this.w;
            }
            if (this.y + this.h > screenHeight) {
                this.y = screenHeight - this.h;
            }
        }
        this.drawFrame();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        Woo.fontManager.drawCenteredString(dtf.format(now), this.x + this.w / 2, this.y + this.h + 11, new Color(255, 255, 255, ClickGuiMod.INSTANCE.getAlpha()).getRGB(), false);
        ScaledResolution sr = new ScaledResolution(this.mc);
        Woo.fontManager.drawString("Woo v1 : Developed by cattyyn, oyzip and yoursleep", 3.0f, sr.getScaledHeight() - 13, new Color(255, 255, 255, ClickGuiMod.INSTANCE.getAlpha()).getRGB());
        Woo.fontManager.drawString("W" + ChatFormatting.WHITE + "oo", this.x + 4, this.y + 7, ColorUtil.rainbowInt(0, 0.6f, 1.0f), true);
        int i2 = 0;
        while (true) {
            if (i2 >= buttons.size()) {
                int wheel = Mouse.getDWheel();
                buttons.forEach(button -> button.render(mouseX, mouseY, wheel, this.x, this.y));
                return;
            }
            CategoryButton button2 = buttons.get(i2);
            button2.setX(this.x + 50 + i2 * 40);
            button2.setY(this.y);
            ++i2;
        }
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (this.isHover(this.x, this.y, this.w, 10, mouseX, mouseY) && mouseButton == 0) {
            this.dragging = true;
            this.dragX = this.x - mouseX;
            this.dragY = this.y - mouseY;
        }
        buttons.forEach(button -> button.mouseClicked(mouseX, mouseY, mouseButton));
    }

    protected void mouseReleased(int mouseX, int mouseY, int state) {
        this.dragging = false;
        buttons.forEach(button -> button.mouseReleased(mouseX, mouseY));
    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        buttons.forEach(button -> button.keyTyped(typedChar, keyCode));
    }

    public void onGuiClosed() {
        this.dragging = false;
        startX = this.x;
        startY = this.y;
        Woo.moduleManager.getModule("ClickGUI").toggle();
    }

    public boolean doesGuiPauseGame() {
        return false;
    }

    private void drawFrame() {
        guicolor = new Color(ClickGuiMod.INSTANCE.r.getValue(), ClickGuiMod.INSTANCE.g.getValue(), ClickGuiMod.INSTANCE.b.getValue(), ClickGuiMod.INSTANCE.getAlpha()).getRGB();
        RenderUtil.drawFakeRect(this.x - 10, this.y + 30, this.w + 20, this.h - 20, new Color(33, 33, 33, ClickGuiMod.alpha).getRGB());
        if (ClickGuiMod.INSTANCE.playerHud.getValue()) {
            RenderUtil.drawFakeRect(this.x + 300, this.y + 30, this.w - 160, this.h - 20, new Color(47, 47, 47, ClickGuiMod.INSTANCE.getAlpha()).getRGB());
            RenderUtil.drawFakeRect(this.x + 300, this.y, this.w - 160, this.h - 150, guicolor);
            RenderUtil.drawFakeRect(this.x + 300, this.y + 190, this.w - 160, 10, guicolor);
            RenderUtil.drawFakeRect(this.x + 305, this.y + 5, this.w - 170, this.h + 10, new Color(38, 37, 37, ClickGuiMod.INSTANCE.getAlpha()).getRGB());
            this.drawPlayer();
        }
        RenderUtil.drawFakeRect(this.x - 10, this.y, this.w + 20, 30, guicolor);
        RenderUtil.drawRoundedFakeRect(this.x - 1, this.y + 39, 72, this.h - 38, new Color(66, 66, 66, ClickGuiMod.INSTANCE.getAlpha()));
        RenderUtil.drawRoundedFakeRect(this.x, this.y + 40, 70, this.h - 40, new Color(44, 44, 44, ClickGuiMod.INSTANCE.getAlpha()));
        RenderUtil.drawRoundedFakeRect(this.x + 79, this.y + 39, this.w - 78, this.h - 38, new Color(66, 66, 66, ClickGuiMod.INSTANCE.getAlpha()));
        RenderUtil.drawRoundedFakeRect(this.x + 80, this.y + 40, this.w - 80, this.h - 40, new Color(44, 44, 44, ClickGuiMod.INSTANCE.getAlpha()));
        RenderUtil.drawFakeRect(this.x - 10, this.y + 190, this.w + 20, 10, guicolor);
        RenderUtil.drawFakeRect(this.x - 10, this.y + 190, this.w + 20, 8, guicolor);
    }

    private boolean isHover(int X, int Y, int W, int H, int mX, int mY) {
        if (mX < X) return false;
        if (mX > X + W) return false;
        if (mY < Y) return false;
        if (mY > Y + H) return false;
        return true;
    }

    public void drawPlayer() {
        EntityPlayerSP ent = this.mc.player;
        GlStateManager.pushMatrix();
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)((float)ClickGuiMod.INSTANCE.getAlpha() / 255.0f));
        RenderHelper.enableStandardItemLighting();
        GlStateManager.enableAlpha();
        GlStateManager.shadeModel((int)7424);
        GlStateManager.enableAlpha();
        GlStateManager.enableDepth();
        GlStateManager.rotate((float)0.0f, (float)0.0f, (float)5.0f, (float)0.0f);
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)(this.x + 360), (float)(this.y + 168), (float)50.0f);
        GlStateManager.scale((float)-75.0f, (float)75.0f, (float)75.0f);
        GlStateManager.rotate((float)180.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        GlStateManager.rotate((float)135.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate((float)-135.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.rotate((float)(-((float)Math.atan(0.025f)) * 20.0f), (float)1.0f, (float)0.0f, (float)0.0f);
        GlStateManager.translate((float)0.0f, (float)0.0f, (float)0.0f);
        RenderManager rendermanager = this.mc.getRenderManager();
        rendermanager.setPlayerViewY(180.0f);
        rendermanager.setRenderShadow(false);
        try {
            rendermanager.renderEntity((Entity)ent, 0.0, 0.0, 0.0, 0.0f, 1.0f, false);
        }
        catch (Exception exception) {
            // empty catch block
        }
        rendermanager.setRenderShadow(true);
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture((int)OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture((int)OpenGlHelper.defaultTexUnit);
        GlStateManager.depthFunc((int)515);
        GlStateManager.resetColor();
        GlStateManager.disableDepth();
        GlStateManager.popMatrix();
    }
}

