//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\maywr\Documents\remapping\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Items
 *  net.minecraft.item.ItemEnderPearl
 *  net.minecraft.util.EnumHand
 *  net.minecraft.world.World
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  org.lwjgl.input.Mouse
 */
package wtf.cattyn.woo.client.modules.player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Mouse;
import wtf.cattyn.woo.api.event.UpdateEvent;
import wtf.cattyn.woo.api.module.Module;
import wtf.cattyn.woo.api.util.ItemUtil;

public class MCP
extends Module {
    private boolean clicked = false;

    public MCP() {
        super("MCP", Module.Category.Player);
    }

    @SubscribeEvent
    public void onUpdate(UpdateEvent event) {
        if (!Mouse.isButtonDown((int)2)) {
            this.clicked = false;
            return;
        }
        if (!this.clicked && this.mc.currentScreen == null) {
            this.onClick();
        }
        this.clicked = true;
    }

    private void onClick() {
        int pearlSlot = ItemUtil.findHotbarBlock(ItemEnderPearl.class);
        boolean offhand = this.mc.player.getHeldItemOffhand().getItem() == Items.ENDER_PEARL;
        boolean bl = offhand;
        if (pearlSlot == -1) {
            if (!offhand) return;
        }
        int oldslot = this.mc.player.inventory.currentItem;
        if (!offhand) {
            ItemUtil.switchToHotbarSlot(pearlSlot, false);
        }
        this.mc.playerController.processRightClick((EntityPlayer)this.mc.player, (World)this.mc.world, offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
        if (offhand) return;
        ItemUtil.switchToHotbarSlot(oldslot, false);
    }
}

