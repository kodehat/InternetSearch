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
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

public class GoogleEngine implements SearchEngine {

    /**
     * The base url.
     */
    private static final String SEARCH_URL = "http://www.google.com/search?q=%s&num=%d";

    /**
     * The default user agent.
     */
    private static final String USER_AGENT = "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)";

    /**
     * The plugin instance.
     */
    private InternetSearch plugin;

    /**
     * Crates a new "Google" search engine object.
     *
     * @param plugin The plugin instance.
     */
    public GoogleEngine(InternetSearch plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean search(String query, int maxResults, CommandSender sender, ResultCallback resultCallback) {
        if (this.isSearching(sender)) return false;
        SEARCHING_SENDERS.add(sender);

        this.plugin.getServer().getScheduler().runTaskAsynchronously(this.plugin, () -> {
            Document doc;
            try {
                doc = Jsoup.connect(buildSearchURL(GoogleEngine.SEARCH_URL, query, maxResults))
                        .userAgent(USER_AGENT).get();
            } catch (IOException e) {
                InternetSearch.logger.severe("Could not connect to 'Google'!");
                return;
            }

            List<Result> resultList = new LinkedList<>();

            Elements links = doc.select("div[class=g]");
            for (Element link : links) {
                Elements titles = link.select("h3[class=r] > a");
                String title = titles.text();
                String url = titles.attr("href");
                String strUrl;
                try {
                    strUrl = URLDecoder.decode(url, StandardCharsets.UTF_8.name());
                } catch (UnsupportedEncodingException e) {
                    InternetSearch.logger.severe("Can't decode result URL!");
                    return;
                }

                if (url.startsWith("/search?") || url.isEmpty()) continue;

                Elements bodies = link.select("span[class=st]");
                String body = bodies.text();
                Result result = new Result(title, body, strUrl.substring(7, strUrl.indexOf("&sa=")));
                resultList.add(result);
            }

            SEARCHING_SENDERS.remove(sender);
            resultCallback.results(resultList);
        });

        return true;
    }

    /**
     * Formats and encodes the URL to get a "send-ready" URL.
     *
     * @param url The URL with format placeholders for query and amount of results.
     * @param query The actual query.
     * @param results The amount of results.
     * @return A "send-ready" (encoded, formatted) URL.
     * @throws UnsupportedEncodingException If charset isn't found.
     */
    private String buildSearchURL(String url, String query, int results) throws UnsupportedEncodingException {
        return String.format(url, URLEncoder.encode(query, StandardCharsets.UTF_8.name()), results);
    }

}
