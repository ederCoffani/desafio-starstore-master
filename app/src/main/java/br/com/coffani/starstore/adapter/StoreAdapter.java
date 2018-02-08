package br.com.coffani.starstore.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

/**
 * Created by Coffani on 23/09/2017.
 */

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.ViewHolder> {
    private List<Product> pList;
    private Context mContext;

    public StoreAdapter(Context mContext, ArrayList<Product> mList) {
        this.mContext = mContext;
        this.pList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_row_new, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Product p = pList.get(position);


        BigDecimal valor = new BigDecimal(p.getPrice());
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        String formatado = nf.format(valor);
        holder.titleTv.setText(p.getTitle());
        holder.subtitleTv.setText(formatado);
        Glide.with(mContext)
                .load(p.getThumbnailHd())
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageV);
        holder.imageV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.imageV.getContext(), DetailActivity.class);
                intent.putExtra("ITEM", p);
                holder.imageV.getContext().startActivity(intent);
            }
        });
        holder.btnGetOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.btnGetOffer.getContext(), DetailActivity.class);
                intent.putExtra("ITEM", p);
                holder.btnGetOffer.getContext().startActivity(intent);
            }
        });
        holder.tvAllOffers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.tvAllOffers.getContext(), DetailActivity.class);
                intent.putExtra("ITEM", p);
                holder.tvAllOffers.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        if (pList != null) {
            return pList.size();
        }
        return 0;
    }

    public void adicionarListaLoja(Product p) {
        pList.add(p);
        notifyDataSetChanged();

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageV;
        private TextView titleTv;
        private TextView subtitleTv;
        private Button btnGetOffer;
        private TextView tvAllOffers;

        public ViewHolder(View itemView) {
            super(itemView);
            imageV = (ImageView) itemView.findViewById(R.id.imageV);
            titleTv = (TextView) itemView.findViewById(R.id.tv_title);
            subtitleTv = (TextView) itemView.findViewById(R.id.tv_subtitle);
            btnGetOffer = (Button) itemView.findViewById(R.id.btnGetOffer);
            tvAllOffers = (TextView) itemView.findViewById(R.id.tvAllOffers);
        }
    }


}
