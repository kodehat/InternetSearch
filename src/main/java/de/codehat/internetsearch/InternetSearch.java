package de.codehat.internetsearch;

import de.codehat.internetsearch.commands.*;
import de.codehat.internetsearch.search.engines.DuckDuckGoEngine;
import de.codehat.internetsearch.search.SearchEngine;
import de.codehat.internetsearch.search.engines.SearxEngine;
import de.codehat.internetsearch.util.Message;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * "InternetSearch" plugin main class.
 *
 * @author CodeHat
 */
public class InternetSearch extends JavaPlugin {

    public static Logger logger;

    private final CommandManager commandManager = new CommandManager(this);
    private final Map<String, SearchEngine> searchEngines = new HashMap<>();

    @Override
    public void onDisable() {
        // Log "disable" message
        PluginDescriptionFile pluginDescriptionFile = this.getDescription();
        logger.info(String.format("Version %s by %s disabled.", pluginDescriptionFile.getVersion(),
                pluginDescriptionFile.getAuthors().get(0)));
    }

    @Override
    public void onEnable() {
        logger = this.getLogger();

        // Save default config if not already there
        saveDefaultConfig();
        this.setPluginTag();
        this.registerCommands();
        this.registerSearchEngines();

        // Log "enable" message
        PluginDescriptionFile pluginDescriptionFile = this.getDescription();
        logger.info(String.format("Version %s by %s enabled.", pluginDescriptionFile.getVersion(),
                pluginDescriptionFile.getAuthors().get(0)));
    }

    /**
     * Register the plugin's commands.
     */
    private void registerCommands() {
        // Register all commands
        this.commandManager.registerCommand("", new InfoCommand(this));
        this.commandManager.registerCommand("help", new HelpCommand(this));
        this.commandManager.registerCommand("reload", new ReloadCommand(this));

        // Set all executors
        this.getCommand("ints").setExecutor(this.commandManager);
        this.getCommand("search").setExecutor(new SearchCommand(this));

        // Set tab completer
        this.getCommand("ints").setTabCompleter(new IntsTabCompletion());
    }

    /**
     * Register all available search engines.
     */
    private void registerSearchEngines() {
        this.searchEngines.put("duckduckgo", new DuckDuckGoEngine(this));
        this.searchEngines.put("searx", new SearxEngine(this));
    }

    /**
     * Sets the plugin's tag from config.
     */
    public void setPluginTag() {
        Message.pluginTag = this.getLangVal("plugin_tag");
    }

    /**
     * Returns the language value string to the given key.
     *
     * @param key The language config key.
     * @return The corresponding language value string.
     */
    public String getLangVal(String key) {
        String langKey = this.getConfig().getString("language");
        return this.getConfig().getString(String.format("language_strings.%s.%s", langKey, key));
    }

    /**
     * Returns a map containing all available search eingines.
     *
     * @return A map containing all available search eingines.
     */
    public Map<String, SearchEngine> getSearchEngines() {
        return searchEngines;
    }
}
