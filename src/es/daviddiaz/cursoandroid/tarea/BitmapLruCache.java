package es.daviddiaz.cursoandroid.tarea;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader.ImageCache; 

public class BitmapLruCache extends LruCache<String, Bitmap>
implements ImageCache {
  
  private static final int CACHE_SIZE_IN_BYTES = 4 * 1024 * 1024;

  public BitmapLruCache() {
    super(CACHE_SIZE_IN_BYTES);
  }

  @Override
  public Bitmap getBitmap(String arg0) {
    return get(arg0);
  }

  @Override
  public void putBitmap(String arg0, Bitmap arg1) {
    put(arg0, arg1);
  }
  
  @Override
  protected int sizeOf(String key, Bitmap value) {
    return value.getRowBytes() * value.getHeight();
  }
}
