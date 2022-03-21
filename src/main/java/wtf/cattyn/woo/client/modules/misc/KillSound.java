//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\maywr\Documents\remapping\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package wtf.cattyn.woo.client.modules.misc;

import java.util.Arrays;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wtf.cattyn.woo.api.event.UpdateEvent;
import wtf.cattyn.woo.api.module.Module;
import wtf.cattyn.woo.api.setting.Setting;
import wtf.cattyn.woo.api.util.SoundUtil;

public class KillSound
extends Module {
    private static EntityPlayer currentTarget;
    public Setting.mode sounds = this.registerMode("Sound", Arrays.asList("Neverlose", "Metallic", "Seal", "Anime", "Head Shot", "Mission", "Girl1", "Girl2", "Girl3", "NNdog"), "Neverlose");

    public KillSound() {
        super("KillSound", Module.Category.Misc);
    }

    @SubscribeEvent
    public void onUpdate(UpdateEvent event) {
        if (this.nullCheck()) {
            return;
        }
        if (currentTarget == null) return;
        if (!(this.mc.player.getDistanceSq((Entity)currentTarget) < 100.0)) return;
        if (!KillSound.currentTarget.isDead) {
            if (!(currentTarget.getHealth() <= 0.0f)) return;
        }
        switch (this.sounds.getValue()) {
            case "Neverlose": {
                SoundUtil.playSound(SoundUtil.INSTANCE.neverlose);
                break;
            }
            case "Metallic": {
                SoundUtil.playSound(SoundUtil.INSTANCE.metallic);
                break;
            }
            case "Seal": {
                SoundUtil.playSound(SoundUtil.INSTANCE.woo);
                break;
            }
            case "Anime": {
                SoundUtil.playSound(SoundUtil.INSTANCE.ya);
                break;
            }
            case "Head Shot": {
                SoundUtil.playSound(SoundUtil.INSTANCE.hs);
                break;
            }
            case "Mission": {
                SoundUtil.playSound(SoundUtil.INSTANCE.mission);
                break;
            }
            case "Girl1": {
                SoundUtil.playSound(SoundUtil.INSTANCE.kill9);
                break;
            }
            case "Girl2": {
                SoundUtil.playSound(SoundUtil.INSTANCE.kill10);
                break;
            }
            case "Girl3": {
                SoundUtil.playSound(SoundUtil.INSTANCE.kill11);
                break;
            }
            case "NNdog": {
                SoundUtil.playSound(SoundUtil.INSTANCE.saynndog);
                break;
            }
        }
        currentTarget = null;
    }

    public static void setCurrentTarget(EntityPlayer player) {
        currentTarget = player;
    }
}

