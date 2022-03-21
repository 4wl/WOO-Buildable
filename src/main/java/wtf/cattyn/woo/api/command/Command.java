/*
 * Decompiled with CFR 0.152.
 */
package wtf.cattyn.woo.api.command;

public class Command {
    public static String prefix = "$";
    String[] name;

    public Command(String[] name) {
        this.name = name;
    }

    public String[] getName() {
        return this.name;
    }

    public static void setPrefix(String p) {
        prefix = p;
    }

    public void onCommand(String command, String[] args) {
    }
}

