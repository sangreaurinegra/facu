-- 2843023 2793486

module Main  where
import System.IO
import System.Environment
import Data.Char
import Comando
import ParseLib
import System.Exit
import Editor
import System.Directory
import Data.Maybe

main :: IO()
main = 	
	do 	editor  <-  iniciarEdi
		editor' <- procesarComandos editor
--		print editor'
                terminarEdi editor'

iniciarEdi ::IO(Editor)
iniciarEdi = do 	
                args <- getArgs
		if (listToMaybe (args) == Nothing)
		then do
			putStrLn "Error: falta indicar archivo"
			exitSuccess
		else do
			let archivo =  head args
	                fileExists <- doesFileExist archivo		
	       	        if fileExists
      	 	        then do 
        	        	contenido <- readFile archivo
				let ed = Editor archivo (lines contenido) (length (lines contenido)) [] False
				print (length contenido)
        	        	return ed
			else do putStrLn ("El archivo " ++ (archivo ++ " no existe."))
				let ed = Editor archivo [] 0 [] False
				return ed

procesarComandos :: Editor -> IO(Editor)
procesarComandos edi =
	do  comando <- leerComando
--	    print comando
            if esValidoComando comando
	    then do editor' <- ejecutarComando comando edi
	            procesarComandos editor'
                else do procesarError
                        procesarComandos edi


leerComando :: IO(Comando)
leerComando = do
                    linea <- getLine
                    let comTokenizado = listToMaybe (comandoEntero(lexer linea))
                    if (comTokenizado == Nothing)
                    then do
                            let resultado = Comando [RangoInicioFinal] NotValid (Argumento [])
                            return resultado
                    else
                            return(fst (last(comandoEntero(lexer linea)))) 

