################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
O_SRCS += \
../src/logger.o \
../src/pct.o \
../src/stringLib.o 

CPP_SRCS += \
../src/logger.cpp \
../src/pct.cpp \
../src/stringLib.cpp 

CC_SRCS += \
../src/enviaFile.cc 

OBJS += \
./src/enviaFile.o \
./src/logger.o \
./src/pct.o \
./src/stringLib.o 

CC_DEPS += \
./src/enviaFile.d 

CPP_DEPS += \
./src/logger.d \
./src/pct.d \
./src/stringLib.d 


# Each subdirectory must supply rules for building sources it contributes
src/%.o: ../src/%.cc
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C++ Compiler'
	g++ -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '

src/%.o: ../src/%.cpp
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C++ Compiler'
	g++ -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


