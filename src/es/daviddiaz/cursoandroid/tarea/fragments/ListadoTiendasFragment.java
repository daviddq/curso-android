package es.daviddiaz.cursoandroid.tarea.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import es.daviddiaz.cursoandroid.tarea.ListAdapter;
import es.daviddiaz.cursoandroid.tarea.R;
import es.daviddiaz.cursoandroid.tarea.activities.DetalleTiendaActivity;
import es.daviddiaz.cursoandroid.tarea.dao.CentroComercialDAO;
import es.daviddiaz.cursoandroid.tarea.dominio.Tienda;

public class ListadoTiendasFragment extends Fragment {
  ListView listView;

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    ListAdapter adapter = new ListAdapter(
        this.getActivity(),
        R.layout.list_view_item,
        CentroComercialDAO.getTiendas()
        ) {
      @Override
      public void onEntrada(Object entrada, View view) {
        if (entrada != null) {
          Tienda tienda = (Tienda)entrada;

          ImageView icono = (ImageView)view.findViewById(R.id.iconoTienda);
          if (null!=icono)
            icono.setImageResource(tienda.getIcono());

          TextView nombre = (TextView)view.findViewById(R.id.nombreTienda);
          if (null!=nombre)
            nombre.setText(tienda.getNombre());

          TextView direccion = (TextView)view.findViewById(R.id.direccionTienda);
          if (null!=direccion)
            direccion.setText(tienda.getDireccion());
        }
      }
    };
    
    ListView listView = (ListView)this.getActivity().findViewById(R.id.listViewTiendas);
    listView.setAdapter(adapter);
    
    listView.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(
          AdapterView<?> parent, 
          View view, 
          int indice,
          long id) {
        
        Intent intent = new Intent();
        intent = new Intent(getActivity().getApplicationContext(),
            DetalleTiendaActivity.class);
        intent.putExtra(DetalleTiendaActivity.ID_TIENDA, indice);
        startActivity(intent);
      }
    });    
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_listado_tiendas, container, false);
  }
}
