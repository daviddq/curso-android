package es.daviddiaz.cursoandroid.tarea.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import es.daviddiaz.cursoandroid.tarea.R;

public class ImagenFragment extends Fragment {
  public final static String RESOURCE = "resource";
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.fragment_imagen, container, false);
      ImageView image = (ImageView)view.findViewById(R.id.imagen);
      Bundle args = getArguments();
      image.setImageResource(args.getInt(RESOURCE));
      return view;
  }
}
