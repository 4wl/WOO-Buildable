//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\maywr\Documents\remapping\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.model.ModelBase
 *  net.minecraft.client.renderer.OpenGlHelper
 *  net.minecraft.client.renderer.entity.Render
 *  net.minecraft.client.renderer.entity.RenderLivingBase
 *  net.minecraft.client.renderer.entity.RenderManager
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package wtf.cattyn.woo.mixin.mixins;

import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.cattyn.woo.client.modules.render.Chams;

@Mixin(value={RenderLivingBase.class})
public class MixinRenderLivingBase<T extends EntityLivingBase>
extends Render {
    private final Minecraft mc = Minecraft.getMinecraft();
    @Shadow
    protected ModelBase mainModel;

    @Inject(method={"renderModel"}, at={@At(value="HEAD")}, cancellable=true)
    public void renderModel(T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, CallbackInfo ci) {
        if (!(entitylivingbaseIn instanceof EntityPlayer)) return;
        if (entitylivingbaseIn == this.mc.player) return;
        if (!Chams.INSTANCE.isToggled()) return;
        if (!Chams.INSTANCE.chams.getValue()) return;
        GL11.glPushAttrib((int)1048575);
        GL11.glDisable((int)3008);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2896);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDepthMask((boolean)false);
        GL11.glLineWidth((float)1.5f);
        GL11.glEnable((int)2960);
        GL11.glClear((int)1024);
        GL11.glClearStencil((int)15);
        GL11.glStencilFunc((int)512, (int)1, (int)15);
        GL11.glStencilOp((int)7681, (int)7681, (int)7681);
        GL11.glPolygonMode((int)1028, (int)6913);
        GL11.glStencilFunc((int)512, (int)0, (int)15);
        GL11.glStencilOp((int)7681, (int)7681, (int)7681);
        GL11.glPolygonMode((int)1028, (int)6914);
        GL11.glStencilFunc((int)514, (int)1, (int)15);
        GL11.glStencilOp((int)7680, (int)7680, (int)7680);
        GL11.glPolygonMode((int)1028, (int)6913);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        GL11.glEnable((int)10754);
        OpenGlHelper.setLightmapTextureCoords((int)OpenGlHelper.lightmapTexUnit, (float)240.0f, (float)240.0f);
        GL11.glColor4f((float)1.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        GL11.glColor4d((double)((float)Chams.INSTANCE.invred.getValue() / 255.0f), (double)((float)Chams.INSTANCE.invgreen.getValue() / 255.0f), (double)((float)Chams.INSTANCE.invblue.getValue() / 255.0f), (double)((float)Chams.INSTANCE.alpha.getValue() / 255.0f));
        this.mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glColor4d((double)((float)Chams.INSTANCE.vred.getValue() / 255.0f), (double)((float)Chams.INSTANCE.vgreen.getValue() / 255.0f), (double)((float)Chams.INSTANCE.vblue.getValue() / 255.0f), (double)((float)Chams.INSTANCE.alpha.getValue() / 255.0f));
        this.mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
        GL11.glEnable((int)3042);
        GL11.glEnable((int)2896);
        GL11.glEnable((int)3553);
        GL11.glEnable((int)3008);
        GL11.glPopAttrib();
        ci.cancel();
    }

    protected MixinRenderLivingBase(RenderManager renderManager) {
        super(renderManager);
    }

    @Nullable
    protected ResourceLocation getEntityTexture(Entity entity) {
        return null;
    }
}

