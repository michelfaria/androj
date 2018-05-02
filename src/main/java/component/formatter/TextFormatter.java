package component.formatter;

public interface TextFormatter {
    String bold(String s);

    String italic(String s);

    String underscore(String s);

    String strike(String s);

    String code(String s);

    String codeBlock(String language, String s);

     static TextFormatter getDefault() {
        return new DiscordTextFormatter();
    }
}
