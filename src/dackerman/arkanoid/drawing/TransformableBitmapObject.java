package dackerman.arkanoid.drawing;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

public class TransformableBitmapObject implements BitmapObject, DrawableObject {
	protected int xPixel = 0;
	protected int yPixel = 0;
	private int width;
	private int height;
	Drawable drawable;
	
	public int alpha = 255;
	public TransformableBitmapObject(Drawable drawable) {
		this.drawable = drawable;
		width = drawable.getIntrinsicWidth();
		height = drawable.getIntrinsicHeight();
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
		c.save();
		int halfwidth = width /2;
		int halfheight = height /2;
		c.rotate(rotation, xPixel, yPixel);
		drawable.setBounds(xPixel - halfwidth, yPixel - halfheight, xPixel + halfwidth, yPixel + halfheight);
		drawable.setAlpha(alpha);
		drawable.draw(c);
		c.restore();
	}
	float rotation = 0;
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}
}
