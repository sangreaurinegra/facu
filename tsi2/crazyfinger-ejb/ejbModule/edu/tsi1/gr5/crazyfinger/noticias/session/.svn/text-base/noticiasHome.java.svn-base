package edu.tsi1.gr5.crazyfinger.noticias.session;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.framework.EntityHome;

import edu.tsi1.gr5.crazyfinger.noticias.entity.noticias;
import edu.tsi1.gr5.crazyfinger.rss.entity.Entry;
import edu.tsi1.gr5.crazyfinger.rss.session.EntryHome;

@Name("noticiasHome")
public class noticiasHome extends EntityHome<noticias>
{
    @RequestParameter Long noticiasId;

    @In(create=true)
    EntryHome entryHome;
    
    @Override
    public Object getId()
    {
        if (noticiasId == null)
        {
            return super.getId();
        }
        else
        {
            return noticiasId;
        }
    }

    @Override @Begin
    public void create() {
        super.create();
    }

    public void publicar(){
    	
    	Entry entry =  new Entry();
    	entry.setAuthor("");
    	
    	entryHome.setInstance(entry);
    	entryHome.persist();
    	//fee
    }
    
}
