using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ArmazonGr6.Models {
    public class FormaPagoRepository {
        private ArmazonModelDataContext db = new ArmazonModelDataContext();

        //
        // Query Methods
        public IEnumerable<FormaPago> FindAllFormaPago() {
            return db.FormaPagos;
        }

        public IEnumerable<Campo> obtnerCamposDeFormaPago(int id) {
            int idp = obtenerIdPlantilla(id);
            var campos = (from c in db.Campos
                          where
                              c.idPlantilla == idp
                          select c).ToList();

            return campos;
        }


        private int obtenerIdPlantilla(int idFP) { 
            var fpres = (from fp in db.FormaPagos where
                     fp.id == idFP select fp).First();
            return fpres.idPlantilla;
        }
    }
}
