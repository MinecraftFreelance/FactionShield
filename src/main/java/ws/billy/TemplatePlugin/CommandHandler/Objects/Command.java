package ws.billy.TemplatePlugin.CommandHandler.Objects;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import ws.billy.TemplatePlugin.Utility.Cooldown.CooldownManager;
import ws.billy.TemplatePlugin.Configuration.Messages.Messages;

import java.util.*;
import java.util.concurrent.TimeUnit;

public abstract class Command implements CommandExecutor, Listener, TabExecutor, iCommand {

	private static final List<Command> commands = new ArrayList<>();
	private final String _name; // name of command
	private final int _arguments; // minimum amount of arguments
	private final String _usage; // usage
	private final String _permission; // permission to use command
	private final CooldownManager _cooldownManager = new CooldownManager();
	private final int _cooldown; // amount of seconds for cooldown
	private final Map<String, SubCommand> _subCommands = new HashMap<>(); // subcommand list
	private String _description; // description of command

	public Command() {
		this._name = getClass().getSimpleName().toLowerCase();
		this._description = "A command";
		this._arguments = 0;
		this._usage = null;
		this._permission = Messages.getCustomMessage(_name + "_command_permission", "TemplatePlugin.command." + _name);
		this._cooldown = 0;
		addCommand(this);
	}

	/***
	 * Add a command to the plugin to keep track of
	 * @param command the command instance to add
	 */
	public static void addCommand(final Command command) {
		commands.add(command);
	}

	/***
	 * Create a new command
	 * @param name the name of the command
	 * @param description the description of the command
	 * @param arguments the argument amount
	 * @param usage the usage of the command
	 * @param permission the permission required
	 * @param cooldown the cooldown, in seconds.
	 */
	public Command(final String name, final String description, final int arguments, final String usage, final String permission, final int cooldown) {
		this._name = name;
		this._description = description;
		this._arguments = arguments;
		this._usage = usage;
		this._permission = permission;
		this._cooldown = cooldown;
		addCommand(this);
	}

	/***
	 * Get all commands on this plugin
	 * @return a list of all the commands
	 */
	public static List<Command> getCommands() {
		return commands;
	}

	public void addSubCommand(final String name, final SubCommand subCommand) {
		_subCommands.put(name, subCommand);
	}

	public String getName() {
		return _name;
	}

	public String getDescription() {
		return _description;
	}

	public void setDescription(final String description) {
		this._description = _description;
	}

	@Override
	public List<String> onTabComplete(final CommandSender sender, final org.bukkit.command.Command command, final String alias, final String[] args) {
		final List<String> returnlist = new ArrayList<>();
		returnlist.add("");
		return returnlist;
	}

	/***
	 * Override the default bukkit plugin command handler and run our own command
	 * @param sender the player/console sending the command
	 * @param command the command
	 * @param label the label
	 * @param args the arguments used
	 * @return always returns true
	 */
	@Override
	public boolean onCommand(final CommandSender sender, final org.bukkit.command.Command command, final String label, final String[] args) {

		// Permission check
		if (sender instanceof Player && ! checkPermission((Player) sender)) {
			commandReply(sender, Messages.getPermissionsErrorMessage(), false);
			return true;
		}

		if (args.length < getArguments()) {
			if (getUsage() == null) {
				final StringBuilder stringBuilder = new StringBuilder();
				commandReply(sender, " \n§6§lCommand usage:", false);
				_subCommands.forEach((s, subCommand) -> stringBuilder.append("§e/").append(command.getName()).append(" ").append(s).append(subCommand.getUsage() != null ? subCommand.getUsage().toLowerCase() : "").append("§8 - §7").append(subCommand.getDescription()).append("\n"));
				commandReply(sender, stringBuilder.toString(), false);
				commandReply(sender, " ", false);
			} else {
				commandReply(sender, "§cInvalid Usage! §eUsage: §7/" + command.getName() + " " + getUsage().toLowerCase(), true);
			}
			return true;
		}

		if (sender instanceof Player && getCooldown() != 0) {
			final long timeLeft = System.currentTimeMillis() - _cooldownManager.getCooldown(((Player) sender).getPlayer().getUniqueId());
			if (TimeUnit.MILLISECONDS.toSeconds(timeLeft) < this.getCooldown()) {
				final long cooldownleft = this.getCooldown() - TimeUnit.MILLISECONDS.toSeconds(timeLeft);
				commandReply(sender, "§cPlease do not spam this command! §7(" + cooldownleft + "s remaining)", true);
				return true;
			}
			_cooldownManager.setCooldown(((Player) sender).getPlayer().getUniqueId(), System.currentTimeMillis());
		}

		// Execute command
		try {
			if (_subCommands.size() != 0) {
				for (final String arg : args) {
					final SubCommand subCommand = _subCommands.get(arg.toLowerCase());
					if (subCommand == null) {
						continue;
					}
					subCommand.execute(sender, command, label, Arrays.stream(args).skip(1).toArray(String[]::new));
					return true;
				}
			}
			return command(sender, command, label, args);
		} catch (final Exception e) {
			sender.sendMessage("§c§lAn error occurred whilst running §e/" + command.getName());
			sender.sendMessage("§7If this issue continues, please contact an administrator!");
			e.printStackTrace();
		}

		return true;

	}

	/***
	 * Check the person running has permission
	 * @param player the player running
	 * @return if they have permission
	 */
	protected Boolean checkPermission(final Player player) {
		return player.hasPermission(getPermission());
	}

	public int getArguments() {
		return _arguments;
	}

	public String getUsage() {
		return _usage;
	}

	public int getCooldown() {
		return _cooldown;
	}

	protected abstract boolean command(final CommandSender sender, final org.bukkit.command.Command command, final String label, final String[] args);

	public String getPermission() {
		return _permission;
	}

}
