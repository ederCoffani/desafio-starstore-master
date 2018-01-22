package br.com.coffani.starstore.feature.historic;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.coffani.starstore.R;
import br.com.coffani.starstore.adapter.MyHistoricRecyclerViewAdapter;
import br.com.coffani.starstore.firebase.NetworkConfigFirebase;

/**
 * Created by Coffani on 20/01/2018.
 */

public class HistoricActivity extends Fragment {

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public HistoricActivity() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_historic_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            NetworkConfigFirebase db = NetworkConfigFirebase.getInstance();
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new MyHistoricRecyclerViewAdapter());
        }
        return view;
    }
}
