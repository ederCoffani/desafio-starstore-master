package br.com.coffani.starstore.helper;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Created by Coffani on 24/01/2018.
 */

public class SearchableProvider extends SearchRecentSuggestionsProvider {
    public static final String AUTHORITY = "br.com.thiengo.tcmaterialdesign.provider.SearchableProvider";
    public static final int MODE = DATABASE_MODE_QUERIES;

    public SearchableProvider(){
        setupSuggestions( AUTHORITY, MODE );
    }
}