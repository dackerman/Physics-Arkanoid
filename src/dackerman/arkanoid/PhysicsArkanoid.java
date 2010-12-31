package dackerman.arkanoid;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import dackerman.arkanoid.gamestate.GameState;

public class PhysicsArkanoid extends Activity {
	boolean titleScreen = true;
	ArkanoidView arkanoidView;
	
	GameState state = null;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.game);
		arkanoidView = (ArkanoidView) findViewById(R.id.arkanoidview);
		arkanoidView.canStart = true;
	}
	
}