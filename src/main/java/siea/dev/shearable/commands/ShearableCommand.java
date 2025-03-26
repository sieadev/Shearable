package siea.dev.shearable.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import siea.dev.shearable.gui.GuiManager;
import siea.dev.shearable.registry.DropState;
import siea.dev.shearable.registry.ShearableRegistry;

public class ShearableCommand implements CommandExecutor {
    private final GuiManager guiManager;

    public ShearableCommand(GuiManager guiManager) {
        this.guiManager = guiManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        if (args.length == 0) {
            guiManager.getDropSettings().view(player);
            return true;
        }

        String subCommand = args[0];

        switch (args[0]) {
            case "add":
                ItemStack item = player.getInventory().getItemInMainHand();
                if (item.getType().isAir()) {
                    player.sendMessage("§cYou must be holding an item to add it to the shearable registry.");
                    return true;
                }
                ShearableRegistry.register(item.getType(), DropState.SHEARABLE);
                player.sendMessage("§eAdded " + item.getType().name() + " to the shearable registry!");
                guiManager.reload();
                return true;
            case "remove":
                ItemStack itemToRemove = player.getInventory().getItemInMainHand();
                if (itemToRemove.getType().isAir()) {
                    player.sendMessage("§cYou must be holding an item to remove it from the shearable registry.");
                    return true;
                }
                ShearableRegistry.unregister(itemToRemove.getType());
                player.sendMessage("§eRemoved " + itemToRemove.getType().name() + " from the shearable registry!");
                guiManager.reload();
                return true;
            default:
                player.sendMessage("§cUnknown subcommand: " + subCommand);
        }

        return true;
    }
}
