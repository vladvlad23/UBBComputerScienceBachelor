cmake_minimum_required(VERSION 3.10)
project(Laboratory_3_4 C)

set(CMAKE_C_STANDARD 11)

add_executable(Laboratory_3_4 main.c
        Repository/VectorList.c Repository/VectorList.h
        Domain/Validator.c Domain/Validator.h Domain/Medicine.h
        Controller/Controller.c Controller/Controller.h
        UserInterface/UserInterface.c UserInterface/UserInterface.h
        Tests/SpecificTestToMedicine.h Tests/SpecificTestToMedicine.c)