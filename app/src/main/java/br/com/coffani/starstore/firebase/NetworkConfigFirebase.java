package br.com.coffani.starstore.firebase;

import android.support.annotation.NonNull;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.coffani.starstore.domain.Historic;

/**
 * Created by Coffani on 20/01/2018.
 */

public class NetworkConfigFirebase {

    private static final NetworkConfigFirebase INSTANCE = new NetworkConfigFirebase();
    private static final String TAG = "NetworkConfigFirebase";
    private static DatabaseReference reference, referenceCard, referenceHistoric;
    private FirebaseDatabase db;
    private DatabaseReference historicRef;
    private List<Historic> historics;
    private DatabaseReference mMovieRef;


    private NetworkConfigFirebase() {

    }

    public static DatabaseReference getFirebase() {
        if (reference == null) {
            reference = FirebaseDatabase.getInstance().getReference();
        }
        return reference;
    }

    public static DatabaseReference getReferenceCard() {
        if (referenceCard == null) {
            referenceCard = FirebaseDatabase.getInstance().getReference("cards");
        }
        return reference;
    }

    public static NetworkConfigFirebase getInstance() {
        return INSTANCE;
    }

    public void addHistoric(Historic h) {
        historicRef.push().setValue(h);
    }

    public List<Historic> getHistoricList() {
        return historics;
    }


}
