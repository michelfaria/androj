package component.modules.reddit;

import net.dean.jraw.RedditClient;
import net.dean.jraw.models.Submission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnBean(RedditClient.class)
public class RedditClientWrapper {

    private final RedditClient redditClient;

    @Autowired
    public RedditClientWrapper(RedditClient redditClient) {
        this.redditClient = redditClient;
    }

    public RedditResponse fetchRandomPost(String subreddit) {
        Submission submission = redditClient.subreddit(subreddit)
                .randomSubmission()
                .getSubject();
        return new RedditResponse(submission.getUrl(), submission.getTitle(), submission.getSelfText());
    }
}
