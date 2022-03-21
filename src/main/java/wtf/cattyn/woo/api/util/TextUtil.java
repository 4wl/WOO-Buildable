//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\maywr\Documents\remapping\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.util.text.ITextComponent
 *  net.minecraft.util.text.TextComponentString
 */
package wtf.cattyn.woo.api.util;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import wtf.cattyn.woo.api.util.Wrapper;
import wtf.cattyn.woo.client.modules.client.Notify;

public class TextUtil {
    public static final String CLIENT = ChatFormatting.BOLD + "[Woo]" + ChatFormatting.GRAY;

    public static void sendMessage(String message, boolean silent) {
        if (Wrapper.mc.player == null) {
            return;
        }
        if (Notify.INSTANCE.useWatermark.getValue()) {
            if (!silent) {
                Wrapper.mc.ingameGUI.getChatGUI().printChatMessage((ITextComponent)new TextComponentString(CLIENT + " " + message));
                return;
            }
            Wrapper.mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion((ITextComponent)new TextComponentString(CLIENT + " " + message), 1);
            return;
        }
        if (!silent) {
            Wrapper.mc.ingameGUI.getChatGUI().printChatMessage((ITextComponent)new TextComponentString("" + message));
            return;
        }
        Wrapper.mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion((ITextComponent)new TextComponentString(message), 1);
    }

    public static void sendMessage(String message) {
        if (Wrapper.mc.player == null) {
            return;
        }
        if (Notify.INSTANCE.useWatermark.getValue()) {
            Wrapper.mc.ingameGUI.getChatGUI().printChatMessage((ITextComponent)new TextComponentString(CLIENT + " " + message));
            return;
        }
        Wrapper.mc.ingameGUI.getChatGUI().printChatMessage((ITextComponent)new TextComponentString("" + message));
    }
}

