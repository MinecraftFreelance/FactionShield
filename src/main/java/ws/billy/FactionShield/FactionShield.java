package ws.billy.FactionShield;

import io.github.classgraph.ClassGraph;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import ws.billy.FactionShield.Configuration.YMLConfiguration;
import ws.billy.FactionShield.Shield.NewShield;
import ws.billy.FactionShield.Shield.Listeners.SpaceUtil;

public class FactionShield extends JavaPlugin {

	private static FactionShield instance;
	private static PluginManager pluginManager;
	private static YMLConfiguration ymlConfiguration;
	private static YMLConfiguration messagesConfiguration;

	public static SpaceUtil getSpaceUtil() {
		return spaceUtil;
	}

	private static SpaceUtil spaceUtil;

	public static YMLConfiguration getMessagesConfiguration() {
		return messagesConfiguration;
	}

	public static YMLConfiguration getConfiguration() {
		return ymlConfiguration;
	}

	public static PluginManager getPluginManager() {
		return pluginManager;
	}

	public static void log(final String log) {
		Bukkit.getLogger().info("[" + getPluginName() + "] " + log);
	}

	public static String getPluginName() {
		return getInstance().getClass().getSimpleName();
	}

	public static FactionShield getInstance() {
		return instance;
	}

	@Override
	public void onLoad() {
		instance = this;
		enableReflection(getPluginName());
	}

	/**
	 * Lets a Plugin "enable" reflection, by scanning all classes inside it, enabling the use of the ClassGraph reflection API by Core and itself. <br>
	 * <b> It is recommended that all plugins call this in their onLoad() function. </b>
	 */
	public static void enableReflection(final String packageSuffix) {
		new ClassGraph().acceptPackages("ws.billy." + packageSuffix).enableClassInfo().scan();
	}

	@Override
	public void onEnable() {
		instance = this;
		pluginManager = Bukkit.getPluginManager();

		log("Loading plugin configuration");
		ymlConfiguration = new YMLConfiguration("config", this);
		messagesConfiguration = new YMLConfiguration("messages", this);

		if (ymlConfiguration.getKeys().size() == 0) {
			getConfiguration().getConfigField("escape_time", 20);
		}

		// command registers
		log("Registering events");
		spaceUtil = new SpaceUtil();
		getPluginManager().registerEvents(spaceUtil, this);

		new BukkitRunnable() {
			@Override
			public void run() {
				log("Enabling shields");
				log("Now monitoring " + NewShield.start() + " shields");
			}
		}.runTaskLater(this, 1);

	}

}
