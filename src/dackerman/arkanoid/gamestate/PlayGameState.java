package dackerman.arkanoid.gamestate;

import android.view.MotionEvent;
import dackerman.arkanoid.R;

public class PlayGameState extends GameState {
	public PlayGameState(GameStateListener a) {
		super(a);
		a.setContentView(R.layout.game);
	}
	
	@Override
	public void onTouchEvent(MotionEvent event) {
		
	}
	
}
