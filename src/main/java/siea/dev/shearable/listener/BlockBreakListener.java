package siea.dev.shearable.listener;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import siea.dev.shearable.registry.DropState;
import siea.dev.shearable.registry.ShearableRegistry;

public class BlockBreakListener implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        DropState dropState = ShearableRegistry.getState(event.getBlock().getType());
        switch (dropState) {
            case DEFAULT:
                break;
            case SHEARABLE:
                if (event.getPlayer().getInventory().getItemInMainHand().getType() != Material.SHEARS) {
                    event.setDropItems(false);
                }
                break;
            case DISABLED:
                event.setDropItems(false);
                break;
        }
    }
}
