//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\maywr\Documents\remapping\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockAir
 *  net.minecraft.block.BlockDeadBush
 *  net.minecraft.block.BlockFire
 *  net.minecraft.block.BlockLiquid
 *  net.minecraft.block.BlockSnow
 *  net.minecraft.block.BlockTallGrass
 *  net.minecraft.block.material.Material
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.Minecraft
 *  net.minecraft.enchantment.EnchantmentHelper
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.SharedMonsterAttributes
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.MobEffects
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketEntityAction
 *  net.minecraft.network.play.client.CPacketEntityAction$Action
 *  net.minecraft.potion.Potion
 *  net.minecraft.util.CombatRules
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.MathHelper
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.util.math.RayTraceResult$Type
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.world.Explosion
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 */
package wtf.cattyn.woo.api.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockDeadBush;
import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.potion.Potion;
import net.minecraft.util.CombatRules;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import wtf.cattyn.woo.Woo;
import wtf.cattyn.woo.api.util.BlockUtil;
import wtf.cattyn.woo.api.util.MathUtil;
import wtf.cattyn.woo.api.util.Wrapper;

public class EntityUtil
extends Wrapper {
    public static final Vec3d[] antiDropOffsetList = new Vec3d[]{new Vec3d(0.0, -2.0, 0.0)};
    public static final Vec3d[] platformOffsetList = new Vec3d[]{new Vec3d(0.0, -1.0, 0.0), new Vec3d(0.0, -1.0, -1.0), new Vec3d(0.0, -1.0, 1.0), new Vec3d(-1.0, -1.0, 0.0), new Vec3d(1.0, -1.0, 0.0)};
    public static final Vec3d[] legOffsetList = new Vec3d[]{new Vec3d(-1.0, 0.0, 0.0), new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, -1.0), new Vec3d(0.0, 0.0, 1.0)};
    public static final Vec3d[] OffsetList = new Vec3d[]{new Vec3d(1.0, 1.0, 0.0), new Vec3d(-1.0, 1.0, 0.0), new Vec3d(0.0, 1.0, 1.0), new Vec3d(0.0, 1.0, -1.0), new Vec3d(0.0, 2.0, 0.0)};
    public static final Vec3d[] antiStepOffsetList = new Vec3d[]{new Vec3d(-1.0, 2.0, 0.0), new Vec3d(1.0, 2.0, 0.0), new Vec3d(0.0, 2.0, 1.0), new Vec3d(0.0, 2.0, -1.0)};
    public static final Vec3d[] antiScaffoldOffsetList = new Vec3d[]{new Vec3d(0.0, 3.0, 0.0)};

    public static EntityPlayer getClosestPlayer(float range) {
        EntityPlayer player = null;
        range *= range;
        double maxDistance = -1.0;
        List playerEntities = EntityUtil.mc.world.playerEntities;
        int i2 = 0;
        while (i2 < playerEntities.size()) {
            double distance;
            EntityPlayer entity = (EntityPlayer)playerEntities.get(i2);
            if (entity != EntityUtil.mc.player && entity.getHealth() > 0.0f && (distance = EntityUtil.mc.player.getDistanceSq((Entity)entity)) < (double)range && entity.getEntityId() != -912 && !Woo.friendManager.isFriend((Entity)entity) && (maxDistance == -1.0 || distance < maxDistance)) {
                maxDistance = distance;
                player = entity;
            }
            ++i2;
        }
        return player;
    }

    public static boolean isLiving(Entity entity) {
        return entity instanceof EntityLivingBase;
    }

    public static boolean isntValid(EntityPlayer entity, double range) {
        if (EntityUtil.mc.player.getDistanceSq((Entity)entity) > range) return true;
        if (entity.getHealth() <= 0.0f) return true;
        if (entity.isDead) return true;
        if (entity == EntityUtil.mc.player) return true;
        if (Woo.friendManager.isFriend((Entity)entity)) return true;
        return false;
    }

    public static boolean isMoving() {
        if ((double)EntityUtil.mc.player.moveForward != 0.0) return true;
        if ((double)EntityUtil.mc.player.moveStrafing != 0.0) return true;
        return false;
    }

    public static double getBaseMoveSpeed() {
        double baseSpeed = 0.2873;
        if (Minecraft.getMinecraft().player == null) return baseSpeed;
        if (!Minecraft.getMinecraft().player.isPotionActive(Potion.getPotionById((int)1))) return baseSpeed;
        int amplifier = Minecraft.getMinecraft().player.getActivePotionEffect(Potion.getPotionById((int)1)).getAmplifier();
        baseSpeed *= 1.0 + 0.2 * (double)(amplifier + 1);
        return baseSpeed;
    }

    public static void resetTimer() {
        Minecraft.getMinecraft().timer.tickLength = 50.0f;
    }

    public static void setSpeed(EntityLivingBase entity, double speed) {
        double[] dir = EntityUtil.forward(speed);
        entity.motionX = dir[0];
        entity.motionZ = dir[1];
    }

    public static double[] forward(double speed) {
        float forward = Minecraft.getMinecraft().player.movementInput.moveForward;
        float side = Minecraft.getMinecraft().player.movementInput.moveStrafe;
        float yaw = Minecraft.getMinecraft().player.prevRotationYaw + (Minecraft.getMinecraft().player.rotationYaw - Minecraft.getMinecraft().player.prevRotationYaw) * Minecraft.getMinecraft().getRenderPartialTicks();
        if (forward != 0.0f) {
            if (side > 0.0f) {
                yaw += (float)(forward > 0.0f ? -45 : 45);
            } else if (side < 0.0f) {
                yaw += (float)(forward > 0.0f ? 45 : -45);
            }
            side = 0.0f;
            if (forward > 0.0f) {
                forward = 1.0f;
            } else if (forward < 0.0f) {
                forward = -1.0f;
            }
        }
        double sin = Math.sin(Math.toRadians(yaw + 90.0f));
        double cos = Math.cos(Math.toRadians(yaw + 90.0f));
        double posX = (double)forward * speed * cos + (double)side * speed * sin;
        double posZ = (double)forward * speed * sin - (double)side * speed * cos;
        return new double[]{posX, posZ};
    }

    public static boolean isMoving(EntityLivingBase entity) {
        if (entity.moveForward != 0.0f) return true;
        if (entity.moveStrafing != 0.0f) return true;
        return false;
    }

    public static BlockPos getRoundedBlockPos(Entity entity) {
        return new BlockPos(MathUtil.roundVec(entity.getPositionVector(), 0));
    }

    public static Vec3d[] getOffsets(int y, boolean floor) {
        List<Vec3d> offsets = EntityUtil.getOffsetList(y, floor);
        Vec3d[] array = new Vec3d[offsets.size()];
        return offsets.toArray(array);
    }

    public static void setTimer(float speed) {
        Minecraft.getMinecraft().timer.tickLength = 50.0f / speed;
    }

    public static List<Vec3d> getOffsetList(int y, boolean floor) {
        ArrayList<Vec3d> offsets = new ArrayList<Vec3d>();
        offsets.add(new Vec3d(-1.0, (double)y, 0.0));
        offsets.add(new Vec3d(1.0, (double)y, 0.0));
        offsets.add(new Vec3d(0.0, (double)y, -1.0));
        offsets.add(new Vec3d(0.0, (double)y, 1.0));
        if (!floor) return offsets;
        offsets.add(new Vec3d(0.0, (double)(y - 1), 0.0));
        return offsets;
    }

    public static boolean isTrapped(EntityPlayer player, boolean antiScaffold, boolean antiStep, boolean legs, boolean platform, boolean antiDrop) {
        if (EntityUtil.getUntrappedBlocks(player, antiScaffold, antiStep, legs, platform, antiDrop).size() != 0) return false;
        return true;
    }

    public static List<Vec3d> getUnsafeBlocks(Entity entity, int height, boolean floor) {
        return EntityUtil.getUnsafeBlocksFromVec3d(entity.getPositionVector(), height, floor);
    }

    public static Vec3d[] getTrapOffsets(boolean antiScaffold, boolean antiStep, boolean legs, boolean platform, boolean antiDrop) {
        List<Vec3d> offsets = EntityUtil.getTrapOffsetsList(antiScaffold, antiStep, legs, platform, antiDrop);
        Vec3d[] array = new Vec3d[offsets.size()];
        return offsets.toArray(array);
    }

    public static List<Vec3d> getTrapOffsetsList(boolean antiScaffold, boolean antiStep, boolean legs, boolean platform, boolean antiDrop) {
        ArrayList<Vec3d> offsets = new ArrayList<Vec3d>(EntityUtil.getOffsetList(1, false));
        offsets.add(new Vec3d(0.0, 2.0, 0.0));
        if (antiScaffold) {
            offsets.add(new Vec3d(0.0, 3.0, 0.0));
        }
        if (antiStep) {
            offsets.addAll(EntityUtil.getOffsetList(2, false));
        }
        if (legs) {
            offsets.addAll(EntityUtil.getOffsetList(0, false));
        }
        if (platform) {
            offsets.addAll(EntityUtil.getOffsetList(-1, false));
            offsets.add(new Vec3d(0.0, -1.0, 0.0));
        }
        if (!antiDrop) return offsets;
        offsets.add(new Vec3d(0.0, -2.0, 0.0));
        return offsets;
    }

    public static List<Vec3d> getUntrappedBlocks(EntityPlayer player, boolean antiScaffold, boolean antiStep, boolean legs, boolean platform, boolean antiDrop) {
        ArrayList<Vec3d> vec3ds = new ArrayList<Vec3d>();
        if (!antiStep && EntityUtil.getUnsafeBlocks((Entity)player, 2, false).size() == 4) {
            vec3ds.addAll(EntityUtil.getUnsafeBlocks((Entity)player, 2, false));
        }
        int i2 = 0;
        while (i2 < EntityUtil.getTrapOffsets(antiScaffold, antiStep, legs, platform, antiDrop).length) {
            Vec3d vector = EntityUtil.getTrapOffsets(antiScaffold, antiStep, legs, platform, antiDrop)[i2];
            BlockPos targetPos = new BlockPos(player.getPositionVector()).add(vector.x, vector.y, vector.z);
            Block block = EntityUtil.mc.world.getBlockState(targetPos).getBlock();
            if (block instanceof BlockAir || block instanceof BlockLiquid || block instanceof BlockTallGrass || block instanceof BlockFire || block instanceof BlockDeadBush || block instanceof BlockSnow) {
                vec3ds.add(vector);
            }
            ++i2;
        }
        return vec3ds;
    }

    public static List<Vec3d> getUnsafeBlocksFromVec3d(Vec3d pos, int height, boolean floor) {
        ArrayList<Vec3d> vec3ds = new ArrayList<Vec3d>();
        Vec3d[] vec3dArray = EntityUtil.getOffsets(height, floor);
        int n = vec3dArray.length;
        int n2 = 0;
        while (n2 < n) {
            Vec3d vector = vec3dArray[n2];
            BlockPos targetPos = new BlockPos(pos).add(vector.x, vector.y, vector.z);
            Block block = EntityUtil.mc.world.getBlockState(targetPos).getBlock();
            if (block instanceof BlockAir || block instanceof BlockLiquid || block instanceof BlockTallGrass || block instanceof BlockFire || block instanceof BlockDeadBush || block instanceof BlockSnow) {
                vec3ds.add(vector);
            }
            ++n2;
        }
        return vec3ds;
    }

    public static List<Vec3d> targets(Vec3d vec3d, boolean antiScaffold, boolean antiStep, boolean legs, boolean platform, boolean antiDrop, boolean raytrace) {
        ArrayList<Vec3d> placeTargets = new ArrayList<Vec3d>();
        if (antiDrop) {
            Collections.addAll(placeTargets, BlockUtil.convertVec3ds(vec3d, antiDropOffsetList));
        }
        if (platform) {
            Collections.addAll(placeTargets, BlockUtil.convertVec3ds(vec3d, platformOffsetList));
        }
        if (legs) {
            Collections.addAll(placeTargets, BlockUtil.convertVec3ds(vec3d, legOffsetList));
        }
        Collections.addAll(placeTargets, BlockUtil.convertVec3ds(vec3d, OffsetList));
        if (antiStep) {
            Collections.addAll(placeTargets, BlockUtil.convertVec3ds(vec3d, antiStepOffsetList));
        } else {
            List<Vec3d> vec3ds = EntityUtil.getUnsafeBlocksFromVec3d(vec3d, 2, false);
            if (vec3ds.size() == 4) {
                block5: for (Vec3d vector : vec3ds) {
                    BlockPos position = new BlockPos(vec3d).add(vector.x, vector.y, vector.z);
                    switch (BlockUtil.isPositionPlaceable(position, raytrace)) {
                        case 0: {
                            break;
                        }
                        case -1: 
                        case 1: 
                        case 2: {
                            continue block5;
                        }
                        case 3: {
                            placeTargets.add(vec3d.add(vector));
                            break;
                        }
                    }
                    if (!antiScaffold) return placeTargets;
                    Collections.addAll(placeTargets, BlockUtil.convertVec3ds(vec3d, antiScaffoldOffsetList));
                    return placeTargets;
                }
            }
        }
        if (!antiScaffold) return placeTargets;
        Collections.addAll(placeTargets, BlockUtil.convertVec3ds(vec3d, antiScaffoldOffsetList));
        return placeTargets;
    }

    public static EntityPlayer getTarget(float range) {
        EntityPlayer currentTarget = null;
        Iterator iterator = EntityUtil.mc.world.playerEntities.iterator();
        while (iterator.hasNext()) {
            EntityPlayer player = (EntityPlayer)iterator.next();
            if (EntityUtil.isntValid(player, range)) continue;
            if (currentTarget == null) {
                currentTarget = player;
                continue;
            }
            if (!(EntityUtil.mc.player.getDistanceSq((Entity)player) < EntityUtil.mc.player.getDistanceSq((Entity)currentTarget))) continue;
            currentTarget = player;
        }
        return currentTarget;
    }

    public static boolean isValidEntity(Entity entity, float range) {
        if (entity == EntityUtil.mc.player) return false;
        if (EntityUtil.isDead(entity)) return false;
        if (!(EntityUtil.mc.player.getDistance(entity) < range)) return false;
        if (entity.getEntityId() == -912) return false;
        if (Woo.friendManager.isFriend(entity)) return false;
        return true;
    }

    public static boolean isDead(Entity entity) {
        if (entity.isDead) return true;
        if (!(entity instanceof EntityLivingBase)) return false;
        if (!(((EntityLivingBase)entity).getHealth() <= 0.0f)) return false;
        return true;
    }

    public static boolean stopSneaking(boolean isSneaking) {
        if (!isSneaking) return false;
        if (EntityUtil.mc.player == null) return false;
        EntityUtil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)EntityUtil.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
        return false;
    }

    public static Vec3d interpolateEntity(Entity entity, float time) {
        return new Vec3d(entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)time, entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)time, entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)time);
    }

    public static float calculate(double posX, double posY, double posZ, Entity entity) {
        double v = (1.0 - entity.getDistance(posX, posY, posZ) / 12.0) * (double)EntityUtil.getBlockDensity(new Vec3d(posX, posY, posZ), entity.getEntityBoundingBox());
        return EntityUtil.getBlastReduction((EntityLivingBase)entity, EntityUtil.getDamageMultiplied((int)((v * v + v) / 2.0 * 85.0 + 1.0)), new Explosion((World)EntityUtil.mc.world, null, posX, posY, posZ, 6.0f, false, true));
    }

    public static float getBlastReduction(EntityLivingBase entity, float damageI, Explosion explosion) {
        float damage = damageI;
        DamageSource ds = DamageSource.causeExplosionDamage((Explosion)explosion);
        damage = CombatRules.getDamageAfterAbsorb((float)damage, (float)entity.getTotalArmorValue(), (float)((float)entity.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue()));
        int k = EnchantmentHelper.getEnchantmentModifierDamage((Iterable)entity.getArmorInventoryList(), (DamageSource)ds);
        float f = MathHelper.clamp((float)k, (float)0.0f, (float)20.0f);
        if (!entity.isPotionActive(MobEffects.RESISTANCE)) return damage *= 1.0f - f / 25.0f;
        damage -= damage / 4.0f;
        return damage *= 1.0f - f / 25.0f;
    }

    public static float getDamageMultiplied(float damage) {
        float f;
        int diff = EntityUtil.mc.world.getDifficulty().getId();
        if (diff == 0) {
            f = 0.0f;
            return damage * f;
        }
        if (diff == 2) {
            f = 1.0f;
            return damage * f;
        }
        if (diff == 1) {
            f = 0.5f;
            return damage * f;
        }
        f = 1.5f;
        return damage * f;
    }

    public static float getBlockDensity(Vec3d vec, AxisAlignedBB bb) {
        double d0 = 1.0 / ((bb.maxX - bb.minX) * 2.0 + 1.0);
        double d1 = 1.0 / ((bb.maxY - bb.minY) * 2.0 + 1.0);
        double d2 = 1.0 / ((bb.maxZ - bb.minZ) * 2.0 + 1.0);
        double d3 = (1.0 - Math.floor(1.0 / d0) * d0) / 2.0;
        double d4 = (1.0 - Math.floor(1.0 / d2) * d2) / 2.0;
        if (!(d0 >= 0.0)) return 0.0f;
        if (!(d1 >= 0.0)) return 0.0f;
        if (!(d2 >= 0.0)) return 0.0f;
        int j2 = 0;
        int k2 = 0;
        float f = 0.0f;
        while (f <= 1.0f) {
            float f1 = 0.0f;
            while (f1 <= 1.0f) {
                float f2 = 0.0f;
                while (f2 <= 1.0f) {
                    double d5 = bb.minX + (bb.maxX - bb.minX) * (double)f;
                    double d6 = bb.minY + (bb.maxY - bb.minY) * (double)f1;
                    double d7 = bb.minZ + (bb.maxZ - bb.minZ) * (double)f2;
                    if (EntityUtil.rayTraceBlocks(new Vec3d(d5 + d3, d6, d7 + d4), vec, false, false, false) == null) {
                        ++j2;
                    }
                    ++k2;
                    f2 = (float)((double)f2 + d2);
                }
                f1 = (float)((double)f1 + d1);
            }
            f = (float)((double)f + d0);
        }
        return (float)j2 / (float)k2;
    }

    public static float getHealth(EntityPlayer player) {
        return player.getHealth() + player.getAbsorptionAmount();
    }

    public static RayTraceResult rayTraceBlocks(Vec3d vec31, Vec3d vec32, boolean stopOnLiquid, boolean ignoreBlockWithoutBoundingBox, boolean returnLastUncollidableBlock) {
        if (Double.isNaN(vec31.x)) return null;
        if (Double.isNaN(vec31.y)) return null;
        if (Double.isNaN(vec31.z)) return null;
        if (Double.isNaN(vec32.x)) return null;
        if (Double.isNaN(vec32.y)) return null;
        if (Double.isNaN(vec32.z)) return null;
        int i2 = MathHelper.floor((double)vec32.x);
        int j = MathHelper.floor((double)vec32.y);
        int k = MathHelper.floor((double)vec32.z);
        int l = MathHelper.floor((double)vec31.x);
        int i1 = MathHelper.floor((double)vec31.y);
        int j1 = MathHelper.floor((double)vec31.z);
        BlockPos blockpos = new BlockPos(l, i1, j1);
        IBlockState iblockstate = EntityUtil.mc.world.getBlockState(blockpos);
        Block block = iblockstate.getBlock();
        if ((!ignoreBlockWithoutBoundingBox || iblockstate.getCollisionBoundingBox((IBlockAccess)EntityUtil.mc.world, blockpos) != Block.NULL_AABB) && block.canCollideCheck(iblockstate, stopOnLiquid) && block != Blocks.WEB) {
            return iblockstate.collisionRayTrace((World)EntityUtil.mc.world, blockpos, vec31, vec32);
        }
        RayTraceResult raytraceresult2 = null;
        int k1 = 200;
        while (k1-- >= 0) {
            EnumFacing enumfacing;
            if (Double.isNaN(vec31.x)) return null;
            if (Double.isNaN(vec31.y)) return null;
            if (Double.isNaN(vec31.z)) {
                return null;
            }
            if (l == i2 && i1 == j && j1 == k) {
                if (!returnLastUncollidableBlock) return null;
                RayTraceResult rayTraceResult = raytraceresult2;
                return rayTraceResult;
            }
            boolean flag2 = true;
            boolean flag = true;
            boolean flag1 = true;
            double d0 = 999.0;
            double d1 = 999.0;
            double d2 = 999.0;
            if (i2 > l) {
                d0 = (double)l + 1.0;
            } else if (i2 < l) {
                d0 = (double)l + 0.0;
            } else {
                flag2 = false;
            }
            if (j > i1) {
                d1 = (double)i1 + 1.0;
            } else if (j < i1) {
                d1 = (double)i1 + 0.0;
            } else {
                flag = false;
            }
            if (k > j1) {
                d2 = (double)j1 + 1.0;
            } else if (k < j1) {
                d2 = (double)j1 + 0.0;
            } else {
                flag1 = false;
            }
            double d3 = 999.0;
            double d4 = 999.0;
            double d5 = 999.0;
            double d6 = vec32.x - vec31.x;
            double d7 = vec32.y - vec31.y;
            double d8 = vec32.z - vec31.z;
            if (flag2) {
                d3 = (d0 - vec31.x) / d6;
            }
            if (flag) {
                d4 = (d1 - vec31.y) / d7;
            }
            if (flag1) {
                d5 = (d2 - vec31.z) / d8;
            }
            if (d3 == -0.0) {
                d3 = -1.0E-4;
            }
            if (d4 == -0.0) {
                d4 = -1.0E-4;
            }
            if (d5 == -0.0) {
                d5 = -1.0E-4;
            }
            if (d3 < d4 && d3 < d5) {
                enumfacing = i2 > l ? EnumFacing.WEST : EnumFacing.EAST;
                vec31 = new Vec3d(d0, vec31.y + d7 * d3, vec31.z + d8 * d3);
            } else if (d4 < d5) {
                enumfacing = j > i1 ? EnumFacing.DOWN : EnumFacing.UP;
                vec31 = new Vec3d(vec31.x + d6 * d4, d1, vec31.z + d8 * d4);
            } else {
                enumfacing = k > j1 ? EnumFacing.NORTH : EnumFacing.SOUTH;
                vec31 = new Vec3d(vec31.x + d6 * d5, vec31.y + d7 * d5, d2);
            }
            l = MathHelper.floor((double)vec31.x) - (enumfacing == EnumFacing.EAST ? 1 : 0);
            i1 = MathHelper.floor((double)vec31.y) - (enumfacing == EnumFacing.UP ? 1 : 0);
            j1 = MathHelper.floor((double)vec31.z) - (enumfacing == EnumFacing.SOUTH ? 1 : 0);
            blockpos = new BlockPos(l, i1, j1);
            IBlockState iblockstate1 = EntityUtil.mc.world.getBlockState(blockpos);
            Block block1 = iblockstate1.getBlock();
            if (ignoreBlockWithoutBoundingBox && iblockstate1.getMaterial() != Material.PORTAL && iblockstate1.getCollisionBoundingBox((IBlockAccess)EntityUtil.mc.world, blockpos) == Block.NULL_AABB) continue;
            if (block1.canCollideCheck(iblockstate1, stopOnLiquid) && block1 != Blocks.WEB) {
                return iblockstate1.collisionRayTrace((World)EntityUtil.mc.world, blockpos, vec31, vec32);
            }
            raytraceresult2 = new RayTraceResult(RayTraceResult.Type.MISS, vec31, enumfacing, blockpos);
        }
        if (!returnLastUncollidableBlock) return null;
        RayTraceResult rayTraceResult = raytraceresult2;
        return rayTraceResult;
    }
}

