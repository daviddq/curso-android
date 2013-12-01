package es.daviddiaz.cursoandroid.tarea.dao;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import es.daviddiaz.cursoandroid.tarea.BitmapLruCache;
import es.daviddiaz.cursoandroid.tarea.R;
import es.daviddiaz.cursoandroid.tarea.dominio.Foto;
import es.daviddiaz.cursoandroid.tarea.fragments.ComunidadFragment;

public class FotoAdapter extends BaseAdapter {
  private ImageLoader imageLoader;
  private LayoutInflater inflater;
  private List<Foto> dataArray; 
  
  public FotoAdapter(Context context, List<Foto> dataArray) {
    this.dataArray = dataArray;
    this.inflater = LayoutInflater.from(context);
    imageLoader = new ImageLoader(ComunidadFragment.requestQueue,
      new BitmapLruCache());
  }
  
  @Override
  public int getCount() {
    return dataArray.size();
  }

  @Override
  public Object getItem(int position) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public long getItemId(int position) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder holder;
    Foto current = dataArray.get(position);
    
    if (null==convertView) {
      convertView = inflater.inflate(R.layout.item_comunidad, null);
      holder = new ViewHolder();
      holder.txtDescripcion = (TextView)convertView.findViewById(R.id.text);
      holder.imgFoto = (NetworkImageView)convertView.findViewById(R.id.image);
      convertView.setTag(holder);
    } else {
      holder = (ViewHolder)convertView.getTag();
    }
    
    holder.txtDescripcion.setText(current.getDescripcion());
    
    if (null!=current.getBitmap()) {
      holder.imgFoto.setImageBitmap(current.getBitmap());
    } else {
      holder.imgFoto.setImageUrl(current.getUrl(), imageLoader );
    }
    
    return convertView;
  }
  
  private class ViewHolder {
    public TextView txtDescripcion;
    public NetworkImageView imgFoto;
  }

}
