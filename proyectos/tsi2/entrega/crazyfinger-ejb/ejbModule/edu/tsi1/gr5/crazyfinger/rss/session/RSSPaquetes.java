package edu.tsi1.gr5.crazyfinger.rss.session;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import edu.tsi1.gr5.crazyfinger.rss.entity.Feed;

@Name("rsspaquetes")
@Scope(ScopeType.SESSION)
public class RSSPaquetes
{
   private Feed feed;
   
   @In(create = true)
	FeedHome feedHome;

   @Create
   public void create()
   {

   }

   public Feed getFeed()
   {
	  // Feed feed = (Feed)em.find(Feed.class,1);
	  feedHome.setId(2L);
	  
      return feedHome.getDefinedInstance();
   }
   
}
