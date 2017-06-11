package de.codehat.internetsearch.commands;

import de.codehat.internetsearch.InternetSearch;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * Represents a command.
 *
 * @author CodeHat
 */
public abstract class AbstractCommand {

    /**
     * The plugin instance.
     */
    private InternetSearch plugin;

    /**
     * Creates a new command.
     *
     * @param plugin The plugin instance.
     */
    public AbstractCommand(InternetSearch plugin) {
        this.plugin = plugin;
    }

    /**
     * Returns the plugin instance.
     *
     * @return The plugin instance.
     */
    public InternetSearch getPlugin() {
        return this.plugin;
    }

    /**
     * The Bukkit {@code onCommand} method.
     * {@link org.bukkit.command.CommandExecutor#onCommand(CommandSender, Command, String, String[])}
     */
    public abstract void onCommand(CommandSender sender, Command cmd, String label, String[] args);

}