leerEntrada :: IO([String])
leerEntrada = do
	linea <- getLine
	case linea of
		"." -> return([])
		otherwise  -> do
			linea'<- leerEntrada
			return([linea]++linea')
							
							
ejecutarComando :: Comando -> Editor -> IO(Editor)
ejecutarComando comando editor =
  do
    let accion = getAccionFromCom comando
    let iniFin = getLineasFromComando comando editor   -- Obtiene linea de inicio y final para los comandos que necesitan 2 direcciones
    let lineaUnica = getLineaFromComando comando editor   -- Obtiene linea de ejecución para los comandos que necesitan solo 1 dirección
    if ((elem (getAccionFromCom comando) [Change,Delete,Join,Move,Printn,Printp,Copyt,Copyy]) && (getPrimeraDir iniFin == -1))
    then do putStrLn "?"
            return editor
    else if ((elem (getAccionFromCom comando) [Append, Insert, PrintEqual,Copyx]) && (lineaUnica == -1))
         then do putStrLn "?"
                 return editor
         else
            case (accion) of a
                                | a == QuitUnc -> do
                                               let ediMod = comando_QuitUnc editor
                                               exitSuccess
                                | a == Quit ->
                                        if (isEditorConCambios editor == False)
                                                  then do exitSuccess
                                                          else do putStrLn "?"
                                                                  return editor
                                | a == Append -> do
							entrada <- leerEntrada
							let editor' = setPosicion editor lineaUnica
							return (append editor' entrada)
                                | a == Change     -> do 
                                                        entrada <- leerEntrada
                                                        return (changes editor (getPrimeraDir iniFin) (getSegundaDir iniFin) entrada)
                                | a == Delete     -> return (deletes editor (getPrimeraDir iniFin) (getSegundaDir iniFin))
                                | a == Insert     -> do
                                                        entrada <- leerEntrada
						        let editor' = setPosicion editor lineaUnica
						        return (inserts editor' entrada)
                                | a == Join     -> return (joins editor (getPrimeraDir iniFin) (getSegundaDir (iniFin)))
                                | a == Move     -> return (moves editor (getPrimeraDir iniFin) (getSegundaDir iniFin) (getIntFromParamDirec comando editor))
                                | a == Printn     -> do
                                                        printn editor (getPrimeraDir iniFin) (getSegundaDir iniFin)
                                | a == Printp     -> printp editor (getPrimeraDir iniFin) (getSegundaDir iniFin)
                                | a == PrintEqual   -> do
                                                            printigual editor lineaUnica
                                                            return editor
                                | a == Copyt     -> return (copyt editor (getPrimeraDir iniFin) (getSegundaDir iniFin) (getIntFromParamDirec comando editor))
                                | a == Copyx     -> return (copyx editor lineaUnica)
                                | a == Copyy     -> return (copyy editor (getPrimeraDir iniFin) (getSegundaDir (iniFin)))
                                | a == Write     -> do
							editorGuardado <- guardarArchivo editor (getPrimeraDir iniFin) (getSegundaDir iniFin)
                                                    	return   editorGuardado 
                                                         
                                | otherwise -> return editor


---------- COMANDOS ------------------------------------
-- Exit edi, sin salvar los cambios que existan
comando_QuitUnc :: Editor -> IO()
comando_QuitUnc editor = terminarEdi editor


procesarError = putStrLn "?"

terminarEdi :: Editor -> IO()
terminarEdi edi = exitSuccess


data Accion = 	Append
		|	Change
		|	Delete
		|	Insert
		|	Join
		|	Move
		|	Printn      --n
		|	Printp      --p
		|	Quit        --q
		|	QuitUnc       --Q
		|	Copyt       --t
		|	Copyx       --x
		|	Copyy       --y
		|	Write       --w
		|   PrintEqual
		|   NotValid    -- Simula comando no valido
	deriving (Show,Eq)

data TiposDirecciones = TiposDirecciones Direc      -- Direc Base [Int]
		| RangoInicioFinal                  -- equivale al rango 1,$
		| RangoCorrienteFinal               -- equivale al rango .,$
		deriving (Show,Eq)


data Argumento =  Argumento [TiposDirecciones]
		deriving (Show,Eq)

data Comando = Comando [TiposDirecciones] Accion Argumento
		deriving (Show,Eq)

listaDirecciones = list tiposDir
tiposDir = (direc >*> coma) `build` getTipoDireccion `alt` (token "," `build` const RangoInicioFinal) `alt` (token ";" `build` const RangoCorrienteFinal)
  
getTipoDireccion :: (Direc,Token)->TiposDirecciones
getTipoDireccion (a,b) = TiposDirecciones a

coma = token "," `alt` (succeed [])


dirMasComando = listaDirecciones >*> operando
operando = spot isOperando `build` hacerOperando
		where hacerOperando x
			| x=="a" = Append
			| x=="c" = Change
			| x=="d" = Delete
			| x=="i" = Insert
			| x=="j" = Join
			| x=="m" = Move
			| x=="n" = Printn
			| x=="p" = Printp
			| x=="q" = Quit
			| x=="Q" = QuitUnc
			| x=="t" = Copyt
			| x=="x" = Copyx
			| x=="y" = Copyy
			| x=="w" = Write
			| x=="=" = PrintEqual
			| x=="@" = NotValid


argumento =  (listaDirecciones `build` Argumento)
comandoEntero = (dirMasComando >*> argumento) `build` crearComando
		where crearComando ((a,b),c) = Comando a b c


------- FUNCIONES AUXILIARES ----------------------------------
isOperando:: Token ->Bool
isOperando x= elem x oper

getLineaCorriente ::Editor -> Int
getLineaCorriente (Editor _ _ i _ _) = i

getUltimaLinea :: Editor -> Int
getUltimaLinea (Editor _ xs _ _ _) = length xs

getBuffer :: Editor -> [String]
getBuffer (Editor _ xs _ _ _) = xs

isEditorConCambios ::Editor -> Bool
isEditorConCambios (Editor _ _ _ _ b) = b


getDirFromEdi :: Direc->Editor->Int
getDirFromEdi (Direc (Abs a) (x:xs)) ed = x + getDirFromEdi(Direc (Abs a) (xs)) ed
getDirFromEdi (Direc (Abs a) []) ed = a
getDirFromEdi (Direc (Rel a) (x:xs)) ed = x + getDirFromEdi(Direc (Rel a) (xs)) ed
getDirFromEdi (Direc (Rel a) []) ed
    | (a >= 0) = (getLineaCorriente ed) + a
    | (a < 0) = (getLineaCorriente ed) + a
getDirFromEdi (Direc Corriente (x:xs)) ed = x + getDirFromEdi(Direc Corriente (xs)) ed
getDirFromEdi (Direc Corriente []) ed = getLineaCorriente ed
getDirFromEdi (Direc Ultima (x:xs)) ed = x + getDirFromEdi(Direc Ultima (xs)) ed
getDirFromEdi (Direc Ultima []) ed = getUltimaLinea ed


esValidoComando (Comando d c a)
	|((c==Append) || (c==Change) || (c==Delete) || (c==Insert) || (c==Join) || (c==Move) || (c==Printn) || (c==Printp)
            || (c==Quit) ||  (c==Copyt) || (c==Copyx) || (c==Copyy) || (c==Write) || (c==PrintEqual))  = True
	| (c==QuitUnc) = True
        | otherwise = False


getParamFromCom :: Comando -> Argumento
getParamFromCom (Comando _ _ a) = a

getDirecFromArg :: Argumento -> TiposDirecciones
getDirecFromArg (Argumento a) = head a

-- Retorna la dir (int) desde el parametro (el 1ero de todos los ingresados)
getIntFromParamDirec :: Comando -> Editor -> Int
getIntFromParamDirec comando editor = getDirFromEdi (getDirecFinal( getDirecFromArg (getParamFromCom comando))) editor

getDirFromCom :: Comando -> [TiposDirecciones]
getDirFromCom (Comando d _ _) = d

getAccionFromCom :: Comando -> Accion
getAccionFromCom (Comando _ c _) = c

getDirecFinal :: TiposDirecciones -> Direc
getDirecFinal (TiposDirecciones d ) = d

getPrimeraDir:: IniFin -> Int
getPrimeraDir (IniFin i _) = i

getSegundaDir:: IniFin -> Int
getSegundaDir (IniFin _ f) = f

-- si dir es > length o menor que 0, o la resta con la actual < 0, retorna false
isInRangoDir:: Int -> Editor -> Bool
isInRangoDir linea edi
    | linea > (getUltimaLinea edi) = False
    | linea < 0 = False
--    | (linea < 0) && (getLineaCorriente edi) + linea < 0 = False
--    | ((getLineaCorriente edi) + linea) < 0 = False    
    | otherwise = True


data IniFin = IniFin Int Int
        deriving Show


{- Se deben aceptar al menos las siguientes variantes:
    ninguna dirección (se asumen las direcciones por omisión según cada comando)    OK
    coma (equivale al rango 1,$)                                                    OK
    punto y coma (equivale al rango .,$)                                            OK
    una sola dirección (bloque de 1 línea)                                          OK
    dos direcciones separados por coma                                              OK
-}
-- ***** Devuelve la linea de inicio y de fin sobre las cuales se aplica el comando correspondiente *******
getLineasFromComando :: Comando -> Editor -> IniFin
getLineasFromComando com edi
        -- Caso sin direcciones para Write
      | ((listToMaybe (getDirFromCom com)) == Nothing) && (getAccionFromCom com == Write) = IniFin (1) (getUltimaLinea edi) --(.,.) o vacío
        -- Caso sin direcciones para el resto de los comandos menos Write
      | (listToMaybe (getDirFromCom com)) == Nothing = IniFin (getLineaCorriente edi) (getLineaCorriente edi) --(.,.) o vacío
        -- solo coma, es el rango (1,$)
      | (last (getDirFromCom com) == RangoInicioFinal) && (length (getDirFromCom com)==1) = IniFin (1) (getUltimaLinea edi)
        -- solo punt y coma es el rango (.,$)
      | (last (getDirFromCom com) == RangoCorrienteFinal) && (length (getDirFromCom com)==1) = IniFin (getLineaCorriente edi) (getUltimaLinea edi)
        -- una sola dirección (bloque de 1 línea), pero no es ningun rango especial
      | (length (getDirFromCom com) == 1) && (isInRangoDir(getDirFromEdi (getDirecFinal (head (getDirFromCom com))) edi) edi) = IniFin (getDirFromEdi (getDirecFinal ((getDirFromCom com) !! 0)) edi) (getDirFromEdi (getDirecFinal((getDirFromCom com) !! 0)) edi)
        -- 2 direcciones al estilo nro coma (4,) resultado sería 4,4
      | (length (getDirFromCom com) == 2) && (isInRangoDir(getDirFromEdi (getDirecFinal (head (getDirFromCom com))) edi) edi) && (last (getDirFromCom com) == RangoInicioFinal) = IniFin (getDirFromEdi (getDirecFinal ((getDirFromCom com) !! 0)) edi) (getDirFromEdi (getDirecFinal((getDirFromCom com) !! 0)) edi)
       -- 2 direcciones al estilo nro punt y coma (4;) resultado sería 4,4
      | (length (getDirFromCom com) == 2) && (isInRangoDir(getDirFromEdi (getDirecFinal (head (getDirFromCom com))) edi) edi) && (last (getDirFromCom com) == RangoCorrienteFinal) = IniFin (getDirFromEdi (getDirecFinal ((getDirFromCom com) !! 0)) edi) (getDirFromEdi (getDirecFinal((getDirFromCom com) !! 0)) edi)
        -- Más de 2 direcciones, tomo ultimas 2 y controlo 1era > 2da (o sea 3)
      | (length (getDirFromCom com) > 2) && ((getDirFromEdi (getDirecFinal ((getDirFromCom com) !! ((length (getDirFromCom com))-3))) edi) > (getDirFromEdi (getDirecFinal (last (getDirFromCom com))) edi)) = IniFin (-1) (-1)
        -- Más de 2 direcciones separados por coma (o sea 3 o más), tiene que tomar las 2 ultimas
      | (length (getDirFromCom com) > 2) && (isInRangoDir(getDirFromEdi (getDirecFinal ((getDirFromCom com) !! ((length (getDirFromCom com))-3))) edi)edi) && (isInRangoDir(getDirFromEdi (getDirecFinal (last (getDirFromCom com))) edi) edi) = IniFin (getDirFromEdi (getDirecFinal ((getDirFromCom com) !! ((length (getDirFromCom com))-3))) edi) (getDirFromEdi (getDirecFinal(last (getDirFromCom com))) edi)      
      | otherwise = IniFin (-1) (-1)    -- el resto de combinaciones sale todo por aca (Va a imprimir "?")


-- ***** Devuelve la linea de dirección para ejecutar el comando, para los comandos que requieren 1 sola direccion ********
getLineaFromComando :: Comando -> Editor -> Int
getLineaFromComando com edi
        -- Sin direcciones. Si es para Append o Insert devuelve punto, si es para = devuelve $
      | (listToMaybe (getDirFromCom com)) == Nothing = if (getAccionFromCom com == PrintEqual) then (getUltimaLinea edi) else (getLineaCorriente edi)
        -- solo coma, es el rango (1,$) -- VER EN EL ED COMO SE COMPORTA. Dependiendo de eso, no controlar largo aca
      | (last (getDirFromCom com) == RangoInicioFinal) && (length (getDirFromCom com)==1) = if (getAccionFromCom com == PrintEqual) then (getUltimaLinea edi) else (getLineaCorriente edi)
        -- solo punt y coma es el rango (.,$) -- VER EN EL ED COMO SE COMPORTA.. Dependiendo de eso, no controlar largo aca
      | (last (getDirFromCom com) == RangoCorrienteFinal) && (length (getDirFromCom com)==1) = if (getAccionFromCom com == PrintEqual) then (getUltimaLinea edi) else (getLineaCorriente edi)
         -- Toma la ultima Dirección, es 0 y es para Append
      | (getDirFromEdi (getDirecFinal (last (getDirFromCom com))) edi == 0) && (getAccionFromCom com == Append) = 0
         -- Toma la ultima Dirección, es 1 y es para Insert
      | (getDirFromEdi (getDirecFinal (last (getDirFromCom com))) edi == 0) && (getAccionFromCom com == Insert) = 1
       -- Al menos 1 dir, no es ni , ni ; tomo la ultima (mas a la derecha)
      | (length (getDirFromCom com) > 0) && (isInRangoDir(getDirFromEdi (getDirecFinal (last (getDirFromCom com))) edi) edi)= (getDirFromEdi (getDirecFinal (last (getDirFromCom com))) edi)
      | otherwise = -1




