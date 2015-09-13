package by.segg3r.game.util.pathresolver;

public class PatternPathResolver extends PathResolver {

	private String pattern;
	
	public PatternPathResolver(String pattern) {
		this(pattern, null);
	}
	
	public PatternPathResolver(String pattern, PathResolver parent) {
		super(parent);
		this.pattern = pattern;
	}
	
	@Override
	protected String build(String path) {
		return pattern.replaceAll("\\$\\{path\\}", path);
	}

	
	
}
