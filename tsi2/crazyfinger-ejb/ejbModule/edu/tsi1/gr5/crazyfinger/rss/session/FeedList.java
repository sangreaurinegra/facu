package edu.tsi1.gr5.crazyfinger.rss.session;

import edu.tsi1.gr5.crazyfinger.rss.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("feedList")
public class FeedList extends EntityQuery<Feed> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -278411667533252968L;

	private static final String EJBQL = "select feed from Feed feed";

	private static final String[] RESTRICTIONS = {
			"lower(feed.link) like lower(concat(#{feedList.feed.link},'%'))",
			"lower(feed.subtitle) like lower(concat(#{feedList.feed.subtitle},'%'))",
			"lower(feed.title) like lower(concat(#{feedList.feed.title},'%'))",
			"lower(feed.uid) like lower(concat(#{feedList.feed.uid},'%'))", };

	private Feed feed = new Feed();

	public FeedList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Feed getFeed() {
		return feed;
	}
}
