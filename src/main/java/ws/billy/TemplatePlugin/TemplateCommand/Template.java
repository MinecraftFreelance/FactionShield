package ws.billy.TemplatePlugin.TemplateCommand;

import org.bukkit.command.CommandSender;
import ws.billy.TemplatePlugin.CommandHandler.Objects.Command;
import ws.billy.TemplatePlugin.Configuration.Messages.Messages;
import ws.billy.TemplatePlugin.TemplateCommand.Subcommands.TestSubcommand;

public class Template extends Command {

	/***
	 * Create a new command, with subcommand
	 */
	public Template() {
		super("template", "A template command", 1, null, Messages.getCustomMessage("template_command_permission", "TemplatePlugin.command.template"), 0);
		super.addSubCommand("test", new TestSubcommand());
	}

	@Override
	protected boolean command(final CommandSender sender, final org.bukkit.command.Command command, final String label, final String[] args) {
		commandReply(sender, "This works!", true);
		return true;
	}
}
