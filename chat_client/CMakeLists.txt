cmake_minimum_required(VERSION 2.8.7)
include($ENV{ROS_ROOT}/core/rosbuild/rosbuild.cmake)

# Set the build type.  Options are:
#  Coverage       : w/ debug symbols, w/o optimization, w/ code-coverage
#  Debug          : w/ debug symbols, w/o optimization
#  Release        : w/o debug symbols, w/ optimization
#  RelWithDebInfo : w/ debug symbols, w/ optimization
#  MinSizeRel     : w/o debug symbols, w/ optimization, stripped binaries
#set(ROS_BUILD_TYPE RelWithDebInfo)

rosbuild_init()

#set the default path for built executables to the "bin" directory
set(EXECUTABLE_OUTPUT_PATH ${PROJECT_SOURCE_DIR}/bin)
#set the default path for built libraries to the "lib" directory
set(LIBRARY_OUTPUT_PATH ${PROJECT_SOURCE_DIR}/lib)
#set java binary path
set(JAVA_OUTPUT_DIR ${PROJECT_SOURCE_DIR}/bin)
include(UseJava)

#uncomment if you have defined messages
#rosbuild_genmsg()
#uncomment if you have defined services
#rosbuild_gensrv()

# TODO why do we even need to specify these libs? they are needed by json_prolog and are already specified there.

# Prolog stuff
rosbuild_find_ros_package(jpl)
add_jni_path(${jpl_PACKAGE_PATH}/prolog)
add_ld_preload(/usr/lib/libswipl.so)
add_ld_preload(/usr/lib/libpl.so)
add_classpath(${jpl_PACKAGE_PATH}/prolog/jpl.jar)

# Java: JSON lib
find_jar(JSON_LIB_JAR NAMES json-lib)
add_classpath(${JSON_LIB_JAR})
add_runtime_classpath(${JSON_LIB_JAR})

# Java: Apache Commons - bean utils
find_jar(COMMONS_BEANUTILS_JAR NAMES commons-beanutils)
add_classpath(${COMMONS_BEANUTILS_JAR})
add_runtime_classpath(${COMMONS_BEANUTILS_JAR})

# Java: Apache Commons - collections
find_jar(COMMONS_COLLECTIONS3_JAR commons-collections3)
add_classpath(${COMMONS_COLLECTIONS3_JAR})
add_runtime_classpath(${COMMONS_COLLECTIONS3_JAR})

# Java: Apache Commons - logging
find_jar(COMMONS_LOGGING_JAR commons-logging)
add_classpath(${COMMONS_LOGGING_JAR})
add_runtime_classpath(${COMMONS_LOGGING_JAR})

# Java: ezmorph
find_jar(EZMORPH_JAR ezmorph)
add_classpath(${EZMORPH_JAR})
add_runtime_classpath(${EZMORPH_JAR})

# Java: Apache Commons - lang
find_jar(COMMONS_LANG_JAR commons-lang)
add_classpath(${COMMONS_LANG_JAR})
add_runtime_classpath(${COMMONS_LANG_JAR})

add_java_source_dir( ${PROJECT_SOURCE_DIR}/src/java )
rospack_add_java_executable( chat_client org.knowrob.tutorials.chat.ChatClient )

#common commands for building c++ executables and libraries
#rosbuild_add_library(${PROJECT_NAME} src/example.cpp)
#target_link_libraries(${PROJECT_NAME} another_library)
#rosbuild_add_boost_directories()
#rosbuild_link_boost(${PROJECT_NAME} thread)
#rosbuild_add_executable(example examples/example.cpp)
#target_link_libraries(example ${PROJECT_NAME})
