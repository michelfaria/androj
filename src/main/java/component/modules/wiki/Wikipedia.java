package component.modules.wiki;

public interface Wikipedia {
	WikipediaResponse lookup(String term) throws Exception;
}
