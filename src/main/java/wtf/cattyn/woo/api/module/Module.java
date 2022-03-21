//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\maywr\Documents\remapping\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraftforge.common.MinecraftForge
 */
package wtf.cattyn.woo.api.module;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Color;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import wtf.cattyn.woo.Woo;
import wtf.cattyn.woo.api.event.Render2DEvent;
import wtf.cattyn.woo.api.event.Render3DEvent;
import wtf.cattyn.woo.api.setting.Setting;
import wtf.cattyn.woo.api.util.TextUtil;
import wtf.cattyn.woo.client.modules.client.Notify;

public class Module {
    public String name;
    public String desc;
    public String hudInfo;
    public boolean open;
    public Category category;
    private int bind;
    private boolean toggled;
    private boolean drawn = true;
    public final Minecraft mc = Minecraft.getMinecraft();
    public final Random random = new Random();

    public Module(String name, String desc, Category category) {
        this.name = name;
        this.open = false;
        this.category = category;
        this.desc = desc;
        this.hudInfo = "";
    }

    public Module(String name, Category category) {
        this.name = name;
        this.open = false;
        this.category = category;
        this.desc = "";
        this.hudInfo = "";
    }

    public void onEnable() {
    }

    public void onDisable() {
    }

    public void onArtificialUpdate() {
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isOpen() {
        return this.open;
    }

    public void onRender2D(Render2DEvent event) {
    }

    public void onRender3D(Render3DEvent event) {
    }

    public void enable() {
        MinecraftForge.EVENT_BUS.register((Object)this);
        this.setToggled(true);
        if (Notify.INSTANCE.enabledModule.getValue()) {
            TextUtil.sendMessage("" + ChatFormatting.LIGHT_PURPLE + ChatFormatting.BOLD + this.getName() + ChatFormatting.GRAY + " was " + ChatFormatting.GREEN + "enabled", true);
        }
        this.onEnable();
    }

    public void disable() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
        this.setToggled(false);
        if (Notify.INSTANCE.enabledModule.getValue()) {
            TextUtil.sendMessage("" + ChatFormatting.LIGHT_PURPLE + ChatFormatting.BOLD + this.getName() + ChatFormatting.GRAY + " was " + ChatFormatting.RED + "disabled", true);
        }
        this.onDisable();
    }

    public void toggle() {
        if (this.isToggled()) {
            this.disable();
            return;
        }
        this.enable();
    }

    public boolean nullCheck() {
        if (this.mc.player == null) return true;
        if (this.mc.world == null) return true;
        return false;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.desc;
    }

    public void setDescription(String description) {
        this.desc = description;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getBind() {
        return this.bind;
    }

    public void setBind(int bind) {
        this.bind = bind;
    }

    public boolean isToggled() {
        return this.toggled;
    }

    public void setToggled(boolean enabled) {
        this.toggled = enabled;
    }

    public boolean isDrawn() {
        return this.drawn;
    }

    public void setDrawn(boolean drawn) {
        this.drawn = drawn;
    }

    public String getHudInfo() {
        return this.hudInfo;
    }

    public void setHudInfo(String info) {
        this.hudInfo = info;
    }

    protected Setting.i registerI(String name, int value, int min, int max) {
        Setting.i s = new Setting.i(name, name, this, this.getCategory(), value, min, max);
        Woo.settingManager.addSetting(s);
        return s;
    }

    protected Setting.d registerD(String name, double value, double min, double max) {
        Setting.d s = new Setting.d(name, name, this, this.getCategory(), value, min, max);
        Woo.settingManager.addSetting(s);
        return s;
    }

    protected Setting.b registerB(String name, boolean value) {
        Setting.b s = new Setting.b(name, name, this, this.getCategory(), value);
        Woo.settingManager.addSetting(s);
        return s;
    }

    protected Setting.mode registerMode(String name, List<String> modes, String value) {
        Setting.mode s = new Setting.mode(name, name, this, this.getCategory(), modes, value);
        Woo.settingManager.addSetting(s);
        return s;
    }

    protected Setting.c registerC(String name, Color value, int alpha) {
        Setting.c s = new Setting.c(name, name, this, this.getCategory(), value, alpha);
        Woo.settingManager.addSetting(s);
        return s;
    }

    public static enum Category {
        Combat,
        Player,
        Movement,
        Render,
        Misc,
        Client;

    }
}

