//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\maywr\Documents\remapping\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package wtf.cattyn.woo.client.modules.misc;

import java.util.LinkedList;
import java.util.List;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wtf.cattyn.woo.api.event.UpdateEvent;
import wtf.cattyn.woo.api.module.Module;
import wtf.cattyn.woo.api.setting.Setting;
import wtf.cattyn.woo.api.util.SoundUtil;
import wtf.cattyn.woo.api.util.Timers;

public class PigFact
extends Module {
    public Setting.b randoms = this.registerB("Random", true);
    public Setting.i delay = this.registerI("Delay [Second]", 5, 1, 60);
    private final List<String> pigfact = new LinkedList<String>();
    private final Timers spamDelay = new Timers();
    private int stage = 0;

    public PigFact() {
        super("PigFact", Module.Category.Misc);
        this.pigfact.add("pig fact #1: pig own you.");
        this.pigfact.add("pig fact #2: who.ru");
        this.pigfact.add("pig fact #3: chardnol, whollow, jordo, proby and entire are pigfriends oink.");
        this.pigfact.add("pig fact #4: can I get some oink oink in the chat? oink in the chat?");
        this.pigfact.add("pig fact #5: `nefriendnul sis prase`");
        this.pigfact.add("pig fact #9: pig exploit.");
        this.pigfact.add("pig fact #10: pighack is private hack.");
        this.pigfact.add("pig fact #99: hraju hraju, vid\u00edm piga, bum o hlavu le\u017e\u00edm zticha.");
        this.pigfact.add("pig fact #100: kinda racist when there is nigger but no whigger...");
        this.pigfact.add("pig fact #1000: pig oink on that pack.");
        this.pigfact.add("pig fact #69: pig is 30 years old.");
        this.pigfact.add("pig fact #7: teampig over everything.");
        this.pigfact.add("pig fact #77: teampig want have ally.");
        this.pigfact.add("pig fact #421: Woo client?");
        this.pigfact.add("pig fact #420: sorry, the language barrier is acting up.");
    }

    @SubscribeEvent
    public void onUpdate(UpdateEvent event) {
        if (this.nullCheck()) {
            return;
        }
        if (this.stage >= this.pigfact.size()) {
            this.stage = 0;
        }
        if (!this.spamDelay.passedMs(this.delay.getValue() * 1000)) return;
        if (!this.randoms.getValue()) {
            this.mc.player.sendChatMessage(this.pigfact.get(this.stage));
        } else {
            SoundUtil.playSound(SoundUtil.INSTANCE.woo);
            this.mc.player.sendChatMessage(this.pigfact.get(this.random.ints(0, this.pigfact.size()).iterator().nextInt()));
        }
        ++this.stage;
        this.spamDelay.reset();
    }
}

