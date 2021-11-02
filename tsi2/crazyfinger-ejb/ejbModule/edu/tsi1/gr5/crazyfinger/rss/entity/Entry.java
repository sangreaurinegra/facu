package edu.tsi1.gr5.crazyfinger.rss.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "entry")
public class Entry{

	private long idEntry; 
	private String uid;
	private String title;
	private String link;
	private String author;
	private String summary;
	private Date published;
	private Date updated;
	private Feed feed;

	
	
	
	@Id
	@GeneratedValue
	public long getIdEntry() {
		return idEntry;
	}

	public void setIdEntry(long idEntry) {
		this.idEntry = idEntry;
	}

	public String getUid()
	{
		return uid;
	}

	public void setUid(String uid)
	{
		this.uid = uid;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getLink()
	{
		return link;
	}

	public void setLink(String link)
	{
		this.link = link;
	}

	public String getAuthor()
	{
		return author;
	}

	public void setAuthor(String author)
	{
		this.author = author;
	}

	@Column(length=999)
	public String getSummary()
	{
		return summary;
	}

	public void setSummary(String summary)
	{
		this.summary = summary;
	}

	public Date getPublished()
	{
		return published;
	}

	public void setPublished(Date published)
	{
		this.published = published;
	}

	public Date getUpdated()
	{
		return updated;
	}

	public void setUpdated(Date updated)
	{
		this.updated = updated;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "feed")
	public Feed getFeed() {
		return feed;
	}

	public void setFeed(Feed feed) {
		this.feed = feed;
	}
	
	
	
}

