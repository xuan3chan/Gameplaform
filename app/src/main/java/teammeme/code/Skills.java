package teammeme.code;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Skills {
    private Bitmap bitmap;
    private Rect buttonRect;
    private int skillType;

    public Skills(Resources res, int drawableId, int left, int top, int right, int bottom, int type) {
        bitmap = BitmapFactory.decodeResource(res, drawableId);
        buttonRect = new Rect(left, top, right, bottom);
        skillType = type;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, null, buttonRect, null);
    }

    public boolean contains(int x, int y) {
        return buttonRect.contains(x, y);
    }

    public int getSkillType() {
        return skillType;
    }
}
