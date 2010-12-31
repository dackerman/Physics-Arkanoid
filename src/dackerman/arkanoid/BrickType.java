package dackerman.arkanoid;

public class BrickType {
	public int resource;
	public int density;
	
	public BrickType(String resource, int density) {
		this.resource = getResourceId(resource);
		this.density = density;
	}

	public static int getResourceId(String resourceName) {
		if(resourceName.equals("block")) {
			return R.drawable.block;
		}else if(resourceName.equals("mean_block")){
			return R.drawable.mean_block;
		}else {
			return -1;
		}
	}
}
