package ws.billy.FactionShield.Shield;

import com.massivecraft.factions.*;
import com.olliez4.space.energy.Machine;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.json.JSONArray;
import org.json.JSONObject;
import ws.billy.FactionShield.Backups.BackupManager;
import ws.billy.FactionShield.Configuration.Messages.Messages;
import ws.billy.FactionShield.FactionShield;

import javax.crypto.Mac;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Shield {

	private static final List<Shield> shieldList = new ArrayList<>();
	private final Faction _faction;
	private final UUID _uuid;
	private final Location _location;
	private final Machine _machine;

	public boolean isEnabled() {
		return _enabled;
	}

	public void setEnabled(final boolean enabled) {
		this._enabled = enabled;
	}

	private boolean _enabled = true;

	public static List<Shield> getShieldList() { return shieldList; }

	private static final String deathMessage = Messages.getCustomMessage("death_message", "&cWell, you were warned, don't come back!");

	private static final List<Player> beingKilled = new ArrayList<>();

	public Shield(final Faction faction, final Location location, final Machine machine) {
		this._faction = faction;
		this._location = location;
		this._machine = machine;
		this._uuid = UUID.randomUUID();
		shieldList.add(this);
	}

	public Shield(final JSONObject jsonObject) {
		this._faction = Factions.getInstance().getFactionById(jsonObject.getString("faction"));
		this._location = new Location(Bukkit.getWorld(jsonObject.getString("world")), jsonObject.getDouble("x"), jsonObject.getDouble("y"), jsonObject.getDouble("z"));
		this._uuid = UUID.fromString(jsonObject.getString("uuid"));
		this._machine = FactionShield.getSpaceUtil().spaceAPI.getMachineByBlock(_location.getBlock());
		if(_machine == null) {
			throw new RuntimeException();
		}
		shieldList.add(this);
	}

	private void kickPersonOut(final FPlayer player, final int time) {
		if(!isEnabled()) {return;}
		if(!player.isInEnemyTerritory()) {
			beingKilled.remove(player.getPlayer());
			System.out.println("f");
			return;
		}
		if(!beingKilled.contains(player.getPlayer())) {beingKilled.add(player.getPlayer());}
		System.out.println("g");
		final String title =  Messages.getCustomMessage("title.title", "&c&l{{remaining_time}}", time);
		final String subtitle = Messages.getCustomMessage("title.subtitle", "&7Exit this land, or die!");
		player.getPlayer().sendTitle(title, subtitle, 0, 40, 5);
		if(time == 0) {
			killPlayer(player);
		}
	}

	public void kickPersonOut(final Player player) {
		if(!isEnabled()) {return;}
		final int[] escapeTime = {FactionShield.getConfiguration().getConfigField("escape_time")};
		String chatMessage = Messages.getCustomMessage("warning_chat_message", "&c{{faction}} has a shield enabled! &eYou have {{time}} to escape!", _faction, escapeTime[0]);
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

	public void scheduleChecker() {
		new BukkitRunnable() {
			@Override
			public void run() {
				for(Entity entity : getEnemyPlayersInLand()) {
					System.out.println("a");
					if(_machine == null) { delete(); this.cancel(); return;}
					System.out.println("b");
					if(!_machine.isActive()) {return;}
					System.out.println("c");
					if(_machine.getEnergy() == 0) {return;}
					System.out.println("d");
					if(!isEnabled()) {this.cancel(); return;}
					System.out.println("e");
					if(!beingKilled.contains((Player) entity)) {
						kickPersonOut((Player)entity);
					}
				}
			}
		}.runTaskTimer(FactionShield.getInstance(), 100, 100);
		new BukkitRunnable() {
			@Override
			public void run() {
				if(FactionShield.getSpaceUtil().spaceAPI.getMachineByBlock(getLocation().getBlock()) == null) {
					delete();
					this.cancel();
				}
			}
		}.runTaskTimer(FactionShield.getInstance(), 20, 20);
	}

	private void killPlayer(final FPlayer player) {
		player.getPlayer().setHealth(0);
		player.getPlayer().sendMessage(deathMessage);
		beingKilled.remove(player.getPlayer());
	}

	public static Shield fromJsonArray(final JSONArray array) {
		for (final Object object : array) {
			final org.json.JSONObject objectInfo = new org.json.JSONObject(object.toString());
			try {
				return new Shield(objectInfo);
			}catch (Exception ignored) {
				FactionShield.error("Looks like " + objectInfo.getString("uuid") + " was broken!");
				BackupManager.removeBackup(UUID.fromString(objectInfo.getString("uuid")));
			}
		}
		return null;
	}

	public JSONObject toJson() {
		final JSONObject jsonObject = new JSONObject();
		jsonObject.put("world", getLocation().getWorld().getName());
		jsonObject.put("x", getLocation().getX());
		jsonObject.put("y", getLocation().getY());
		jsonObject.put("z", getLocation().getZ());
		jsonObject.put("faction", getFfaction().getId());
		jsonObject.put("uuid", getUuid().toString());
		return jsonObject;
	}

	private List<Entity> getEnemyPlayersInLand() {
		final List<Entity> players = new ArrayList<>();
		for(final FLocation land : _faction.getAllClaims()) {
			players.addAll(Arrays.stream(land.getChunk().getEntities())
					.filter(entity -> entity instanceof Player)
					.filter(entity -> FPlayers.getInstance().getByPlayer((Player)entity).isInEnemyTerritory()).toList());
		}
		return players;
	}

	public Location getLocation() {
		return _location;
	}

	public Faction getFfaction() {
		return _faction;
	}

	public UUID getUuid() {
		return _uuid;
	}

	public void delete() {
		if(!isEnabled()) {return;}
		FactionShield.log("Deleting shield " + getUuid());
		shieldList.remove(this);
		BackupManager.removeBackup(getUuid());
		setEnabled(false);
		final String deleteMessage = Messages.getCustomMessage("delete_message", "&7Your faction shield at &e{{location}} &7has been destroyed!", _location);
		_faction.getFPlayers().stream().filter(FPlayer::isOnline).forEach(player -> player.sendMessage(deleteMessage));
	}

}
