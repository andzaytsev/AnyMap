package edu.uiuc.anymap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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


public class Picture extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        Intent myIntent = getIntent();
        File photoFile = (File)myIntent.getExtras().get("file");
        Uri selectedImage = (Uri)myIntent.getExtras().get("Uri");



        System.out.println(selectedImage);

        ImageView imageView = (ImageView)findViewById(R.id.image_camera);

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float xc = event.getX(), yc = event.getY();
                AlertDialog.Builder builder = new AlertDialog.Builder(Picture.this);
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
        });


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
