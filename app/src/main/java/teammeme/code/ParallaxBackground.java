package teammeme.code;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class ParallaxBackground {
    private int toadonen1_X = 0;
    private int toadonen2_X = 0;
    private int toadonen3_X = 0;
    private Bitmap hinhnen1;
    private Bitmap hinhnen2;
    private Bitmap hinhnen3;

    public ParallaxBackground(Resources resources) {
        hinhnen1 = BitmapFactory.decodeResource(resources, R.drawable.may);
        hinhnen2 = BitmapFactory.decodeResource(resources, R.drawable.nui);
        hinhnen3 = BitmapFactory.decodeResource(resources, R.drawable.dat);
    }

    public void doDrawRunning(Canvas canvas) {
        // Update X-coordinate for background 1
        toadonen1_X -= 1;
        if (toadonen1_X <= -hinhnen1.getWidth()) {
            toadonen1_X = 0;
        }

        // Update X-coordinate for background 2
        toadonen2_X -= 4;
        if (toadonen2_X <= -hinhnen2.getWidth()) {
            toadonen2_X = 0;
        }

        // Update X-coordinate for background 3
        toadonen3_X -= 9;
        if (toadonen3_X <= -hinhnen3.getWidth()) {
            toadonen3_X = 0;
        }

        // Draw the backgrounds
        canvas.drawBitmap(hinhnen1, toadonen1_X, 0, null);
        canvas.drawBitmap(hinhnen1, toadonen1_X + hinhnen1.getWidth(), 0, null);

        canvas.drawBitmap(hinhnen2, toadonen2_X, -80, null);
        canvas.drawBitmap(hinhnen2, toadonen2_X + hinhnen2.getWidth(), -80, null);

        canvas.drawBitmap(hinhnen3, toadonen3_X, -100, null);
        canvas.drawBitmap(hinhnen3, toadonen3_X + hinhnen3.getWidth(), -100, null);
    }
}
