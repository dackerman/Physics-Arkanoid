package dackerman.arkanoid.physics;


public class BasicPhysicsObject implements PhysicsObject {
	protected double xPos = 10;
	protected double yPos = 400;
	
	public void setPosition(double x, double y) {
		xPos = x;
		yPos = y;
	}
	
	public double getX() {
		return xPos;
	}
	
	public double getY() {
		return yPos;
	}

	@Override
	public float getRotation() {
		return 0;
	}
}
