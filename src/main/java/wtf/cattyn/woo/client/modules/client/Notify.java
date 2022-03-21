/*
 * Decompiled with CFR 0.152.
 */
package wtf.cattyn.woo.client.modules.client;

import wtf.cattyn.woo.api.module.Module;
import wtf.cattyn.woo.api.setting.Setting;

public class Notify
extends Module {
    public static Notify INSTANCE;
    public Setting.b enabledModule = this.registerB("Chat Enabled Module", true);
    public Setting.b useWatermark = this.registerB("Notify Watermark", true);

    public Notify() {
        super("Notify", Module.Category.Client);
        INSTANCE = this;
    }
}

