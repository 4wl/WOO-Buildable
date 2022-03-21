/*
 * Decompiled with CFR 0.152.
 */
package wtf.cattyn.woo.api.util;

import java.awt.Color;

public class ColorUtil {
    public static int rainbowInt(int delay, float saturation, float brightness) {
        return ColorUtil.rainbow(delay, saturation, brightness).getRGB();
    }

    public static Color rainbow(int delay, float saturation, float brightness) {
        double rainbowState = Math.ceil((double)(System.currentTimeMillis() + (long)delay) / 20.0);
        return Color.getHSBColor((float)((rainbowState %= 360.0) / 360.0), saturation, brightness);
    }
}

