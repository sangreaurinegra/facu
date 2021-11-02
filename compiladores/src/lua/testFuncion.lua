print("ASIGNACION DE FUNCION: fun = function(x,y) return x end")
print("res = fun(23)")
print("imprimo res, tiene que ser 23")
fun = function(x,y) return x end
res = fun(23)
print(res)

print("defino fun2(a,b) y la llamo, debe imprimir : 1, hola")
function fun2(a,b)
        print(a,b)
end

fun2(1,"hola")
print("llamo a fun2 con menos paramtros fun2(25) debe imprimir 25, nil")
fun2(25)

print("llamo a fun2 con mas paramtros fun2(25,'hola',40) debe imprimir 25, hola")
fun2(25,"hola",40)

print("defino una funcion sin cuerpo y la llamo, esto es valido")
function fun3(param,param2)
end
fun3()


