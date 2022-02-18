print("Switching de variables en el if")
if (AreWeGonnaDieIn2012 or "2012's a joke.") then
    print("2012 is a joke")
else
    print("nop")
end

print("Pruebo el 'if name then' tal que si name es distinto de nil tiene que entrar y devolver Hello Jack")
--tiene que devolver hello jack
function SayHiTo(name)
        if name then
        print("Hello",name)
        end
end
SayHiTo("Jack")
SayHiTo()

--
print("Pruebo el if y el else, tiene que devolver Hello Jack y Hello Stranger")
function SayHiTo2(name)
        if name then
                print("Hello",name)
        else
                print("Hello Stranger")
        end
end
SayHiTo2("Jack")
SayHiTo2()

print("Pruebo el not, tiene que devolver Hello Stranger y Hello Jack ")
function SayHiTo3(name)
if not name then
print("Hello Stranger")
else
print("Hello", name)
end
end
SayHiTo3("Jack")
SayHiTo3()

--


function I_want_to_find(the_number)
  for a_number = 1, 5 do
    if the_number == a_number then
         print ("I found him!")
    else
          print ("Sorry, he's not here.")
    end
  end
end
print("Busco el numero 2 entre 1  y 5")
I_want_to_find(2)
print("Busco el numero 0 entre 1  y 5")
I_want_to_find(0)
