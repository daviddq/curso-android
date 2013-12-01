package es.daviddiaz.cursoandroid.tarea.fragments;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.PopupMenu.OnMenuItemClickListener;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import es.daviddiaz.cursoandroid.tarea.R;
import es.daviddiaz.cursoandroid.tarea.dao.FotoAdapter;
import es.daviddiaz.cursoandroid.tarea.dao.FotosDao;
import es.daviddiaz.cursoandroid.tarea.dominio.Foto;

public class ComunidadFragment extends Fragment 
implements OnClickListener, OnMenuItemClickListener {
  private static final int RESULT_LOAD_IMAGE = 1;
  private static final int RESULT_CAMERA = 2;

  public static RequestQueue requestQueue;
  FotoAdapter fotoAdapter;
  ImageView botonCamara;
  PopupMenu popup;
  String photoPath;

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    botonCamara.setOnClickListener(this);
    popup.setOnMenuItemClickListener(this);
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (resultCode == Activity.RESULT_OK && data != null) {
      switch (requestCode) {
        case RESULT_LOAD_IMAGE:
          Uri selectedImage = data.getData();
          String[] filePathColumn = { MediaStore.Images.Media.DATA };
  
          Cursor cursor = getActivity().getContentResolver().query(
              selectedImage, filePathColumn, null, null, null);
          cursor.moveToFirst();
  
          int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
          String picturePath = cursor.getString(columnIndex);
          cursor.close();
  
          String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
              Locale.getDefault())
          .format(Calendar.getInstance().getTime());
          Bitmap bitmap = resizeBitmap(picturePath, 640, 480);
          FotosDao.getFotos().add(new Foto(null, bitmap, timeStamp, 2));
          fotoAdapter.notifyDataSetChanged();
          
  
//          Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
//          File f = new File(photoPath);
//          Uri contentUri = Uri.fromFile(f);
//          mediaScanIntent.setData(contentUri);
//          this.sendBroadcast(mediaScanIntent);              
//          break;

      default:
        break;
      }
    }
  }   

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    requestQueue = Volley.newRequestQueue(getActivity());

    View view = inflater.inflate(R.layout.fragment_comunidad, container, false);
    botonCamara = (ImageView)view.findViewById(R.id.imageView);
    ListView list = (ListView)view.findViewById(R.id.listView);
    fotoAdapter = new FotoAdapter(getActivity(), FotosDao.getFotos());
    list.setAdapter(fotoAdapter);
    popup = new PopupMenu(getActivity(), botonCamara);
    popup.getMenuInflater().inflate(R.menu.menu_foto , popup.getMenu());
    return view; 
  }

  @Override
  public void onClick(View v) {
    popup.show();
  }

  @Override
  public boolean onMenuItemClick(MenuItem item) {
    switch (item.getItemId()) {
    case R.id.galeria:
      tomarFotoDeGaleria();
      break;
    case R.id.camara:
      tomarFotoDeCamara();
      break;
    }
    return false;
  }

  private void tomarFotoDeGaleria() {
    Intent i = new Intent(
        Intent.ACTION_PICK, 
        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    startActivityForResult(i, RESULT_LOAD_IMAGE);
  }

  private void tomarFotoDeCamara() {
    File photo = setUpFile();
    photoPath = photo.getAbsolutePath();
    Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    i.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
    startActivityForResult(i, RESULT_CAMERA);
  }

  private Bitmap resizeBitmap(String photoPath, int targetW, int targetH) {
    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
    bmOptions.inJustDecodeBounds = true;
    BitmapFactory.decodeFile(photoPath, bmOptions);
    int photoW = bmOptions.outWidth;
    int photoH = bmOptions.outHeight;

    int scaleFactor = 1;
    if ((targetW > 0) || (targetH > 0)) {
      scaleFactor = Math.min(photoW/targetW, photoH/targetH); 
    }

    bmOptions.inJustDecodeBounds = false;
    bmOptions.inSampleSize = scaleFactor;
    bmOptions.inPurgeable = true;

    return BitmapFactory.decodeFile(photoPath, bmOptions);
  }

  private File setUpFile() {
    String albumName = "ejemplo";
    File albumDir;
    
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
      albumDir = new File(
          Environment.getExternalStoragePublicDirectory(
              Environment.DIRECTORY_PICTURES
              ), 
              albumName
          );    
    } else {
      albumDir = new File (
          Environment.getExternalStorageDirectory()
          + "/dcim/"
          + albumName);       
    }

    albumDir.mkdirs();

    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
      Locale.getDefault()).format(Calendar.getInstance().getTime());
    String imageFileName = "IMG_" + timeStamp + ".jpg";
    File imageF = new File(albumDir + "/" + imageFileName);
    return imageF;
  }  
}
