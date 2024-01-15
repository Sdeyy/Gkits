package dev.hardling.us;

import dev.hardling.us.Listeners.Editor.EditorListener;
import dev.hardling.us.Listeners.Editor.KitListener;
import dev.hardling.us.Listeners.Editor.SlotListener;
import dev.hardling.us.Listeners.GkitListener;
import dev.hardling.us.Listeners.PlayerListener;
import dev.hardling.us.Utils.CC;
import dev.hardling.us.Utils.command.CommandFramework;
import dev.hardling.us.Utils.command.CommandManager;
import dev.hardling.us.Utils.provider.files.ConfigCreator;
import dev.hardling.us.Utils.provider.files.License;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


@Getter
public class ReactGkits extends JavaPlugin {

    @Getter
    private static ReactGkits plugin;
    @Getter
    private static ReactGkits inst;
    @Setter
    private ConfigCreator licenseFile;
    @Setter
    private ConfigCreator langFile;
    @Setter
    private ConfigCreator gkitFile;
    @Setter
    private ConfigCreator dataFile;
    private String prefix = CC.translate("&8[&3&lReactGkits&8] &f");
    private CommandFramework commandFramework;
    private CommandManager commandManager;

    @Override
    public void onEnable() {
        inst = this;
        plugin = this;
        try {
            this.licenseFile = new ConfigCreator("license.yml");
            this.langFile = new ConfigCreator("lang.yml");
            this.gkitFile = new ConfigCreator("gkit.yml");
            this.dataFile = new ConfigCreator("data.yml");
        } catch (RuntimeException e) {
            CC.log(CC.LINERROR);
            CC.log(this.prefix + "&cYMLs did not load, please restart the server or contact support. ");
            CC.log(CC.LINERROR);
            Bukkit.getPluginManager().disablePlugin(this);
            Bukkit.getScheduler().cancelTasks(this);
        }
        
            String a1 = this.licenseFile.getString("LICENSE");
            String a2 = "http://154.53.52.177:8042/api/client";
            String a3 = "1ff58e638b9f78ebeb01093d3c1e0g0a089073ad88";
            while (!new License(this, a2, a1, a3).verify()){
            getServer().getPluginManager().disablePlugin(this);
            getServer().getScheduler().cancelTasks(this);
            CC.log(" &8| &cYour license is invalid.");
            CC.log(" &8| &cContact Support on &bhttps://discord.hardling.com");
            return;        
        }
                    this.loadAll();
                    CC.log(" &8| &aYour License is valid.");
                    CC.log(" &8| &aLoading Plugin.");
                    CC.log("");
                    CC.log("            &3&lReactGkits &8- [&aHardling Development&8]");
                    CC.log("");
                    CC.log(" &8| &bAuthor&8: &f Hardling Development");
                    CC.log(" &8| &bVersion&8: &f 1.0.2-SNAPSHOT");
                    CC.log(" &8| &bDiscord&8: &fhttps://discord.hardling.com");
                    CC.log("");
    }

    @Override
    public void onDisable() {
        CC.log(this.prefix + "&cDisabled ReactGkits");
    }

    private void loadCommands() {
        commandFramework = new CommandFramework(this);
        commandManager = new CommandManager();
        commandManager.loadCommands();
        commandFramework.loadCommandsInFile();
    }

    private void loadDepend() {
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            CC.log(this.prefix + "&aPlaceholderAPI Expansion Successfully");
        } else {
            CC.log("&d=====&5============&8[&bPlaceholderAPI&8]&d============&5=====");
            CC.log("&cYou have no support");
            CC.log("&cPlease put the PlaceholderAPI");
            CC.log("&d=====&5============&8[&bPlaceholderAPI&8]&d============&5=====");
            Bukkit.getPluginManager().disablePlugin(ReactGkits.getPlugin());
            Bukkit.getScheduler().cancelTasks(ReactGkits.getPlugin());
        }
    }

    private void loadListeners() {
        PluginManager manager = ReactGkits.getInst().getServer().getPluginManager();
        manager.registerEvents(new GkitListener(), ReactGkits.getInst());
        manager.registerEvents(new PlayerListener(), ReactGkits.getInst());
        manager.registerEvents(new EditorListener(), ReactGkits.getInst());
        manager.registerEvents(new KitListener(), ReactGkits.getInst());
        manager.registerEvents(new SlotListener(), ReactGkits.getInst());
        //manager.registerEvents(new ViewInventoryListener(), ReactGkits.getInst());
        //manager.registerEvents(new PermissionListener(), ReactGkits.getInst());
    }

    public void loadAll() {
        loadDepend();
        loadListeners();
        loadCommands();
    }

    public static ReactGkits get() {
        return ReactGkits.getPlugin(ReactGkits.class);
    }
}