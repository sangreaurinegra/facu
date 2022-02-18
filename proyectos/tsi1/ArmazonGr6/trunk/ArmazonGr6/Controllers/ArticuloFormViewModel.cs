using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using ArmazonGr6.Models;
using CommunicationServer;
using ArmazonGr6.Helpers;


namespace ArmazonGr6.Controllers
{
    public class ArticuloFormViewModel
    {
        PaginatedList<Articulo> listaArmazon;
        PaginatedList<Articulo> listaArtAmazon;
        PaginatedList<Articulo> listaOtroAr;
        int pagActFT;
        int pagActAmazon;
        int pagActOtroAr;
        int sizePageFT;
        int sizePageAmazon;
        int sizePageOtroAr;
        string texto;

        String msgError;
        bool hayError;
        int tipoError;
        //0 No hay Error, 1 Armazon, 2 Amazon, 3 Otro Armazon, 4 Am y Ar , 5 Error Inesp


        public ArticuloFormViewModel(List<Articulo> l, int actFT, int sizeFT,
                                    List<Articulo> la,  int actA, int sizeA,
                                    List<Articulo> lo, int actO, int sizeO,
                                    string txt)
        {
            hayError=false;
            listaArmazon = new PaginatedList<Articulo>(l,actFT,sizeFT);
            listaArtAmazon = new PaginatedList<Articulo>(la, actA % 2, sizeA);
            listaOtroAr = new PaginatedList<Articulo>(lo, actO, sizeO);
            pagActFT = actFT;
            pagActAmazon = actA;
            pagActOtroAr = actO;
            sizePageFT= sizeFT ;
            sizePageAmazon = sizeA;
            sizePageOtroAr = sizeO;
            texto = txt;
        }
        //metodo para usar en pag buscarfulltext.aspx al cargarse la primera vez
        public ArticuloFormViewModel(List<Articulo> l, int actFT, int sizeFT,
                                    List<Articulo> la, int actA, int sizeA,
                                    List<Articulo> lo, int actO, int sizeO,
                                    bool hayErrAm,
                                    bool hayErrOAr,
                                    string txt)
        {
            
             if (hayErrAm && hayErrOAr)
                {
                    tipoError = 4;
                    hayError = true;
                }
                else if (hayErrAm)
                {
                    tipoError = 2;
                    hayError = true;
                }
                else if (hayErrOAr)
                {
                    tipoError = 3;
                    hayError = true;
                }
                else
                {
                    tipoError = 0;
                    hayError = false;
                }

            if (hayErrAm==false)
            {
                listaArtAmazon = new PaginatedList<Articulo>(la, actA % 2, sizeA);
            }
            if (hayErrOAr==false )
            {
                listaOtroAr = new PaginatedList<Articulo>(lo, actO, sizeO);
            }
            listaArmazon = new PaginatedList<Articulo>(l, actFT, sizeFT);
            pagActFT = actFT;
            pagActAmazon = actA;
            pagActOtroAr = actO;
            sizePageFT = sizeFT;
            sizePageAmazon = sizeA;
            sizePageOtroAr = sizeO;
            texto = txt;
        }
        public bool hasPreviousPageAm
        {
            get
            {
                return (pagActAmazon > 0);
            }
        }

        public bool hasNextPageAm
        {
            get
            {
                return (pagActAmazon + 1 < 6);//(PageIndex + 1 < TotalPagesla);
            }
        }
        
        public ArticuloFormViewModel(string s,int esInesp)
        {
            hayError = true;
            tipoError = esInesp;
            msgError =  s;
        }
        public PaginatedList<Articulo> getListaAmazon()
        {
            return listaArtAmazon;
        }
        public PaginatedList<Articulo> getListaArmazon()
        {
            return listaArmazon;
        }
        public PaginatedList<Articulo> getListaOtroAr()
        {
            return listaOtroAr;
        }
        public string getMsgError(){
            return msgError;
        }
        public bool getHayError()
        {
            return this.hayError;
        }
        public bool getHayErrorInesperado()
        {
            return (this.hayError && tipoError==5);
        }
        public bool getHayErrorArmazon()
        {
            return (this.hayError && tipoError == 1);
        }
        public bool getHayErrorAmazon()
        {
            return (this.hayError && (tipoError == 2 || tipoError == 4));
        }
        public bool getHayErrorOtroAr()
        {
            return (this.hayError && (tipoError == 3|| tipoError == 4));
        }
        public int getPagActFT()
        {
            return pagActFT;
        }
        public int getPagActAm()
        {
            return pagActAmazon;
        }
        public int getPagActOtroAr()
        {
            return pagActOtroAr;
        }
        public string getTexto()
        {
            return texto;
        }
    }
}
