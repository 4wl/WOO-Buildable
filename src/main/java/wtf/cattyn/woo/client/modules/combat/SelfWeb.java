//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\maywr\Documents\remapping\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.math.BlockPos
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package wtf.cattyn.woo.client.modules.combat;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wtf.cattyn.woo.api.event.UpdateEvent;
import wtf.cattyn.woo.api.module.Module;
import wtf.cattyn.woo.api.util.BlockUtil;
import wtf.cattyn.woo.api.util.ItemUtil;

public class SelfWeb
extends Module {
    private BlockPos down;

    public SelfWeb() {
        super("SelfWeb", Module.Category.Combat);
    }

    @SubscribeEvent
    public void onUpdate(UpdateEvent event) {
        if (this.nullCheck()) {
            return;
        }
        this.down = new BlockPos(this.mc.player.posX, Math.ceil(this.mc.player.posY), this.mc.player.posZ);
        int webslot = ItemUtil.findHotbarBlock(Blocks.WEB);
        int oldslot = this.mc.player.inventory.currentItem;
        if (webslot == -1) {
            this.disable();
            return;
        }
        ItemUtil.switchToHotbarSlot(webslot, false);
        BlockUtil.placeBlock(this.down);
        ItemUtil.switchToHotbarSlot(oldslot, false);
        this.disable();
    }
}

