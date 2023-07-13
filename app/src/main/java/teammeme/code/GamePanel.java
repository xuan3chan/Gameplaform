package teammeme.code;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    Element myelement;
    ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    int thoigiannapdan = 0; // bang 10 moi ban tiep duoc, tao do tre khi ban
    ParallaxBackground background; // bien hinh nen chuyen dong
    private MainThread thread;
    private Bitmap bitmap;
    int mX;
    int mY;
    int score = 0;
    private SoundPool soundPool;
    private int enemyAppearSound;
    private int enemyDestroySound;
    private AudioManager audioManager;
    private float volume;
    //varskill
    private Skills skillButton1;
    private Skills skillButton2;
    private Skills skillButton3;
    private boolean isSkill1Activated;
    private boolean isSkill2Activated;
    private boolean isSkill3Activated;

    ArrayList<Enemies> enemies = new ArrayList<Enemies>();
    int thoigiankethu = 0; // thoi gian ra ke thu, 10 se ra
    Enemies motkethu;

    public GamePanel(Context context) {
        super(context);
        background = new ParallaxBackground(this.getResources());
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.hinhre);
        //khaisusound
        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        enemyAppearSound = soundPool.load(context, R.raw.kimchihanquoc, 1);
        enemyDestroySound = soundPool.load(context, R.raw.caubesound, 1);
        //khaicontructorskill
//        skillButton1 = new Skills(getResources(), R.drawable.skill1_button, 100, 100, 200, 200, 1);
//        skillButton2 = new Skills(getResources(), R.drawable.skill2_button, 300, 100, 400, 200, 2);
//        skillButton3 = new Skills(getResources(), R.drawable.skill3_button, 500, 100, 600, 200, 3);

        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        volume = (float) currentVolume / maxVolume;
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
            thoigiankethu++;
            //canvas.drawBitmap(bitmap, mX, mY, null);

            if (myelement != null){
                myelement.doDraw(canvas);
                this.doDrawBullet(canvas);
                this.doDrawEnemies(canvas);
                xetvacham(canvas);
//                viendan.doDraw(canvas);
            }
        }
            skillButton1.draw(canvas);
            skillButton2.draw(canvas);
            skillButton3.draw(canvas);
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
        //touchskill
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            if (skillButton1.contains((int) event.getX(), (int) event.getY())) {
//                isSkill1Activated = true;
//                activateSkill1();
//            } else if (skillButton2.contains((int) event.getX(), (int) event.getY())) {
//                isSkill2Activated = true;
//                activateSkill2();
//            } else if (skillButton3.contains((int) event.getX(), (int) event.getY())) {
//                isSkill3Activated = true;
//                activateSkill3();
//            }
//        } else if (event.getAction() == MotionEvent.ACTION_UP) {
//            isSkill1Activated = false;
//            isSkill2Activated = false;
//            isSkill3Activated = false;
//        }

// Thêm các phương thức mới để xử lý kích hoạt kỹ năng:
//        private void activateSkill1() {
//            if (isSkill1Activated) {
//                // Bắn 5 viên đạn tỏa ra hướng trước
//                // Thêm mã xử lý tạo đạn và di chuyển đạn theo hướng mong muốn
//            }
//        }
//
//        private void activateSkill2() {
//            if (isSkill2Activated) {
//                // Bắn 5 viên đạn hình vòng tròn về phía trước
//                // Thêm mã xử lý tạo đạn và di chuyển đạn theo hướng mong muốn
//            }
//        }
//
//        private void activateSkill3() {
//            if (isSkill3Activated) {
//                // Bắn 5 viên đạn nhanh về phía trước
//                // Thêm mã xử lý tạo đạn và di chuyển đạn theo hướng mong muốn
//            }
//        }
//
//// Trong phương thức update, kiểm tra xem các kỹ năng đã được kích hoạt hay chưa và thực hiện xử lý tương ứng:
//        public void update() {
//            // Các phần xử lý cập nhật khác
//
//            if (isSkill1Activated) {
//                // Thực hiện cập nhật cho kỹ năng 1
//            }
//
//            if (isSkill2Activated) {
//                // Thực hiện cập nhật cho kỹ năng 2
//            }
//
//            if (isSkill3Activated) {
//                // Thực hiện cập nhật cho kỹ năng 3
//            }
//        }
//
//
        return true;//super.onTouchEvent(event);

    }
    //ve tap hop cac vien dan
    public void doDrawBullet(Canvas canvas)
    {
        int rectLeft = 10;
        int rectTop = 30;
        int rectRight = rectLeft + (thoigiannapdan * 10);
        int rectBottom = 10;
        Paint p=new Paint();
        p.setColor(Color.WHITE);
        p.setTextSize(50);
        canvas.drawRect(rectLeft, rectTop, rectRight, rectBottom, p);
        canvas.drawText("Nạp đạn:"+thoigiannapdan*10, 70, 70,p);
        if(thoigiannapdan>=30)
        {
            thoigiannapdan=0;
            Bullet motviendan=
                    new Bullet(getResources(), myelement.mX, myelement.mY,R.drawable.lua);

            bullets.add(motviendan);
        }
        for(int i=0;i<bullets.size();i++)
            bullets.get(i).doDraw(canvas);
        for(int i=0;i<bullets.size();i++)
            if(bullets.get(i).x>canvas.getWidth())
                bullets.remove(i);

        Log.d("viendan", "sovien: "+bullets.size());
        
    }
    public void doDrawEnemies(Canvas canvas) {

        if(thoigiankethu>=50) //hoisinhkethu
        {
            thoigiankethu=0;
            Enemies motkethu=new Enemies(getResources(),
                    canvas.getWidth(),canvas.getHeight());
            enemies.add(motkethu);
            //soundPool.play(enemyAppearSound, volume, volume, 10, 0, 1.0f);
        }
        for(int i=0;i<enemies.size();i++)
            enemies.get(i).doDraw(canvas);

        for(int i=0;i<enemies.size();i++)
            if(enemies.get(i).y<0)
                enemies.remove(i);
        Log.d("viendan","so vien: "+enemies.size());

    }

    public boolean vc_b_e(Bullet bullet,Enemies enemies)
    {

        float nuarong_b=(float)bullet.getWidth()/2;
        int nuacao_b=bullet.getHeight()/2;
        float nuarong_e=(float)enemies.getWidth()/2;
        int nuacao_e=enemies.getHeight()/2;
        //khoang cach 2 tam theo x
        int kc_ht_x=Math.abs(bullet.gettamX()-enemies.gettamX());
        //khoang cach 2 tam theo y
        int kc_ht_y=Math.abs(bullet.gettamY()-enemies.gettamY());
        if(kc_ht_x<=nuarong_b+nuarong_e && kc_ht_y<=nuacao_b+nuacao_e)
            return true;
        else
            return false;
    }

    public  void xetvacham(Canvas canvas)
    {

        try{
            for(int i=0;i<bullets.size();i++)
                for(int j=0;j<enemies.size();j++)
                {
                    if(vc_b_e(bullets.get(i), enemies.get(j))==true)
                    {
                        bullets.remove(i);
                        enemies.remove(j);
                        score += 1;
                        soundPool.play(enemyDestroySound, volume, volume, 10, 0, 1f);
                    }
                }
        }catch(Exception e)
        {
            Log.d("loi",e.toString());
        }
        Paint p=new Paint();
        p.setColor(Color.WHITE);
        p.setTextSize(50);
        canvas.drawText("Score:"+score,500,70,p);

    }



}
