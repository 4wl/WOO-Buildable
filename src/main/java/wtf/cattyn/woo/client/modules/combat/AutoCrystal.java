//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\maywr\Documents\remapping\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityEnderCrystal
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Items
 *  net.minecraft.init.MobEffects
 *  net.minecraft.init.SoundEvents
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketHeldItemChange
 *  net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock
 *  net.minecraft.network.play.client.CPacketUseEntity
 *  net.minecraft.network.play.client.CPacketUseEntity$Action
 *  net.minecraft.network.play.server.SPacketSoundEffect
 *  net.minecraft.network.play.server.SPacketSpawnObject
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.SoundCategory
 *  net.minecraft.util.math.BlockPos
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package wtf.cattyn.woo.client.modules.combat;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.network.play.server.SPacketSpawnObject;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wtf.cattyn.woo.api.accessor.ICPacketUseEntity;
import wtf.cattyn.woo.api.event.PacketEvent;
import wtf.cattyn.woo.api.event.Render3DEvent;
import wtf.cattyn.woo.api.event.UpdateEvent;
import wtf.cattyn.woo.api.module.Module;
import wtf.cattyn.woo.api.setting.Setting;
import wtf.cattyn.woo.api.util.BlockUtil;
import wtf.cattyn.woo.api.util.EntityUtil;
import wtf.cattyn.woo.api.util.ItemUtil;
import wtf.cattyn.woo.api.util.RenderUtil;
import wtf.cattyn.woo.api.util.Timers;
import wtf.cattyn.woo.client.modules.misc.KillSound;

