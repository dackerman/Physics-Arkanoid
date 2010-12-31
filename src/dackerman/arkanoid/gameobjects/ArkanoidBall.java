package dackerman.arkanoid.gameobjects;

import android.content.res.Resources;
import android.graphics.Canvas;
import dackerman.arkanoid.R;
import dackerman.arkanoid.drawing.BasicBitmapObject;
import dackerman.arkanoid.drawing.DrawableObject;
import dackerman.arkanoid.physics.MovingPhysicsObject;

public class ArkanoidBall implements DrawableObject{
	public MovingPhysicsObject physics;
	private BasicBitmapObject bitmap;
	
	public ArkanoidBall(Resources res) {
		 physics = new MovingPhysicsObject();
		 bitmap = new BasicBitmapObject(res, R.drawable.ball);
	}

	@Override
	public void draw(Canvas c) {
		int x = getX();
		int y = getY();
		bitmap.setPixelPosition(x, y);
		bitmap.draw(c);
	}

	public int getWidth() {
		return bitmap.getWidth();
	}
	
	public int getHeight() {
		return bitmap.getHeight();
	}

	public int getX() {
		return (int)physics.getX();
	}
	
	public int getY() {
		return (int)physics.getY();
	}

	public void setPixelPosition(int x, int y) {
		physics.setPosition(x, y);
	}
	
	
	
}
