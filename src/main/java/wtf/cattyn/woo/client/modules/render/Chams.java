//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\maywr\Documents\remapping\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.model.ModelBase
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  org.lwjgl.opengl.GL11
 */
package wtf.cattyn.woo.client.modules.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;
import wtf.cattyn.woo.api.module.Module;
import wtf.cattyn.woo.api.setting.Setting;

public class Chams
extends Module {
    public static Chams INSTANCE;
    public Setting.b chams = this.registerB("Chams", false);
    public Setting.b wireframe = this.registerB("Wiredrame", false);
    public Setting.d scale = this.registerD("Scale", 1.0, 0.0, 1.5);
    public Setting.d lineWidth = this.registerD("Line Width", 1.0, 0.1, 4.0);
    public Setting.i alpha = this.registerI("Alpha", 255, 0, 255);
    public Setting.i vred = this.registerI("Visible Red", 255, 0, 255);
    public Setting.i vgreen = this.registerI("Visible Green", 255, 0, 255);
    public Setting.i vblue = this.registerI("Visible Blue", 255, 0, 255);
    public Setting.i invred = this.registerI("Invisible Red", 255, 0, 255);
    public Setting.i invgreen = this.registerI("Invisible Green", 255, 0, 255);
    public Setting.i invblue = this.registerI("Invisible Blue", 255, 0, 255);

    public Chams() {
        super("Chams", Module.Category.Render);
        INSTANCE = this;
    }

    public final void onRenderModel(ModelBase base, Entity entity, float limbSwing, float limbSwingAmount, float age, float headYaw, float headPitch, float scale) {
        if (entity instanceof EntityPlayer) {
            return;
        }
        GL11.glPushAttrib(1048575);
        GL11.glPolygonMode(1032, 6913);
        GL11.glDisable(3553);
        GL11.glDisable(2896);
        GL11.glDisable(2929);
        GL11.glColorMaterial(1032, 5634);
        GL11.glEnable(2848);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth((float)((float)this.lineWidth.getValue()));
        GL11.glDepthMask((boolean)false);
        GL11.glColor4d((double)((float)Chams.INSTANCE.invred.getValue() / 255.0f), (double)((float)Chams.INSTANCE.invgreen.getValue() / 255.0f), (double)((float)Chams.INSTANCE.invblue.getValue() / 255.0f), (double)((float)Chams.INSTANCE.alpha.getValue() / 255.0f));
        base.render(entity, limbSwing, limbSwingAmount, age, headYaw, headPitch, scale);
        GL11.glEnable(2929);
        GL11.glDepthMask((boolean)true);
        GL11.glColor4d((double)((float)Chams.INSTANCE.vred.getValue() / 255.0f), (double)((float)Chams.INSTANCE.vgreen.getValue() / 255.0f), (double)((float)Chams.INSTANCE.vblue.getValue() / 255.0f), (double)((float)Chams.INSTANCE.alpha.getValue() / 255.0f));
        base.render(entity, limbSwing, limbSwingAmount, age, headYaw, headPitch, scale);
        GL11.glEnable(3042);
        GL11.glPopAttrib();
    }
}

