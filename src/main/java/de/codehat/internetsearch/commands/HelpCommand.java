package de.codehat.internetsearch.commands;

import de.codehat.internetsearch.InternetSearch;
import de.codehat.internetsearch.util.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * The plugin's help command.
 *
 * @author CodeHat
 */
public class HelpCommand extends AbstractCommand {

    /**
     * Creates a new help command.
     *
     * @param plugin The plugin instance.
     */
    public HelpCommand(InternetSearch plugin) {
        super(plugin);
    }

    @Override
    public void onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("internetsearch.help")) {
            Message.sendWithLogo(sender, this.getPlugin().getLangVal("no_permission"));
        } else {
            Message.send(sender, "&7+------------------" + Message.pluginTag.trim() + "&7------------------+");
            Message.send(sender, "&3/search [query] &8--- " + this.getPlugin().getLangVal("cmd_help_search"));
            Message.send(sender, "&3/ints reload &8--- " + this.getPlugin().getLangVal("cmd_help_reload"));
            Message.send(sender, "&3/ints help &8--- " + this.getPlugin().getLangVal("cmd_help_help"));
            Message.send(sender, "&7+-------------------------------------------------+");
        }
    }
}
