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
    private String card_number;
    private double value;
    private String cvv;
    private String card_holder_name;
    private String exp_date;
    //Product
    private String titulo;
    private String loja;
    private String urlFoto;
    private String data;
    //Total
    private String total;

    public Historic() {
    }

    public Historic(String id, String card_number, double value, String cvv,
                    String card_holder_name, String exp_date, String titulo,
                    String loja, String urlFoto, String data, String total) {
        this.id = id;
        this.card_number = card_number;
        this.value = value;
        this.cvv = cvv;
        this.card_holder_name = card_holder_name;
        this.exp_date = exp_date;
        this.titulo = titulo;
        this.loja = loja;
        this.urlFoto = urlFoto;
        this.data = data;
        this.total = total;
    }
    public void setID(String id){
        this.id = id;
    }
    public String getID(){
        return id;
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getCard_holder_name() {
        return card_holder_name;
    }

    public void setCard_holder_name(String card_holder_name) {
        this.card_holder_name = card_holder_name;
    }

    public String getExp_date() {
        return exp_date;
    }

    public void setExp_date(String exp_date) {
        this.exp_date = exp_date;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getLoja() {
        return loja;
    }

    public void setLoja(String loja) {
        this.loja = loja;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTotal() {
        return total;
    }

    public void salvar(){
        Historic historic = new Historic(getID(), getCard_number(), getValue(), getCvv(), getCard_holder_name(), getExp_date(), getTitulo(), getLoja(), getUrlFoto(), getData(), getTotal());
        DatabaseReference reference = NetworkConfigFirebase.getReferenceHistoric();

        String id = reference.push().getKey();
        historic.setID(id);

        historic.setValue(getValue());
        historic.setExp_date(getExp_date());
        historic.setCvv(getCvv());
        historic.setCard_number(getCard_number());
        historic.setCard_holder_name(getCard_holder_name());

        historic.setTitulo(getTitulo());
        historic.setUrlFoto(getUrlFoto());
        historic.setData(getData());
        historic.setTotal(getTotal());


        reference.child(historic.getID()).setValue(historic);

    }
//    public void salvar() {
//        Historic historic = new Historic();
//        DatabaseReference reference = NetworkConfigFirebase.getReferenceHistoric();
//        reference.child(historic.getID()).setValue(this);
//    }
//
//    @Exclude
//
//    public Map<String, Object> toMap() {
//        HashMap<String, Object> hashMapUsuario = new HashMap<>();
//
//        hashMapUsuario.put("id", getID());
//        hashMapUsuario.put("uri", getUrlFoto());
//        hashMapUsuario.put("data", getData());
//        hashMapUsuario.put("total", getTotal());
//        hashMapUsuario.put("value", getValue());
//        hashMapUsuario.put("dataEX", getExp_date());
//        hashMapUsuario.put("cvv", getCvv());
//        hashMapUsuario.put("numCard", getCard_number());
//        hashMapUsuario.put("name", getCard_holder_name());
//
//
//        return hashMapUsuario;
//
//    }
}
