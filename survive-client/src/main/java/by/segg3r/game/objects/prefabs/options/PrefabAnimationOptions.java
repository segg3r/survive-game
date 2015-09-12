package by.segg3r.game.objects.prefabs.options;

import org.newdawn.slick.Color;

public abstract class PrefabAnimationOptions<ObjectType> {

	private int duration;
	private Color backgroundColor;

	public PrefabAnimationOptions(int duration, Color backgroundColor) {
		super();
		this.duration = duration;
		this.backgroundColor = backgroundColor;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

}
