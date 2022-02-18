using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ArmazonGr6.Models {
    public class ProveedorRepository {

        private ArmazonModelDataContext db;
        //
        // Query Methods

        public ProveedorRepository(string conn)
        {
            db = new ArmazonModelDataContext(conn);
        }

        public ProveedorRepository()
        {
            db = new ArmazonModelDataContext();
        }


        public IQueryable<Proveedor> FindAllProveedors()
        {
            return db.Proveedors;
        }

        public Proveedor GetProveedor(int id)
        {
            return db.Proveedors.SingleOrDefault(d => d.id == id);
        }
        public Proveedor FindProveedor(string nombre)
        {
            return db.Proveedors.SingleOrDefault(d => d.nombre.Equals(nombre));
        }
        //
        // Insert/Delete Methods
        public void Add(Proveedor proveedor)
        {
            db.Proveedors.InsertOnSubmit(proveedor);
            
        }

        public void Delete(Proveedor proveedor)
        {
            db.Proveedors.DeleteOnSubmit(proveedor);
        }
        //
        // Persistence
        public void Save()
        {
            db.SubmitChanges();
        }

        public void Update(Proveedor proveedor) {
            var prov = db.Proveedors.SingleOrDefault(d => d.id == proveedor.id);
            prov.nombre = proveedor.nombre;
            prov.url = proveedor.url;
            db.SubmitChanges();
        }

    }
}
