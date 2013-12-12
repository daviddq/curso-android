package es.daviddiaz.cursoandroid.tarea.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import es.daviddiaz.cursoandroid.tarea.R;
import es.daviddiaz.cursoandroid.tarea.dao.CentroComercialDao;
import es.daviddiaz.cursoandroid.tarea.dao.FotosDao;
import es.daviddiaz.cursoandroid.tarea.fragments.ComunidadFragment;
import es.daviddiaz.cursoandroid.tarea.fragments.ImagenesFragment;
import es.daviddiaz.cursoandroid.tarea.fragments.TiendasFragment;

public class MainActivity 
  extends ActionBarActivity 
{
  private ListView drawerList;
  private DrawerLayout drawerLayout;
  private String[] drawerOptions;
  private ActionBarDrawerToggle drawerToggle;
  
  static final int INDEX_IMAGENES = 0;
  static final int INDEX_TIENDAS = 1;
  static final int INDEX_COMUNIDAD = 2;
  static final int CONNECTION_FAILURE_REQUEST = 9000;
  
  int indexAnterior = 0;

  private Fragment[] fragments = {
    new ImagenesFragment(),
    new TiendasFragment(),
    new ComunidadFragment()
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    CentroComercialDao.Inicializar(this);
    FotosDao.Inicializar(this);
    
    drawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);
    drawerList = (ListView)findViewById(R.id.left_drawer);
    drawerOptions = getResources().getStringArray(R.array.drawer_options);
    drawerList.setAdapter(new ArrayAdapter<String>(
        this, 
        R.layout.item_drawer_list, 
        drawerOptions));
    
    drawerList.setItemChecked(0, true);
    drawerList.setOnItemClickListener(new DrawerItemClickListener());
    
    drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
        R.drawable.ic_drawer_am, R.string.drawer_open, R.string.drawer_close) 
    {
      public void onDrawerClosed(View view) {
        ActivityCompat.invalidateOptionsMenu(MainActivity.this);
      }
      
      public void onDrawerOpened(View view) {
        ActivityCompat.invalidateOptionsMenu(MainActivity.this);
      }
    };
    
    drawerLayout.setDrawerListener(drawerToggle);
    
    final ActionBar actionBar = getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setHomeButtonEnabled(true);

    getSupportFragmentManager()
      .beginTransaction()
      .add(R.id.contentFrame, fragments[INDEX_IMAGENES])
      .hide(fragments[INDEX_IMAGENES])
      .add(R.id.contentFrame, fragments[INDEX_TIENDAS])
      .hide(fragments[INDEX_TIENDAS])
      .add(R.id.contentFrame, fragments[INDEX_COMUNIDAD])
      .hide(fragments[INDEX_COMUNIDAD])
      .commit();
    setContent(0);
  }
  
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      if (drawerLayout.isDrawerOpen(drawerList)) {
        drawerLayout.closeDrawer(drawerList);
      } else {
        drawerLayout.openDrawer(drawerList);
      }
      return true;
     }
    return super.onOptionsItemSelected(item);
  }
  
  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    drawerToggle.syncState();
  }
  
  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    drawerToggle.onConfigurationChanged(newConfig);
  }
  
  class DrawerItemClickListener
  implements ListView.OnItemClickListener {
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long arg3) {
      setContent(position);
    }
  }
  
  private void setContent(int index) {
    Fragment toHide = null;
    Fragment toShow = null;
    
    final ActionBar bar = getSupportActionBar();
    bar.setTitle(drawerOptions[index]);
    
    toHide = fragments[indexAnterior];
    toShow = fragments[index];

    if (index==INDEX_TIENDAS) {
      bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
      ((TiendasFragment)fragments[INDEX_TIENDAS]).setMapMenuEnabled(true);
    } else {
      bar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
      ((TiendasFragment)fragments[INDEX_TIENDAS]).setMapMenuEnabled(false);
    }

    getSupportFragmentManager()
      .beginTransaction()
      .hide(toHide)
      .show(toShow)
      .commit();

    drawerList.setItemChecked(index, true);
    drawerLayout.closeDrawer(drawerList);
    
    indexAnterior = index;
  }
}