public class AutoCrystal
extends Module {
    public Setting.d breakRange = this.registerD("Break Range", 5.0, 1.0, 6.0);
    public Setting.i breakDelay = this.registerI("Delay", 10, 0, 500);
    public Setting.d breakwallRange = this.registerD("Wall Range", 5.0, 1.0, 6.0);
    public Setting.b instant = this.registerB("Predict", true);
    public Setting.b antiWeakness = this.registerB("Anti Weakness", false);
    public Setting.b antiWeaknessSilent = this.registerB("Anti Weakness Silent", false);
    public Setting.b switchBack = this.registerB("Switch Back", true);
    public Setting.d placeRange = this.registerD("Place Range", 5.0, 1.0, 6.0);
    public Setting.i facePlaceHp = this.registerI("Face Place HP", 8, 0, 36);
    public Setting.d minDamage = this.registerD("Min Damage", 5.0, 1.0, 36.0);
    public Setting.d maxSelfDamage = this.registerD("Max Self Damage", 8.0, 1.0, 36.0);
    public Setting.i range = this.registerI("Range", 9, 1, 15);
    public Setting.b secondCheck = this.registerB("Second Check", false);
    public Setting.d cooldown = this.registerD("Switch Delay", 0.0, 1.0, 500.0);
    public Setting.b autoSwitch = this.registerB("Auto Switch", false);
    public Setting.b sync = this.registerB("Rainbow", false);
    public Setting.i red = this.registerI("Red", 255, 0, 255);
    public Setting.i green = this.registerI("Green", 255, 0, 255);
    public Setting.i blue = this.registerI("Blue", 250, 0, 255);
    public Setting.i alpha = this.registerI("Alpha", 100, 0, 255);
    Setting.c color = this.registerC("RGBA Color", new Color(this.red.getValue(), this.green.getValue(), this.blue.getValue()), this.alpha.getValue());
    private final Set<BlockPos> placeSet = new HashSet<BlockPos>();
    private final Map<Integer, Integer> attackMap = new HashMap<Integer, Integer>();
    private final Timers clearTimer = new Timers();
    private final Timers breakTimer = new Timers();
    private BlockPos renderPos;
    public EntityPlayer currentTarget;
    private int predictedId = -1;
    private int ticks;
    private boolean offhand;
    public static final AutoCrystal INSTANCE = new AutoCrystal();

    public AutoCrystal() {
        super("AutoCrystal", Module.Category.Combat);
    }

    @Override
    public void onDisable() {
        this.currentTarget = null;
        this.attackMap.clear();
        this.placeSet.clear();
        this.predictedId = -1;
        this.renderPos = null;
    }

    @SubscribeEvent
    public void onUpdate(UpdateEvent event) {
        if (this.nullCheck()) {
            return;
        }
        this.setHudInfo("" + this.currentTarget);
        if (this.ticks < 40) {
            ++this.ticks;
        } else {
            this.ticks = 0;
            this.attackMap.clear();
        }
        if (this.clearTimer.passedMs(500L)) {
            this.currentTarget = null;
            this.placeSet.clear();
            this.predictedId = -1;
            this.renderPos = null;
            this.clearTimer.reset();
        }
        boolean bl = this.offhand = this.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL;
        if (!this.offhand && this.mc.player.getHeldItemMainhand().getItem() != Items.END_CRYSTAL && !this.autoSwitch.getValue()) {
            return;
        }
        this.doBreak();
        this.doPlace();
    }

    public void doBreak() {
        EntityPlayer target = EntityUtil.getTarget(this.range.getValue() * 10);
        if (target == null) {
            return;
        }
        float maxDamage = 0.0f;
        Entity maxCrystal = null;
        float minDmg = EntityUtil.getHealth(target) < (float)this.facePlaceHp.getValue() ? 2.0f : (float)this.minDamage.getValue();
        for (Entity crystal : this.mc.world.loadedEntityList) {
            float selfDamage;
            float targetDamage;
            if (!(crystal instanceof EntityEnderCrystal) || (double)this.mc.player.getDistance(crystal) > (this.mc.player.canEntityBeSeen(crystal) ? this.breakRange.getValue() : this.breakwallRange.getValue()) || crystal.isDead || this.attackMap.containsKey(crystal.getEntityId()) && this.attackMap.get(crystal.getEntityId()) > 5 || !((targetDamage = EntityUtil.calculate(crystal.posX, crystal.posY, crystal.posZ, (Entity)target)) > minDmg) || (double)(selfDamage = EntityUtil.calculate(crystal.posX, crystal.posY, crystal.posZ, (Entity)this.mc.player)) > this.maxSelfDamage.getValue() || selfDamage + 0.5f >= EntityUtil.getHealth((EntityPlayer)this.mc.player) || !(targetDamage > maxDamage)) continue;
            maxDamage = targetDamage;
            maxCrystal = crystal;
        }
        if (maxCrystal == null) return;
        if (!this.breakTimer.passedMs(this.breakDelay.getValue())) return;
        int lastSlot = -1;
        if (this.antiWeakness.getValue() && this.mc.player.isPotionActive(MobEffects.WEAKNESS)) {
            boolean swtch = !this.mc.player.isPotionActive(MobEffects.STRENGTH) || Objects.requireNonNull(this.mc.player.getActivePotionEffect(MobEffects.STRENGTH)).getAmplifier() != 2;
            int swordSlot = ItemUtil.getItemFromHotbar(Items.DIAMOND_SWORD);
            if (swtch && swordSlot != -1) {
                lastSlot = this.mc.player.inventory.currentItem;
                if (this.antiWeaknessSilent.getValue()) {
                    this.mc.getConnection().sendPacket((Packet)new CPacketHeldItemChange(swordSlot));
                } else {
                    this.mc.player.inventory.currentItem = swordSlot;
                }
            }
        }
        this.mc.getConnection().sendPacket((Packet)new CPacketUseEntity(maxCrystal));
        this.attackMap.put(maxCrystal.getEntityId(), this.attackMap.containsKey(maxCrystal.getEntityId()) ? this.attackMap.get(maxCrystal.getEntityId()) + 1 : 1);
        this.mc.player.swingArm(EnumHand.OFF_HAND);
        if (lastSlot != -1 && this.switchBack.getValue()) {
            if (this.antiWeaknessSilent.getValue()) {
                this.mc.getConnection().sendPacket((Packet)new CPacketHeldItemChange(lastSlot));
            } else {
                this.mc.player.inventory.currentItem = lastSlot;
            }
        }
        this.breakTimer.reset();
    }

    public void doPlace() {
        EntityPlayer target = EntityUtil.getTarget(this.range.getValue() * 10);
        if (target == null) {
            return;
        }
        float maxDamage = 0.0f;
        float minDmg = EntityUtil.getHealth(target) < (float)this.facePlaceHp.getValue() ? 2.0f : (float)this.minDamage.getValue();
        BlockPos placePos = null;
        for (BlockPos pos : BlockUtil.getSphere((float)this.placeRange.getValue())) {
            float selfDamage;
            float targetDamage;
            if (!BlockUtil.canPlaceCrystal(pos, this.secondCheck.getValue()) || (targetDamage = EntityUtil.calculate((double)pos.getX() + 0.5, pos.getY() + 1, (double)pos.getZ() + 0.5, (Entity)target)) < minDmg || (double)(selfDamage = EntityUtil.calculate((double)pos.getX() + 0.5, pos.getY() + 1, (double)pos.getZ() + 0.5, (Entity)this.mc.player)) > this.maxSelfDamage.getValue() || selfDamage + 0.5f >= EntityUtil.getHealth((EntityPlayer)this.mc.player) || !(targetDamage > maxDamage)) continue;
            maxDamage = targetDamage;
            placePos = pos;
            this.currentTarget = target;
        }
        if (placePos == null) {
            this.renderPos = null;
            return;
        }
        if (this.autoSwitch.getValue() && !this.offhand) {
            int crystalSlot = ItemUtil.getItemFromHotbar(Items.END_CRYSTAL);
            if (crystalSlot == -1) return;
            if (this.mc.player.getHeldItemMainhand().getItem() == Items.GOLDEN_APPLE && this.mc.player.isHandActive()) {
                return;
            }
            this.mc.player.inventory.currentItem = crystalSlot;
            this.mc.playerController.updateController();
        }
        this.mc.getConnection().sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(placePos, EnumFacing.UP, this.offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0.5f, 1.0f, 0.5f));
        this.placeSet.add(placePos);
        this.renderPos = placePos;
        KillSound.setCurrentTarget(this.currentTarget);
    }

    @Override
    public void onRender3D(Render3DEvent event) {
        if (this.renderPos == null) return;
        RenderUtil.drawBoxESP(this.renderPos, this.sync.getValue() ? new Color(this.red.getValue(), this.green.getValue(), this.blue.getValue(), this.alpha.getValue()) : new Color(this.red.getValue(), this.green.getValue(), this.blue.getValue(), this.alpha.getValue()), false, this.sync.getValue() ? new Color(this.red.getValue(), this.green.getValue(), this.blue.getValue(), this.alpha.getValue()) : new Color(this.red.getValue(), this.green.getValue(), this.blue.getValue(), this.alpha.getValue()), 1.0f, false, true, this.alpha.getValue(), false);
    }

    @SubscribeEvent
    public void onPacketReceive(PacketEvent.Receive event) {
        SPacketSpawnObject packet;
        if (event.getPacket() instanceof SPacketSpawnObject && this.instant.getValue() && (packet = (SPacketSpawnObject)event.getPacket()).getType() == 51 && this.placeSet.contains(new BlockPos(packet.getX(), packet.getY(), packet.getZ()).down())) {
            ICPacketUseEntity hitPacket = (ICPacketUseEntity)new CPacketUseEntity();
            hitPacket.setEntityId(packet.getEntityID());
            hitPacket.setAction(CPacketUseEntity.Action.ATTACK);
            this.mc.getConnection().sendPacket((Packet)hitPacket);
            this.predictedId = packet.getEntityID();
            this.attackMap.put(packet.getEntityID(), this.attackMap.containsKey(packet.getEntityID()) ? this.attackMap.get(packet.getEntityID()) + 1 : 1);
            this.mc.player.swingArm(EnumHand.OFF_HAND);
        }
        if (!(event.getPacket() instanceof SPacketSoundEffect)) return;
        packet = event.getPacket();

        Iterator iterator = new ArrayList(this.mc.world.loadedEntityList).iterator();
        while (iterator.hasNext()) {
            Entity entity = (Entity)iterator.next();
            if (!(entity instanceof EntityEnderCrystal) || !(entity.getDistanceSq(packet.getX(), packet.getY(), packet.getZ()) < 36.0)) continue;
            entity.setDead();
            this.mc.world.removeEntity(entity);
        }
    }
}

