package es.daviddiaz.cursoandroid.tarea.dao;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import es.daviddiaz.cursoandroid.tarea.R;
import es.daviddiaz.cursoandroid.tarea.fragments.ImagenFragment;

public class ImagenPagerAdapter extends FragmentPagerAdapter {
  private int[] imagenes = {
      R.drawable.mall1,
      R.drawable.mall2,
      R.drawable.mall3,
      R.drawable.mall4,
      R.drawable.mall5
  };

  public ImagenPagerAdapter(FragmentManager fm) {
    super(fm);
  }

  @Override
  public Fragment getItem(int position) {
    Fragment fragment = new ImagenFragment();
    Bundle args = new Bundle();
    args.putInt(ImagenFragment.RESOURCE, imagenes[position]);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public int getCount() {
    return imagenes.length;
  }

}
