//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\maywr\Documents\remapping\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.model.ModelBase
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.entity.RenderEnderCrystal
 *  net.minecraft.entity.Entity
 *  org.lwjgl.opengl.GL11
 */
package wtf.cattyn.woo.mixin.mixins;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderEnderCrystal;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import wtf.cattyn.woo.client.modules.render.Chams;

@Mixin(value={RenderEnderCrystal.class})
public class MixinRenderEnderCrystal {
    @Redirect(method={"doRender"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V"))
    public void renderModelBaseHook(ModelBase model, Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (!Chams.INSTANCE.isToggled()) {
            model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            return;
        }
        GlStateManager.scale((double)Chams.INSTANCE.scale.getValue(), (double)Chams.INSTANCE.scale.getValue(), (double)Chams.INSTANCE.scale.getValue());
        if (Chams.INSTANCE.wireframe.getValue() || Chams.INSTANCE.chams.getValue()) {
            if (Chams.INSTANCE.wireframe.getValue()) {
                Chams.INSTANCE.onRenderModel(model, entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            }
            if (Chams.INSTANCE.chams.getValue()) {
                GL11.glPushAttrib((int)1048575);
                GL11.glDisable((int)3008);
                GL11.glDisable((int)3553);
                GL11.glDisable((int)2896);
                GL11.glEnable((int)3042);
                GL11.glBlendFunc((int)770, (int)771);
                GL11.glLineWidth((float)1.5f);
                GL11.glEnable((int)2960);
                GL11.glEnable((int)10754);
                GL11.glDepthMask((boolean)false);
                GL11.glColor4d((double)((float)Chams.INSTANCE.invred.getValue() / 255.0f), (double)((float)Chams.INSTANCE.invgreen.getValue() / 255.0f), (double)((float)Chams.INSTANCE.invblue.getValue() / 255.0f), (double)((float)Chams.INSTANCE.alpha.getValue() / 255.0f));
                model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
                GL11.glEnable((int)2929);
                GL11.glDepthMask((boolean)true);
                GL11.glColor4d((double)((float)Chams.INSTANCE.vred.getValue() / 255.0f), (double)((float)Chams.INSTANCE.vgreen.getValue() / 255.0f), (double)((float)Chams.INSTANCE.vblue.getValue() / 255.0f), (double)((float)Chams.INSTANCE.alpha.getValue() / 255.0f));
                model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
                GL11.glEnable((int)3042);
                GL11.glEnable((int)2896);
                GL11.glEnable((int)3553);
                GL11.glEnable((int)3008);
                GL11.glPopAttrib();
            }
        } else {
            model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        }
        GlStateManager.scale((double)(1.0 / Chams.INSTANCE.scale.getValue()), (double)(1.0 / Chams.INSTANCE.scale.getValue()), (double)(1.0 / Chams.INSTANCE.scale.getValue()));
    }
}

