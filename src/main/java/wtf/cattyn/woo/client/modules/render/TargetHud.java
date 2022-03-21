//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\maywr\Documents\remapping\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.OpenGlHelper
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.entity.RenderManager
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package wtf.cattyn.woo.client.modules.render;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wtf.cattyn.woo.Woo;
import wtf.cattyn.woo.api.event.Render2DEvent;
import wtf.cattyn.woo.api.module.Module;
import wtf.cattyn.woo.api.setting.Setting;
import wtf.cattyn.woo.api.util.RenderUtil;
import wtf.cattyn.woo.client.modules.combat.AutoCrystal;
import wtf.cattyn.woo.client.modules.combat.KillAura;

public class TargetHud
extends Module {
    public static TargetHud INSTANCE;
    public Setting.i posX = this.registerI("Pos X", 0, 1, 600);
    public Setting.i posY = this.registerI("Pos Y", 0, 1, 600);

    public TargetHud() {
        super("TargetHud", Module.Category.Render);
        INSTANCE = this;
    }

    @Override
    @SubscribeEvent
    public void onRender2D(Render2DEvent event) {
        EntityPlayer target = null;
        if (this.nullCheck()) {
            return;
        }
        EntityPlayer entityPlayer = AutoCrystal.INSTANCE.currentTarget != null ? AutoCrystal.INSTANCE.currentTarget : (target = KillAura.target instanceof EntityPlayer ? (EntityPlayer)KillAura.target : TargetHud.getClosestEnemy());
        if (target == null) {
            return;
        }
        float health = target.getHealth() + target.getAbsorptionAmount();
        int healthColor = health >= 16.0f ? new Color(0, 255, 0, 255).getRGB() : (health >= 10.0f ? new Color(255, 255, 0, 255).getRGB() : new Color(255, 0, 0, 255).getRGB());
        DecimalFormat df = new DecimalFormat("##.#");
        RenderUtil.drawFakeRect(this.posX.getValue(), this.posY.getValue(), 150, 70, new Color(1, 1, 1, 150).getRGB());
        RenderUtil.drawGradientSideways(this.posX.getValue(), this.posY.getValue(), this.posX.getValue() + 150, this.posY.getValue() + 3, new Color(120, 243, 32, 255).getRGB(), new Color(216, 18, 220, 255).getRGB());
        Woo.fontManager.drawString(target.getName(), this.posX.getValue() + 40, this.posY.getValue() + 8, new Color(255, 255, 255, 255).getRGB());
        Woo.fontManager.drawString(df.format(target.getHealth() + target.getAbsorptionAmount()), this.posX.getValue() + 40, this.posY.getValue() + 16, new Color(255, 255, 255, 255).getRGB());
        this.drawPlayer(target, this.posX.getValue(), this.posY.getValue());
    }

    public static EntityPlayer getClosestEnemy() {
        EntityPlayer closestPlayer = null;
        Iterator iterator = Minecraft.getMinecraft().world.playerEntities.iterator();
        while (iterator.hasNext()) {
            EntityPlayer player = (EntityPlayer)iterator.next();
            if (player == Minecraft.getMinecraft().player || Woo.friendManager.isFriend((Entity)player)) continue;
            if (closestPlayer == null) {
                closestPlayer = player;
                continue;
            }
            if (!(Minecraft.getMinecraft().player.getDistanceSq((Entity)player) < Minecraft.getMinecraft().player.getDistanceSq((Entity)closestPlayer))) continue;
            closestPlayer = player;
        }
        return closestPlayer;
    }

    public void drawPlayer(EntityPlayer player, int x, int y) {
        EntityPlayer ent = player;
        GlStateManager.pushMatrix();
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.enableAlpha();
        GlStateManager.shadeModel((int)7424);
        GlStateManager.enableAlpha();
        GlStateManager.enableDepth();
        GlStateManager.rotate((float)0.0f, (float)0.0f, (float)5.0f, (float)0.0f);
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)(this.posX.getValue() + 20), (float)(this.posY.getValue() + 64), (float)50.0f);
        GlStateManager.scale((float)-30.0f, (float)30.0f, (float)30.0f);
        GlStateManager.rotate((float)180.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        GlStateManager.rotate((float)135.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate((float)-135.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.rotate((float)(-((float)Math.atan((float)this.posY.getValue() / 40.0f)) * 10.0f), (float)1.0f, (float)0.0f, (float)0.0f);
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

