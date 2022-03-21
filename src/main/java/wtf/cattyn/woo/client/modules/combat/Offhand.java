//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\maywr\Documents\remapping\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.inventory.GuiContainer
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Items
 *  net.minecraft.inventory.ClickType
 *  net.minecraft.inventory.EntityEquipmentSlot
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketClickWindow
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package wtf.cattyn.woo.client.modules.combat;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketClickWindow;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wtf.cattyn.woo.api.event.UpdateEvent;
import wtf.cattyn.woo.api.module.Module;
import wtf.cattyn.woo.api.setting.Setting;
import wtf.cattyn.woo.api.util.EntityUtil;
import wtf.cattyn.woo.api.util.ItemUtil;
import wtf.cattyn.woo.api.util.Timers;

public class Offhand
extends Module {
    public Setting.d health = this.registerD("Crystal Health", 15.0, 1.0, 32.0);
    public Setting.d gappleHealth = this.registerD("Gapple Health", 15.0, 1.0, 32.0);
    public Setting.b gappleOnSword = this.registerB("Sword Apple", true);
    public Setting.i delay = this.registerI("Delay", 15, 0, 200);
    public Setting.i range = this.registerI("Range", 15, 0, 200);
    private boolean gappling;
    private final Timers timer = new Timers();
    private static Offhand INSTANCE = new Offhand();

    public Offhand() {
        super("Offhand", Module.Category.Combat);
    }

    @SubscribeEvent
    public void onUpdate(UpdateEvent event) {
        if (this.nullCheck()) {
            return;
        }
        if (this.mc.currentScreen instanceof GuiContainer) {
            return;
        }
        if (!this.timer.passedMs(this.delay.getValue())) return;
        Item item = this.shouldTotem() ? Items.TOTEM_OF_UNDYING : (this.gappling ? Items.GOLDEN_APPLE : Items.END_CRYSTAL);
        int getSlot = ItemUtil.getItemSlot(item);
        if (item == Items.END_CRYSTAL) {
            this.setHudInfo("Crystal");
        } else if (item == Items.TOTEM_OF_UNDYING) {
            this.setHudInfo("Totem");
        } else {
            this.setHudInfo("Gapple");
        }
        if (this.mc.player.getHeldItemOffhand() == ItemStack.EMPTY || this.mc.player.getHeldItemOffhand().getItem() != item) {
            int slot = getSlot < 9 ? getSlot + 36 : getSlot;
            int n = slot;
            if (getSlot != -1) {
                this.clickSlot(0, slot, 0);
                this.clickSlot(0, 45, 0);
                this.clickSlot(0, slot, 0);
            }
        }
        this.timer.reset();
    }

    private void clickSlot(int windowId, int slotId, int button) {
        this.mc.getConnection().sendPacket((Packet)new CPacketClickWindow(windowId, slotId, button, ClickType.PICKUP, this.mc.player.openContainer.slotClick(slotId, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player), this.mc.player.openContainer.getNextTransactionID(this.mc.player.inventory)));
    }

    private boolean nearPlayers() {
        if (EntityUtil.getTarget(this.range.getValue()) == null) return false;
        return true;
    }

    private boolean shouldTotem() {
        if (ItemUtil.getItemCount(Items.TOTEM_OF_UNDYING) == 0) {
            return false;
        }
        if (ItemUtil.getItemCount(this.gappling ? Items.GOLDEN_APPLE : Items.END_CRYSTAL) == 0) {
            return true;
        }
        if ((double)(this.mc.player.getHealth() + this.mc.player.getAbsorptionAmount()) <= (!this.gappling ? this.health.getValue() : this.gappleHealth.getValue())) return true;
        if (this.mc.player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() == Items.ELYTRA) return true;
        if (this.gappling) return false;
        if (this.nearPlayers()) return false;
        return true;
    }
}

