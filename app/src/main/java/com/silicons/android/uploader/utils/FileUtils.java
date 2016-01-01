package com.silicons.android.uploader.utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.util.Pair;
import android.util.Log;

import com.silicons.android.uploader.config.UploaderApplication;
import com.silicons.android.uploader.uploader.model.PhotoItem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.silicons.android.uploader.utils.LogUtils.makeLogTag;

/**
 * Created by Huynh Quang Thao on 12/30/15.
 */
public class FileUtils {

    private static final String TAG = makeLogTag(FileUtils.class);

    public static final int IO_BUFFER_SIZE = 8 * 1024;


    public static String[] loadStringArray(Context context, int ResourceId) {
        return context.getResources().getStringArray(ResourceId);
    }

    public static Drawable getDrawableFromResId(Context context, int id) {
        return context.getResources().getDrawable(id);
    }

    public static String readFile(String path) {
        //Get the text file
        File file = new File(path);

        //Read text from file
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
        } catch (IOException e) {
            //You'll need to add proper error handling here
            e.printStackTrace();
        }
        return text.toString();
    }

    public static boolean isExternalStorageRemovable() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            return Environment.isExternalStorageRemovable();
        }
        return true;
    }

    public static File getExternalCacheDir(Context context) {
        if (hasExternalCacheDir()) {
            return context.getExternalCacheDir();
        }

        // Before Froyo we need to construct the external cache dir ourselves
        final String cacheDir = "/Android/data/" + context.getPackageName() + "/cache/";
        return new File(Environment.getExternalStorageDirectory().getPath() + cacheDir);
    }

    public static boolean hasExternalCacheDir() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
    }

    /**
     * return pair of:
     * File: file object of image
     * String: path of this image. can be retrieved later
     */
    public static Pair<File, String> createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        String currentPhotoPath = "file:" + image.getAbsolutePath();
        Log.e(TAG, "Photo Path: " + currentPhotoPath);
        return new Pair<>(image, currentPhotoPath);
    }

    public static File createTemporaryFile(String part, String extension) throws Exception {
        File tempDir = Environment.getExternalStorageDirectory();
        tempDir = new File(tempDir.getAbsolutePath() + "/.temp/");
        if (!tempDir.exists()) {
            tempDir.mkdir();
        }
        return File.createTempFile(part, extension, tempDir);
    }

    public static byte[] convertBitmaptoByte(Bitmap bitmap) {
        //calculate how many bytes our image consists of.
        int bytes = byteSizeOf(bitmap);

        ByteBuffer buffer = ByteBuffer.allocate(bytes); //Create a new buffer
        bitmap.copyPixelsToBuffer(buffer); //Move the byte data to the buffer

        //Get the underlying array containing the data.
        return buffer.array();
    }

    protected static int byteSizeOf(Bitmap data) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR1) {
            return data.getRowBytes() * data.getHeight();
        }
        else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return data.getByteCount();
        }
        else {
            return data.getAllocationByteCount();
        }
    }

    public static String getImageNameFromUri(Uri uri) {
        Context context = UploaderApplication.getAppContext();
        String fileName = null;

        String scheme = uri.getScheme();
        if (scheme.equals("file")) {
            fileName = uri.getLastPathSegment();
        }
        else if (scheme.equals("content")) {
            String[] proj = { MediaStore.Images.Media.TITLE };
            Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
            if (cursor != null && cursor.getCount() != 0) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.TITLE);
                cursor.moveToFirst();
                fileName = cursor.getString(columnIndex);
            }
            if (cursor != null) {
                cursor.close();
            }
        }
        return fileName;
    }

    public static String getRealPathFromURI(Uri contentUri) {
        Cursor cursor = null;
        Context context = UploaderApplication.getAppContext();
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static String getExtension(PhotoItem item) {
        try {
            File file = new File(item.getPath());
            int lastIndexOf = file.getName().lastIndexOf('.');
            if (lastIndexOf > 0) {
                return file.getName().substring(lastIndexOf + 1).trim().toLowerCase(Locale.US);
            }
        } catch (Exception e1) {
            Log.e(TAG, e1.getClass().getSimpleName() + " : " + e1.getMessage());
        }
        return null;
    }


}
