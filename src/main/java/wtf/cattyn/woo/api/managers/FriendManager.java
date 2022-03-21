//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\maywr\Documents\remapping\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectOpenHashSet
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 */
package wtf.cattyn.woo.api.managers;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.Set;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class FriendManager {
    private static Set<String> friends = new ObjectOpenHashSet();
    private File directory;

    public void init(File directory) {
        this.directory = directory;
        if (!directory.exists()) {
            try {
                directory.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.load();
    }

    public void removeFriend(Entity entity) {
        if (!(entity instanceof EntityPlayer)) return;
        friends.remove(entity.getName());
    }

    public void removeFriend(String name) {
        friends.remove(name);
    }

    public void addFriend(Entity entity) {
        if (!(entity instanceof EntityPlayer)) return;
        friends.add(entity.getName());
    }

    public void addFriend(String name) {
        friends.add(name);
    }

    public boolean isFriend(Entity entity) {
        if (!(entity instanceof EntityPlayer)) return false;
        return friends.contains(entity.getName());
    }

    public boolean isFriend(String name) {
        return friends.contains(name);
    }

    public void save() {
        if (!this.directory.exists()) return;
        try (FileWriter writer = new FileWriter(this.directory);){
            writer.write(new GsonBuilder().setPrettyPrinting().create().toJson(friends));
            return;
        }
        catch (IOException e) {
            this.directory.delete();
        }
    }

    public void load() {
        if (!this.directory.exists()) {
            return;
        }
        try (FileReader inFile = new FileReader(this.directory);){
            friends = (Set)new GsonBuilder().setPrettyPrinting().create().fromJson((Reader)inFile, new TypeToken<ObjectOpenHashSet<String>>(){}.getType());
            if (friends != null) return;
            friends = new ObjectOpenHashSet();
            return;
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

