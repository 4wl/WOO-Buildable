//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\maywr\Documents\remapping\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.monster.EntityMob
 *  net.minecraft.entity.passive.EntityAnimal
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.projectile.EntityShulkerBullet
 *  net.minecraft.item.ItemSword
 *  net.minecraft.util.EnumHand
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package wtf.cattyn.woo.client.modules.combat;

import java.util.Iterator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityShulkerBullet;
import net.minecraft.item.ItemSword;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wtf.cattyn.woo.api.event.UpdateEvent;
import wtf.cattyn.woo.api.module.Module;
import wtf.cattyn.woo.api.setting.Setting;
import wtf.cattyn.woo.api.util.EntityUtil;
import wtf.cattyn.woo.client.modules.misc.KillSound;

public class KillAura
extends Module {
    public Setting.i range = this.registerI("Range", 5, 0, 6);
    public Setting.b players = this.registerB("Players", true);
    public Setting.b animals = this.registerB("Animals", false);
    public Setting.b mobs = this.registerB("Mobs", false);
    public Setting.b projectiles = this.registerB("Projectiles", false);
    public static Entity target;

    public KillAura() {
        super("KillAura", Module.Category.Combat);
    }

    @SubscribeEvent
    public void onUpdate(UpdateEvent event) {
        if (this.nullCheck()) {
            return;
        }
        if (!(this.mc.player.getHeldItemMainhand().getItem() instanceof ItemSword)) {
            return;
        }
        Entity entity = this.getTarget();
        if (entity == null) {
            return;
        }
        KillSound.setCurrentTarget((EntityPlayer)entity);
        if (!(this.mc.player.getCooledAttackStrength(0.0f) >= 1.0f)) return;
        this.mc.playerController.attackEntity((EntityPlayer)this.mc.player, entity);
        this.mc.player.swingArm(EnumHand.MAIN_HAND);
    }

    public Entity getTarget() {
        target = null;
        double maxDistance = -1.0;
        Iterator iterator = this.mc.world.loadedEntityList.iterator();
        while (iterator.hasNext()) {
            Entity ent = (Entity)iterator.next();
            if (!EntityUtil.isValidEntity(ent, this.range.getValue()) || !(ent instanceof EntityPlayer && this.players.getValue() || ent instanceof EntityAnimal && this.animals.getValue() || ent instanceof EntityMob && this.mobs.getValue()) && (!(ent instanceof EntityShulkerBullet) || !this.projectiles.getValue())) continue;
            double distance = this.mc.player.getDistanceSq(ent);
            if (maxDistance != -1.0 && !(distance < maxDistance)) continue;
            maxDistance = distance;
            target = ent;
        }
        return target;
    }
}

