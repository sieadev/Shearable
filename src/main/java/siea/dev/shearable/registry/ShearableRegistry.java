package siea.dev.shearable.registry;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

public class ShearableRegistry {
    private static final Map<Material, DropState> registry = new HashMap<>();
    private static final Logger logger = Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("Shearable")).getLogger();

    public static void register(ConfigurationSection section) {
        if (section == null) { return; }
        for (String key : section.getKeys(false)) {
            Material material = Material.getMaterial(key);
            if (material == null) {
                logger.warning("Unable to find Material: " + key);
                continue;
            }
            DropState dropState = DropState.valueOf(section.getString(key));
            registry.put(material, dropState);
            logger.info("Registered Material: " + material + " as " + dropState);
        }
    }

    public static void register(Material material, DropState dropState) {
        registry.put(material, dropState);
    }

    public static void unregister(Material type) {
        registry.remove(type);
    }

    public static DropState getState(Material material) {
        return registry.getOrDefault(material, DropState.DEFAULT);
    }

    public static Map<Material, DropState> getRegistry() {
        return registry;
    }

    public static int getRegistrySize() {
        return registry.size();
    }
}
