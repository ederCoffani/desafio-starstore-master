package br.com.coffani.starstore.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.coffani.starstore.R;
import br.com.coffani.starstore.domain.Historic;
import br.com.coffani.starstore.firebase.NetworkConfigFirebase;


public class HistoricAdapter extends RecyclerView.Adapter<HistoricAdapter.ViewHolder> {

    private Context mainContext;
    private  List<Historic> items;

    public HistoricAdapter(List<Historic> items, Context contexto) {
        this.mainContext = contexto;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_historic, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = items.get(position);

        holder.valor.setText(holder.mItem.getValor());
        holder.date.setText(holder.mItem.getDatahora());
        holder.name.setText(holder.mItem.getName());
        holder.digito.setText(holder.mItem.getDigito());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        public final TextView valor;
        public final TextView date;
        public final TextView name;
        public final TextView digito;

        public Historic mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            valor =(TextView) view.findViewById(R.id.tv_valor);
            date = (TextView) view.findViewById(R.id.tv_datahora);
            name = (TextView) view.findViewById(R.id.tv_name);
            digito = (TextView) view.findViewById(R.id.tv_digito);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + name.getText() + "'";
        }
    }
}
