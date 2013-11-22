package es.daviddiaz.cursoandroid.tarea.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import es.daviddiaz.cursoandroid.tarea.R;
import es.daviddiaz.cursoandroid.tarea.dao.ImagenPagerAdapter;

public class ImagenesFragment extends Fragment {
  ViewPager pager;
  
  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    
    ImagenPagerAdapter adapter = new ImagenPagerAdapter(getChildFragmentManager());
    pager.setAdapter(adapter);
  }
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_imagenes, container, false);
    pager = (ViewPager)view.findViewById(R.id.pager);
    return view;
  }
}
