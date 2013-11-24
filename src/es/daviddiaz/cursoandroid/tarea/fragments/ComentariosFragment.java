package es.daviddiaz.cursoandroid.tarea.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import es.daviddiaz.cursoandroid.tarea.R;
import es.daviddiaz.cursoandroid.tarea.activities.TiendaProvider;
import es.daviddiaz.cursoandroid.tarea.dominio.Tienda;
import es.daviddiaz.cursoandroid.tarea.dao.ListAdapter;

public class ComentariosFragment 
extends Fragment 
implements OnClickListener {
  Button button;
  EditText editText;
  ListView list;
  Tienda tienda;
  ListAdapter adapter;

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    // Obtener tienda desde la activity contenedora
    tienda = ((TiendaProvider)getActivity()).getTienda();
    button.setOnClickListener(this);
    
    adapter = new ListAdapter(
        getActivity(),
        R.layout.item_comentario,
        tienda.getComentarios()) {

      @Override
      public void onEntrada(Object entrada, View view) {
        if (entrada != null) {
          String comentario = (String)entrada;
          TextView texto = (TextView)view.findViewById(R.id.textComentario);
          if (null!=texto)
            texto.setText(comentario);
        }
      }
    };
    
    list.setAdapter(adapter);
  }
  
  @Override
  public void onResume() {
    super.onResume();
    adapter.notifyDataSetChanged();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_comentarios, container, false);

    editText = (EditText)view.findViewById(R.id.editComentarios);
    button = (Button)view.findViewById(R.id.buttonComentarios);
    list = (ListView)view.findViewById(R.id.listViewComentarios);

    return view;
  }

  @Override
  public void onClick(View v) {
    String text = editText.getText().toString();
    if (null!=tienda && !"".equals(text)) {
      tienda.getComentarios().add(text);
      adapter.notifyDataSetChanged();
      editText.setText("");
    }
  }
}
