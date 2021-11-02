package edu.tsi1.gr5.crazyfinger.rss.session;

import edu.tsi1.gr5.crazyfinger.rss.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("feedHome")
public class FeedHome extends EntityHome<Feed> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -943176952834996896L;

	public void setFeedIdFeed(Long id) {
		setId(id);
	}

	public Long getFeedIdFeed() {
		return (Long) getId();
	}

	@Override
	protected Feed createInstance() {
		Feed feed = new Feed();
		return feed;
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
		getInstance();
	}

	public boolean isWired() {
		return true;
	}

	public Feed getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	
}
