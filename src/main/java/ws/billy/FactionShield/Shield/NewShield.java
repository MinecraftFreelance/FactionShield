package ws.billy.FactionShield.Shield;

import com.massivecraft.factions.*;
import com.olliez4.space.energy.Machine;
import com.olliez4.space.energy.machines.absorbers.ElectronBeltGenerator;
import com.olliez4.space.energy.machines.network.EnergyNetwork;
import com.olliez4.space.energy.machines.network.NetworkManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import ws.billy.FactionShield.Configuration.Messages.Messages;
import ws.billy.FactionShield.FactionShield;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewShield {

	private static final String deathMessage = Messages.getCustomMessage("death_message", "&cWell, you were warned, don't come back!");

	private static final List<Player> beingKilled = new ArrayList<>();

	public static int start() {
		NetworkManager manager = FactionShield.getSpaceUtil().spaceAPI.getNetworkManager();
		int loops = 0;
		for(EnergyNetwork network : manager.getNetworks()) {
			for(Machine machine : network.getMachines()) {
				if(machine.getName().equals("Electron Belt Generator")) {
					System.out.println("electron belt found");
					FLocation fLocation = new FLocation(machine.getLocation());
					Faction faction = Board.getInstance().getFactionAt(fLocation);
					if(faction == null || faction.isWilderness()) {
						continue;
					}
					loops++;
					scheduleChecker(machine, faction);
				}
			}
		}
		return loops;
	}

	private static void kickPersonOut(final FPlayer player, final int time) {
		if (!player.isInEnemyTerritory() || time < 0) {
			beingKilled.remove(player.getPlayer());
			return;
		}
		if(!beingKilled.contains(player.getPlayer())) {
			beingKilled.add(player.getPlayer());
		}
		final String title =  Messages.getCustomMessage("title.title", "&c&l{{remaining_time}}", time);
		final String subtitle = Messages.getCustomMessage("title.subtitle", "&7Exit this land, or die!");
		player.getPlayer().sendTitle(title, subtitle, 0, 40, 5);
		if (time == 0) {
			killPlayer(player);
		}
	}

	public static void kickPersonOut(final Player player, Faction faction) {
		final int[] escapeTime = {FactionShield.getConfiguration().getConfigField("escape_time")};
		String chatMessage = Messages.getCustomMessage("warning_chat_message", "&c{{faction}} has a shield enabled! &eYou have {{time}} to escape!", faction, escapeTime[0]);
		player.sendMessage(chatMessage);
		beingKilled.add(player);
		new BukkitRunnable() {
			@Override
			public void run() {
				if(!beingKilled.contains(player)) {this.cancel(); return;}
				kickPersonOut(FPlayers.getInstance().getByPlayer(player), escapeTime[0]);
				escapeTime[0]--;
			}
		}.runTaskTimer(FactionShield.getInstance(), 20, 20);
	}

	public static void scheduleChecker(Machine machine, Faction faction) {
		new BukkitRunnable() {
			@Override
			public void run() {
				for(Entity entity : getEnemyPlayersInLand(faction)) {
					if (FactionShield.getSpaceUtil().spaceAPI.getMachineByBlock(machine.getLocation().getBlock()) == null) {
						this.cancel();
						final String deleteMessage = Messages.getCustomMessage("delete_message", "&7Your faction shield at &e{{location}} &7has been destroyed!", machine.getLocation());
						faction.getFPlayers().stream().filter(FPlayer::isOnline).forEach(player -> player.sendMessage(deleteMessage));
						return;
					}
					if(!machine.isActive()) {
						return;
					}
					if(machine.getEnergy() == 0) {
						return;
					}
					if(!beingKilled.contains((Player) entity)) {
						kickPersonOut((Player)entity, faction);
					}
				}
			}
		}.runTaskTimer(FactionShield.getInstance(), 20, 20);
	}

	private static void killPlayer(final FPlayer player) {
		player.getPlayer().setHealth(0);
		player.getPlayer().sendMessage(deathMessage);
		beingKilled.remove(player.getPlayer());
	}

	private static List<Entity> getEnemyPlayersInLand(final Faction faction) {
		final List<Entity> players = new ArrayList<>();
		for(final FLocation land : faction.getAllClaims()) {
			players.addAll(Arrays.stream(land.getChunk().getEntities())
					.filter(entity -> entity instanceof Player)
					.filter(entity -> FPlayers.getInstance().getByPlayer((Player)entity).isInEnemyTerritory()).toList());
		}
		return players;
	}

}
