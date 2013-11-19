package es.daviddiaz.cursoandroid.tarea;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import es.daviddiaz.cursoandroid.tarea.dao.CentroComercialDAO;
import es.daviddiaz.cursoandroid.tarea.dominio.Tienda;

public class ListadoTiendasActivity 
    extends Activity
    {
    
  ListView listView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_listado_tiendas);
    
    CentroComercialDAO.Inicializar();
    
    ListAdapter adapter = new ListAdapter(
        this,
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
    
    ListView listView = (ListView)findViewById(R.id.listViewTiendas);
    listView.setAdapter(adapter);
    
    listView.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(
          AdapterView<?> parent, 
          View view, 
          int indice,
          long id) {
        
        
        Intent intent = new Intent();
        intent = new Intent(getApplicationContext(),
            DetalleTiendaActivity.class);

        intent.putExtra(DetalleTiendaActivity.ID_TIENDA, indice);
        
        startActivity(intent);
      }
    });
  }
}
