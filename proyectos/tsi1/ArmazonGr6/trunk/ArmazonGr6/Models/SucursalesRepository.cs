using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ArmazonGr6.Models
{
    public class SucursalesRepository
    {
        private ArmazonModelDataContext db;
        //
        // Query Methods

        public SucursalesRepository(string conn)
        {
            db = new ArmazonModelDataContext(conn);
        }

        public SucursalesRepository()
        {
            db = new ArmazonModelDataContext();
        }


        public IQueryable<Sucursal> FindAllSucursals()
        {
            return db.Sucursals;
        }
        public IQueryable<Sucursal> FindSucursal(string nom)
        {
            return from Sucursals in db.Sucursals
                   where Sucursals.nombre == nom
                   select Sucursals;
        }
        public Sucursal GetSucursal(int id)
        {
            return db.Sucursals.SingleOrDefault(d => d.id == id);
        }
        //
        // Insert/Delete Methods
        public void Add(Sucursal Sucursal)
        {
            db.Sucursals.InsertOnSubmit(Sucursal);
        }

        public void Delete(Sucursal Sucursal)
        {
            db.Sucursals.DeleteOnSubmit(Sucursal);
        }
        //
        // Persistence
        public void Save()
        {
            db.SubmitChanges();
        }

        public void Update(Sucursal sucursal)
        {
            var sucu = db.Sucursals.SingleOrDefault(d => d.id == sucursal.id);
            sucu.nombre = sucursal.nombre;
            sucu.direccion = sucursal.direccion;
            sucu.telefono = sucursal.telefono;
            sucu.ciudad = sucursal.ciudad;
            sucu.latitud = sucursal.latitud;
            sucu.longitud = sucursal.longitud;
            db.SubmitChanges();
        }
    }
}
