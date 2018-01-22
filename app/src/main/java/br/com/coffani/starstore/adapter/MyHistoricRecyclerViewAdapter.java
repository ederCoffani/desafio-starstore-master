package br.com.coffani.starstore.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.coffani.starstore.R;
import br.com.coffani.starstore.domain.Historic;
import br.com.coffani.starstore.firebase.NetworkConfigFirebase;


public class MyHistoricRecyclerViewAdapter extends RecyclerView.Adapter<MyHistoricRecyclerViewAdapter.ViewHolder> {

    private NetworkConfigFirebase db;

    public MyHistoricRecyclerViewAdapter() {
        db = NetworkConfigFirebase.getInstance();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_historic, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = db.getHistoricList().get(position);
        holder.tv_name.setText(holder.mItem.getCard_holder_name());
        holder.tv_title.setText(holder.mItem.getTitulo());
        holder.tv_store.setText(holder.mItem.getLoja());
        holder.tv_date.setText(holder.mItem.getData());

    }

    @Override
    public int getItemCount() {
        return db.getHistoricList().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView url_image;
        public final TextView tv_name;
        public final TextView tv_title;
        public final TextView tv_store;
        public final TextView tv_date;
        public final TextView tv_maney;
        public Historic mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            url_image =(ImageView) view.findViewById(R.id.url_image);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_title = (TextView) view.findViewById(R.id.tv_title);
            tv_store = (TextView) view.findViewById(R.id.tv_store);
            tv_date = (TextView) view.findViewById(R.id.tv_date);
            tv_maney = (TextView) view.findViewById(R.id.tv_maney);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + tv_name.getText() + "'";
        }
    }
}
