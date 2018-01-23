package br.com.coffani.starstore.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;


import br.com.coffani.starstore.R;
import br.com.coffani.starstore.domain.Product;
import br.com.coffani.starstore.feature.detail.DetailActivity;
import br.com.coffani.starstore.helper.RecyclerViewOnClickListenerHack;

/**
 * Created by kaike on 23/09/2017.
 */

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.ViewHolder> implements Filterable {
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;
    private ArrayList<Product> mArrayList;
    private ArrayList<Product> mFilteredList;
    private Context mContext;

    public StoreAdapter(Context mContext, ArrayList<Product> mList) {
        this.mContext = mContext;
        this.mArrayList = (ArrayList<Product>) mList;
        mFilteredList = (ArrayList<Product>) mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Product l = mArrayList.get(position);


        BigDecimal valor = new BigDecimal(l.getValor());
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        String formatado = nf.format (valor);
        holder.titleTv.setText(l.getTitulo());
        holder.subtitleTv.setText(formatado);
        Glide.with(mContext)
                .load(l.getUrlFoto())
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageV);
        holder.imageV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.imageV.getContext(), DetailActivity.class);
                intent.putExtra("ITEM", l);
                holder.imageV.getContext().startActivity(intent);
            }
        });




    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }
    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r){
        mRecyclerViewOnClickListenerHack = r;
    }
    public void adicionarListaLoja(ArrayList<Product> listProduct) {
        mArrayList.addAll(listProduct);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    mFilteredList = mArrayList;
                } else {

                    ArrayList<Product> filteredList = new ArrayList<>();

                    for (Product androidVersion : mArrayList) {

                        if (androidVersion.getLoja().toLowerCase().contains(charString) || androidVersion.getTitulo().toLowerCase().contains(charString)) {
                            filteredList.add(androidVersion);
                        }
                    }

                    mFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<Product>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView imageV;
        private TextView titleTv;
        private TextView subtitleTv;

        public ViewHolder(View itemView) {
            super(itemView);
            imageV = (ImageView) itemView.findViewById(R.id.imageV);
            titleTv = (TextView) itemView.findViewById(R.id.tv_title);
            subtitleTv = (TextView) itemView.findViewById(R.id.tv_subtitle);
        }

        @Override
        public void onClick(View view) {
            if(mRecyclerViewOnClickListenerHack != null) {
                mRecyclerViewOnClickListenerHack.onClickListener(view, getPosition());
            }
        }
    }


}
