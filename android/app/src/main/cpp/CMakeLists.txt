cmake_minimum_required(VERSION 3.22.1)

project(y2kboy_jni LANGUAGES C CXX)

set(CMAKE_C_STANDARD 11)
set(CMAKE_CXX_STANDARD 20)
set(CMAKE_C_STANDARD_REQUIRED ON)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

set(Y2KBOY_REPO_ROOT ${CMAKE_CURRENT_LIST_DIR}/../../../../..)
set(Y2KBOY_ENABLE_REAL_MGBA OFF CACHE BOOL "Compile against the real mGBA core sources")

add_library(
    y2kboy
    SHARED
    y2kboy_jni.cpp
)

target_include_directories(
    y2kboy
    PRIVATE
    ${Y2KBOY_REPO_ROOT}/include
    ${Y2KBOY_REPO_ROOT}/src
)

target_compile_definitions(
    y2kboy
    PRIVATE
    Y2KBOY_REPO_ROOT="${Y2KBOY_REPO_ROOT}"
)

if(Y2KBOY_ENABLE_REAL_MGBA)
    target_compile_definitions(y2kboy PRIVATE Y2KBOY_ENABLE_REAL_MGBA=1)
endif()

find_library(android-lib android)
find_library(log-lib log)
find_library(egl-lib EGL)
find_library(glesv2-lib GLESv2)

target_link_libraries(
    y2kboy
    ${android-lib}
    ${log-lib}
    ${egl-lib}
    ${glesv2-lib}
)
