package es.daviddiaz.cursoandroid.tarea;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class ListAdapter extends BaseAdapter {
    private ArrayList<?> elementos; 
    private int R_layout_IdView; 
    private Context contexto;

    public ListAdapter(Context contexto, int R_layout_IdView, ArrayList<?> tiendas) {
        super();
        
        this.contexto = contexto;
        this.elementos = tiendas; 
        this.R_layout_IdView = R_layout_IdView; 
    }

    @Override
    public View getView(int posicion, View view, ViewGroup pariente) {
        if (view == null) {
        	LayoutInflater vi = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
            view = vi.inflate(R_layout_IdView, null); 
        }
        onEntrada (elementos.get(posicion), view);
        return view; 
    }

    @Override
    public int getCount() {
        return elementos.size();
    }

    @Override
    public Object getItem(int posicion) {
        return elementos.get(posicion);
    }

    @Override
    public long getItemId(int posicion) {
        return posicion;
    }

    public abstract void onEntrada (Object entrada, View view);
}