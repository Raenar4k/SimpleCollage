package com.raenarapps.simplecollage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;

public class ShareTask extends AsyncTask<Bitmap, Void, Void> {
    private Context context;

    public ShareTask(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Bitmap... params) {
        Bitmap inputBitmap = params[0];
        if (!inputBitmap.isRecycled()) {
            Bitmap copyBitmap = inputBitmap.copy(inputBitmap.getConfig(), true);
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, "#testtest");
            String uriString = MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    copyBitmap, "shared_collage", "temp file");
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(uriString));
            shareIntent.setType("image/*");
            context.startActivity(shareIntent);
        } else {
            Log.d("ShareTask", "cannot create share intent: bitmap is recycled");
        }
        return null;
    }
}
