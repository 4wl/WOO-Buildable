//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\maywr\Documents\remapping\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.event.entity.player.AttackEntityEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package wtf.cattyn.woo.client.modules.misc;

import java.util.Arrays;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wtf.cattyn.woo.api.module.Module;
import wtf.cattyn.woo.api.setting.Setting;
import wtf.cattyn.woo.api.util.SoundUtil;

public class HitSound
extends Module {
    public Setting.mode sounds = this.registerMode("Sound", Arrays.asList("Neverlose", "Metallic", "Hit"), "Neverlose");

    public HitSound() {
        super("HitSound", Module.Category.Misc);
    }

    @Override
    public void onEnable() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }

    @Override
    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }

    @SubscribeEvent
    public void onAttackEntity(AttackEntityEvent event) {
        if (!event.getEntity().equals((Object)this.mc.player)) {
            return;
        }
        switch (this.sounds.getValue()) {
            case "Neverlose": {
                SoundUtil.playSound(SoundUtil.INSTANCE.neverlose);
                return;
            }
            case "Metallic": {
                SoundUtil.playSound(SoundUtil.INSTANCE.metallic);
                return;
            }
            case "Hit": {
                SoundUtil.playSound(SoundUtil.INSTANCE.hitsound);
                return;
            }
        }
    }
}

