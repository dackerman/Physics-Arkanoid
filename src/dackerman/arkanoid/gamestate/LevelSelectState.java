package dackerman.arkanoid.gamestate;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;
import dackerman.arkanoid.R;

public class LevelSelectState extends GameState implements OnClickListener {
	
	private Spinner s;

	public LevelSelectState(GameStateListener a) {
		super(a);
		a.setContentView(R.layout.levelselect);
		Button b = (Button)a.findViewById(R.id.Button01);
		b.setOnClickListener(this);
		s = (Spinner)a.findViewById(R.id.levels);
	}

	@Override
	public void onClick(View button) {
	}

}