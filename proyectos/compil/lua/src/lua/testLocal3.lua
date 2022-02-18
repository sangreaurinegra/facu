print("En una funcion establezo la variable local Sam = 'I am Sam' ")
function GreenEggsAndHam()
        local Sam = "I Am Sam"
end

print("Afuera de la funcion imprimi el valor de Sam, que sera nil")
print(Sam)
--imprime nil

print("Pongo la variable global Sam = 'Sam I Am'")
print("En una funcion hago local Sam = 'I Am Sam'")
Sam = "Sam I Am"
function GreenEggsAndHam()
        local Sam = "I Am Sam"
end
print("Afuera de la funcion se tomara la variable global, imprime sam I Am")
print(Sam)
--imprime sam I Am"

---
print("Defino una funcion fun y una variable a")
a=10

function fun(b)
        print("Entro Fun")
        local a = 20
        print("local a =",a)
        print("b =",b)
        print("Salgo Fun")
end

print("Sin llamar a fun a = ",a)


fun("este es el print de la b")
print("Llamando a fun se tomara la variale a global= 10 ---- a = ",a)

