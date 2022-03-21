//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\maywr\Documents\remapping\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.client.Minecraft
 *  net.minecraft.enchantment.Enchantment
 *  net.minecraft.enchantment.EnchantmentHelper
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Enchantments
 *  net.minecraft.init.Items
 *  net.minecraft.inventory.ClickType
 *  net.minecraft.inventory.EntityEquipmentSlot
 *  net.minecraft.inventory.Slot
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemArmor
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketHeldItemChange
 */
package wtf.cattyn.woo.api.util;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import wtf.cattyn.woo.api.util.Wrapper;

public class ItemUtil
extends Wrapper {
    public static int findHotbarBlock(Class clazz) {
        int i2 = 0;
        while (i2 < 9) {
            ItemStack stack = ItemUtil.mc.player.inventory.getStackInSlot(i2);
            if (stack != ItemStack.EMPTY) {
                if (clazz.isInstance(stack.getItem())) {
                    return i2;
                }
                if (stack.getItem() instanceof ItemBlock) {
                    Block block = ((ItemBlock)stack.getItem()).getBlock();
                    if (clazz.isInstance(block)) return i2;
                }
            }
            ++i2;
        }
        return -1;
    }

    public static boolean holdingItem(Class clazz) {
        boolean result = false;
        ItemStack stack = ItemUtil.mc.player.getHeldItemMainhand();
        result = ItemUtil.isInstanceOf(stack, clazz);
        if (result) return result;
        ItemStack offhand = ItemUtil.mc.player.getHeldItemOffhand();
        return ItemUtil.isInstanceOf(stack, clazz);
    }

    public static boolean isInstanceOf(ItemStack stack, Class clazz) {
        if (stack == null) {
            return false;
        }
        Item item = stack.getItem();
        if (clazz.isInstance(item)) {
            return true;
        }
        if (!(item instanceof ItemBlock)) return false;
        Block block = Block.getBlockFromItem((Item)item);
        return clazz.isInstance(block);
    }

    public static int getItemCount(Item item) {
        int count = 0;
        int size = ItemUtil.mc.player.inventory.mainInventory.size();
        int i2 = 0;
        while (true) {
            if (i2 >= size) {
                ItemStack offhandStack = ItemUtil.mc.player.getHeldItemOffhand();
                if (offhandStack.getItem() != item) return count;
                count += offhandStack.getCount();
                return count;
            }
            ItemStack itemStack = (ItemStack)ItemUtil.mc.player.inventory.mainInventory.get(i2);
            if (itemStack.getItem() == item) {
                count += itemStack.getCount();
            }
            ++i2;
        }
    }

    public static int getItemSlot(Class clss) {
        int itemSlot = -1;
        int i2 = 45;
        while (i2 > 0) {
            if (ItemUtil.mc.player.inventory.getStackInSlot(i2).getItem().getClass() == clss) {
                return i2;
            }
            --i2;
        }
        return itemSlot;
    }

    public static int getItemSlot(Item item) {
        int itemSlot = -1;
        int i2 = 45;
        while (i2 > 0) {
            if (ItemUtil.mc.player.inventory.getStackInSlot(i2).getItem().equals(item)) {
                return i2;
            }
            --i2;
        }
        return itemSlot;
    }

    public static int getItemFromHotbar(Item item) {
        int slot = -1;
        int i2 = 8;
        while (i2 >= 0) {
            if (ItemUtil.mc.player.inventory.getStackInSlot(i2).getItem() == item) {
                return i2;
            }
            --i2;
        }
        return slot;
    }

    public static int findHotbarBlock(Block blockIn) {
        int i2 = 0;
        while (i2 < 9) {
            ItemStack stack = ItemUtil.mc.player.inventory.getStackInSlot(i2);
            if (stack != ItemStack.EMPTY && stack.getItem() instanceof ItemBlock) {
                Block block = ((ItemBlock)stack.getItem()).getBlock();
                if (block == blockIn) return i2;
            }
            ++i2;
        }
        return -1;
    }

    public static void switchToHotbarSlot(int slot, boolean silent) {
        if (ItemUtil.mc.player.inventory.currentItem == slot) return;
        if (slot < 0) {
            return;
        }
        ItemUtil.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
        if (!silent) {
            ItemUtil.mc.player.inventory.currentItem = slot;
        }
        ItemUtil.mc.playerController.updateController();
    }

    public static void switchToHotbarSlot(Class clazz, boolean silent) {
        int slot = ItemUtil.findHotbarBlock(clazz);
        if (slot <= -1) return;
        ItemUtil.switchToHotbarSlot(slot, silent);
    }

    public static int findArmorSlot(EntityEquipmentSlot type, boolean binding) {
        int slot = -1;
        float damage = 0.0f;
        int i2 = 9;
        while (i2 < 45) {
            ItemArmor armor;
            ItemStack s = Minecraft.getMinecraft().player.inventoryContainer.getSlot(i2).getStack();
            if (s.getItem() != Items.AIR && s.getItem() instanceof ItemArmor && (armor = (ItemArmor)s.getItem()).getEquipmentSlot() == type) {
                float currentDamage = armor.damageReduceAmount + EnchantmentHelper.getEnchantmentLevel((Enchantment)Enchantments.PROTECTION, (ItemStack)s);
                boolean cursed = binding && EnchantmentHelper.hasBindingCurse((ItemStack)s);
                boolean bl = cursed;
                if (currentDamage > damage && !cursed) {
                    damage = currentDamage;
                    slot = i2;
                }
            }
            ++i2;
        }
        return slot;
    }

    public static int findArmorSlot(EntityEquipmentSlot type, boolean binding, boolean withXCarry) {
        int slot = ItemUtil.findArmorSlot(type, binding);
        if (slot != -1) return slot;
        if (!withXCarry) return slot;
        float damage = 0.0f;
        int i2 = 1;
        while (i2 < 5) {
            ItemArmor armor;
            Slot craftingSlot = (Slot)ItemUtil.mc.player.inventoryContainer.inventorySlots.get(i2);
            ItemStack craftingStack = craftingSlot.getStack();
            if (craftingStack.getItem() != Items.AIR && craftingStack.getItem() instanceof ItemArmor && (armor = (ItemArmor)craftingStack.getItem()).getEquipmentSlot() == type) {
                float currentDamage = armor.damageReduceAmount + EnchantmentHelper.getEnchantmentLevel((Enchantment)Enchantments.PROTECTION, (ItemStack)craftingStack);
                boolean cursed = binding && EnchantmentHelper.hasBindingCurse((ItemStack)craftingStack);
                boolean bl = cursed;
                if (currentDamage > damage && !cursed) {
                    damage = currentDamage;
                    slot = i2;
                }
            }
            ++i2;
        }
        return slot;
    }

    public static class Task {
        private final int slot;
        private final boolean update;
        private final boolean quickClick;

        public Task() {
            this.update = true;
            this.slot = -1;
            this.quickClick = false;
        }

        public Task(int slot) {
            this.slot = slot;
            this.quickClick = false;
            this.update = false;
        }

        public Task(int slot, boolean quickClick) {
            this.slot = slot;
            this.quickClick = quickClick;
            this.update = false;
        }

        public void run() {
            if (this.update) {
                Wrapper.mc.playerController.updateController();
            }
            if (this.slot == -1) return;
            Wrapper.mc.playerController.windowClick(0, this.slot, 0, this.quickClick ? ClickType.QUICK_MOVE : ClickType.PICKUP, (EntityPlayer)Wrapper.mc.player);
        }

        public boolean isSwitching() {
            if (this.update) return false;
            return true;
        }
    }
}

