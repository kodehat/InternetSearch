package de.codehat.internetsearch.search.engines;

import de.codehat.internetsearch.InternetSearch;
import de.codehat.internetsearch.search.Result;
import de.codehat.internetsearch.search.ResultCallback;
import de.codehat.internetsearch.search.SearchEngine;
import org.bukkit.command.CommandSender;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents the "DuckDuckGo" search engine.
 *
 * @author CodeHat
 */
public class DuckDuckGoEngine implements SearchEngine {

    /**
     * The base url.
     */
    private static final String SEARCH_URL = "https://duckduckgo.com/html/?q=";

    /**
     * The default user agent.
     */
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36";

    /**
     * The plugin instance.
     */
    private InternetSearch plugin;

    /**
     * Crates a new "DuckDuckGo" search engine object.
     *
     * @param plugin The plugin instance.
     */
    public DuckDuckGoEngine(InternetSearch plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean search(String query, int maxResults, CommandSender sender, ResultCallback resultCallback) {
        if (this.isSearching(sender)) return false;
        SEARCHING_SENDERS.add(sender);

        this.plugin.getServer().getScheduler().runTaskAsynchronously(this.plugin, () -> {
            Document doc = null;
            try {
                doc = Jsoup.connect(DuckDuckGoEngine.SEARCH_URL + URLEncoder.encode(query, "UTF-8"))
                        .userAgent(USER_AGENT).get();
            } catch (IOException e) {
                InternetSearch.logger.severe("Could not connect to 'DuckDuckGo'!");
                return;
            }

            Elements links = doc.select("div.result");
            List<Result> resultList = new LinkedList<>();

            links.stream().limit(maxResults).forEach(e -> {
                // Title
                Element title = e.select("h2.result__title").first();
                String strTitle = title.text();
                // Description and URL
                Element data = e.select("a.result__snippet").first();
                String strDescription = data.text();
                String strUrl = data.attr("href");

                Result result = new Result(strTitle, strDescription, strUrl);
                resultList.add(result);
            });
            SEARCHING_SENDERS.remove(sender);
            resultCallback.results(resultList);
        });

        return true;
    }
}
