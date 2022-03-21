/*
 * Decompiled with CFR 0.152.
 */
package wtf.cattyn.woo.client.commands;

import wtf.cattyn.woo.Woo;
import wtf.cattyn.woo.api.command.Command;
import wtf.cattyn.woo.api.util.TextUtil;

public class FriendCommand
extends Command {
    public FriendCommand() {
        super(new String[]{"f", "friend"});
    }

    @Override
    public void onCommand(String command, String[] args) {
        switch (args[0].toUpperCase()) {
            case "add": {
                Woo.friendManager.addFriend(args[1]);
                TextUtil.sendMessage("Added " + args[1] + " as a friend.");
                return;
            }
            case "del": {
                Woo.friendManager.removeFriend(args[1]);
                TextUtil.sendMessage("Removed " + args[1] + " from friends.");
                return;
            }
        }
    }
}

