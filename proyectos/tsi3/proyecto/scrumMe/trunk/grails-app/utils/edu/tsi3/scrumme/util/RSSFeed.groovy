package edu.tsi3.scrumme.util

class RSSFeed {

	String titulo
	String link
    String desc
    String pubDate
	
	RSSFeed(String titulo, String link,
            String desc, String pubDate ){
		this.titulo = titulo
		this.link = link
		this.desc = desc
		this.pubDate = pubDate 
	}
	
}
