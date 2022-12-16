package ws.billy.FactionShield.Shield.Listeners;

import com.massivecraft.factions.*;
import com.olliez4.space.energy.Machine;
import com.olliez4.space.energy.machines.absorbers.ElectronBeltGenerator;
import com.olliez4.space.events.SpaceEntityDeathEvent;
import com.olliez4.space.gui.items.SpaceStack;
import com.olliez4.space.main.SpaceAPI;
import com.olliez4.space.managers.PlaceableManager;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import ws.billy.FactionShield.Configuration.Messages.Messages;
import ws.billy.FactionShield.FactionShield;
import ws.billy.FactionShield.Shield.NewShield;

import java.util.Arrays;
import java.util.Optional;

public class SpaceUtil implements Listener {

	public final SpaceAPI spaceAPI = new SpaceAPI();
	private final String placeMessage = Messages.getCustomMessage("placed_shield", "&eYou have placed a shield! &cEnemy factions will be punished!");
	private final String notInFactionMessage = Messages.getCustomMessage("not_in_faction", "&cYou are not in a faction, join one to be able to place this!");
	private final String notYourLand = Messages.getCustomMessage("not_your_land", "&cYou cannot place this outside of your land!");

	private void removeBlock(final Location location, final Player player) {
		new BukkitRunnable() {
			@Override
			public void run() {
				final Optional<Entity> entity = Arrays.stream(location.getChunk().getEntities()).filter(entity1 -> entity1 instanceof ArmorStand).filter(entity1 -> entity1.getTicksLived() < 5).findFirst();
				if(entity.isEmpty()) {return;}
				spaceAPI.getSpace().getNetworkManager().attemptRemove(player, (ArmorStand) entity.get());
			}
		}.runTaskLater(FactionShield.getInstance(), 1);
	}

	private void createBlock(BlockPlaceEvent e) {
		new BukkitRunnable() {
			@Override
			public void run() {
				FLocation fLocation = new FLocation(e.getBlock().getLocation());
				Faction faction = Board.getInstance().getFactionAt(fLocation);
				NewShield.scheduleChecker(spaceAPI.getMachineByBlock(e.getBlock()), faction);
			}
		}.runTaskLater(FactionShield.getInstance(), 10);
	}

	@EventHandler
	public void place(final BlockPlaceEvent e) {
		if (spaceAPI.isMachine(e.getItemInHand(), "electronBeltGenerator")) {
			final FPlayer fPlayer = FPlayers.getInstance().getByPlayer(e.getPlayer());
			final Faction faction = Board.getInstance().getFactionAt(new FLocation(e.getBlock().getLocation()));
			if (!fPlayer.hasFaction()) {
				e.getPlayer().sendMessage(notInFactionMessage);
				e.setCancelled(true);
				removeBlock(e.getBlock().getLocation(), e.getPlayer());
				return;
			}
			if (faction != fPlayer.getFaction()) {
				e.setCancelled(true);
				e.getPlayer().sendMessage(notYourLand);
				removeBlock(e.getBlock().getLocation(), fPlayer.getPlayer());
				return;
			}
			createBlock(e);
			e.getPlayer().sendMessage(placeMessage);
			return;
		}
	}

}
