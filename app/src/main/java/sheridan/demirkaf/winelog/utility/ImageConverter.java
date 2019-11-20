package sheridan.demirkaf.winelog.utility;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class ImageConverter {

    public static String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG,20,baos);

        byte[] b = baos.toByteArray();

        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    public static Bitmap base64ToBitmap(String base64String) {
        byte[] imageAsBytes = Base64.decode(base64String.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }

}
