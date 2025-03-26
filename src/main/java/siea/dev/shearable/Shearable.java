package siea.dev.shearable;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;
import siea.dev.shearable.commands.ShearableCommand;
import siea.dev.shearable.commands.ShearableTabCompleter;
import siea.dev.shearable.gui.GuiManager;
import siea.dev.shearable.listener.BlockBreakListener;
import siea.dev.shearable.registry.DropState;
import siea.dev.shearable.registry.ShearableRegistry;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public final class Shearable extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        ShearableRegistry.register(getConfig().getConfigurationSection("plants"));
        getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
        GuiManager guiManager = new GuiManager(this);
        Objects.requireNonNull(getCommand("shearable")).setExecutor(new ShearableCommand(guiManager));
        Objects.requireNonNull(getCommand("shearable")).setTabCompleter(new ShearableTabCompleter());
    }

    @Override
    public void onDisable() {
        ConfigurationSection section = getConfig().createSection("plants");
        Set<String> configKeys = section.getKeys(false);
        Set<String> registryKeys = ShearableRegistry.getRegistry().keySet().stream()
                .map(Material::name)
                .collect(Collectors.toSet());
        if (!configKeys.equals(registryKeys)) {
            for (Map.Entry<Material, DropState> entry : ShearableRegistry.getRegistry().entrySet()) {
                section.set(entry.getKey().name(), entry.getValue().name());
            }
            saveConfig();
        }
    }

    public static void log(String message) {

    }
}
