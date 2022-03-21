/*
 * Decompiled with CFR 0.152.
 */
package wtf.cattyn.woo.client.modules.client;

import wtf.cattyn.woo.Woo;
import wtf.cattyn.woo.api.module.Module;

public class Font
extends Module {
    public static Font INSTANCE;

    public Font() {
        super("Font", Module.Category.Client);
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
        if (this.nullCheck()) {
            return;
        }
        Woo.fontManager.setCustomFont(true);
    }

    @Override
    public void onDisable() {
        if (this.nullCheck()) {
            return;
        }
        Woo.fontManager.setCustomFont(false);
    }
}

