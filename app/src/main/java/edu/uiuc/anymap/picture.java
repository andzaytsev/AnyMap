package edu.uiuc.anymap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;


public class picture extends Activity {
    private MyLocationListener locationListener;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        //imageView.setOnTouchListener(imageListener);

        Intent myIntent = getIntent();
        Uri selectedImage = (Uri) myIntent.getExtras().get("fileUri");
        File imageFile = (File)myIntent.getExtras().get("fileFile");

        getContentResolver().notifyChange(selectedImage, null);

        ImageView imageView = (ImageView)findViewById(R.id.image_camera);

        //setUpImage(selectedImage, imageView, imageFile);
        //ContentResolver cr = getContentResolver();

        //Drawable[] drawables = new Drawable[2];
        Bitmap bitmap;
        ContentResolver cr = getContentResolver();


        //Try saving the image
        try{
            //drawables[0] = new BitmapDrawable(getResources(), imageFile.toString());

            bitmap = MediaStore.Images.Media.getBitmap(cr, selectedImage);
            imageView.setImageBitmap(loadImage(imageFile.toString()));


            //imageView.setImageDrawable(drawables[0]);
            Toast.makeText(picture.this, selectedImage.toString(), Toast.LENGTH_LONG).show();
        } catch(Exception e) {
            System.out.println("Caught an exception saving an image!");
        }

        locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        locationListener = new MyLocationListener();
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        locationListener.locString = "Lat: " + location.getLatitude() +
                "\nLng: " + location.getLongitude();
        ;

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                500, 10, locationListener);
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

    /*private View.OnTouchListener imageListener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            float xc = event.getX(), yc = event.getY();
            AlertDialog.Builder builder = new AlertDialog.Builder(picture.this);
            builder.setTitle("Touched the image");
            builder.setMessage("X: " + xc + "\nY: " + yc);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.create().show();
            return true;
        }
    };

*/
    private void setUpImage(String selectedImage, ImageView imageView, File imageFile) {
        //ContentResolver cr = getContentResolver();

        Drawable[] drawables = new Drawable[2];
        Bitmap bitmap;

        //Try saving the image
        try{
            drawables[0] = new BitmapDrawable(getResources(), imageFile.toString());

            System.out.println(imageView);
            imageView.setImageDrawable(drawables[0]);
            Toast.makeText(picture.this, selectedImage, Toast.LENGTH_LONG).show();
        } catch(Exception e) {
            System.out.println("Caught an exception saving an image!");
        }
    }
    /*

    public void showLocation(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(picture.this);
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
    }*/
}
