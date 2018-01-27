package br.com.coffani.starstore.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import br.com.coffani.starstore.R;
import br.com.coffani.starstore.domain.Product;


/**
 * Created by kaike on 23/09/2017.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private ArrayList<Product> products;

    private Context context;

    public CartAdapter(Context context) {
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(final CartAdapter.ViewHolder holder, int position) {
        final Product l = products.get(position);
        holder.tituloTextView.setText(l.getTitulo());
        holder.valorTextView.setText(String.valueOf(l.getValor()));
        Glide.with(context)
               .load(l.getUrlFoto())
                .into(holder.fotoImageView);

    }

    @Override
    public int getItemCount() {
        if (products == null)
            return 0;
        return products.size();
    }

    public void mostrasListaDeItens(ArrayList<Product> products) {
        this.products = products;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView fotoImageView;
        private TextView tituloTextView;
        private TextView valorTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            fotoImageView = (ImageView) itemView.findViewById(R.id.foto_item);
            tituloTextView = (TextView) itemView.findViewById(R.id.titulo_item);
            valorTextView = (TextView) itemView.findViewById(R.id.valor_item);
        }
    }

}
