package br.com.coffani.starstore.domain;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

import br.com.coffani.starstore.firebase.NetworkConfigFirebase;

/**
 * Created by Coffani on 15/04/2017.
 */

public class User {

    private byte[] bytes;
    private boolean active;
    private String id, email, password, name;


    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }


    public User() {
    }

    public User(boolean active) {
        this.active = active;
    }

    public User(String id, String correo, String password, String nombre) {
        this.id = id;
        this.email = correo;
        this.password = password;
        this.name = nombre;
    }

    public User(String id, String email, String password,byte[] bytes, String name) {
        this.bytes = bytes;
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String pass) {
        this.password = pass;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
