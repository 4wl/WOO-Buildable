/*
 * Decompiled with CFR 0.152.
 */
package wtf.cattyn.woo.api.setting;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import wtf.cattyn.woo.api.module.Module;
import wtf.cattyn.woo.api.setting.Setting;

public class SettingManager {
    private final List<Setting> settings = new ArrayList<Setting>();

    public List<Setting> getSettings() {
        return this.settings;
    }

    public void addSetting(Setting setting) {
        this.settings.add(setting);
    }

    public Setting getSettingByNameAndMod(String name, Module parent) {
        return this.settings.stream().filter(s -> s.getParent().equals(parent)).filter(s -> s.getConfigName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public Setting getSettingByNameAndModConfig(String configname, Module parent) {
        return this.settings.stream().filter(s -> s.getParent().equals(parent)).filter(s -> s.getConfigName().equalsIgnoreCase(configname)).findFirst().orElse(null);
    }

    public List<Setting> getSettingsForMod(Module parent) {
        return this.settings.stream().filter(s -> s.getParent().equals(parent)).collect(Collectors.toList());
    }

    public List<Setting> getSettingsByCategory(Module.Category category) {
        return this.settings.stream().filter(s -> s.getCategory().equals((Object)category)).collect(Collectors.toList());
    }

    public Setting getSettingByName(String name) {
        Setting set;
        Iterator<Setting> iterator = this.getSettings().iterator();
        do {
            if (iterator.hasNext()) continue;
            System.err.println("[plutonium] Error Setting NOT found: '" + name + "'!");
            return null;
        } while (!(set = iterator.next()).getName().equalsIgnoreCase(name));
        return set;
    }
}

