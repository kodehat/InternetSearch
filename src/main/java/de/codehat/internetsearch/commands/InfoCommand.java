package de.codehat.internetsearch.commands;

import de.codehat.internetsearch.InternetSearch;
import de.codehat.internetsearch.util.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * The plugin's info command.
 *
 * @author CodeHat
 */
public class InfoCommand extends AbstractCommand {

    /**
     * Creates a new info command.
     *
     * @param plugin The plugin instance.
     */
    public InfoCommand(InternetSearch plugin) {
        super(plugin);
    }

    @Override
    public void onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("internetsearch.info")) {
            Message.sendWithLogo(sender, this.getPlugin().getLangVal("no_permission"));
        } else {
            Message.send(sender, "&7+------------------" + Message.pluginTag.trim() + "&7------------------+");
            Message.send(sender, String.format(this.getPlugin().getLangVal("cmd_info_version"),
                    this.getPlugin().getDescription().getVersion()));
            Message.send(sender, String.format(this.getPlugin().getLangVal("cmd_info_author"),
                    this.getPlugin().getDescription().getAuthors().get(0)));
            Message.send(sender, this.getPlugin().getLangVal("cmd_info_license"));
            Message.send(sender, "&7+-------------------------------------------------+");
        }
    }
}
