package by.segg3r.game.objects.prefabs;

import org.newdawn.slick.SlickException;

import by.segg3r.data.GameObject;
import by.segg3r.game.objects.ClientGameObject;
import by.segg3r.game.objects.prefabs.options.PrefabAnimationOptions;

public abstract class Prefab<ObjectType extends ClientGameObject, 
	AnimationOptionsType extends PrefabAnimationOptions<ObjectType>> {

	private AnimationOptionsType animationOptions;

	public Prefab(AnimationOptionsType animationOptions) {
		super();
		this.animationOptions = animationOptions;
	}

	public abstract ObjectType instantiate(GameObject gameObject, ImageHolder imageHolders)
			throws SlickException;

	protected AnimationOptionsType getAnimationOptions() {
		return this.animationOptions;
	}

}
