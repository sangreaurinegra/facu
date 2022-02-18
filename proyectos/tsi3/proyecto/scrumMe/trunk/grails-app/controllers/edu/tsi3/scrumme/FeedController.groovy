package edu.tsi3.scrumme


import edu.tsi3.scrumme.util.RSSFeed;

class FeedController {
	
	def index = {
		def	url = "http://localhost:8080/scrumMe/mensaje/feed"
		def xmlFeed = new XmlParser().parse(url);
		
		def feedList = []
		
		log.error "entre -> "+xmlFeed.channel.item.size()             
		                
		(0..< xmlFeed.channel.item.size()).each {
			
			def item = xmlFeed.channel.item.get(it);
			
			
//			log.error item.title.text()
//			log.error item.link.text()
//			log.error item.description.text()
//			log.error item.pubDate.text()
//			
//			log.error item.encoded
//			
//			log.error item
			
			RSSFeed feed = new RSSFeed( item.title.text(), item.link.text(),
			item.description.text(), item.pubDate.text() )
			feedList << feed
		}
		
		
		[feedList:feedList]
	}
	
	def readFeed( url )
	{
		
		url = "http://localhost:8080/scrumMe/mensaje/feed"
		def xmlFeed = new XmlParser().parse(url);
		
		def feedList = []
		
		(0..< xmlFeed.channel.item.size()).each {
			
			def item = xmlFeed.channel.item.get(it);
			RSSFeed feed = new RSSFeed( item.title.text(), item.link.text(),
					item.description.text(), item.pubDate.text() )
			feedList << feed
		}
		
		feedList
	}
	
}
