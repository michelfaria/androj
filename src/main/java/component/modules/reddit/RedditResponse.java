package component.modules.reddit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.dean.jraw.models.Submission;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RedditResponse {
	private String link;
	private String title;
	private String selfText;

	public static RedditResponse fromSubmission(Submission submission) {
		return new RedditResponse(submission.getTitle(), submission.getUrl(), submission.getSelfText());
	}
}
