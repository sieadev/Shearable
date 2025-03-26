package siea.dev.shearable.gui;

import dev.siea.uilabs.UILabs;
import dev.siea.uilabs.element.ItemElement;
import dev.siea.uilabs.frame.Border;
import dev.siea.uilabs.gui.DefaultInventoryGui;
import dev.siea.uilabs.gui.InventoryGui;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import siea.dev.shearable.Shearable;
import siea.dev.shearable.registry.DropState;
import siea.dev.shearable.registry.ShearableRegistry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GuiManager {
    private final UILabs uilabs;
    private DefaultInventoryGui dropSettings;

    public GuiManager(Shearable shearable) {
        uilabs = new UILabs(shearable);
        reload();
    }

    public InventoryGui getDropSettings() {
        return dropSettings;
    }

    public void reload() {
        List<HumanEntity> viewers = new ArrayList<>();
        if (dropSettings != null) {
            viewers.addAll(dropSettings.getViewers());
            dropSettings.closeAll();
        }
        dropSettings = uilabs.create("Shearable - Drop Settings", (int) Math.ceil(ShearableRegistry.getRegistrySize() / 7.0) + 2 , 9);
        dropSettings.removeAll();
        dropSettings.setBorder(Border.of(Material.BLACK_STAINED_GLASS_PANE));
        for (Map.Entry<Material, DropState> entry : ShearableRegistry.getRegistry().entrySet()) {
            DropStateButton button = new DropStateButton(entry.getKey(), entry.getValue());
            dropSettings.addElement(button);
        }
        dropSettings.addElement(new ItemElement(
                Material.BARRIER,
                "§eSomething missing?",
                Collections.singletonList("§eUse §6/shearable add §eto add items!")
        ), ((int) Math.ceil(ShearableRegistry.getRegistrySize() / 7.0) + 2) * 9 - 5);
        for (HumanEntity viewer : viewers) {
            dropSettings.view((org.bukkit.entity.Player) viewer);
        }
    }
}
