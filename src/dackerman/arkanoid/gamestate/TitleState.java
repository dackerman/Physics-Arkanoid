package dackerman.arkanoid.gamestate;

import android.view.MotionEvent;
import dackerman.arkanoid.R;

public class TitleState extends GameState {
	public TitleState(GameStateListener a) {
		super(a);
		a.setContentView(R.layout.main);
	}
	
	@Override
	public void onTouchEvent(MotionEvent event) {
		a.changedGameState(new PlayGameState(a));
	}
	
}
