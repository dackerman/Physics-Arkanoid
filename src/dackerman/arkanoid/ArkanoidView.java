package dackerman.arkanoid;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

public class ArkanoidView extends SurfaceView implements Callback{

	private ArkanoidGame game;
	boolean canStart = false;
	
	public ArkanoidView(Context context, AttributeSet set) {
		super(context, set);
		SurfaceHolder holder = getHolder();
		holder.addCallback(this);
		game = new ArkanoidGame(holder, context);
		setFocusable(true);
	}
	
	@Override
	public boolean onTrackballEvent(MotionEvent event) {
		game.trackballMoved(event);
		return super.onTouchEvent(event);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		game.start();
	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		game.stop();
	}
}
