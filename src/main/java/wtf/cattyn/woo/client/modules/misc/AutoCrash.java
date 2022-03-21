//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\maywr\Documents\remapping\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.152.
 */
package wtf.cattyn.woo.client.modules.misc;

import wtf.cattyn.woo.Woo;
import wtf.cattyn.woo.api.module.Module;

public class AutoCrash
extends Module {
    public AutoCrash() {
        super("WentyMode", Module.Category.Misc);
    }

    @Override
    public void onEnable() {
        if (this.nullCheck()) {
            return;
        }
        Woo.log.info("BlockGame is SHIT!");
        this.disable();
        this.mc.player = null;
    }
}

