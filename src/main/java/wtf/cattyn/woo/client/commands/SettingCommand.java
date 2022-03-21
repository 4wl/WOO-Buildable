/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 */
package wtf.cattyn.woo.client.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import wtf.cattyn.woo.Woo;
import wtf.cattyn.woo.api.command.Command;
import wtf.cattyn.woo.api.module.Module;
import wtf.cattyn.woo.api.setting.Setting;
import wtf.cattyn.woo.api.util.TextUtil;

public class SettingCommand
extends Command {
    public SettingCommand() {
        super(new String[]{"set", "setting"});
    }

    @Override
    public void onCommand(String command, String[] args) {
        if (args.length < 3) {
            System.out.println("negri!");
            TextUtil.sendMessage(ChatFormatting.RED + "Invalid Syntax", true);
            return;
        }
        Module mod = Woo.moduleManager.getModule(args[0]);
        if (mod == null) return;
        Setting setting = Woo.settingManager.getSettingByNameAndMod(args[1], mod);
        if (setting == null) return;
        if (setting instanceof Setting.b) {
            try {
                ((Setting.b)setting).setValue(Boolean.parseBoolean(args[2]));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (setting instanceof Setting.i) {
            try {
                ((Setting.i)setting).setValue(Integer.parseInt(args[2]));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (setting instanceof Setting.d) {
            try {
                ((Setting.d)setting).setValue(Double.parseDouble(args[2]));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (!(setting instanceof Setting.mode)) return;
        try {
            ((Setting.mode)setting).setValue(args[2]);
            return;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

