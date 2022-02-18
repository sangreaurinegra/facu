using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace ArmazonGr6.Models {
    public partial class Campo {
        // ids de campos para articulos externos, de otros armazon
        public static string Tipo_Str = "String";
        public static string Tipo_Bool = "bool";
        public static string Tipo_Float = "double";
        public static string Tipo_Int = "int";
        public static SelectList getListaValores() {

            String[] valores = { "String", "int", "double", "url", "bool", "pwd" };
            SelectList listaPuntuaciones = new SelectList(valores);
            return listaPuntuaciones;
        }

        public static SelectList getValoresBool(String valorSelected) {

            String[] valores = {  " Sí ", " No " };
            SelectList listaPuntuaciones = new SelectList(valores, " " + valorSelected.Trim() + " ");
            return listaPuntuaciones;
        }
    }
}
