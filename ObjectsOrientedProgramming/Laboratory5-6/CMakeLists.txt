cmake_minimum_required(VERSION 3.13)
project(Laboratory5_6)


set(CMAKE_CXX_STANDARD 14)

find_package(Qt5Widgets REQUIRED)

set(CMAKE_AUTOUIC ON)
set(CMAKE_AUTOMOC ON)
set(CMAKE_INCLUDE_CURRENT_DIR ON)

add_library(clientwindow UserInterface/ClientGUI.cpp)
target_link_libraries(clientwindow Qt5::Widgets)

add_executable(Laboratory5_6
        main.cpp
        Domain/Dog.cpp Domain/Dog.h
        Repository/DynamicVector.cpp Repository/DynamicVector.h
        Controller/DogListController.cpp Controller/DogListController.h
        Repository/DogListValidator.cpp Repository/DogListValidator.h
        UserInterface/DogUserInterface.cpp UserInterface/DogUserInterface.h
        Tests/tests.cpp Tests/tests.h Repository/Repository.cpp Repository/Repository.h Comparator.cpp Comparator.h
        UserInterface/GraphicalUserInterface.h UserInterface/GraphicalUserInterface.cpp
        UserInterface/ClientGUI.h UserInterface/ClientGUI.cpp UserInterface/AdminGUI.cpp UserInterface/AdminGUI.h
        UserInterface/CommandLineInterface.h UserInterface/CommandLineInterface.cpp)

target_link_libraries(Laboratory5_6 Qt5::Widgets clientwindow)

#SET(GCC_COVERAGE_COMPILE_FLAGS "-fprofile-arcs -ftest-coverage")
#SET(GCC_COVERAGE_LINK_FLAGS    "-lgcov")

#SET(CMAKE_CXX_FLAGS  "${CMAKE_CXX_FLAGS} ${GCC_COVERAGE_COMPILE_FLAGS}")
#SET(CMAKE_EXE_LINKER_FLAGS  "${CMAKE_EXE_LINKER_FLAGS} ${GCC_COVERAGE_LINK_FLAGS}")