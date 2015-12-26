package by.segg3r.server;

import java.util.List;

import by.segg3r.data.GameObject;
import by.segg3r.data.Position;
import by.segg3r.exception.AuthenticationException;

public interface PlayerService {

	GameObject authenticate(String login, String password) throws AuthenticationException;
	
	void changePosition(long id, Position position);
	
	List<GameObject> getOtherPlayers(long id);
	
	void removePlayer(long id);
	
}
