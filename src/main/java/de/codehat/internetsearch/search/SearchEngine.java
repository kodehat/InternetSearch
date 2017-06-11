package de.codehat.internetsearch.search;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a search engine.
 *
 * @author CodeHat
 */
public interface SearchEngine {

    List<CommandSender> SEARCHING_SENDERS = new ArrayList<>();

    /**
     * Runs a new search request based on the given query.
     * Method should be run asynchronously and the result should be given to the callback.
     *
     * @param query Search query.
     * @param maxResults Amount of results, which is shown to the executer.
     * @param sender Sender, which executed search command.
     * @param resultCallback Callback, which gets all found results.
     * @return true if executed successfully, false if player already started a search request some seconds ago.
     */
    boolean search(String query, int maxResults, CommandSender sender, ResultCallback resultCallback);

    /**
     * Checks if the given sender is currently searching.
     *
     * @param sender The sender to check.
     * @return true if currently searching, false if currently not searching.
     */
    default boolean isSearching(CommandSender sender) {
        return SEARCHING_SENDERS.contains(sender);
    }
}
