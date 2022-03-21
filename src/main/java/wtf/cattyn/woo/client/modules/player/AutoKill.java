//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\maywr\Documents\remapping\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.152.
 */
package wtf.cattyn.woo.client.modules.player;

import wtf.cattyn.woo.api.module.Module;

public class AutoKill
extends Module {
    public AutoKill() {
        super("AutoKill", Module.Category.Player);
    }

    @Override
    public void onEnable() {
        if (this.nullCheck()) {
            return;
        }
        this.mc.player.sendChatMessage("/kill");
        this.disable();
    }
}

