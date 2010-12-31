package dackerman.arkanoid.drawing;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class BasicBitmapObject implements BitmapObject, DrawableObject {
	protected int xPixel = 0;
	protected int yPixel = 0;
	private int width;
	private int height;
	Bitmap bitmap;
	
	protected Paint color = null;
	public BasicBitmapObject(Resources res, int drawableId) {
		bitmap = BitmapFactory.decodeResource(res, drawableId);
		width = bitmap.getWidth();
		height = bitmap.getWidth();
	};
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setPixelPosition(int x, int y) {
		xPixel = x;
		yPixel = y;
	}
	
	public int getX() {
		return xPixel;
	}
	
	public int getY() {
		return yPixel;
	}
	
	public void draw(Canvas c) {
		c.drawBitmap(bitmap, xPixel, yPixel, color);
	}
}
