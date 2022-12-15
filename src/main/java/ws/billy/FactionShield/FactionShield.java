package ws.billy.FactionShield;

import io.github.classgraph.ClassGraph;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import ws.billy.FactionShield.CommandHandler.Objects.Command;
import ws.billy.FactionShield.Configuration.YMLConfiguration;

import java.util.List;

public class FactionShield extends JavaPlugin {

	private static FactionShield instance;
	private static PluginManager pluginManager;
	private static YMLConfiguration ymlConfiguration;
	private static YMLConfiguration messagesConfiguration;

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

	public static void error(final String log) {
		Bukkit.getLogger().severe("[" + getPluginName() + "] " + log);
	}

	public static String getPluginName() {
		return getInstance().getClass().getSimpleName();
	}

	public static FactionShield getInstance() {
		return instance;
	}

	/**
	 * Uses reflection to register commands.
	 *
	 * @param plugin        Java Plugin to use for registering the commands.
	 * @param packageSuffix plugin src path after net.thecookiemc. (e.g. for core, 'cookiecore')
	 */
	public static void loadCommands(final JavaPlugin plugin, final String packageSuffix) {
		final List<Class<Command>> commands = new ClassGraph().acceptPackages("ws.billy." + packageSuffix).enableClassInfo().scan().getSubclasses(Command.class).loadClasses(Command.class);

		commands.forEach(command -> { // Try to register each command
			try {
				plugin.getCommand(command.getSimpleName().toLowerCase()).setExecutor(command.newInstance());
				log("Registered command /" + command.getSimpleName().toLowerCase() + " successfully!");
			} catch (InstantiationException | IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		});

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

		// command registers
		log("Loading plugin commands");
		loadCommands(this, getPluginName());

	}

}
