package es.daviddiaz.cursoandroid.tarea.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import es.daviddiaz.cursoandroid.tarea.R;

public class ComentariosFragment extends Fragment {
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_comentarios, container, false);
  }
}
