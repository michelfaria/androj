package component.modules.wiki;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

@Component
@ConditionalOnBean(WikiConfig.class)
public class WikipediaImpl implements Wikipedia {

	@Autowired
	public WikipediaImpl() {
	}

	@Override
	public WikipediaResponse lookup(String term) throws Exception {
		Objects.requireNonNull(term);

		// Replace spaces with underscores
		term = term.replaceAll(" +", "_");

		// Build the request URL
		URL wikiUrl = UriComponentsBuilder.newInstance().scheme("https").host("en.wikipedia.org").path("wiki")
				.pathSegment(term).build().toUri().toURL();

		HttpURLConnection con = getHttpURLConnection(wikiUrl);
		StringBuilder content = new StringBuilder();
		// Read content from the wikipedia page and writte it into the buffer
		read(con, content);

		return new WikipediaResponse(parseSummary(content.toString()), wikiUrl.toString());
	}

	private String parseSummary(String content) {
		Document doc = Jsoup.parse(content);
		Elements article = doc.select(".mw-parser-output p");
		List<String> summary = parseNWikiElements(article, 3);
		String text = String.join("\n\n", summary);

		// populate the summary with bullet points if this is a disambiguation
		if (text.contains("may refer to:")) {
			Elements bullets = doc.select(".mw-parser-output li");
			List<String> bulletContents = parseNWikiElements(bullets, 5);
			text = text + String.join("\n\n", bulletContents);
		}
		return text;
	}

	private List<String> parseNWikiElements(Elements elements, int n) {
		ListIterator<Element> elIt = elements.listIterator();
		List<String> texts = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			if (!elIt.hasNext()) {
				break;
			}
			Element next = elIt.next();
			String text = next.text();
			if (text.isEmpty()) {
				continue;
			}
			// remove all those annoying reference brackets
			text = text.replaceAll("\\[.*]", "");
			texts.add(text);
		}
		return texts;
	}

	private void read(HttpURLConnection con, StringBuilder content) throws IOException {
		con.connect();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
			String line;
			while ((line = br.readLine()) != null) {
				content.append(line);
			}
		} finally {
			con.disconnect();
		}
	}

	private HttpURLConnection getHttpURLConnection(URL wikiUrl) throws IOException {
		HttpURLConnection con = (HttpURLConnection) wikiUrl.openConnection();
		con.setRequestMethod(HttpMethod.GET.toString());
		con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) "
				+ "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.139 Safari/537.36");
		return con;
	}
}
