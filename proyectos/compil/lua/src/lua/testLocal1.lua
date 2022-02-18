a=0

function func(a)
        print("-Paramentro de funcion a = "..a)
        local a = true
        if a then
                a= "a en Scope nivel 2"
                print(a)
                if true then 
	                local a = "a en Scope nivel 3"
	                print(a)
                end	
        print(a)
        else
                a = "No entro"
end
print("-Valor de final fucion a = "..a)

end

function funcb()
        a= "funcb cambio valor de a global"
        print(a)
end


print("Valor de a inicial a ="..a)
func(a)
print("a en principal = "..a)
funcb()
print("a en principal despues de funcb= "..a)











