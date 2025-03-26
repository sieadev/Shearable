package siea.dev.shearable.gui;

import dev.siea.uilabs.element.Button;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import siea.dev.shearable.registry.DropState;
import siea.dev.shearable.registry.ShearableRegistry;

import java.util.ArrayList;
import java.util.List;

public class DropStateButton extends Button {
    private final Material material;
    private DropState dropState;

    public DropStateButton(Material material, DropState dropState) {
        super(generateItemStack(material, dropState));
        this.material = material;
        this.dropState = dropState;
    }

    @Override
    public void onButtonPressed(InventoryClickEvent e) {
        dropState = dropState.next();
        ItemStack icon = generateItemStack(material, dropState);
        setItemStack(icon);
        e.setCurrentItem(icon);
        ShearableRegistry.register(material, dropState);
    }

    private static ItemStack generateItemStack(Material material, DropState dropState) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName(generateName(material, dropState));
        itemMeta.setLore(generateLore(dropState));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    private static String generateName(Material material, DropState dropState) {
        StringBuilder name = new StringBuilder();
        switch (dropState) {
            case DEFAULT -> name.append("§2");
            case SHEARABLE -> name.append("§e");
            case DISABLED -> name.append("§4");
        }
        name.append(material.name().replace("_", " "));
        return name.toString();
    }

    private static List<String> generateLore(DropState dropState) {
        List<String> lore = new ArrayList<>();
        lore.add(dropState == DropState.DEFAULT ? "§6> §2§lDefault" : "§2  Default");
        lore.add(dropState == DropState.SHEARABLE ? "§6> §e§lShearable" : "§e  Shearable");
        lore.add(dropState == DropState.DISABLED ? "§6> §4§lDisabled" : "§4  Disabled");
        return lore;
    }
}
