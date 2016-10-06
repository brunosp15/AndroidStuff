ckage br.com.consultasaude.utils;

import android.content.Context;
import android.net.Uri;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by bpinto2 on 9/20/16.
 */

public class FileUtils {

    public static File getFileFromUri(Uri fileUri, Context context) throws IOException {
        File file = new File(context.getCacheDir(), "photo.jpg");
        InputStream inputStream = context.getContentResolver().openInputStream(fileUri);
        OutputStream output = new FileOutputStream(file);
        byte[] buffer = new byte[4 * 1024]; // or other buffer size
        int read;

        if (inputStream != null) {
            while ((read = inputStream.read(buffer)) != -1) {
                output.write(buffer, 0, read);
            }
        }
        output.flush();
        return file;
    }
}


