/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 */
package wtf.cattyn.woo.api.command;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.ArrayList;
import wtf.cattyn.woo.api.command.Command;
import wtf.cattyn.woo.api.util.TextUtil;
import wtf.cattyn.woo.client.commands.FriendCommand;
import wtf.cattyn.woo.client.commands.PrefixCommand;
import wtf.cattyn.woo.client.commands.SettingCommand;

public class CommandManager {
    public static ArrayList<Command> commands;
    public static boolean commandFound;

    public CommandManager() {
        commands = new ArrayList();
        commands.add(new FriendCommand());
        commands.add(new PrefixCommand());
        commands.add(new SettingCommand());
    }

    public static void runCommand(String input) {
        String[] argss = input.split(" ");
        String command = argss[0];
        String args = input.substring(command.length()).trim();
        commands.forEach(c2 -> {
            String[] stringArray = c2.name;
            int n = stringArray.length;
            int n2 = 0;
            while (true) {
                if (n2 >= n) {
                    if (commandFound) return;
                    TextUtil.sendMessage(ChatFormatting.RED + "Invalid Syntax", true);
                    return;
                }
                String name = stringArray[n2];
                if (argss[0].contains(name)) {
                    c2.onCommand(command, args.split(" "));
                    commandFound = true;
                }
                ++n2;
            }
        });
    }

    static {
        commandFound = false;
    }
}

