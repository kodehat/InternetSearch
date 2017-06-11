package de.codehat.internetsearch.commands;

import de.codehat.internetsearch.InternetSearch;
import de.codehat.internetsearch.search.SearchEngine;
import de.codehat.internetsearch.util.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * The plugin's search command.
 *
 * @author CodeHat
 */
public class SearchCommand implements CommandExecutor {

    /**
     * The plugin instance.
     */
    private InternetSearch plugin;

    /**
     * Creates a new search command.
     *
     * @param plugin The plugin instance.
     */
    public SearchCommand(InternetSearch plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("internetsearch.search")) {
            Message.sendWithLogo(sender, this.plugin.getLangVal("no_permission"));
            return true;
        }
        if (args.length == 0) {
            Message.sendWithLogo(sender, this.plugin.getLangVal("cmd_search_empty_query"));
            return true;
        }
        String query = this.buildQuery(args);

        if (query.length() > 100) {
            Message.sendWithLogo(sender, this.plugin.getLangVal("cmd_search_long_query"));
            return true;
        }

        SearchEngine searchEngine = this.plugin.getSearchEngines()
                .get(this.plugin.getConfig().getString("search_engine"));

        if (searchEngine == null) {
            Message.sendWithLogo(sender, this.plugin.getLangVal("cmd_search_undefined_engine"));
            return true;
        }

        if (searchEngine.isSearching(sender)) {
            Message.sendWithLogo(sender, this.plugin.getLangVal("cmd_search_already_searching"));
            return true;
        }

        Message.sendWithLogo(sender, this.plugin.getLangVal("cmd_search_searching"));
        searchEngine.search(query, this.plugin.getConfig().getInt("max_results"), sender, (rl) -> {
                    this.plugin.getServer().getScheduler().runTask(this.plugin, () -> {
                        Message.sendWithLogo(sender,
                                String.format(this.plugin.getLangVal("cmd_search_results"), query));
                        Message.send(sender, "&c-----------------------------------------------------");

                        rl.forEach(r -> {
                            Message.send(sender, String.format(this.plugin.getLangVal("cmd_search_title"),
                                    r.getTitle()));
                            Message.send(sender, "");
                            Message.send(sender, String.format(this.plugin.getLangVal("cmd_search_description"),
                                    r.getDescription()));
                            Message.send(sender, "");
                            Message.send(sender, String.format(this.plugin.getLangVal("cmd_search_url"),
                                    r.getUrl()));
                            Message.send(sender, "&c-----------------------------------------------------");
                        });
                    });
                });
        return true;
    }

    /**
     * Builds the search query based on an array of strings and concatenates them with " " delimiter.
     *
     * @param args An array of strings.
     * @return A string, which was built by concatenating all string in given string array with " " delimiter.
     */
    private String buildQuery(String[] args) {
        return Arrays.stream(args).collect(Collectors.joining(" "));
    }
}
