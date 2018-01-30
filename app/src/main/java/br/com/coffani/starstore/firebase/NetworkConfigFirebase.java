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

    private static DatabaseReference reference;

    private NetworkConfigFirebase() {

    }
    public static DatabaseReference getFirebase() {
        if (reference == null) {
            reference = FirebaseDatabase.getInstance().getReference();
        }
        return reference;
    }
}
