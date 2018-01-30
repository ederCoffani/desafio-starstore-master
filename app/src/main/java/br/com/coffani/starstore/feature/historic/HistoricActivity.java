package br.com.coffani.starstore.feature.historic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import br.com.coffani.starstore.R;
import br.com.coffani.starstore.adapter.HistoricAdapter;
import br.com.coffani.starstore.database.DatabaseManagerTransition;
import br.com.coffani.starstore.domain.Historic;

/**
 * Created by Coffani on 20/01/2018.
 */

public class HistoricActivity extends AppCompatActivity {

    private DatabaseManagerTransition managerTransition;//TRANSIÇÃO COM O DB SQLITE
    private RecyclerView recycler;
    private HistoricAdapter adapter;
    private RecyclerView.LayoutManager lManager;
    private List<Historic> listaItemsCursos;//LISTA DE TRANSIÇÃO

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historic_list);
        //ESTANCIA DA CLASS GERENCIADORA DA TRANSIÇÃO
        managerTransition= new DatabaseManagerTransition(this);


        inicializarRecicler();
    }
    public void inicializarRecicler() {

        listaItemsCursos = managerTransition.getHistoricsList();
        // Obter Recycler
        recycler = (RecyclerView) findViewById(R.id.list);
        recycler.setHasFixedSize(true);
        // Usar um administrador para LinearLayout
        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);
        // Criar un novo adaptador
        adapter = new HistoricAdapter(listaItemsCursos, this);

        recycler.setAdapter(adapter);


        recycler.setItemAnimator(new DefaultItemAnimator());

    }
    @Override
    protected void onDestroy() {
        managerTransition.cargarCursor();
        super.onDestroy();
    }

}
