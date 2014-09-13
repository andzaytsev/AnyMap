package edu.uiuc.anymap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.MailTo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.lang.String;

import java.io.File;

public class MyActivity extends Activity {

    private MyLocationListener locationListener;
    private LocationManager locationManager;

    private Uri imageUri;
    private static int TAKE_PICTURE = 1;
    private String logtag = "AnyMap";
    private File photoFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        locationListener = new MyLocationListener();
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        locationListener.locString = "Lat: " + location.getLatitude() +
                "\nLng: " + location.getLongitude();
        ;

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                500, 10, locationListener);
        Log.i("MyActivity", "teeheee");
        Button cameraButton = (Button)findViewById(R.id.button_camera);
        cameraButton.setOnClickListener(cameraListener);
    }

    public void showLocation(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(MyActivity.this);
        builder.setTitle("My location");
        String locStr = locationListener.locString;
        builder.setMessage(locStr + " und das ist " +
                (locStr == null ? "nicht " : "") + "gut!");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
    private OnClickListener cameraListener = new OnClickListener () {
        public void onClick(View v) {
            System.out.println("lala");
            takePhoto(v);

        }
    };

    private void takePhoto(View v) {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        photoFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "picture.jpg");
        imageUri = Uri.fromFile(photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode,resultCode, intent);

        if(resultCode == Activity.RESULT_OK) {
            Uri selectedImage = imageUri;

            System.out.println(imageUri);

            getContentResolver().notifyChange(selectedImage, null);

            ImageView imageView = (ImageView)findViewById(R.id.image_camera);
            //WebView wv = (WebView) findViewById(R.id.image_camera);
            //wv.getSettings().setBuiltInZoomControls(true);

            ContentResolver cr = getContentResolver();
            Bitmap bitmap;

            //Try saving the image
            try{
                bitmap = MediaStore.Images.Media.getBitmap(cr, selectedImage);
                System.out.println(photoFile);
                imageView.setImageBitmap(loadImage(""+photoFile));
                //wv.loadUrl(""+photoFile);
                System.out.println("#2:" + loadImage(""+photoFile));
                //imageView.setImageDrawable(R.drawable.ic_launcher);
                Toast.makeText(MyActivity.this, selectedImage.toString(), Toast.LENGTH_LONG).show();
            } catch(Exception e) {
                System.out.println("adfadfadfadfa");
            }

        }
    }

    private Bitmap loadImage(String imgPath) {
        BitmapFactory.Options options;
        try {
            options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            Bitmap bitmap = BitmapFactory.decodeFile(imgPath, options);
            return bitmap;
        } catch(Exception e) {
            System.out.println("errorrrr");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
