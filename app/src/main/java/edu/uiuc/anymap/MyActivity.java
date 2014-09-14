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
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.lang.String;

import java.io.File;

public class MyActivity extends Activity {

    private MyLocationListener locationListener;
    private LocationManager locationManager;

    private Uri imageUri;
    private static int TAKE_PICTURE = 1;
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
            takePhoto(v);

        }
    };

    private void takePhoto(View v) {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"/");
        directory.mkdirs();
        photoFile = new File(directory, "picture.jpg");
        imageUri = Uri.fromFile(photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode,resultCode, intent);

        if(resultCode == Activity.RESULT_OK) {
            Intent picture = new Intent(this, Picture.class);
            picture.putExtra("file", photoFile);
            picture.putExtra("Uri", imageUri);
            startActivity(picture);

        }
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
