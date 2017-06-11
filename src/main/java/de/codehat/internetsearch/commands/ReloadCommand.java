package de.codehat.internetsearch.commands;

import de.codehat.internetsearch.InternetSearch;
import de.codehat.internetsearch.util.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * The plugin's config reload command.
 *
 * @author CodeHat
 */
public class ReloadCommand extends AbstractCommand {

    /**
     * Creates a new reload command.
     *
     * @param plugin The plugin instance.
     */
    public ReloadCommand(InternetSearch plugin) {
        super(plugin);
    }

    @Override
    public void onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("internetsearch.reload")) {
            Message.sendWithLogo(sender, this.getPlugin().getLangVal("no_permission"));
        } else {
            this.getPlugin().reloadConfig();
            this.getPlugin().setPluginTag();
            Message.sendWithLogo(sender, this.getPlugin().getLangVal("cmd_reload"));
        }
    }
}
