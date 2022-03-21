/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 */
package wtf.cattyn.woo.client.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import wtf.cattyn.woo.api.command.Command;
import wtf.cattyn.woo.api.util.TextUtil;

public class PrefixCommand
extends Command {
    public PrefixCommand() {
        super(new String[]{"p", "prefix"});
    }

    @Override
    public void onCommand(String command, String[] args) {
        if (args.length < 1) {
            TextUtil.sendMessage(ChatFormatting.RED + "Invalid Syntax", true);
            return;
        }
        PrefixCommand.setPrefix(args[0]);
        TextUtil.sendMessage(ChatFormatting.WHITE + "prefix is " + args[0], true);
    }
}

