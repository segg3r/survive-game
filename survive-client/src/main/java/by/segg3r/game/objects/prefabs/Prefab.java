package by.segg3r.game.objects.prefabs;

import org.newdawn.slick.SlickException;

import by.segg3r.game.objects.GameObject;
import by.segg3r.game.objects.prefabs.options.PrefabAnimationOptions;
import by.segg3r.game.rooms.Room;

public abstract class Prefab
	<ObjectType extends GameObject, AnimationOptionsType extends PrefabAnimationOptions<ObjectType>> {

	private AnimationOptionsType animationOptions;

	public Prefab(AnimationOptionsType animationOptions) {
		super();
		this.animationOptions = animationOptions;
	}

	public abstract GameObject instantiate(ImageHolder imageHolder, Room room) throws SlickException;

	protected AnimationOptionsType getAnimationOptions() {
		return this.animationOptions;
	}

}
