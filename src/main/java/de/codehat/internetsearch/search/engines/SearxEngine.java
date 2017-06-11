package de.codehat.internetsearch.search.engines;

import de.codehat.internetsearch.InternetSearch;
import de.codehat.internetsearch.search.Result;
import de.codehat.internetsearch.search.ResultCallback;
import de.codehat.internetsearch.search.SearchEngine;
import de.codehat.internetsearch.util.HttpRequest;
import org.bukkit.command.CommandSender;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents the "Searx" search engine.
 *
 * @author CodeHat
 */
public class SearxEngine implements SearchEngine {

    /**
     * The base url.
     */
    private static final String SEARCH_URL = "https://searx.me/search?format=json&q=";

    /**
     * The plugin instance.
     */
    private InternetSearch plugin;

    /**
     * Crates a new "Searx" search engine object.
     *
     * @param plugin The plugin instance.
     */
    public SearxEngine(InternetSearch plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean search(String query, int maxResults, CommandSender sender, ResultCallback resultCallback) {
        if (this.isSearching(sender)) return false;
        SEARCHING_SENDERS.add(sender);

        this.plugin.getServer().getScheduler().runTaskAsynchronously(this.plugin, () -> {
            String response = "";
            try {
                 response = HttpRequest.get(SearxEngine.SEARCH_URL + URLEncoder.encode(query, "UTF-8"));
            } catch (Exception e) {
                InternetSearch.logger.severe("Could not connect to 'Searx'!");
                return;
            }
            JSONParser parser = new JSONParser();
            JSONObject all = null;
            try {
                 all = (JSONObject) parser.parse(response);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            JSONArray results = (JSONArray) all.get("results");

            List<Result> resultList = new LinkedList<>();

            results.stream().limit(maxResults).forEach(r -> {
                JSONObject result = (JSONObject) r;
                String title = (String) result.get("title");
                String description = (String) result.get("content");
                String url = (String) result.get("url");

                Result ret = new Result(title, description, url);
                resultList.add(ret);
            });
            SEARCHING_SENDERS.remove(sender);
            resultCallback.results(resultList);
        });

        return true;
    }
}
