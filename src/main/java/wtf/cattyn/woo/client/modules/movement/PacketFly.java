//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\maywr\Documents\remapping\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.util.internal.ConcurrentSet
 *  net.minecraft.client.gui.GuiDownloadTerrain
 *  net.minecraft.entity.Entity
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketConfirmTeleport
 *  net.minecraft.network.play.client.CPacketPlayer
 *  net.minecraft.network.play.client.CPacketPlayer$Position
 *  net.minecraft.network.play.server.SPacketPlayerPosLook
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package wtf.cattyn.woo.client.modules.movement;

import io.netty.util.internal.ConcurrentSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wtf.cattyn.woo.api.event.PacketEvent;
import wtf.cattyn.woo.api.event.PushEvent;
import wtf.cattyn.woo.api.event.Stage;
import wtf.cattyn.woo.api.event.UpdateWalkingPlayerEvent;
import wtf.cattyn.woo.api.module.Module;
import wtf.cattyn.woo.api.util.EntityUtil;
import wtf.cattyn.woo.api.util.Timers;

public class PacketFly
extends Module {
    private final Set<CPacketPlayer> packets = new ConcurrentSet();
    private final Map<Integer, IDtime> teleportmap = new ConcurrentHashMap<Integer, IDtime>();
    private int flightCounter = 0;
    private int teleportID = 0;

    public PacketFly() {
        super("PacketFly", Module.Category.Movement);
    }

    @SubscribeEvent
    public void onUpdateWalkingPlayer(UpdateWalkingPlayerEvent event) {
        double speed = 0;
        if (event.getStage() == Stage.POST) {
            return;
        }
        this.mc.player.setVelocity(0.0, 0.0, 0.0);
        boolean checkCollisionBoxes = this.checkHitBoxes();
        double d2 = this.mc.player.movementInput.jump && (checkCollisionBoxes || !EntityUtil.isMoving()) ? (checkCollisionBoxes ? 0.062 : (this.resetCounter(10) ? -0.032 : 0.062)) : (this.mc.player.movementInput.sneak ? -0.062 : (checkCollisionBoxes ? 0.0 : (speed = this.resetCounter(4) ? -0.04 : 0.0)));
        if (checkCollisionBoxes && EntityUtil.isMoving() && speed != 0.0) {
            double antiFactor = 2.5;
            speed /= 2.5;
        }
        boolean strafeFactor = true;
        double[] strafing = this.getMotion(checkCollisionBoxes ? 0.031 : 0.26);
        int loops = 1;
        int i2 = 1;
        while (i2 < loops + 1) {
            double extraFactor = 1.0;
            this.mc.player.motionX = strafing[0] * (double)i2 * 1.0;
            this.mc.player.motionY = speed * (double)i2;
            this.mc.player.motionZ = strafing[1] * (double)i2 * 1.0;
            boolean sendTeleport = true;
            this.sendPackets(this.mc.player.motionX, this.mc.player.motionY, this.mc.player.motionZ, true);
            ++i2;
        }
    }

    @SubscribeEvent
    public void onPacketSend(PacketEvent.Send event) {
        if (!(event.getPacket() instanceof CPacketPlayer)) return;
        if (this.packets.remove(event.getPacket())) return;
        event.setCanceled(true);
    }

    @SubscribeEvent
    public void onPushOutOfBlocks(PushEvent event) {
        if (event.getStage() != Stage.POST) return;
        event.setCanceled(true);
    }

    @SubscribeEvent
    public void onPacketReceive(PacketEvent.Receive event) {
        if (!(event.getPacket() instanceof SPacketPlayerPosLook)) return;
        if (this.nullCheck()) return;
        SPacketPlayerPosLook packet = (SPacketPlayerPosLook)event.getPacket();
        if (this.mc.player.isEntityAlive() && this.mc.world.isBlockLoaded(new BlockPos(this.mc.player.posX, this.mc.player.posY, this.mc.player.posZ), false) && !(this.mc.currentScreen instanceof GuiDownloadTerrain)) {
            this.teleportmap.remove(packet.getTeleportId());
        }
        this.teleportID = packet.getTeleportId();
    }

    private boolean checkHitBoxes() {
        if (this.mc.world.getCollisionBoxes((Entity)this.mc.player, this.mc.player.getEntityBoundingBox().expand(-0.0, -0.1, -0.0)).isEmpty()) return false;
        return true;
    }

    private boolean resetCounter(int counter) {
        if (++this.flightCounter < counter) return false;
        this.flightCounter = 0;
        return true;
    }

    private double[] getMotion(double speed) {
        float moveForward = this.mc.player.movementInput.moveForward;
        float moveStrafe = this.mc.player.movementInput.moveStrafe;
        float rotationYaw = this.mc.player.prevRotationYaw + (this.mc.player.rotationYaw - this.mc.player.prevRotationYaw) * this.mc.getRenderPartialTicks();
        if (moveForward != 0.0f) {
            if (moveStrafe > 0.0f) {
                rotationYaw += (float)(moveForward > 0.0f ? -45 : 45);
            } else if (moveStrafe < 0.0f) {
                rotationYaw += (float)(moveForward > 0.0f ? 45 : -45);
            }
            moveStrafe = 0.0f;
            if (moveForward > 0.0f) {
                moveForward = 1.0f;
            } else if (moveForward < 0.0f) {
                moveForward = -1.0f;
            }
        }
        double posX = (double)moveForward * speed * -Math.sin(Math.toRadians(rotationYaw)) + (double)moveStrafe * speed * Math.cos(Math.toRadians(rotationYaw));
        double posZ = (double)moveForward * speed * Math.cos(Math.toRadians(rotationYaw)) - (double)moveStrafe * speed * -Math.sin(Math.toRadians(rotationYaw));
        return new double[]{posX, posZ};
    }

    private void sendPackets(double x, double y, double z, boolean teleport) {
        Vec3d vec = new Vec3d(x, y, z);
        Vec3d position = this.mc.player.getPositionVector().add(vec);
        Vec3d outOfBoundsVec = this.outOfBoundsVec(position);
        this.packetSender((CPacketPlayer)new CPacketPlayer.Position(position.x, position.y, position.z, this.mc.player.onGround));
        this.packetSender((CPacketPlayer)new CPacketPlayer.Position(outOfBoundsVec.x, outOfBoundsVec.y, outOfBoundsVec.z, this.mc.player.onGround));
        this.teleportPacket(position, teleport);
    }

    private void teleportPacket(Vec3d pos, boolean shouldTeleport) {
        if (!shouldTeleport) return;
        this.mc.player.connection.sendPacket((Packet)new CPacketConfirmTeleport(++this.teleportID));
        this.teleportmap.put(this.teleportID, new IDtime(pos, new Timers()));
    }

    private Vec3d outOfBoundsVec(Vec3d position) {
        return position.add(0.0, 1337.0, 0.0);
    }

    private void packetSender(CPacketPlayer packet) {
        this.packets.add(packet);
        this.mc.player.connection.sendPacket((Packet)packet);
    }

    public static class IDtime {
        private final Vec3d pos;
        private final Timers timer;

        public IDtime(Vec3d pos, Timers timer) {
            this.pos = pos;
            this.timer = timer;
            this.timer.reset();
        }

        public Vec3d getPos() {
            return this.pos;
        }

        public Timers getTimer() {
            return this.timer;
        }
    }
}

