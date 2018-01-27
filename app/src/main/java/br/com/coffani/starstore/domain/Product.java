package br.com.coffani.starstore.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


/**
 * Created by Coffani on 22/09/2017.
 */

public class Product implements Parcelable {

    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
    @SerializedName("title")
    String title;
    @SerializedName("price")
    Double price;
    @SerializedName("zipcode")
    String zipcode;
    @SerializedName("seller")
    String seller;
    @SerializedName("thumbnailHd")
    String thumbnailHd;
    @SerializedName("date")
    String date;

    public Product() {

    }

    protected Product(Parcel in) {
        this.title = in.readString();
        this.price = (Double) in.readValue(Double.class.getClassLoader());
        this.zipcode = in.readString();
        this.seller = in.readString();
        this.thumbnailHd = in.readString();
        this.date = in.readString();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String titulo) {
        this.title = titulo;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double valor) {
        this.price = valor;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String cep) {
        this.zipcode = cep;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getThumbnailHd() {
        return thumbnailHd;
    }

    public void setThumbnailHd(String thumbnailHd) {
        this.thumbnailHd = thumbnailHd;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Product{" +
                "title='" + title + '\'' +
                ", price=" + price +
                ", zipcode='" + zipcode + '\'' +
                ", seller='" + seller + '\'' +
                ", thumbnailHd='" + thumbnailHd + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeValue(this.price);
        dest.writeString(this.zipcode);
        dest.writeString(this.seller);
        dest.writeString(this.thumbnailHd);
        dest.writeString(this.date);
    }




}
