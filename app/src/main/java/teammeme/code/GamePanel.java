package teammeme.code;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    Element myelement;
    private MainThread thread;
    private Bitmap bitmap;
    int mX;
    int mY;

    public GamePanel(Context context) {
        super(context);
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
            //canvas.drawBitmap(bitmap, mX, mY, null);

            if (myelement != null)
                myelement.doDraw(canvas);
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
}
