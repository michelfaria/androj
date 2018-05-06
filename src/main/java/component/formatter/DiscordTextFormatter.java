package component.formatter;

import org.springframework.stereotype.Component;

@Component
public class DiscordTextFormatter implements TextFormatter {
	@Override
	public String bold(String s) {
		return "**" + s + "**";
	}

	@Override
	public String italic(String s) {
		return "_" + s + "_";
	}

	@Override
	public String underscore(String s) {
		return "__" + s + "__";
	}

	@Override
	public String strike(String s) {
		return "~~" + s + "~~";
	}

	@Override
	public String code(String s) {
		return "`" + s + "`";
	}

	@Override
	public String codeBlock(String language, String s) {
		return "```" + language + "\n" + s + "```";
	}
}
