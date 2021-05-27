cmake_minimum_required(VERSION 3.13)
project(FirstTest)

set(CMAKE_CXX_STANDARD 14)

add_executable(FirstTest main.cpp Repository.cpp Repository.h Population.cpp Population.h Controller.cpp Controller.h UserInterface.cpp UserInterface.h Tests.cpp Tests.h)