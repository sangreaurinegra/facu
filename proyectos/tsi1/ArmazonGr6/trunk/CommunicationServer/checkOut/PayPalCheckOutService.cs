using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using CommunicationServer.Common;

namespace CommunicationServer.checkOut {

    //esta clase se encarga de la comunicacion con paypal.
    public class PayPalCheckOutService {
        
        private String PAYPAL_url_Details = "https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout";
        private String PAYPAL_url_IniCheckOut = "https://api-3t.sandbox.paypal.com/nvp";
        private String PAYPAL_url_OkCheckOut = "https://api-3t.sandbox.paypal.com/nvp";


        public String iniciarCheckOut(float importe) {

            //retorna la direcciona a la cual debe direccionar
            // de esta forma la direccion no depende del web server,
            // y la comunicacion directa con paypal depende de Comm Server
            SystemProperties sp;
            if (SystemProperties.isSystemPropertiesLoaded())
                sp = SystemProperties.getSystemProperties();
            else
                sp = SystemProperties.loadProperties("C:/Projects/ArmazonGr6/CommunicationServer/config/CommServer.config");
            String RETURNURL = sp.getProperty("PayPalRETURNURL");
            String CANCELURL = sp.getProperty("PayPalCANCELURL");


            PostSubmitter post = new PostSubmitter();
            post.Url = PAYPAL_url_IniCheckOut;
            post.PostItems.Add("USER", "maxi_4_1241897001_biz_api1.yahoo.com.ar");
            post.PostItems.Add("PWD", "1241897008");
            post.PostItems.Add("SIGNATURE", "AFcWxV21C7fd0v3bYYYRCpSSRl31ArvbIHRhL0QMCfIYZGeySDfdrnxp");
            post.PostItems.Add("VERSION", "2.3");
            post.PostItems.Add("PAYMENTACTION", "Authorization");
            
            post.PostItems.Add("AMT", ""+importe);

            post.PostItems.Add("RETURNURL", RETURNURL);
            post.PostItems.Add("CANCELURL", CANCELURL);
            post.PostItems.Add("METHOD", "SetExpressCheckout");

            post.Type = PostSubmitter.PostTypeEnum.Post;
            string result = "Error de conexion con PayPal.";
            int maxIntentos = int.Parse(sp.getProperty("Intentos"));
            int intentos = 0;
            String ack = "";
            while (intentos < maxIntentos && !ack.Equals("Success")) {
                try {
                    result = post.Post();
                    ack = obtenerAck(result);
                    if (ack.Equals("Success")) {
                        String token = obtenerToken(result);
                        token = token.Replace("%2d", "-");
                        return PAYPAL_url_Details + "&token=" + token;
                    }
                }
                catch (Exception e) {
                    // aumento los intentos
                    intentos++;
                }
            }

            return "Error-"+result;
        }


        public bool confirmarCheckOut(String token, String payerId, double monto) {

            PostSubmitter post = new PostSubmitter();
            post.Url = PAYPAL_url_OkCheckOut;
            post.PostItems.Add("USER", "maxi_4_1241897001_biz_api1.yahoo.com.ar");
            post.PostItems.Add("PWD", "1241897008");
            post.PostItems.Add("SIGNATURE", "AFcWxV21C7fd0v3bYYYRCpSSRl31ArvbIHRhL0QMCfIYZGeySDfdrnxp");
            post.PostItems.Add("VERSION", "2.3");
            post.PostItems.Add("TOKEN", token);
            
            post.PostItems.Add("METHOD", "GetExpressCheckoutDetails");

            post.Type = PostSubmitter.PostTypeEnum.Post;
            //obtengo los resultados, si todo esta ok, lo confirmo
            SystemProperties sp;
            if (SystemProperties.isSystemPropertiesLoaded())
                sp = SystemProperties.getSystemProperties();
            else
                sp = SystemProperties.loadProperties("C:/Projects/ArmazonGr6/CommunicationServer/config/CommServer.config");
            
            string result = "Error de conexion con PayPal.";
            int maxIntentos = int.Parse(sp.getProperty("Intentos"));
            int intentos = 0;
            String ack = "";
            while (intentos < maxIntentos && !ack.Equals("Success")) {
                try {
                    result = post.Post();
                    ack = obtenerAck(result);
                    if (ack != null && ack.Equals("Success")) {
                        String resp = doCheckOut(token, payerId, monto);
                        String ackFinal = obtenerAck(resp);
                        return ackFinal.Equals("Success");
                    }
                }
                catch (Exception e) {
                    // aumento los intentos
                    intentos++;
                }
            }
            return false;
        }

        private String doCheckOut(String token, String payerId, double monto) {
            PostSubmitter post = new PostSubmitter();
            post.Url = PAYPAL_url_OkCheckOut;
            post.PostItems.Add("USER", "maxi_4_1241897001_biz_api1.yahoo.com.ar");
            post.PostItems.Add("PWD", "1241897008");
            post.PostItems.Add("SIGNATURE", "AFcWxV21C7fd0v3bYYYRCpSSRl31ArvbIHRhL0QMCfIYZGeySDfdrnxp");
            post.PostItems.Add("VERSION", "2.3");
            post.PostItems.Add("TOKEN", token);
            post.PostItems.Add("PAYERID", payerId);
            post.PostItems.Add("PAYMENTACTION", "Authorization");
            // TODO : VALOR
            post.PostItems.Add("AMT", ""+monto);
            post.PostItems.Add("METHOD", "DoExpressCheckoutPayment");

            post.Type = PostSubmitter.PostTypeEnum.Post;

            return post.Post();
        }


        private String obtenerAck(String result){
            String ack ="";
            char[] separador = {'&'};
            String[] resp = result.Split(separador);
            for (int i = 0; i < resp.Length; i++) {
                String aux = resp[i];
                if (aux.StartsWith("ACK")) {
                    ack = aux.Substring(4);
                    break;
                }
            }

            return ack;
        }

        private String obtenerToken(String result) {
            String token = "";
            char[] separador = { '&' };
            String[] resp = result.Split(separador);
            for (int i = 0; i < resp.Length; i++) {
                String aux = resp[i];
                if (aux.StartsWith("TOKEN")) {
                    token = aux.Substring(6);
                    break;
                }
            }

            return token;
        }

        private String obtenerPayerId(String result) {
            String token = "";
            char[] separador = { '&' };
            String[] resp = result.Split(separador);
            for (int i = 0; i < resp.Length; i++) {
                String aux = resp[i];
                if (aux.StartsWith("PAYERID")) {
                    token = aux.Substring(8);
                    break;
                }
            }

            return token;
        }

    }
}
