package wtf.cattyn.woo.api.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import wtf.cattyn.woo.Woo;
import wtf.cattyn.woo.api.command.Command;
import wtf.cattyn.woo.api.module.Module;
import wtf.cattyn.woo.api.setting.Setting;
import wtf.cattyn.woo.client.gui.clickgui.ClickGUI;

public class ConfigUtil
        extends Thread {
    private final Minecraft mc = Minecraft.getMinecraft();
    private final File mainFolder;
    private static final String ENABLED_MODULES = "EnabledModules.txt";
    private static final String SETTINGS = "Settings.txt";
    private static final String BINDS = "Binds.txt";
    private static final String DRAWN = "Drawn.txt";
    private static final String PREFIX = "Prefix.txt";
    private static final String FRIEND = "Friends.txt";
    private static final String GUIPOS = "GuiPosition.txt";

    public ConfigUtil() {
        this.mainFolder = new File(this.mc.gameDir + File.separator + "woo");
    }

    @Override
    public void run() {
        if (!this.mainFolder.exists() && !this.mainFolder.mkdirs()) {
            System.out.println("Failed to create config folder");
        }
        try {
            ConfigUtil.saveFile(new File(this.mainFolder.getAbsolutePath(), ENABLED_MODULES), Woo.moduleManager.getModules().stream().filter(Module::isToggled).map(Module::getName).collect(Collectors.toCollection(ArrayList::new)));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        try {
            ConfigUtil.saveFile(new File(this.mainFolder.getAbsolutePath(), SETTINGS), this.getSettings());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        try {
            ConfigUtil.saveFile(new File(this.mainFolder.getAbsolutePath(), BINDS), Woo.moduleManager.getModules().stream().map(module -> module.getName() + ":" + module.getBind()).collect(Collectors.toCollection(ArrayList::new)));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        try {
            ConfigUtil.saveFile(new File(this.mainFolder.getAbsolutePath(), DRAWN), Woo.moduleManager.getModules().stream().map(module -> module.getName() + ":" + module.isDrawn()).collect(Collectors.toCollection(ArrayList::new)));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        try {
            ConfigUtil.saveFileString(new File(this.mainFolder.getAbsolutePath(), PREFIX), Command.prefix);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        try {
            ConfigUtil.saveFileString(new File(this.mainFolder.getAbsolutePath(), GUIPOS), ClickGUI.startX + ":" + ClickGUI.startY);
            return;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadConfig() {
        if (!this.mainFolder.exists()) {
            return;
        }
        try {
            String[] split;
            for (String s : ConfigUtil.loadFile(new File(this.mainFolder.getAbsolutePath(), ENABLED_MODULES))) {
                try {
                    Woo.moduleManager.getModule(s).toggle();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            for (String s : ConfigUtil.loadFile(new File(this.mainFolder.getAbsolutePath(), SETTINGS))) {
                try {
                    split = s.split(":");
                    this.saveSetting(Woo.settingManager.getSettingByNameAndMod(split[0], Woo.moduleManager.getModule(split[1])), split[2]);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            for (String s : ConfigUtil.loadFile(new File(this.mainFolder.getAbsolutePath(), BINDS))) {
                try {
                    split = s.split(":");
                    Woo.moduleManager.getModule(split[0]).setBind(Integer.parseInt(split[1]));
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            for (String s : ConfigUtil.loadFile(new File(this.mainFolder.getAbsolutePath(), DRAWN))) {
                try {
                    split = s.split(":");
                    Woo.moduleManager.getModule(split[0]).setDrawn(Boolean.parseBoolean(split[1]));
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            for (String s : ConfigUtil.loadFile(new File(this.mainFolder.getAbsolutePath(), PREFIX))) {
                try {
                    split = s.split(" ");
                    Command.setPrefix(split[0]);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            for (String s : ConfigUtil.loadFile(new File(this.mainFolder.getAbsolutePath(), FRIEND))) {
                try {
                    split = s.split(" ");
                    Woo.friendManager.addFriend(split[0]);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Iterator<String> iterator = ConfigUtil.loadFile(new File(this.mainFolder.getAbsolutePath(), GUIPOS)).iterator();
            while (iterator.hasNext()) {
                String s;
                s = iterator.next();
                try {
                    split = s.split(":");
                    ClickGUI.startX = Integer.parseInt(split[0]);
                    ClickGUI.startY = Integer.parseInt(split[1]);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<String> getSettings() {
        ArrayList<String> content = new ArrayList<String>();
        Iterator<Setting> iterator = Woo.settingManager.getSettings().iterator();
        while (iterator.hasNext()) {
            Setting setting = iterator.next();
            switch (setting.getType()) {
                case B: {
                    content.add(String.format("%s:%s:%s", setting.getName(), ((Setting.b)setting).getParent().getName(), ((Setting.b)setting).getValue()));
                    break;
                }
                case I: {
                    content.add(String.format("%s:%s:%s", setting.getName(), ((Setting.i)setting).getParent().getName(), ((Setting.i)setting).getValue()));
                    break;
                }
                case D: {
                    content.add(String.format("%s:%s:%s", setting.getName(), ((Setting.d)setting).getParent().getName(), ((Setting.d)setting).getValue()));
                    break;
                }
                case M: {
                    content.add(String.format("%s:%s:%s", setting.getName(), ((Setting.mode)setting).getParent().getName(), ((Setting.mode)setting).getValue()));
                    break;
                }
            }
        }
        return content;
    }

    private void saveSetting(Setting setting, String value) {
        switch (setting.getType()) {
            case B: {
                ((Setting.b)setting).setValue(Boolean.parseBoolean(value));
                return;
            }
            case I: {
                ((Setting.i)setting).setValue(Integer.parseInt(value));
                return;
            }
            case D: {
                ((Setting.d)setting).setValue(Double.parseDouble(value));
                return;
            }
            case M: {
                ((Setting.mode)setting).setValue(value);
                return;
            }
        }
    }

    public static void saveFile(File file, ArrayList<String> content) throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter(file));
        Iterator<String> iterator = content.iterator();
        while (true) {
            if (!iterator.hasNext()) {
                out.close();
                return;
            }
            String s = iterator.next();
            out.write(s);
            out.write("\r\n");
        }
    }

    public static void saveFileString(File file, String content) throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter(file));
        out.write(content);
        out.write("\r\n");
        out.close();
    }

    public static ArrayList<String> loadFile(File file) throws IOException {
        ArrayList<String> content = new ArrayList<String>();
        FileInputStream stream = new FileInputStream(file.getAbsolutePath());
        DataInputStream in = new DataInputStream(stream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        while (true) {
            String line;
            if ((line = br.readLine()) == null) {
                br.close();
                return content;
            }
            content.add(line);
        }
    }
}

