package nothing.impossible.com.nothing.Activity;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import nothing.impossible.com.nothing.R;


public class FullScreenViewActivity extends Activity {
	public static final String TAG_SEL_IMAGE = "selectedImage";
	private String selectedPhoto;
	private ImageView fullImageView;
	private LinearLayout llSetWallpaper, llDownloadWallpaper;

	// Picasa JSON response node keys
	private static final String TAG_ENTRY = "entry",
			TAG_MEDIA_GROUP = "media$group",
			TAG_MEDIA_CONTENT = "media$content", TAG_IMG_URL = "url",
			TAG_IMG_WIDTH = "width", TAG_IMG_HEIGHT = "height";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fullscreen_image);

		fullImageView = (ImageView) findViewById(R.id.imgFullscreen);
		llSetWallpaper = (LinearLayout) findViewById(R.id.llSetWallpaper);
		llDownloadWallpaper = (LinearLayout) findViewById(R.id.llDownloadWallpaper);


		// hide the action bar in fullscreen mode
//		getActionBar().hide();

//		utils = new Utils(getApplicationContext());

		// layout click listeners

//		llDownloadWallpaper.setOnClickListener(this);

		// setting layout buttons alpha/opacity
		llSetWallpaper.getBackground().setAlpha(70);
		llDownloadWallpaper.getBackground().setAlpha(70);

		Intent i = getIntent();
//		selectedPhoto = i.getS(TAG_SEL_IMAGE);
		selectedPhoto=i.getStringExtra(TAG_SEL_IMAGE);

		// check for selected photo null
		if (selectedPhoto != null) {

			// fetch photo full resolution image by making another json request
			Glide.with(this).load(selectedPhoto)
					.centerCrop()
					.thumbnail(0.5f)
					.diskCacheStrategy(DiskCacheStrategy.ALL)
					.into(fullImageView);

		} else {
			Toast.makeText(getApplicationContext(),
					getString(R.string.msg_unknown_error), Toast.LENGTH_SHORT)
					.show();
		}

        llSetWallpaper.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            Glide.with(getApplication())
            .load(selectedPhoto)
            .asBitmap()
            .into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {

                    try {
                        WallpaperManager.getInstance(getApplicationContext()).setBitmap(resource);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
                Toast.makeText(getApplicationContext(),"Set Wallpaper",Toast.LENGTH_SHORT).show();

            }
        });
        llDownloadWallpaper.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Glide.with(getApplication())
                        .load(selectedPhoto)
                        .asBitmap()
                        .into(new SimpleTarget<Bitmap>(100,100) {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation)  {
                                saveImage(resource);
                            }
                        });
            }
        });
	}
    private String saveImage(Bitmap image) {
        String savedImagePath = null;

        String imageFileName = "JPEG_" + "Motivation" + ".jpg";
        File storageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                + "/Motivation Wallpaper");
        boolean success = true;
        if (!storageDir.exists()) {
            success = storageDir.mkdirs();
        }
        if (success) {
            File imageFile = new File(storageDir, imageFileName);
            savedImagePath = imageFile.getAbsolutePath();
            try {
                OutputStream fOut = new FileOutputStream(imageFile);
                image.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                fOut.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Add the image to the system gallery
            galleryAddPic(savedImagePath);
            Toast.makeText(getApplicationContext(), "IMAGE SAVED", Toast.LENGTH_LONG).show();
        }
        return savedImagePath;
    }

    private void galleryAddPic(String imagePath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(imagePath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
    }



//	@Override
//	public void onClick(View v) {
//		Bitmap bitmap = ((BitmapDrawable) fullImageView.getDrawable())
//				.getBitmap();
//		switch (v.getId()) {
//		// button Download Wallpaper tapped
//		case R.id.llDownloadWallpaper:
//
//			break;
//		// button Set As Wallpaper tapped
//		case R.id.llSetWallpaper:
//			Toast.makeText(this,selectedPhoto+"",Toast.LENGTH_SHORT).show();
////			try {
////				WallpaperManager wm = WallpaperManager.getInstance(this);
////
////				wm.setBitmap(bitmap);
////				Toast.makeText(this,
////						this.getString(R.string.toast_wallpaper_set),
////						Toast.LENGTH_SHORT).show();
////			} catch (Exception e) {
////				e.printStackTrace();
////				Toast.makeText(this,
////						this.getString(R.string.toast_wallpaper_set_failed),
////						Toast.LENGTH_SHORT).show();
////			}
//			break;
//		default:
//			break;
//		}
//
//	}
}