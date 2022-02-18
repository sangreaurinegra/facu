################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
O_SRCS += \
../test/testCliente/testCliente.o 

CPP_SRCS += \
../test/testCliente/testCliente.cpp 

OBJS += \
./test/testCliente/testCliente.o 

CPP_DEPS += \
./test/testCliente/testCliente.d 


# Each subdirectory must supply rules for building sources it contributes
test/testCliente/%.o: ../test/testCliente/%.cpp
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C++ Compiler'
	g++ -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


