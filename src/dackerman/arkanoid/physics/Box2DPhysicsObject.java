package dackerman.arkanoid.physics;

import org.jbox2d.collision.PolygonDef;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

import android.util.Log;


public class Box2DPhysicsObject implements PhysicsObject {
	Body body;
	World world;
	
	float xPos = 0;
	float yPos = 0;
	
	float hx = 0;
	float hy = 0;
	
	public Box2DPhysicsObject(World world) {
		this.world = world;
	}
	
	public void startSimulating() {
		BodyDef def = new BodyDef();
		def.position = new Vec2(xPos, yPos);
		Log.i("physics", "x:"+xPos+" y:"+yPos);
		body = world.createBody(def);
		PolygonDef shape = new PolygonDef();
		shape.setAsBox(hx, hy);
		shape.density = 1.0f;
		shape.friction = 0.3f;
		shape.restitution = 0.4f;
		body.createShape(shape);
		body.setMassFromShapes();
		body.setAngularVelocity(1f + (float)Math.random()*5f);
	}

	@Override
	public double getX() {
		if(body != null) return body.getPosition().x;
		else return xPos;
	}

	@Override
	public double getY() {
		if(body != null) return body.getPosition().y;
		else return yPos;
	}

	@Override
	public void setPosition(double x, double y) {
		xPos = (float)x;
		yPos = (float)y;
	}
	
	public void setDimensions(float hx, float hy) {
		this.hx = hx;
		this.hy = hy;
	}

	@Override
	public float getRotation() {
		if(body != null) return (float)Math.toDegrees(body.getAngle());
		else return 0;
	}

}
