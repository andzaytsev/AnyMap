package edu.uiuc.anymap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.util.Queue;


public class Picture extends Activity {

    private Calculations calc;
    private float xc, yc;
    private MyLocationListener locationListener;
    private LocationManager locationManager;
    private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        //Location shit
        locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        locationListener = new MyLocationListener();
        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location == null)
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location != null)
            locationListener.locString = "Lat: " + location.getLatitude() +
                    "\nLng: " + location.getLongitude();

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                500, 10, locationListener);
        //End location shit

        Intent myIntent = getIntent();
        File photoFile = (File)myIntent.getExtras().get("file");
        Uri selectedImage = (Uri)myIntent.getExtras().get("Uri");

        final ImageView imageView = (ImageView)findViewById(R.id.image_camera);

        calc = new Calculations();

        System.out.println(selectedImage);

        imageView.setOnTouchListener(imageTouch);


        Button butOk = (Button)findViewById(R.id.button_ok);
        Button butCan = (Button)findViewById(R.id.button_can);
        butCan.setOnClickListener(canList);

        ContentResolver cr = getContentResolver();
        Bitmap bitmap;

        System.out.println(selectedImage);
        //Try saving the image
        try{
            bitmap = MediaStore.Images.Media.getBitmap(cr, selectedImage);
            imageView.setImageBitmap(loadImage(photoFile.toString()));
            Toast.makeText(Picture.this, selectedImage.toString(), Toast.LENGTH_LONG).show();
        } catch(Exception e) {
            System.out.println("Caught an exception saving an image!");
        }
    }

    private View.OnClickListener canList = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };

    private View.OnClickListener okList = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            calc.setPoint(new DoublePoint(xc, yc, (float)location.getLongitude(), (float)location.getLatitude()));
        }
    };

    private View.OnTouchListener imageTouch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            final ImageView imageLoc = (ImageView)findViewById(R.id.image_location);

            xc = event.getX(); yc = event.getY();
            RelativeLayout.LayoutParams params =
                    new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins((int)xc, (int)yc, 0, 0);
            imageLoc.setLayoutParams(params);
            return true;
        }
    };

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
        getMenuInflater().inflate(R.menu.picture, menu);
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
