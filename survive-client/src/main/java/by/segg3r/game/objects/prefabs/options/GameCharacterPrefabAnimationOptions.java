package by.segg3r.game.objects.prefabs.options;

import by.segg3r.game.objects.characters.GameCharacter;

public class GameCharacterPrefabAnimationOptions extends PrefabAnimationOptions<GameCharacter> {

	private String bodyFileName;
	private int duration;

	public GameCharacterPrefabAnimationOptions(String bodyFileName, int duration) {
		super();
		this.bodyFileName = bodyFileName;
		this.duration = duration;
	}

	public String getBodyFileName() {
		return bodyFileName;
	}

	public void setBodyFileName(String fileName) {
		this.bodyFileName = fileName;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

}
