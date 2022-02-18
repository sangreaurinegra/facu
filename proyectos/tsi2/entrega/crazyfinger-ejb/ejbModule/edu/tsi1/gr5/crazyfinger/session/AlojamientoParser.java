package edu.tsi1.gr5.crazyfinger.session;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.ejb.Local;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;

@Local
public interface AlojamientoParser
{
    public String alojamientoParser() throws FailingHttpStatusCodeException, MalformedURLException, IOException;
    public String getValue();
    public void setValue(String value);
    //public void destroy();

    // add additional interface methods here

}
