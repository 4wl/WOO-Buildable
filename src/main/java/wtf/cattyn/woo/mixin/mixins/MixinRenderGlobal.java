//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\maywr\Documents\remapping\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.ChunkRenderContainer
 *  net.minecraft.client.renderer.RenderGlobal
 *  net.minecraft.client.renderer.entity.RenderManager
 *  net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher
 *  net.minecraft.util.math.AxisAlignedBB
 */
package wtf.cattyn.woo.mixin.mixins;

import net.minecraft.client.renderer.ChunkRenderContainer;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.AxisAlignedBB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import wtf.cattyn.woo.client.modules.movement.YPort;

@Mixin(value={RenderGlobal.class})
public abstract class MixinRenderGlobal {
    @Redirect(method={"setupTerrain"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/renderer/ChunkRenderContainer;initialize(DDD)V"))
    public void initializeHook(ChunkRenderContainer chunkRenderContainer, double viewEntityXIn, double viewEntityYIn, double viewEntityZIn) {
        double y = viewEntityYIn;
        if (YPort.INSTANCE.isToggled() && YPort.INSTANCE.noJumpEffect.getValue()) {
            y = YPort.INSTANCE.startY;
        }
        chunkRenderContainer.initialize(viewEntityXIn, y, viewEntityZIn);
    }

    @Redirect(method={"renderEntities"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/renderer/entity/RenderManager;setRenderPosition(DDD)V"))
    public void setRenderPositionHook(RenderManager renderManager, double renderPosXIn, double renderPosYIn, double renderPosZIn) {
        double y = renderPosYIn;
        if (YPort.INSTANCE.isToggled() && YPort.INSTANCE.noJumpEffect.getValue()) {
            y = YPort.INSTANCE.startY;
        }
        TileEntityRendererDispatcher.staticPlayerY = y;
        renderManager.setRenderPosition(renderPosXIn, y, renderPosZIn);
    }

    @Redirect(method={"drawSelectionBox"}, at=@At(value="INVOKE", target="Lnet/minecraft/util/math/AxisAlignedBB;offset(DDD)Lnet/minecraft/util/math/AxisAlignedBB;"))
    public AxisAlignedBB offsetHook(AxisAlignedBB axisAlignedBB, double x, double y, double z) {
        double yIn = y;
        if (!YPort.INSTANCE.isToggled()) return axisAlignedBB.offset(x, y, z);
        if (!YPort.INSTANCE.noJumpEffect.getValue()) return axisAlignedBB.offset(x, y, z);
        yIn = YPort.INSTANCE.startY;
        return axisAlignedBB.offset(x, y, z);
    }
}

