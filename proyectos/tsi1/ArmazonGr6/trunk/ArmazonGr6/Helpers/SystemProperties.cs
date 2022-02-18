using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.IO;

namespace ArmazonGr6.Helpers {
    public class SystemProperties {

        private Dictionary<String, String> properties;

        private static SystemProperties sp = null;

        //carga las properties desde el archivo y las deja disponibles en el objeto devuelto 
        public static SystemProperties loadProperties(String fileName) {
            if (sp == null) {
                sp = new SystemProperties();
                sp.properties = new Dictionary<String, String>();
            }
            StreamReader arch = new StreamReader(fileName);
            string sLine = "";
            char[] separador = { '=' };
            while (sLine != null) {
                sLine = arch.ReadLine();
                if (sLine != null && !sLine.StartsWith("#") && !sLine.Equals("")) {
                    String[] resp = sLine.Split(separador);
                    try {
                        sp.properties.Add(resp[0], resp[1]);
                    }
                    catch (Exception e) {
                        throw new Exception("Error al cargar las properties, revise el archivo.");
                    }
                }
            }
            arch.Close();


            return sp;

        }

        public static SystemProperties getSystemProperties() {
            if (sp != null)
                return sp;
            else
                throw new Exception("Debe ejecutar loadProperties primero!!!!!");
        
        }


        public String getProperty(String nombre) {
            return this.properties.First(value => value.Key == nombre).Value;
        
        }

        public static bool isSystemPropertiesLoaded() {
            return sp != null;
 
        }


    }
}
