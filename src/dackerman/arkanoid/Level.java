package dackerman.arkanoid;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbox2d.collision.AABB;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.xmlpull.v1.XmlPullParserException;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import dackerman.arkanoid.gameobjects.ArkanoidBrick;

public class Level {
	private List<ArkanoidBrick> bricks = new ArrayList<ArkanoidBrick>();
	
	World world;
	private Vec2 gravity;
	private Resources res;
	
	public Level(Vec2 gravity, Resources res) {
		this.gravity = gravity;
		this.res = res;
		AABB boundingbox = new AABB(new Vec2(-1, -1), new Vec2(20, 25));
		world = new World(boundingbox, gravity, true);
	}
	
	public void addBrick(BrickType type, int x, int y) {
		ArkanoidBrick brick = new ArkanoidBrick(res.getDrawable(type.resource), world);
		bricks.add(brick);
	}
	
	public Vec2 getGravity() {
		return gravity;
	}
	
	
	public static List<Level> getLevels(XmlResourceParser parser, Resources res){
		Map<String, BrickType> brickTypes = new HashMap<String, BrickType>();
		List<Level> levels = new ArrayList<Level>();
		Level currentLevel = null;
		try {
			int type = parser.getEventType();
			while(type != XmlResourceParser.END_DOCUMENT) {
				String tagName = parser.getName();
				if(type == XmlResourceParser.START_TAG) {
					if(tagName == "level") {
						currentLevel = new Level(new Vec2(0,-10), res);
						levels.add(currentLevel);
					}else if(tagName == "bricktype") {
						String name = parser.getAttributeValue(null, "name");
						String resource = parser.getAttributeValue(null, "resource");
						int density = parser.getAttributeIntValue(null, "density", 1);
						brickTypes.put(name, new BrickType(resource, density));
					}else if(tagName == "brick") {
						String brickTypeName = parser.getAttributeValue(null, "type");
						int x = parser.getAttributeIntValue(null, "x", 0);
						int y = parser.getAttributeIntValue(null, "y", 0);
						BrickType brickType = brickTypes.get(brickTypeName);
						currentLevel.addBrick(brickType, x, y);
					}
				}else if(type == XmlResourceParser.END_TAG) {
					if(tagName == "level" ) {
						currentLevel = null;
					}
				}
				
				parser.next();
			}
		} catch (Exception e) {
			throw new LevelLoadingException("Failed to load levels",e);
		}
		return levels;
	}

}
