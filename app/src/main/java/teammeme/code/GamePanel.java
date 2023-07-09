package teammeme.code;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    Element myelement;
    ArrayList<Bullet> bullets=new ArrayList<Bullet>();
    int thoigiannapdan=0; //bang 10 moi ban tiep duoc, tao do tre khi ban
//    Bullet viendan;
    ParallaxBackground background; //bien hinh nen chuyen dong
    private MainThread thread;
    private Bitmap bitmap;
    int mX;
    int mY;

    public GamePanel(Context context) {
        super(context);
        background=new ParallaxBackground(this.getResources());
//        viendan = new Bullet(getResources(),0,0,R.drawable.lua);
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.hinhre);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // Handle surface changes if needed
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        // Update game state here
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            canvas.drawColor(Color.BLACK);
            background.doDrawRunning(canvas);
            thoigiannapdan++;
            //canvas.drawBitmap(bitmap, mX, mY, null);

            if (myelement != null){
                myelement.doDraw(canvas);
                this.doDrawBullet(canvas);
//                viendan.doDraw(canvas);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        if (myelement == null) {
            myelement = new Element(getResources(), (int) event.getX(), (int) event.getY());
            Log.d("abc", "khoi tao dau tien");
            return true;
        } else {
            myelement.mX = (int) event.getX() - myelement.bitmap.getWidth() / 2;
            myelement.mY = (int) event.getY() - myelement.bitmap.getHeight() / 2;
        }

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            myelement.mX = (int) event.getX() - myelement.bitmap.getWidth() / 2;
            myelement.mY = (int) event.getY() - myelement.bitmap.getHeight() / 2;
            Log.d("abc", "ddddddddddddddddddddddddddddown");
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            myelement.mX = (int) event.getX() - myelement.bitmap.getWidth() / 2;
            myelement.mY = (int) event.getY() - myelement.bitmap.getHeight() / 2;
            Log.d("abc", "uuuuuuuuuuuuuuuuuuuuuuuuuuuup");
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            myelement.mX = (int) event.getX() - myelement.bitmap.getWidth() / 2;
            myelement.mY = (int) event.getY() - myelement.bitmap.getHeight() / 2;
            Log.d("abc", "mmmmmmmmmmmmmmmmmmmmmmmmmmove");
        }

        return true;//super.onTouchEvent(event);

    }
    //ve tap hop cac vien dan
    public void doDrawBullet(Canvas canvas)
    {
        Paint p=new Paint();
        p.setColor(Color.WHITE);
        p.setTextSize(20);
        canvas.drawText("napdan:"+thoigiannapdan, 20, 20,p);

        if(thoigiannapdan>=30)
        {
            thoigiannapdan=0;
            Bullet motviendan=
                    new Bullet(getResources(), myelement.mX, myelement.mY,R.drawable.lua);

            bullets.add(motviendan);
        }
        for(int i=0;i<bullets.size();i++){
            bullets.get(i).doDraw(canvas);
        }
        Log.d("viendan", "sovien: "+bullets.size());
    }

}
