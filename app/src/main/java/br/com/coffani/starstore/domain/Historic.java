package br.com.coffani.starstore.domain;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

import br.com.coffani.starstore.firebase.NetworkConfigFirebase;

/**
 * Created by Coffani on 20/01/2018.
 */

public class Historic {
    //Card
    private String id;
    private String valor;
    private String datahora;
    private String name;
    private String digito;

    public Historic() {
    }

    public Historic(String id, String valor, String datahora, String name, String digito) {
        this.id = id;
        this.valor = valor;
        this.datahora = datahora;
        this.name = name;
        this.digito = digito;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getDatahora() {
        return datahora;
    }

    public void setDatahora(String datahora) {
        this.datahora = datahora;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDigito() {
        return digito;
    }

    public void setDigito(String digito) {
        this.digito = digito;
    }
}
