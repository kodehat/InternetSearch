package de.codehat.internetsearch.search;

import java.util.List;

/**
 * Represents a callback having all found results.
 * Called after a search request has been executed successfully.
 *
 * @author CodeHat
 */
@FunctionalInterface
public interface ResultCallback {

    /**
     * Takes a list of results.
     *
     * @param results A list of results.
     */
    void results(List<Result> results);
}
