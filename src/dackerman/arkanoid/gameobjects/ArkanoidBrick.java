package dackerman.arkanoid.gameobjects;

import org.jbox2d.dynamics.World;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import dackerman.arkanoid.drawing.DrawableObject;
import dackerman.arkanoid.drawing.TransformableBitmapObject;
import dackerman.arkanoid.physics.Box2DPhysicsObject;

public class ArkanoidBrick implements DrawableObject {
	
	private TransformableBitmapObject bitmap;
	private Box2DPhysicsObject physics;
	private boolean dead = false;
	
	public ArkanoidBrick(Drawable drawable, World world) {
		bitmap = new TransformableBitmapObject(drawable);
		physics = new Box2DPhysicsObject(world);
		
		physics.setDimensions(getWidth()/40f, getHeight()/40f);
	}
	
	public int getX() {
		return transformX(physics.getX());
	}
	
	public int getY() {
		return transformY(physics.getY());
	}
	
	public int getCornerX() {
		return getX() - getWidth() / 2;
	}
	
	public int getCornerY() {
		return getY() - getWidth() / 2;
	}
	
	public int getWidth() {
		return bitmap.getWidth();
	}
	
	public int getHeight() {
		return bitmap.getHeight();
	}
	
	public void kill() {
		dead = true;
		physics.startSimulating();
	}
	
	public void setPixelPosition(int x, int y) {
		physics.setPosition(invTransformX(x), invTransformY(y));
	}
	
	public boolean isDead() {
		return dead;
	}
	
	public void draw(Canvas c) {
		int x = transformX(physics.getX());
		int y = transformY(physics.getY());
		bitmap.setPixelPosition(x, y);
		bitmap.setRotation(physics.getRotation());
		bitmap.draw(c);
	}
	
	public int transformX(double x) {
		return (int) (x * 20);
	}
	
	public float invTransformX(int x) {
		return x / 20.0f;
	}
	
	public int transformY(double y) {
		return (int)((25 - y) * 20);
	}
	
	public float invTransformY(int y) {
		return 25f - (y / 20.0f);
	}
}
