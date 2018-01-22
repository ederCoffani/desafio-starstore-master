package br.com.coffani.starstore.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.DatabaseReference;

import br.com.coffani.starstore.firebase.NetworkConfigFirebase;

/**
 * Created by coffani on 23/09/2017.
 */

public class Card implements Parcelable {

    /**
     * card_number : 1234123412341234
     * value : 7990
     * cvv : 789
     * card_holder_name : Luke Skywalker
     * exp_date : 12/24
     */
    private String id;
    private String card_number;
    private double value;
    private String cvv;
    private String card_holder_name;
    private String exp_date;

    public Card(String id, String card_number, double value, String cvv, String card_holder_name, String exp_date) {
        this.card_number = card_number;
        this.value = value;
        this.cvv = cvv;
        this.card_holder_name = card_holder_name;
        this.exp_date = exp_date;
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

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.card_number);
        dest.writeDouble(this.value);
        dest.writeString(this.cvv);
        dest.writeString(this.card_holder_name);
        dest.writeString(this.exp_date);
    }

    public Card() {
    }

    protected Card(Parcel in) {
        this.id = in.readString();
        this.card_number = in.readString();
        this.value = in.readDouble();
        this.cvv = in.readString();
        this.card_holder_name = in.readString();
        this.exp_date = in.readString();
    }

    public static final Parcelable.Creator<Card> CREATOR = new Parcelable.Creator<Card>() {
        @Override
        public Card createFromParcel(Parcel source) {
            return new Card(source);
        }

        @Override
        public Card[] newArray(int size) {
            return new Card[size];
        }
    };


    public void salvar(String s){
        Card card = new Card(getId(), getCard_holder_name(), getValue(), getCvv(), getCard_number(), getExp_date());
        DatabaseReference reference = NetworkConfigFirebase.getReferenceCard();

        String id = reference.push().getKey();
        this.id = s;
        card.setId(id);

        card.setValue(getValue());
        card.setExp_date(getExp_date());
        card.setCvv(getCvv());
        card.setCard_number(getCard_number());
        card.setCard_holder_name(getCard_holder_name());

        reference.child(card.getId()).setValue(card);



    }

}
