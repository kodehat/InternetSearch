package de.codehat.internetsearch.search;

/**
 * Represents a result.
 *
 * @author CodeHat
 */
public class Result {

    /**
     * Result title.
     */
    private String title;

    /**
     * Result description.
     */
    private String description;

    /**
     * Result url.
     */
    private String url;

    /**
     * Creates a new result.
     *
     * @param title Result title.
     * @param description Result description.
     * @param url Result url.
     */
    public Result(String title, String description, String url) {
        this.title = title;
        this.description = description;
        this.url = url;
    }

    /**
     * Returns the result's title.
     *
     * @return The result's title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the result's description.
     *
     * @return The result's description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the result's url.
     *
     * @return The result's url.
     */
    public String getUrl() {
        return url;
    }
}
