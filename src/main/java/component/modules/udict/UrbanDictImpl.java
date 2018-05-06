package component.modules.udict;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Objects;

@Component
public class UrbanDictImpl implements UrbanDict {
	private final RestTemplate rest;

	@Autowired
	public UrbanDictImpl(RestTemplate rest) {
		this.rest = rest;
	}

	@Override
	public UrbanDictResponse define(String term) {
		Objects.requireNonNull(term);
		UriComponents uriComponents;
		try {
			uriComponents = UriComponentsBuilder.newInstance().scheme("https").host("api.urbandictionary.com")
					.path("/v0/define").queryParam("term", URLEncoder.encode(term, "UTF-8")).build();
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		return rest.getForObject(uriComponents.toUri(), UrbanDictResponse.class);
	}
}
