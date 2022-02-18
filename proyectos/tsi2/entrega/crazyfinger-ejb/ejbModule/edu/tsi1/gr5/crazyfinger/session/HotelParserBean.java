package edu.tsi1.gr5.crazyfinger.session;

import javax.ejb.Stateless;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.log.Log;
import org.jboss.seam.international.StatusMessages;

@Stateless
@Name("HotelParser")
public class HotelParserBean implements HotelParser
{
    @Logger private Log log;

    @In StatusMessages statusMessages;

    public void hotelParser()
    {
        // implement your business logic here
        log.info("HotelParser.hotelParser() action called");
        statusMessages.add("hotelParser");
    }

    // add additional action methods

}
