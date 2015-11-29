package by.segg3r.game.objects.prefabs.options;

import org.newdawn.slick.Color;

import by.segg3r.data.Position;

public abstract class PrefabAnimationOptions<ObjectType> {

	private int duration;
	private Color backgroundColor;
	private Position offset;

	public PrefabAnimationOptions(int duration, Color backgroundColor,
			Position offset) {
		super();
		this.duration = duration;
		this.backgroundColor = backgroundColor;
		this.offset = offset;
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

	public Position getOffset() {
		return offset;
	}

	public void setOffset(Position offset) {
		this.offset = offset;
	}

}
