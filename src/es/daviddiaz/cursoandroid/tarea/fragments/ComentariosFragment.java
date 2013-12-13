package es.daviddiaz.cursoandroid.tarea.fragments;

import java.util.ArrayList;
import java.util.List;

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
import es.daviddiaz.cursoandroid.tarea.dao.ComentariosDao;
import es.daviddiaz.cursoandroid.tarea.dao.ListAdapter;
import es.daviddiaz.cursoandroid.tarea.dominio.Comentario;
import es.daviddiaz.cursoandroid.tarea.dominio.Tienda;

public class ComentariosFragment 
extends Fragment 
implements OnClickListener {
  Button button;
  EditText editText;
  ListView list;
  Tienda tienda;
  ListAdapter adapter;
  List<Comentario> comentarios = new ArrayList<Comentario>();

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    // Obtener tienda desde la activity contenedora
    tienda = ((TiendaProvider)getActivity()).getTienda();
    button.setOnClickListener(this);
    
    obtenerComentarios();

    adapter = new ListAdapter(
        getActivity(),
        R.layout.item_comentario,
        comentarios) {

      @Override
      public void onEntrada(Object entrada, View view) {
        if (entrada != null) {
          TextView texto = (TextView)view.findViewById(R.id.textComentario);
          if (null!=texto) {
            Comentario comentario = (Comentario)entrada;
            texto.setText(comentario.getTexto());
          }
        }
      }
    };
    
    list.setAdapter(adapter);
  }
  
  private void obtenerComentarios() {
    comentarios.clear();
    comentarios.addAll(ComentariosDao.obtenerComentariosDeTienda(tienda.getId()));
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
    if (null!=tienda && !"".equals(editText.getText().toString())) {
      Comentario c = new Comentario();
      c.setTiendaId(tienda.getId());
      c.setTexto(editText.getText().toString());
      ComentariosDao.agregarComentario(getActivity(), c);
      obtenerComentarios();
      editText.setText("");
      adapter.notifyDataSetChanged();
    }
  }
}
