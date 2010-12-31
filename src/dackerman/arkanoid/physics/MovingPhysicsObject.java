package dackerman.arkanoid.physics;

import android.util.Log;



public class MovingPhysicsObject extends BasicPhysicsObject{
	public double dx = 0;
	public double dy = 0;
	
	double prevXpos = 0;
	double prevYpos = 0;
	
	public void setVector(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}
	
	public double getDx() {
		return dx;
	}
	
	public double getDy() {
		return dy;
	}
	
	public void move(double timeStep) {
		setPrev();
		xPos += dx * timeStep;
		yPos += dy * timeStep;
		//Log.i("XXXXXXXXXXXXX","physics says d="+dx+","+dy+" pos="+xPos+","+yPos+" timestep = "+timeStep);
	}
	
	public void revert() {
		double tmpX = xPos;
		double tmpY = yPos;
		
		xPos = prevXpos;
		yPos = prevYpos;
		
		prevXpos = tmpX;
		prevYpos = tmpY;
	}
	
	private void setPrev() {
		prevXpos = xPos;
		prevYpos = yPos;
	}

	public void bounce() {
		bounceX();
		bounceY();
	}
	
	public void bounceX() {
		dx = -dx;
	}
	
	public void bounceY() {
		dy = -dy;
	}

}
