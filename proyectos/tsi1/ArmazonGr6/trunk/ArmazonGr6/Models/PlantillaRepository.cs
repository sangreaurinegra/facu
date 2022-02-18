using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ArmazonGr6.Models {
    public class PlantillaRepository {

        private ArmazonModelDataContext db = new ArmazonModelDataContext();

        public static int PLANTILLA_POR_DEFECTO = 8;

        public void Save() {
            db.SubmitChanges();
        }

        /* =========================================================================
         * ------------------------ MANTENIMIENTO DE PLANTILLAS--------------------
         * ========================================================================
         */
        public Plantilla GetPlantilla(int id) {
            return db.Plantillas.SingleOrDefault(d => d.id == id);
        }
        public IQueryable<Plantilla> FindAllPlantillas() {
            return db.Plantillas;
        }
        //
        // Insert/Delete Methods
        public void Add(Plantilla c) {
            db.Plantillas.InsertOnSubmit(c);
        }

        public void Update(Plantilla c) {
            Plantilla p = (from a in db.Plantillas
                          where a.id == c.id
                          select a).First();
            p.nombre = c.nombre;
            p.Campos = c.Campos;
            db.SubmitChanges();
        }

        //si incluir supercategorias es true, obtiene los campos de las supercategorias tambien.
        public IEnumerable<Campo> FindTodosCamposArticulo(int idCategoria, bool incluirSuperCategorias) {
            // creo la lista, si no encuentra nada, trae los cmapos de la plantilla por defecto.
            LinkedList<Campo> campos = new LinkedList<Campo>();
            FindCampos(idCategoria, incluirSuperCategorias, campos);
            if (campos.Count > 0)
                return campos;
            else
                return FindAllCampos(PLANTILLA_POR_DEFECTO);
        }

        private void FindCampos(int idCategoria, bool incluirSuperCategorias, LinkedList<Campo> campos) {
            CategoriaRepository cr = new CategoriaRepository();
            
            Categoria c = cr.GetCategoria(idCategoria);
            if (c.idPlantilla != null) { // tiene plantilla asociada, traigo los campos
                IQueryable<Campo> campos2 = FindAllCampos((int)c.idPlantilla);
                foreach (Campo campo in campos2) {
                    campos.AddLast(campo);
                }
            }
            //busco los campos de la super Categoria
            if (incluirSuperCategorias && c.idSuperCategoria != null) {
                FindCampos((int)c.idSuperCategoria, incluirSuperCategorias, campos);
            }
            
        }
        public IQueryable<Campo> FindAllCampos(int idPlantilla) {
            return db.Campos.Where(c => c.idPlantilla == idPlantilla);
        }

        public void Add(Campo c) {
            db.Campos.InsertOnSubmit(c);
        }

        public void DeleteCampo(Campo c) {
            db.Campos.DeleteOnSubmit(c);
        }

        public Campo GetCampo(int id) {
            return db.Campos.SingleOrDefault(d => d.id == id);
        }

        public Registro GetRegistroPorNombreYArticulo(int idArt, String nombreCampo) {
            // obtengo los registros del articulo, sino tengo ninguno retorno null
            var regs = db.Registros.Where(r => r.idArticulo == idArt);
            if (regs == null || regs.Count() == 0)
                return null;
            else {
                foreach (var reg in regs) {
                    if (reg.Campo.nombre.Trim() == nombreCampo)
                        return reg;
                }
                return null;
            }
            
        }

        public void Add(Registro c) {
            db.Registros.InsertOnSubmit(c);
        }

        public Registro GetRegistro(int idArt, int idCampo) {
           return db.Registros.SingleOrDefault(r => r.idArticulo == idArt && r.idCampo == idCampo);
        }

        public void Update(Registro c) {
            Registro reg = db.Registros.SingleOrDefault(r => r.idArticulo == c.idArticulo && r.idCampo == c.idCampo);
            reg.valor = c.valor;
            Save();
        }
    }
}
