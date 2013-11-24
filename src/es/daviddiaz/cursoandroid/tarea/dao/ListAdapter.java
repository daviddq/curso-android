package es.daviddiaz.cursoandroid.tarea.dao;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class ListAdapter extends BaseAdapter {
    private List<?> elementos; 
    private int R_layout_IdView; 
    private Context contexto;

    public ListAdapter(Context contexto, int R_layout_IdView, List<?> list) {
        super();
        
        this.contexto = contexto;
        this.elementos = list; 
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