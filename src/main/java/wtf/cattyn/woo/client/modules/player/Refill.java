//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\maywr\Documents\remapping\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Items
 *  net.minecraft.inventory.ClickType
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 */
package wtf.cattyn.woo.client.modules.player;

import java.util.ArrayList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import wtf.cattyn.woo.api.event.UpdateEvent;
import wtf.cattyn.woo.api.module.Module;
import wtf.cattyn.woo.api.setting.Setting;
import wtf.cattyn.woo.api.util.Timers;

public class Refill
extends Module {
    public Setting.i stacks = this.registerI("Stack", 32, 1, 64);
    public Setting.i delay = this.registerI("Delay", 10, 0, 10);
    private final Timers timer = new Timers();
    private final ArrayList<Item> Hotbar = new ArrayList();

    public Refill() {
        super("Refill", Module.Category.Player);
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent e) {
        if (this.nullCheck()) {
            return;
        }
        this.Hotbar.clear();
        int l_I = 0;
        while (l_I < 9) {
            ItemStack l_Stack = this.mc.player.inventory.getStackInSlot(l_I);
            if (!l_Stack.isEmpty() && !this.Hotbar.contains(l_Stack.getItem())) {
                this.Hotbar.add(l_Stack.getItem());
            } else {
                this.Hotbar.add(Items.AIR);
            }
            ++l_I;
        }
    }

    @SubscribeEvent
    public void onUpdate(UpdateEvent event) {
        if (this.mc.currentScreen != null) {
            return;
        }
        if (!this.timer.passedMs(this.delay.getValue() * 1000)) {
            return;
        }
        int l_I = 0;
        while (l_I < 9) {
            if (this.RefillSlotIfNeed(l_I)) {
                this.timer.reset();
                return;
            }
            ++l_I;
        }
    }

    private boolean RefillSlotIfNeed(int p_Slot) {
        ItemStack l_Stack = this.mc.player.inventory.getStackInSlot(p_Slot);
        if (l_Stack.isEmpty()) return false;
        if (l_Stack.getItem() == Items.AIR) {
            return false;
        }
        if (!l_Stack.isStackable()) {
            return false;
        }
        if (l_Stack.getCount() >= l_Stack.getMaxStackSize()) {
            return false;
        }
        if (l_Stack.getItem().equals(Items.GOLDEN_APPLE) && l_Stack.getCount() >= this.stacks.getValue()) {
            return false;
        }
        if (l_Stack.getItem().equals(Items.EXPERIENCE_BOTTLE) && l_Stack.getCount() > this.stacks.getValue()) {
            return false;
        }
        int l_I = 9;
        while (l_I < 36) {
            ItemStack l_Item = this.mc.player.inventory.getStackInSlot(l_I);
            if (!l_Item.isEmpty() && this.CanItemBeMergedWith(l_Stack, l_Item)) {
                this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, l_I, 0, ClickType.QUICK_MOVE, (EntityPlayer)this.mc.player);
                this.mc.playerController.updateController();
                return true;
            }
            ++l_I;
        }
        return false;
    }

    private boolean CanItemBeMergedWith(ItemStack p_Source, ItemStack p_Target) {
        if (p_Source.getItem() != p_Target.getItem()) return false;
        if (!p_Source.getDisplayName().equals(p_Target.getDisplayName())) return false;
        return true;
    }
}

