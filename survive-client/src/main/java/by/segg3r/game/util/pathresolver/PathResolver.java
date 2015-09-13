package by.segg3r.game.util.pathresolver;

public abstract class PathResolver {

	private PathResolver parent;
	
	public PathResolver() {
		this(null);
	}
	
	public PathResolver(PathResolver parent) {
		this.parent = parent;
	}
	
	public String resolve(String path) {
		String result = build(path);
		if (parent != null) {
			result = parent.resolve(result);
		}
		return result;
	}
	
	protected abstract String build(String path);
	
}
