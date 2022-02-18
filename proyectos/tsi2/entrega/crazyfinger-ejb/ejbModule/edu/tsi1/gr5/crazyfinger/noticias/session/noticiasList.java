package edu.tsi1.gr5.crazyfinger.noticias.session;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;

import edu.tsi1.gr5.crazyfinger.noticias.entity.noticias;

@Name("noticiasList")
public class noticiasList extends EntityQuery<noticias>
{
    public noticiasList()
    {
        setEjbql("select noticias from noticias noticias");
    }
}
