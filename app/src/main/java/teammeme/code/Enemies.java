package teammeme.code;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

import java.util.Random;

public class Enemies {
    int x;
    int y;
    Bitmap bitmap;
    int []tocdo={5,7,30};
    int []manghinh={R.drawable.camlansuc,
            R.drawable.caube,
            R.drawable.miko};
    int e_ngnhien;

    public Enemies(Resources res, int rong_cv, int cao_cv)
    {
        Random rand=new Random();
        e_ngnhien=rand.nextInt(3);
        Log.d("nn",""+e_ngnhien);
        this.x=rong_cv;//x tu phai
        int a=0+(int)(Math.random()*((cao_cv-0)+1));
        this.y=a;
        bitmap= BitmapFactory.decodeResource(res,manghinh[e_ngnhien]);
    }
    public Enemies(Resources res,int x,int y, int hinh)
    {
        this.x=x;
        this.y=y;
        bitmap=BitmapFactory.decodeResource(res, hinh);
    }
    public void doDraw(Canvas canvas)
    {
        canvas.drawBitmap(bitmap, x,y, null);
        x-=tocdo[e_ngnhien]; //tru vi chay phai sang trai

    }
    public void setXY(int x,int y)
    {
        this.x=x;
        this.y=y;
    }

    public int getWidth()
    {
        return bitmap.getWidth();
    }
    public int getHeight()
    {
        return bitmap.getHeight();
    }
    public int gettamX()
    {
        //tam x=toa do x cong voi nua rong
        return x+(bitmap.getWidth()/2);
    }

    public int gettamY()
    {
        //tam y=toa do y cong nua cao
        return y+(bitmap.getHeight()/2);
    }


}

