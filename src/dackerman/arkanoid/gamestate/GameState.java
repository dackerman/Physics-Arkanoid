package dackerman.arkanoid.gamestate;

import android.view.MotionEvent;

public abstract class GameState {
	protected GameStateListener a = null;
	
	public GameState(GameStateListener a) {
		this.a = a;
	}
	public void onTouchEvent(MotionEvent event) {
		
	}
	
	public boolean onBackPressed() {
		return false;
	}
}
