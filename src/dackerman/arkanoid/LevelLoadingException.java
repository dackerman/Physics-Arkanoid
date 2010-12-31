package dackerman.arkanoid;


public class LevelLoadingException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public LevelLoadingException(String message, Exception e) {
		super(message, e);
	}

}
