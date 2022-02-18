-- Laboratorio 2013 Modulo Direccion
-- Gonzalo Mi�ños 2.843.023-3 gminos@gmail.com - Gabriel Centurion 2.793.486-8 gccomputos@gmail.com

module Main  where
import System.IO
import System.Environment
import Data.Char
import Direccion
import Comando
import ParseLib
import System.Exit



--data Editor = Editor [(String,[Char])] Int Char Bool
data Editor = Editor [String] Int Char Bool
		deriving (Show,Eq)

main :: IO()
main = 	
	do 	editor  <-  iniciarEdi
		editor' <- procesarComandos editor
		print editor'
                terminarEdi editor'

iniciarEdi ::IO(Editor)
iniciarEdi = do 	
                archivo <- getArgs
                print archivo
		contenido <- readFile (head archivo)
		let ed = Editor (lines contenido) (length (lines contenido)) 'n' False
		print ed
                return ed
--  let ed = Editor (zip (lines archivo) []) (length (lines archivo)) 'n' False

--procesarComandos :: Editor -> IO(String)
procesarComandos :: Editor -> IO(Editor)
procesarComandos edi =
	do  comando <- leerComando
	    print comando
            if esValidoComando comando
	    then do editor' <- ejecutarComando comando edi
	            procesarComandos editor'
	        else do procesarError
                        procesarComandos edi

leerComando :: IO(Comando)
leerComando = do
                    linea <- getLine
                    return(fst (last(direccionTotal(lexer linea))))

ejecutarComando :: Comando -> Editor -> IO(Editor)
ejecutarComando comando editor =
                do  let accion = getAccion comando
                    print accion
                    if (accion == QuitUnc)
                    then do
                        comandoQuitUnc editor
                        return editor
                      --  terminarEdi edi
                        else do comandoQuitUnc editor
                                return editor

comandoQuitUnc :: Editor -> IO(Editor)
comandoQuitUnc editor = return editor


esValidoComando (Comando _ c (ArgDireccion (x:xs)))
	|((c==Append) || (c==Change) || (c==Delete) || (c==Insert) || (c==Join) || (c==Move) || (c==Printn) || (c==Printp)) = True
        |((c==Quit) || (c==QuitUnc) || (c==Copyt) || (c==Copyx) || (c==Copyy) || (c==Write) || (c==PrintEqual))  = True
        | otherwise = False

procesarError = print "Error en la entrada"

terminarEdi :: Editor -> IO()
terminarEdi msg = exitSuccess                           -- exitSuccess :: IO a

writeBuffer :: [String] ->IO()
writeBuffer (x:xs)
	| (x:xs) == [] = putStr " " 
	| otherwise = do    print x
			    writeBuffer xs


getLineaCorriente ::Editor -> Int
getLineaCorriente (Editor _ i _ _) = i

getUltimaLinea :: Editor -> Int
getUltimaLinea (Editor xs _ _ _) = length xs

--marcaNum :: Char -> Editor -> Int
--marcaNum c (Editor ((a,b):xs) j p bo) 
--	|elem c b = 0
--	|otherwise = 1 + marcaNum c (Editor xs j p bo)


darDirecNumero :: Direc->Editor->Int
darDirecNumero (Direc (Abs x) (y:ys)) h =y + darDirecNumero (Direc (Abs x) (ys)) h
darDirecNumero (Direc (Abs x) []) h = x
darDirecNumero (Direc Ultima (y:ys)) h =y + darDirecNumero (Direc Ultima (ys)) h 
darDirecNumero (Direc Ultima []) h = getUltimaLinea h
darDirecNumero (Direc Corriente (y:ys)) h =y + darDirecNumero (Direc Corriente (ys)) h
darDirecNumero (Direc Corriente []) h = getLineaCorriente h
darDirecNumero (Direc (Marca c) (y:ys)) h =y + darDirecNumero (Direc (Marca c) (ys)) h
--darDirecNumero (Direc (Marca c) []) h = marcaNum c h
darDirecNumero (Direc (Rel i) (y:ys)) h =y + darDirecNumero (Direc (Rel i) (ys)) h
darDirecNumero (Direc (Rel i) []) h = i

------------- COMANDOS ---------------------------
getAccion :: Comando -> Accion
getAccion (Comando _ c _) = c
