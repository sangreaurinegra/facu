package edu.tsi1.gr5.crazyfinger.rss.session;

import java.util.logging.Logger;

import edu.tsi1.gr5.crazyfinger.rss.entity.*;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("entryHome")
public class EntryHome extends EntityHome<Entry> {

	public void setEntryIdEntry(Long id) {
		setId(id);
	}

	public Long getEntryIdEntry() {
		return (Long) getId();
	}

	@Override
	protected Entry createInstance() {
		Entry entry = new Entry();
		return entry;
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

	public Entry getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	
}
