package edu.tsi1.gr5.crazyfinger.rss.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@Table(name = "feed")
@NamedQueries({@NamedQuery(name = "Feed.findAll", query = "SELECT f FROM Feed f"), @NamedQuery(name = "Feed.findByIdFeed", query = "SELECT f FROM Feed f WHERE f.idFeed = :idFeed")})
public class Feed{

	private long idFeed; 
	private String uid;
	private String title;
	private String subtitle;
	private Date updated;
	private String link;
	private List<Entry> entries;


	@Id
	@GeneratedValue
	public long getIdFeed() {
		return idFeed;
	}

	public void setIdFeed(long idFeed) {
		this.idFeed = idFeed;
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

	public String getSubtitle()
	{
		return subtitle;
	}

	public void setSubtitle(String subtitle)
	{
		this.subtitle = subtitle;
	}

	public Date getUpdated()
	{
		return updated;
	}

	public void setUpdated(Date updated)
	{
		this.updated = updated;
	}

	public String getLink()
	{
		return link;
	}

	public void setLink(String link)
	{
		this.link = link;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "feed")
	public List<Entry> getEntries()
	{
		return entries;
	}

	public void setEntries(List<Entry> entries)
	{
		this.entries = entries;
	}

}

