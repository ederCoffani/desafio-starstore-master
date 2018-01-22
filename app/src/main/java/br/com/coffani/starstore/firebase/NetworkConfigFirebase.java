package br.com.coffani.starstore.firebase;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
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

    private FirebaseDatabase db;
    private DatabaseReference historicRef;
    private List<Historic> historics;

    private static DatabaseReference reference, referenceCard, referenceHistoric;
    private DatabaseReference mMovieRef;
    private static FirebaseAuth autentication;
    private static FirebaseAuth.AuthStateListener authStateListener;
    private static FirebaseUser firebaseUser;


    public static DatabaseReference getFirebase() {
        if (reference == null) {
            reference = FirebaseDatabase.getInstance().getReference("user");
        }
        return reference;
    }

    public static DatabaseReference getReferenceCard() {
        if (referenceCard == null) {
            referenceCard = FirebaseDatabase.getInstance().getReference("cards");
        }
        return reference;
    }

    public static DatabaseReference getReferenceHistoric() {
        if (referenceHistoric == null) {
            referenceHistoric = FirebaseDatabase.getInstance().getReference();
        }
        return reference;
    }

    private NetworkConfigFirebase() {

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

    public static FirebaseAuth getAutentication(){
        if (autentication == null){
            initFirebaseAuth();
        }
        return autentication;
    }


    public static void initFirebaseAuth() {
        autentication = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    firebaseUser = user;
                }
            }
        };
        autentication.addAuthStateListener(authStateListener);

    }
    public static FirebaseUser getFirebaseUser(){
        return firebaseUser;
    }
    public static void logOut(){
        autentication.signOut();
    }
//        db = FirebaseDatabase.getInstance();
//        historics = new ArrayList<Historic>();
//        historicRef = db.getReference("historic");
//        historicRef.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                historics.add(dataSnapshot.getValue(Historic.class));
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
}
