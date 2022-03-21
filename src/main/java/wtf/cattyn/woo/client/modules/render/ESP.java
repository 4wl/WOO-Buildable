//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\maywr\Documents\remapping\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$PlayerTickEvent
 */
package wtf.cattyn.woo.client.modules.render;

import java.util.Iterator;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import wtf.cattyn.woo.api.module.Module;

public class ESP
extends Module {
    public ESP() {
        super("ESP", Module.Category.Render);
    }

    @SubscribeEvent
    public void onTick(TickEvent.PlayerTickEvent e) {
        if (this.nullCheck()) {
            return;
        }
        Iterator iterator = this.mc.world.playerEntities.iterator();
        while (iterator.hasNext()) {
            Entity entity = (Entity)iterator.next();
            entity.setGlowing(true);
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        Iterator iterator = this.mc.world.playerEntities.iterator();
        while (iterator.hasNext()) {
            Entity entity = (Entity)iterator.next();
            entity.setGlowing(false);
        }
    }
}

