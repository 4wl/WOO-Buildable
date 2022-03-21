//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\maywr\Documents\remapping\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.play.server.SPacketTimeUpdate
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 */
package wtf.cattyn.woo.client.modules.render;

import net.minecraft.network.play.server.SPacketTimeUpdate;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import wtf.cattyn.woo.api.event.PacketEvent;
import wtf.cattyn.woo.api.module.Module;
import wtf.cattyn.woo.api.setting.Setting;

public class CustomTime
extends Module {
    public Setting.i time = this.registerI("Time", 1, 1, 24);

    public CustomTime() {
        super("CustomTime", Module.Category.Render);
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent e) {
        if (this.nullCheck()) {
            return;
        }
        this.mc.world.setWorldTime((long)(this.time.getValue() * 1000));
        this.setHudInfo("" + this.time.getValue());
    }

    @SubscribeEvent
    public void onPacketReceive(PacketEvent.Receive event) {
        if (this.nullCheck()) {
            return;
        }
        if (!(event.getPacket() instanceof SPacketTimeUpdate)) return;
        event.setCanceled(true);
    }
}

