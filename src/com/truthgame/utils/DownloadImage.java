package com.truthgame.utils;

import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DownloadImage extends AsyncTask<String, Void, Bitmap> {
	  ImageView bmImage;
	  TextView message;

	  public DownloadImage(ImageView bmImage, TextView message) {
	      this.bmImage = bmImage;
	      this.message = message;
	  }

	  protected Bitmap doInBackground(String... urls) {
	      String urldisplay = urls[0];
	      Bitmap mIcon11 = null;
	      try {
	        InputStream in = new java.net.URL(urldisplay).openStream();
	        mIcon11 = BitmapFactory.decodeStream(in);
	      } catch (Exception e) {
	          e.printStackTrace();
	      }
	      return mIcon11;
	  }

	  protected void onPostExecute(Bitmap result) {
	      bmImage.setImageBitmap(result);
	      bmImage.setVisibility(View.VISIBLE);
	      message.setVisibility(View.GONE);
	  }

}
