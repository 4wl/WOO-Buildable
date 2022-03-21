//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\maywr\Documents\remapping\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.inventory.GuiContainer
 *  net.minecraft.client.gui.inventory.GuiInventory
 *  net.minecraft.init.Items
 *  net.minecraft.inventory.EntityEquipmentSlot
 *  net.minecraft.item.ItemStack
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package wtf.cattyn.woo.client.modules.combat;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wtf.cattyn.woo.api.event.UpdateEvent;
import wtf.cattyn.woo.api.module.Module;
import wtf.cattyn.woo.api.setting.Setting;
import wtf.cattyn.woo.api.util.ItemUtil;
import wtf.cattyn.woo.api.util.Timers;

public class AutoArmor
extends Module {
    public Setting.i delay = this.registerI("Delay", 50, 0, 500);
    public Setting.b curse = this.registerB("Curse", true);
    public Setting.i packet = this.registerI("Packet", 1, 0, 4);
    private final Timers timer = new Timers();
    private final Queue<ItemUtil.Task> taskList = new ConcurrentLinkedQueue<ItemUtil.Task>();
    private final List<Integer> doneSlots = new ArrayList<Integer>();
    boolean flag;
    private float TPS = 20.0f;

    public AutoArmor() {
        super("AutoArmor", Module.Category.Combat);
    }

    @Override
    public void onDisable() {
        this.taskList.clear();
        this.doneSlots.clear();
        this.flag = false;
    }

    public float getTpsFactor() {
        return 20.0f / this.TPS;
    }

    @SubscribeEvent
    public void onUpdate(UpdateEvent event) {
        if (this.nullCheck()) return;
        if (this.mc.currentScreen instanceof GuiContainer && !(this.mc.currentScreen instanceof GuiInventory)) {
            return;
        }
        if (this.taskList.isEmpty()) {
            int slot;
            ItemStack feet;
            int slot2;
            ItemStack legging;
            int slot3;
            ItemStack chest;
            int slot4;
            this.flag = false;
            ItemStack helm = this.mc.player.inventoryContainer.getSlot(5).getStack();
            if (helm.getItem() == Items.AIR && (slot4 = ItemUtil.findArmorSlot(EntityEquipmentSlot.HEAD, this.curse.getValue(), true)) != -1) {
                this.getSlotOn(5, slot4);
            }
            if ((chest = this.mc.player.inventoryContainer.getSlot(6).getStack()).getItem() == Items.AIR && (slot3 = ItemUtil.findArmorSlot(EntityEquipmentSlot.CHEST, this.curse.getValue(), true)) != -1) {
                this.getSlotOn(6, slot3);
            }
            if ((legging = this.mc.player.inventoryContainer.getSlot(7).getStack()).getItem() == Items.AIR && (slot2 = ItemUtil.findArmorSlot(EntityEquipmentSlot.LEGS, this.curse.getValue(), true)) != -1) {
                this.getSlotOn(7, slot2);
            }
            if ((feet = this.mc.player.inventoryContainer.getSlot(8).getStack()).getItem() == Items.AIR && (slot = ItemUtil.findArmorSlot(EntityEquipmentSlot.FEET, this.curse.getValue(), true)) != -1) {
                this.getSlotOn(8, slot);
            }
        }
        if (!this.timer.passedMs((int)((float)this.delay.getValue() * this.getTpsFactor()))) return;
        if (!this.taskList.isEmpty()) {
            for (int i2 = 0; i2 < this.packet.getValue(); ++i2) {
                ItemUtil.Task task = this.taskList.poll();
                if (task == null) continue;
                task.run();
            }
        }
        this.timer.reset();
    }

    private void getSlotOn(int slot, int target) {
        if (!this.taskList.isEmpty()) return;
        this.doneSlots.remove((Object)target);
        this.taskList.add(new ItemUtil.Task(target));
        this.taskList.add(new ItemUtil.Task(slot));
        this.taskList.add(new ItemUtil.Task());
    }
}

