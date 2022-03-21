//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\maywr\Documents\remapping\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  net.minecraft.client.entity.EntityOtherPlayerMP
 *  net.minecraft.enchantment.EnchantmentHelper
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.MoverType
 *  net.minecraft.world.World
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 */
package wtf.cattyn.woo.client.modules.player;

import com.mojang.authlib.GameProfile;
import java.util.UUID;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import wtf.cattyn.woo.api.module.Module;
import wtf.cattyn.woo.api.setting.Setting;

public class FakePlayer
extends Module {
    public Setting.b moving = this.registerB("Moving", false);
    private EntityOtherPlayerMP otherPlayer;

    public FakePlayer() {
        super("FakePlayer", Module.Category.Player);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (this.otherPlayer == null) return;
        if (!this.moving.getValue()) return;
        this.otherPlayer.moveForward = this.mc.player.moveForward + (float)this.random.nextInt(5) / 10.0f;
        this.otherPlayer.moveStrafing = this.mc.player.moveStrafing + (float)this.random.nextInt(5) / 10.0f;
        this.travel(this.otherPlayer.moveStrafing, this.otherPlayer.moveVertical, this.otherPlayer.moveForward);
    }

    public void travel(float strafe, float vertical, float forward) {
        double d0 = this.otherPlayer.posY;
        float f1 = 0.8f;
        float f2 = 0.02f;
        float f3 = EnchantmentHelper.getDepthStriderModifier((EntityLivingBase)this.otherPlayer);
        if (f3 > 3.0f) {
            f3 = 3.0f;
        }
        if (!this.otherPlayer.onGround) {
            f3 *= 0.5f;
        }
        if (f3 > 0.0f) {
            f1 += (0.54600006f - f1) * f3 / 3.0f;
            f2 += (this.otherPlayer.getAIMoveSpeed() - f2) * f3 / 3.0f;
        }
        this.otherPlayer.moveRelative(strafe, vertical, forward, f2);
        this.otherPlayer.move(MoverType.SELF, this.otherPlayer.motionX, this.otherPlayer.motionY, this.otherPlayer.motionZ);
        this.otherPlayer.motionX *= (double)f1;
        this.otherPlayer.motionY *= (double)0.8f;
        this.otherPlayer.motionZ *= (double)f1;
        if (!this.otherPlayer.hasNoGravity()) {
            this.otherPlayer.motionY -= 0.02;
        }
        if (!this.otherPlayer.collidedHorizontally) return;
        if (!this.otherPlayer.isOffsetPositionInLiquid(this.otherPlayer.motionX, this.otherPlayer.motionY + (double)0.6f - this.otherPlayer.posY + d0, this.otherPlayer.motionZ)) return;
        this.otherPlayer.motionY = 0.3f;
    }

    @Override
    public void onEnable() {
        if (this.mc.world == null) return;
        if (this.mc.player == null) {
            return;
        }
        if (this.otherPlayer == null) {
            this.otherPlayer = new EntityOtherPlayerMP((World)this.mc.world, new GameProfile(UUID.randomUUID(), "IsCattyn"));
            this.otherPlayer.copyLocationAndAnglesFrom((Entity)this.mc.player);
            this.otherPlayer.inventory.copyInventory(this.mc.player.inventory);
        }
        this.mc.world.spawnEntity((Entity)this.otherPlayer);
    }

    @Override
    public void onDisable() {
        if (this.otherPlayer == null) return;
        this.mc.world.removeEntity((Entity)this.otherPlayer);
        this.otherPlayer = null;
    }
}

