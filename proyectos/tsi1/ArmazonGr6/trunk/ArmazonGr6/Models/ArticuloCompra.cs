using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ArmazonGr6.Models
{
    public partial class ArticuloCompra
    {
        public int idArticulo { get; set; }
        public string nombre { get; set; }
        public double precio { get; set; }
        public int cantidad { get; set; }

        public ArticuloCompra(int idAr,string nom, double monto,int cant)
        {
            this.idArticulo = idAr;
            this.nombre = nom;
            this.precio = monto;
            this.cantidad = cant;
        }


    }
}
