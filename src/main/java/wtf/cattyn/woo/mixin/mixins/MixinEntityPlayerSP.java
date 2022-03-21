//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\maywr\Documents\remapping\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  net.minecraft.client.entity.AbstractClientPlayer
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.entity.MoverType
 *  net.minecraft.world.World
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.Event
 */
package wtf.cattyn.woo.mixin.mixins;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.MoverType;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.cattyn.woo.api.event.MoveEvent;
import wtf.cattyn.woo.api.event.Stage;
import wtf.cattyn.woo.api.event.UpdateEvent;
import wtf.cattyn.woo.api.event.UpdateWalkingPlayerEvent;

@Mixin(value={EntityPlayerSP.class})
public class MixinEntityPlayerSP
extends AbstractClientPlayer {
    public MixinEntityPlayerSP(World worldIn, GameProfile playerProfile) {
        super(worldIn, playerProfile);
    }

    @Inject(method={"onUpdate"}, at={@At(value="HEAD")})
    public void onUpdateHead(CallbackInfo ci) {
        MinecraftForge.EVENT_BUS.post((Event)new UpdateEvent(Stage.PRE));
    }

    @Inject(method={"onUpdate"}, at={@At(value="RETURN")})
    public void onUpdateReturn(CallbackInfo ci) {
        MinecraftForge.EVENT_BUS.post((Event)new UpdateEvent(Stage.POST));
    }

    @Redirect(method={"move"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/entity/AbstractClientPlayer;move(Lnet/minecraft/entity/MoverType;DDD)V"))
    public void move(AbstractClientPlayer player, MoverType type, double x, double y, double z) {
        MoveEvent event = new MoveEvent(x, y, z);
        MinecraftForge.EVENT_BUS.post((Event)event);
        super.move(type, event.motionX, event.motionY, event.motionZ);
    }

    @Inject(method={"onUpdateWalkingPlayer"}, at={@At(value="HEAD")})
    private void preMotion(CallbackInfo info) {
        UpdateWalkingPlayerEvent event = new UpdateWalkingPlayerEvent(Stage.PRE);
        MinecraftForge.EVENT_BUS.post((Event)event);
    }

    @Inject(method={"onUpdateWalkingPlayer"}, at={@At(value="RETURN")})
    private void postMotion(CallbackInfo info) {
        UpdateWalkingPlayerEvent event = new UpdateWalkingPlayerEvent(Stage.POST);
        MinecraftForge.EVENT_BUS.post((Event)event);
    }
}

