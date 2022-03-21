//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\maywr\Documents\remapping\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.util.math.RayTraceResult$Type
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  org.lwjgl.input.Mouse
 */
package wtf.cattyn.woo.client.modules.player;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Mouse;
import wtf.cattyn.woo.Woo;
import wtf.cattyn.woo.api.event.UpdateEvent;
import wtf.cattyn.woo.api.module.Module;
import wtf.cattyn.woo.api.util.TextUtil;

public class MCF
extends Module {
    private boolean clicked = false;

    public MCF() {
        super("MCF", Module.Category.Player);
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
        Entity entity;
        RayTraceResult result = this.mc.objectMouseOver;
        if (result != null && result.typeOfHit == RayTraceResult.Type.ENTITY && (entity = result.entityHit) instanceof EntityPlayer) {
            if (Woo.friendManager.isFriend(entity.getName())) {
                Woo.friendManager.removeFriend(entity.getName());
                TextUtil.sendMessage(ChatFormatting.RED + entity.getName() + ChatFormatting.RED + " has been unfriended.");
            } else {
                Woo.friendManager.addFriend(entity.getName());
                TextUtil.sendMessage(ChatFormatting.AQUA + entity.getName() + ChatFormatting.AQUA + " has been friended.");
            }
        }
        this.clicked = true;
    }
}

