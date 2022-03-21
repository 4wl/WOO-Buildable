/*
 * Decompiled with CFR 0.152.
 */
package wtf.cattyn.woo.client.modules.misc;

import wtf.cattyn.woo.api.module.Module;

public class NameProtect
extends Module {
    public static String protectName = "Protected";
    public static final NameProtect INSTANCE = new NameProtect();
    public static String fakeName = "You";

    public NameProtect() {
        super("NameProtect", Module.Category.Misc);
    }
}

