package teammeme.code;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class ParallaxBackground {
    private int toadonen1_X = 0;
    private int toadonen2_X = 0;
    private Bitmap hinhnen1;
    private Bitmap hinhnen2;

    public ParallaxBackground(Resources c)
    {
        hinhnen1 = BitmapFactory.decodeResource(c, R.drawable.khonggian);
        hinhnen2 = BitmapFactory.decodeResource(c, R.drawable.hanhtinh);
        //hinhnen3 = BitmapFactory.decodeResource(c, R.drawable.nui);
    }
    public void doDrawRunning(Canvas canvas) {

        //giam toa do de dich chuyen cho nen1
        toadonen1_X = toadonen1_X - 1;

        //giam toa do de dich chuyen cho nen2
        toadonen2_X = toadonen2_X - 4;

        // tinh do lech cho hinh 2 (xem hinh minh hoa)
        int toadonen1_phu_X = hinhnen1.getWidth() - (-toadonen1_X);

        //da di chuyen het thi quay lai tu dau
        if (toadonen1_phu_X <= 0) {
            toadonen1_X = 0;
            // chi can ve 1 tam
            canvas.drawBitmap(hinhnen1,0, 0, null);

        } else {
            // ve 1 tam lech va tam 2 noi duoi theo
            canvas.drawBitmap(hinhnen1, toadonen1_X, 0, null);
            canvas.drawBitmap(hinhnen1, toadonen1_phu_X, 0, null);
        }

        int toadonen2_phu_X = hinhnen2.getWidth() - (-toadonen2_X);

        if (toadonen2_phu_X <= 0) {
            toadonen2_X = 0;
            canvas.drawBitmap(hinhnen2, toadonen2_X, 0, null);

        } else {
            canvas.drawBitmap(hinhnen2, toadonen2_X, 0, null);
            canvas.drawBitmap(hinhnen2, toadonen2_phu_X, 0, null);
        }

    }


}
