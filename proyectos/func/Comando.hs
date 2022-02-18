-- Laboratorio 2013 Modulo Direccion
-- Gonzalo Mi�ños 2.843.023-3 gminos@gmail.com - Gabriel Centurion 2.793.486-8 gccomputos@gmail.com

module Comando where
import ParseLib


acc = ["a","c","d","i","j","m","n","p","q","Q","t","x","y","w","="]

data Accion = 	Append
		|	Change
		|	Delete      -- d
		|	Insert      -- i
		|	Join        -- j
		|	Move        -- m
		|	Printn      --n
		|	Printp      --p
		|	Quit        --q
		|	QuitUnc       --Q
		|	Copyt       --t
		|	Copyx       --x
		|	Copyy       --y
		|	Write       --w
		|       PrintEqual  -- Prints the line number of the addressed line.
	deriving (Show,Eq)



