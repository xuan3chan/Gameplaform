package teammeme.code;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class MainThread extends Thread {

    private SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    private boolean running;

    public MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel) {
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        Canvas canvas = null;
        long frameCount = 0L;

        while (running) {
            try {
                canvas = surfaceHolder.lockCanvas();
                if (canvas != null) {
                    synchronized (surfaceHolder) {
                        gamePanel.update(); // Update game state
                        gamePanel.draw(canvas); // Render to the canvas
                    }
                }
            } catch (Exception e) {
                Log.e("MainThread", "Error in game loop: " + e.getMessage());
            } finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }

            frameCount++;
            Log.d("testloop", "Loop: " + frameCount);
        }
    }
}
