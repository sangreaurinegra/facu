using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ArmazonGr6.Models
{
    public partial class ArtCant
    {
        public ArtCant(int idArt, int idCarrito, int cant)
        {
            this._id = 0;
            this._idArticulo = idArt;
            this._idCarrito = idCarrito;
            this.cantidad = cant;
        }
    }
}
