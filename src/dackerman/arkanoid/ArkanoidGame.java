package dackerman.arkanoid;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jbox2d.collision.AABB;
import org.jbox2d.collision.PolygonDef;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import dackerman.arkanoid.drawing.DrawableObject;
import dackerman.arkanoid.gameobjects.ArkanoidBall;
import dackerman.arkanoid.gameobjects.ArkanoidBrick;
import dackerman.arkanoid.gameobjects.FloatingPlate;

public class ArkanoidGame implements SensorEventListener {
	Resources res;

	DrawingThread drawingThread;
	SurfaceHolder mSurfaceHolder;
	boolean isRunning = false;

	Bitmap background;
	FloatingPlate player;
	ArkanoidBall ball;

	List<ArkanoidBrick> bricks = new ArrayList<ArkanoidBrick>();
	List<DrawableObject> drawableObjects = new ArrayList<DrawableObject>();

	private List<Level> levels;
	private XmlResourceParser levelsXml;
	
	public ArkanoidGame(SurfaceHolder holder, Context context) {
		mSurfaceHolder = holder;
		res = context.getResources();
		background = BitmapFactory.decodeResource(res, R.drawable.starry_background);
		initGame();
		drawingThread = new DrawingThread();
		//SensorManager sensorman = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
		//List<Sensor> sensors = sensorman.getSensorList(Sensor.TYPE_ORIENTATION);
		//sensorman.registerListener(this, sensors.get(0), SensorManager.SENSOR_DELAY_GAME);
		// levelsXml = context.getResources().getXml(R.xml.levels);
		// levels = Level.getLevels(levelsXml, res);
	}
	

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		
	}
	Vec2 gravity = new Vec2(0, -10);
	@Override
	public void onSensorChanged(SensorEvent event) {
		Log.i("orientation raw",String.valueOf(event.values[2]));
		double orientation = Math.toRadians(event.values[2]);
		gravity.x = -(float)(10.0 * Math.sin(orientation));
		gravity.y = -(float)(10.0 * Math.cos(orientation));
		
	}

	World world;

	private void initGame() {
		// box2d
		AABB boundingbox = new AABB(new Vec2(-1, -1), new Vec2(20, 25));
		world = new World(boundingbox, gravity, true);

		// create ground for blocks to collide with
		BodyDef bd = new BodyDef();
		bd.position.set(10f, 2.0f);
		PolygonDef sd = new PolygonDef();
		sd.setAsBox(9.5f, 1f);
		Body ground = world.createBody(bd);
		ground.createShape(sd);

		// game ball
		ball = new ArkanoidBall(res);
		ball.setPixelPosition(100, 350);
		ball.physics.setVector(Math.random() * 250 - 100, -Math.random() * 50 - 100);
		drawableObjects.add(ball);

		// add player
		player = new FloatingPlate(res, 320, 480);
		drawableObjects.add(player);

		// add bricks
		Drawable standardBrick = res.getDrawable(R.drawable.block);
		Drawable meanBlock = res.getDrawable(R.drawable.mean_block);
		int bx = 50;
		int by = 50;
		for(int x=0;x<4;x++) {
			ArkanoidBrick brick = new ArkanoidBrick(meanBlock,world);
			brick.setPixelPosition(bx, by);
			bx += brick.getWidth() + 2;
			drawableObjects.add(brick);
			bricks.add(brick);
		}
		by += bricks.get(0).getHeight() + 5;
		for (int y = 0; y < 4; y++) {
			bx = 50 + (y%2==0 ? 0 : 20);
			for (int x = 0; x < 3; x++) {
				ArkanoidBrick brick = new ArkanoidBrick(standardBrick, world);
				brick.setPixelPosition(bx, by);
				bx += brick.getWidth() + 2;
				drawableObjects.add(brick);
				bricks.add(brick);
			}
			by += bricks.get(bricks.size()-1).getHeight()+5;
		}
	}

	private double xMoveScale = 100;
	private double yMoveScale = 100;

	public void trackballMoved(MotionEvent event) {
		player.moveBy(event.getX() * xMoveScale, event.getY() * yMoveScale);
	}
	
	private void loseGame() {
		isRunning = false;
		Canvas c = null;
		try{
			c = mSurfaceHolder.lockCanvas();
			synchronized(mSurfaceHolder) {
				Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.died);
				c.drawBitmap(bitmap, 0,0 , null);
			}
		}finally {
			if (c != null) {
				mSurfaceHolder.unlockCanvasAndPost(c);
			}
		}
		
	}

	class DrawingThread extends Thread {
		double timeStep = 1/30f;
		// gives us 30fps to start with
		long starttime;
		long endtime;

		@Override
		public void run() {
			float b2dTimeStep = 1.0f / 30.0f;
			while (isRunning) {
				starttime = new Date().getTime();
				lockAndDraw();
				//Log.i("Gravity", String.format("x:%f,y:%f", gravity.x, gravity.y));
				world.setGravity(gravity);
				world.step(b2dTimeStep, 6);
				calculatePositions(timeStep);
				endtime = new Date().getTime();
				timeStep = (endtime - starttime) / 1000.0;
			}
		}

		private void lockAndDraw() {
			Canvas c = null;
			try {
				c = mSurfaceHolder.lockCanvas(null);
				synchronized (mSurfaceHolder) {
					doDraw(c);
				}
			} finally {
				if (c != null) {
					mSurfaceHolder.unlockCanvasAndPost(c);
				}
			}
		}

		private void calculatePositions(double timeStep) {
			ball.physics.move(timeStep);
			double THRESHOLD = ball.getWidth() / 2;
			int bx = ball.getX() + (int)THRESHOLD;
			int by = ball.getY() - (int)THRESHOLD;

			// Kill and bounce off arkanoid bricks
			for (int i = 0; i < bricks.size(); i++) {
				ArkanoidBrick brick = bricks.get(i);
				// skip dead blocks
				if (brick.isDead()) {
					continue;
				}
				int brx = brick.getCornerX();
				int bry = brick.getCornerY();

				// Optimization to compare only as many sides as is necessary to
				// determine collision.
				// We can't just do '&&' for all sides because we need the
				// variables later to decide which
				// direction to bounce the ball. We bounce off of the side which
				// is closest to the ball.
				double yTopDist = by + THRESHOLD - bry;
				if (yTopDist > 0) {
					double yBottomDist = bry + brick.getHeight() - by
							+ THRESHOLD;
					if (yBottomDist > 0) {
						double xRightDist = bx + THRESHOLD - brx;
						if (xRightDist > 0) {
							double xLeftDist = brx + brick.getWidth() - bx
									+ THRESHOLD;
							if (xLeftDist > 0) {
								brick.kill();

								// if you are moving right, you can't hit the
								// left edge of a block, and
								// likewise with top/bottom sides. So choose the
								// x and y distances that are
								// possible before comparing.
								double xDist, yDist;
								if (ball.physics.dx > 0) {
									xDist = xLeftDist;
								} else {
									xDist = xRightDist;
								}
								if (ball.physics.dy > 0) {
									yDist = yTopDist;
								} else {
									yDist = yBottomDist;
								}

								// Bounce off the side that has the smallest
								// distance to the ball.
								ball.physics.revert();
								if (xDist < yDist) {
									ball.physics.bounceX();
								} else {
									ball.physics.bounceY();
								}

								// remove from future collision detection
								bricks.remove(i);
								return;
							}
						}
					}
				}
			}

			// Bounce off inner walls of game
			// Assume that if we hit a block, we aren't outside the game space,
			// so skip this step
			if (bx + THRESHOLD > background.getWidth() || bx - THRESHOLD < 0) {
				ball.physics.revert();
				ball.physics.bounceX();
			}
			if (by - THRESHOLD < 0) {
				ball.physics.revert();
				ball.physics.bounceY();
			}
			
			if(by - THRESHOLD < player.getTopEdge() && by + THRESHOLD > player.getTopEdge() && 
					player.getLeftEdge() < bx && player.getRightEdge() > bx) {
				ball.physics.revert();
				ball.physics.bounceY();
				return;
			}
			
			//If they hit the bottom edge, they lose :(
			if(by + THRESHOLD >= background.getHeight()) {
				Log.i("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX", "dead!");
				//loseGame();
				ball.physics.revert();
				ball.physics.bounceY();
			}
		}


		private void doDraw(Canvas c) {
			c.drawBitmap(background, 0, 0, null);
			for (DrawableObject o : drawableObjects) {
				o.draw(c);
			}
		}
	};

	public void start() {
		isRunning = true;
		drawingThread.start();
	}

	public void stop() {
		isRunning = false;
		gracefullyStopGameThread();
	}

	private void gracefullyStopGameThread() {
		boolean retry = true;
		while (retry) {
			try {
				drawingThread.join();
				retry = false;
			} catch (InterruptedException e) {
			}
		}
	}


}
