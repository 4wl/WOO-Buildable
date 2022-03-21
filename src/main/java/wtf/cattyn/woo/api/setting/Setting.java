/*
 * Decompiled with CFR 0.152.
 */
package wtf.cattyn.woo.api.setting;

import java.awt.Color;
import java.util.List;
import wtf.cattyn.woo.api.module.Module;

public class Setting {
    private final String name;
    private final String configname;
    private final Module parent;
    private final Module.Category category;
    private final Type type;
    private boolean hidden;

    public Setting(String name, String configname, Module parent, Module.Category category, Type type) {
        this.name = name;
        this.configname = configname;
        this.parent = parent;
        this.type = type;
        this.category = category;
        this.hidden = false;
    }

    public String getName() {
        return this.name;
    }

    public String getConfigName() {
        return this.configname;
    }

    public Module getParent() {
        return this.parent;
    }

    public Type getType() {
        return this.type;
    }

    public Module.Category getCategory() {
        return this.category;
    }

    public boolean getHidden() {
        return this.hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = !hidden;
    }

    public static class c
    extends Setting {
        private Color value;
        private int alpha;

        public c(String name, String configname, Module parent, Module.Category category, Color value, int alpha) {
            super(name, configname, parent, category, Type.C);
            this.value = new Color(value.getRed(), value.getGreen(), value.getBlue(), alpha);
        }

        public Color getValue() {
            return this.value;
        }

        public void setValue(Color value) {
            this.value = value;
        }

        public void setAlpha(int alpha) {
            this.alpha = alpha;
        }

        public int getAlpha() {
            return this.alpha;
        }
    }

    public static class mode
    extends Setting {
        private final List<String> modes;
        private String value;

        public mode(String name, String configname, Module parent, Module.Category category, List<String> modes, String value) {
            super(name, configname, parent, category, Type.M);
            this.value = value;
            this.modes = modes;
        }

        public String getValue() {
            return this.value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public List<String> getModes() {
            return this.modes;
        }
    }

    public static class b
    extends Setting {
        private boolean value;

        public b(String name, String configname, Module parent, Module.Category category, boolean value) {
            super(name, configname, parent, category, Type.B);
            this.value = value;
        }

        public boolean getValue() {
            return this.value;
        }

        public void setValue(boolean value) {
            this.value = value;
        }
    }

    public static class d
    extends Setting {
        private double min;
        private double max;
        private double value;

        public d(String name, String configname, Module parent, Module.Category category, double value, double min, double max) {
            super(name, configname, parent, category, Type.D);
            this.value = value;
            this.min = min;
            this.max = max;
        }

        public double getValue() {
            return this.value;
        }

        public void setValue(double value) {
            this.value = value;
        }

        public double getMin() {
            return this.min;
        }

        public double getMax() {
            return this.max;
        }

        public void setMax(int max) {
            this.max = max;
        }

        public void setMin(int min) {
            this.min = min;
        }
    }

    public static class i
    extends Setting {
        private int min;
        private int max;
        private int value;

        public i(String name, String configname, Module parent, Module.Category category, int value, int min, int max) {
            super(name, configname, parent, category, Type.I);
            this.value = value;
            this.min = min;
            this.max = max;
        }

        public int getValue() {
            return this.value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public int getMin() {
            return this.min;
        }

        public int getMax() {
            return this.max;
        }

        public void setMax(int max) {
            this.max = max;
        }

        public void setMin(int min) {
            this.min = min;
        }
    }

    public static enum Type {
        I,
        D,
        B,
        STRING,
        M,
        C,
        NONE;

    }
}

