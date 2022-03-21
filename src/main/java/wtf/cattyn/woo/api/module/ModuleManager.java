/*
 * Decompiled with CFR 0.152.
 */
package wtf.cattyn.woo.api.module;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.stream.Collectors;
import wtf.cattyn.woo.api.event.Render2DEvent;
import wtf.cattyn.woo.api.event.Render3DEvent;
import wtf.cattyn.woo.api.module.Module;
import wtf.cattyn.woo.client.modules.client.ClickGuiMod;
import wtf.cattyn.woo.client.modules.client.Font;
import wtf.cattyn.woo.client.modules.client.HUD;
import wtf.cattyn.woo.client.modules.client.Notify;
import wtf.cattyn.woo.client.modules.combat.AutoArmor;
import wtf.cattyn.woo.client.modules.combat.AutoCrystal;
import wtf.cattyn.woo.client.modules.combat.Criticals;
import wtf.cattyn.woo.client.modules.combat.KillAura;
import wtf.cattyn.woo.client.modules.combat.Offhand;
import wtf.cattyn.woo.client.modules.combat.SelfFill;
import wtf.cattyn.woo.client.modules.combat.SelfWeb;
import wtf.cattyn.woo.client.modules.combat.Surround;
import wtf.cattyn.woo.client.modules.misc.AutoCrash;
import wtf.cattyn.woo.client.modules.misc.CustomFPS;
import wtf.cattyn.woo.client.modules.misc.HitSound;
import wtf.cattyn.woo.client.modules.misc.KillSound;
import wtf.cattyn.woo.client.modules.misc.NameProtect;
import wtf.cattyn.woo.client.modules.misc.PigFact;
import wtf.cattyn.woo.client.modules.movement.Clip;
import wtf.cattyn.woo.client.modules.movement.NoWeb;
import wtf.cattyn.woo.client.modules.movement.PacketFly;
import wtf.cattyn.woo.client.modules.movement.ReverseStep;
import wtf.cattyn.woo.client.modules.movement.Sprint;
import wtf.cattyn.woo.client.modules.movement.Step;
import wtf.cattyn.woo.client.modules.movement.Strafe;
import wtf.cattyn.woo.client.modules.movement.YPort;
import wtf.cattyn.woo.client.modules.player.AutoKill;
import wtf.cattyn.woo.client.modules.player.FakePlayer;
import wtf.cattyn.woo.client.modules.player.FastXP;
import wtf.cattyn.woo.client.modules.player.InstantMine;
import wtf.cattyn.woo.client.modules.player.MCF;
import wtf.cattyn.woo.client.modules.player.MCP;
import wtf.cattyn.woo.client.modules.player.MCXP;
import wtf.cattyn.woo.client.modules.player.Refill;
import wtf.cattyn.woo.client.modules.player.XPDown;
import wtf.cattyn.woo.client.modules.render.Chams;
import wtf.cattyn.woo.client.modules.render.CustomTime;
import wtf.cattyn.woo.client.modules.render.ESP;
import wtf.cattyn.woo.client.modules.render.Fullbright;
import wtf.cattyn.woo.client.modules.render.GuiGradient;
import wtf.cattyn.woo.client.modules.render.HoleEsp;
import wtf.cattyn.woo.client.modules.render.TargetHud;

public class ModuleManager {
    private final ArrayList<Module> modules = new ArrayList();

    public ModuleManager() {
        this.modules.add(new SelfFill());
        this.modules.add(new Criticals());
        this.modules.add(new KillAura());
        this.modules.add(new AutoArmor());
        this.modules.add(new Surround());
        this.modules.add(new Offhand());
        this.modules.add(new AutoCrystal());
        this.modules.add(new SelfWeb());
        this.modules.add(new FakePlayer());
        this.modules.add(new FastXP());
        this.modules.add(new InstantMine());
        this.modules.add(new AutoKill());
        this.modules.add(new MCP());
        this.modules.add(new MCXP());
        this.modules.add(new MCF());
        this.modules.add(new XPDown());
        this.modules.add(new Sprint());
        this.modules.add(new Step());
        this.modules.add(new ReverseStep());
        this.modules.add(new Strafe());
        this.modules.add(new Clip());
        this.modules.add(new NoWeb());
        this.modules.add(new YPort());
        this.modules.add(new PacketFly());
        this.modules.add(new PigFact());
        this.modules.add(new NameProtect());
        this.modules.add(new Refill());
        this.modules.add(new AutoCrash());
        this.modules.add(new HitSound());
        this.modules.add(new KillSound());
        this.modules.add(new CustomFPS());
        this.modules.add(new Fullbright());
        this.modules.add(new ESP());
        this.modules.add(new GuiGradient());
        this.modules.add(new CustomTime());
        this.modules.add(new HoleEsp());
        this.modules.add(new Chams());
        this.modules.add(new TargetHud());
        this.modules.add(new HUD());
        this.modules.add(new Font());
        this.modules.add(new ClickGuiMod());
        this.modules.add(new Notify());
        this.modules.sort(Comparator.comparing(object -> object.name));
    }

    public ArrayList<Module> getModules() {
        return this.modules;
    }

    public Module getModule(String name) {
        Module module;
        Iterator<Module> iterator = this.modules.iterator();
        do {
            if (!iterator.hasNext()) return null;
        } while (!(module = iterator.next()).getName().equalsIgnoreCase(name));
        return module;
    }

    public ArrayList<Module> getModules(Module.Category category) {
        ArrayList<Module> mods = new ArrayList<Module>();
        Iterator<Module> iterator = this.modules.iterator();
        while (iterator.hasNext()) {
            Module module = iterator.next();
            if (!module.getCategory().equals((Object)category)) continue;
            mods.add(module);
        }
        return mods;
    }

    public ArrayList<Module> getEnabledModules() {
        return this.modules.stream().filter(Module::isToggled).collect(Collectors.toCollection(ArrayList::new));
    }

    public void onRender2D(Render2DEvent event) {
        this.modules.stream().filter(Module::isToggled).forEach(module -> module.onRender2D(event));
    }

    public void onRender3D(Render3DEvent event) {
        this.modules.stream().filter(Module::isToggled).forEach(module -> module.onRender3D(event));
    }

    public ArrayList<Module> getEnabledAndDrawnModules() {
        return this.modules.stream().filter(Module::isToggled).filter(Module::isDrawn).collect(Collectors.toCollection(ArrayList::new));
    }
}

