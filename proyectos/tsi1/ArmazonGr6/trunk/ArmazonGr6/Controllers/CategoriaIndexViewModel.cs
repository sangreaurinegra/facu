using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using ArmazonGr6.Models;
using ArmazonGr6.Helpers;

namespace ArmazonGr6.Controllers
{
    public class CategoriaIndexViewModel
    {
        //CategoriaRepository categoriaRepository= new CategoriaRepository();
        IEnumerable<Categoria> listaCatAMostrar;
        PaginatedList<Articulo> listaArtAMostrar;
        IEnumerable<Categoria> rutaCategorias;
        IEnumerable<Articulo> listaArtAProponer;
        int nroPagActual;
        int? idCatPadre;

        public CategoriaIndexViewModel(IEnumerable<Categoria> listaCat, PaginatedList<Articulo> listaArt, IEnumerable<Articulo> listaArtAProp, IEnumerable<Categoria> ruta, int pagActual, int? idCategoriaPadre)
        {
            listaCatAMostrar = listaCat;
            listaArtAMostrar = listaArt;
            nroPagActual = pagActual;
            idCatPadre = idCategoriaPadre;// ?? -1;
            rutaCategorias = ruta;
            listaArtAProponer = listaArtAProp;
        }

        public IEnumerable<Categoria> getListaCategoriasAMostrar() 
        {
            return listaCatAMostrar;
        }

        public PaginatedList<Articulo> getListaArticulosAMostrar() 
        {
            return listaArtAMostrar;
        }

        public int getPaginaActual()
        {
            return nroPagActual;
        }
        
        public int? getIdCategoriaPadre() {
            return idCatPadre; 
        }

        public IEnumerable<Categoria> getRutaCategorias()
        {
            return rutaCategorias;
        }

        public IEnumerable<Articulo> getListaArtAProponer()
        {
            return listaArtAProponer;
        }

    }
}
