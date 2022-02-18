package edu.tsi1.gr5.crazyfinger.rss.session;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import edu.tsi1.gr5.crazyfinger.rss.entity.Entry;
import edu.tsi1.gr5.crazyfinger.rss.entity.Feed;

@Name("rss")
@Scope(ScopeType.SESSION)
public class RSS
{
   private Feed feed;
   
   
   @In(create = true)
	FeedHome feedHome;

   @Create
   public void create()
   {
//      feed = new Feed();
//      List<Entry> entries = new ArrayList<Entry>();
//      for (int i = 0; i < 5; i++)
//      {
//         Entry entry = new Entry();
//         entry.setAuthor("Author " + i);
//         entry.setLink("Link " + i);
//         entry.setPublished(new Date(0));
//         entry.setSummary("Summary <b>" + i + "</b>");
//         entry.setTitle("Title <i>" + i + "</i>");
//         entry.setUid(UUID.randomUUID().toString());
//         entry.setUpdated(new Date());
//         entries.add(entry);
//      }
//      
//      Entry entrygc = new Entry();
//      entrygc.setAuthor("Author GC");
//      entrygc.setLink("www.google.com.uy");
//      entrygc.setPublished(new Date(0));
//      entrygc.setSummary("Summary <b> Poronga </b>");
//      entrygc.setTitle("Title <i> Poronga Tit </i>");
//      entrygc.setUid(UUID.randomUUID().toString());
//      entrygc.setUpdated(new Date());
//      entries.add(entrygc);
//      
//      feed.setEntries(entries);
//      feed.setLink("Link Feed");
//      feed.setSubtitle("Subtitle feed");
//      feed.setTitle("Title Feed");
//      feed.setUid(UUID.randomUUID().toString());
//      feed.setUpdated(new Date());
   }

   public Feed getFeed()
   {
	  // Feed feed = (Feed)em.find(Feed.class,1);
	   
	  feedHome.setId(1L);
	   
      return feedHome.getDefinedInstance();
   }

}
