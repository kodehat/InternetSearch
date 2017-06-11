package de.codehat.internetsearch.commands;

import de.codehat.internetsearch.InternetSearch;
import de.codehat.internetsearch.util.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;

/**
 * A manager that handles the plugin's commands and executes them.
 *
 * @author CodeHat
 */
public class CommandManager implements CommandExecutor {

    /**
     * The plugin instance.
     */
    private InternetSearch plugin;

    /**
     * A map containing all available commands of the plugin.
     */
    private Map<String, AbstractCommand> commandDatabase = new HashMap<>();

    /**
     * Creates a new command manager.
     *
     * @param plugin The plugin instance.
     */
    public CommandManager(InternetSearch plugin) {
        this.plugin = plugin;
    }

    /**
     * Registers the given command in the command database.
     *
     * @param name Name of the command e.g. /cmd "name"
     * @param command The corresponding command object.
     * @return true if command has been successfully added, false if command already exists in database.
     */
    public boolean registerCommand(String name, AbstractCommand command) {
        if (!this.existsCommand(name)) {
            this.commandDatabase.put(name.toLowerCase(), command);
            return true;
        }
        return false;
    }

    /**
     * Checks if the given command name already exists in command database.
     *
     * @param name Name of the command e.g. /cmd "name"
     * @return true if command already exists, false if command doesn't exist in database.
     */
    public boolean existsCommand(String name) {
        return this.commandDatabase.containsKey(name);
    }

    /**
     * Returns the command object - if available - to the given command name.
     *
     * @param name Name of the command e.g. /cmd "name"
     * @return The command object if available or {@code null} if not found.
     */
    public AbstractCommand getCommand(String name) {
        return this.commandDatabase.get(name);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            this.getCommand("").onCommand(sender, cmd, label, args);
        } else if (this.existsCommand(args[0])) {
            this.getCommand(args[0]).onCommand(sender, cmd, label, args);
        } else {
            Message.sendWithLogo(sender, this.plugin.getLangVal("cmd_not_exists"));
        }
        return true;
    }



}
