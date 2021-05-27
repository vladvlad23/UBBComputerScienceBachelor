cmake_minimum_required(VERSION 3.10)
project(DSATeamWork)

set(CMAKE_CXX_STANDARD 14)
set(CMAKE_INCLUDE_CURRENT_DIR  ON)

add_executable(DSATeamWork
        Heap.cpp Heap.h
        main.cpp
        Tests.h Tests.cpp
        TestP11.h TestP11.cpp
        P11.h P11.cpp)