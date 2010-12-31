package dackerman.arkanoid.gamestate;

public interface GameStateListener {
	public void changedGameState(GameState g);

	public void setContentView(int view);

	public Object findViewById(int arkanoidview);
}
