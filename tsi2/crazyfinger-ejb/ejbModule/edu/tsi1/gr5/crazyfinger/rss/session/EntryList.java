package edu.tsi1.gr5.crazyfinger.rss.session;

import edu.tsi1.gr5.crazyfinger.rss.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("entryList")
public class EntryList extends EntityQuery<Entry> {

	private static final String EJBQL = "select entry from Entry entry";

	private static final String[] RESTRICTIONS = {
			"lower(entry.author) like lower(concat(#{entryList.entry.author},'%'))",
			"lower(entry.link) like lower(concat(#{entryList.entry.link},'%'))",
			"lower(entry.summary) like lower(concat(#{entryList.entry.summary},'%'))",
			"lower(entry.title) like lower(concat(#{entryList.entry.title},'%'))",
			"lower(entry.uid) like lower(concat(#{entryList.entry.uid},'%'))", };

	private Entry entry = new Entry();

	public EntryList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Entry getEntry() {
		return entry;
	}
}
