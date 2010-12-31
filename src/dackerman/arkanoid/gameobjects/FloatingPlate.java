package dackerman.arkanoid.gameobjects;

import android.content.res.Resources;
import android.graphics.Canvas;
import dackerman.arkanoid.R;
import dackerman.arkanoid.drawing.BasicBitmapObject;
import dackerman.arkanoid.drawing.DrawableObject;

public class FloatingPlate implements DrawableObject {
	private int gameWidth = 320;
	private int gameHeight = 480;
	private int topEdge;
	
	private BasicBitmapObject bitmap;
	
	boolean changed = false;
	
	public FloatingPlate(Resources res) {
		bitmap = new BasicBitmapObject(res, R.drawable.plate);
		bitmap.setPixelPosition(10, gameHeight - 80);
		topEdge = bitmap.getY();
	}
	
	public FloatingPlate(Resources res, int gameWidth, int gameHeight) {
		this(res);
		this.gameWidth = gameWidth;
		this.gameHeight = gameHeight;
	}
	
	public void moveBy(double dx, double dy) {
		if(movementInsideBounds(dx)) {
			bitmap.setPixelPosition((int)(bitmap.getX() + dx), bitmap.getY());
		}
	}
	
	private boolean movementInsideBounds(double dx) {
		return (bitmap.getX() + bitmap.getWidth() + dx < gameWidth) &&
			   (bitmap.getX() + dx > 0);
	}
	
	public void draw(Canvas c) {
		bitmap.draw(c);
	}

	public int getTopEdge() {
		return topEdge;
	}
	
	public int getLeftEdge() {
		return bitmap.getX();
	}
	
	public int getRightEdge() {
		return bitmap.getX() + bitmap.getWidth();
	}
	
	
}
