using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace ArmazonGr6.Models {
    public partial class Calificacion {

        private SelectList listaPuntuaciones {
            get;
            set;
        }

        public static int getNumberRating(String valor) {
            int val = 1;
            if (valor.Equals("Fantabuloso"))
                val = 5;
            else if (valor.Equals("Bueno"))
                val = 4;
            else if (valor.Equals("Aceptable"))
                val = 3;
            else if (valor.Equals("Pesimo"))
                val = 2;
       
            return val;
        }

        public static SelectList getListaPuntuaciones() {
           
                String[] valores = { "Desastrozo", "Pesimo", "Aceptable", "Bueno", "Fantabuloso" };
                SelectList listaPuntuaciones = new SelectList(valores);
                return listaPuntuaciones;
        }
    }
}
