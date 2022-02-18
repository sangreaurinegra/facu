--Modulo
print("14 % 5 = ",14 % 5) -- 4

--Operadores tradicionales: + - * / ^
print("(2+2*5)/10 - 1 = ",(2+2*5)/10 - 1 ) 

print("1-0.5 = ",1-0.5 ) 
print("0.5-1 = ",0.5-1 ) 
print("1 + 0.5 = ",1 + 0.5) 
print("0.5 + 1 = ",0.5 + 1)
print("1/2 = ",1/2)
print("1.5/ 3 = ",1.5/ 3)
print("3/ 1.5 = ",3/ 1.5)
print(",3.5%2 = ",3.5%2)
print("2%3.5 = ",2%3.5)
print("1.5 ^2 = ",1.5 ^2)
print("2^5 = ", 2^5 ) 

d=(2)
c = ((2+3)-(4+5)) 
h = -((2+3)-(4+5)) 
g = 2 + 2 + 2
m = -2 
n = 2 + 2 + 2 + 2
print("d=",d)
print("c=",c)
print("h=",h)
print("g=",g)
print("m=",m)
print("n=",n)

print("Prueba de concatenacion entre string, numeros y largo de tabla")
print("I".."am".."Sam")
bag_of_stuff = {"do", "re", "me", "fa", "so", "la", "si"}
a = 23

print("integer "..a.." real "..2.4.." largo "..#bag_of_stuff)
print(a.."hola".." real "..2.4.." largo "..#bag_of_stuff)


bag_of_stuff = {"do", "re", "me", "fa", "so", "la", "si"}
print("I haz "..#bag_of_stuff.." things")


print("Pruebas Logicas:")
--logicos

bool1= (2==2)~=false
print("bool1=",bool1)

a= true
b= false
c= true

if ((a and not b) ) then
    print ("anotb")
end

if (a or c) then
    print("aorc")
end

--

a= 1
b= 20

if (a<b ) then
    print ("amenor")
end

if (a>=0 ) then
    print ("amayor0")
end



