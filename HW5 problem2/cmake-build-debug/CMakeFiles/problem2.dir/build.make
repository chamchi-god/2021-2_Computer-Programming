# CMAKE generated file: DO NOT EDIT!
# Generated by "MinGW Makefiles" Generator, CMake Version 3.20

# Delete rule output on recipe failure.
.DELETE_ON_ERROR:

#=============================================================================
# Special targets provided by cmake.

# Disable implicit rules so canonical targets will work.
.SUFFIXES:

# Disable VCS-based implicit rules.
% : %,v

# Disable VCS-based implicit rules.
% : RCS/%

# Disable VCS-based implicit rules.
% : RCS/%,v

# Disable VCS-based implicit rules.
% : SCCS/s.%

# Disable VCS-based implicit rules.
% : s.%

.SUFFIXES: .hpux_make_needs_suffix_list

# Command-line flag to silence nested $(MAKE).
$(VERBOSE)MAKESILENT = -s

#Suppress display of executed commands.
$(VERBOSE).SILENT:

# A target that is always out of date.
cmake_force:
.PHONY : cmake_force

#=============================================================================
# Set environment variables for the build.

SHELL = cmd.exe

# The CMake executable.
CMAKE_COMMAND = "C:\Program Files\JetBrains\CLion 2021.2.3\bin\cmake\win\bin\cmake.exe"

# The command to remove a file.
RM = "C:\Program Files\JetBrains\CLion 2021.2.3\bin\cmake\win\bin\cmake.exe" -E rm -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = "C:\Users\chamchigod\Documents\computer programming\HW5\problem2"

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = "C:\Users\chamchigod\Documents\computer programming\HW5\problem2\cmake-build-debug"

# Include any dependencies generated for this target.
include CMakeFiles/problem2.dir/depend.make
# Include the progress variables for this target.
include CMakeFiles/problem2.dir/progress.make

# Include the compile flags for this target's objects.
include CMakeFiles/problem2.dir/flags.make

CMakeFiles/problem2.dir/main.obj: CMakeFiles/problem2.dir/flags.make
CMakeFiles/problem2.dir/main.obj: ../main.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir="C:\Users\chamchigod\Documents\computer programming\HW5\problem2\cmake-build-debug\CMakeFiles" --progress-num=$(CMAKE_PROGRESS_1) "Building CXX object CMakeFiles/problem2.dir/main.obj"
	C:\MinGW\bin\g++.exe $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles\problem2.dir\main.obj -c "C:\Users\chamchigod\Documents\computer programming\HW5\problem2\main.cpp"

CMakeFiles/problem2.dir/main.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/problem2.dir/main.i"
	C:\MinGW\bin\g++.exe $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E "C:\Users\chamchigod\Documents\computer programming\HW5\problem2\main.cpp" > CMakeFiles\problem2.dir\main.i

CMakeFiles/problem2.dir/main.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/problem2.dir/main.s"
	C:\MinGW\bin\g++.exe $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S "C:\Users\chamchigod\Documents\computer programming\HW5\problem2\main.cpp" -o CMakeFiles\problem2.dir\main.s

CMakeFiles/problem2.dir/CSI.obj: CMakeFiles/problem2.dir/flags.make
CMakeFiles/problem2.dir/CSI.obj: ../CSI.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir="C:\Users\chamchigod\Documents\computer programming\HW5\problem2\cmake-build-debug\CMakeFiles" --progress-num=$(CMAKE_PROGRESS_2) "Building CXX object CMakeFiles/problem2.dir/CSI.obj"
	C:\MinGW\bin\g++.exe $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles\problem2.dir\CSI.obj -c "C:\Users\chamchigod\Documents\computer programming\HW5\problem2\CSI.cpp"

CMakeFiles/problem2.dir/CSI.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/problem2.dir/CSI.i"
	C:\MinGW\bin\g++.exe $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E "C:\Users\chamchigod\Documents\computer programming\HW5\problem2\CSI.cpp" > CMakeFiles\problem2.dir\CSI.i

CMakeFiles/problem2.dir/CSI.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/problem2.dir/CSI.s"
	C:\MinGW\bin\g++.exe $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S "C:\Users\chamchigod\Documents\computer programming\HW5\problem2\CSI.cpp" -o CMakeFiles\problem2.dir\CSI.s

CMakeFiles/problem2.dir/TestHelper.obj: CMakeFiles/problem2.dir/flags.make
CMakeFiles/problem2.dir/TestHelper.obj: ../TestHelper.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir="C:\Users\chamchigod\Documents\computer programming\HW5\problem2\cmake-build-debug\CMakeFiles" --progress-num=$(CMAKE_PROGRESS_3) "Building CXX object CMakeFiles/problem2.dir/TestHelper.obj"
	C:\MinGW\bin\g++.exe $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles\problem2.dir\TestHelper.obj -c "C:\Users\chamchigod\Documents\computer programming\HW5\problem2\TestHelper.cpp"

CMakeFiles/problem2.dir/TestHelper.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/problem2.dir/TestHelper.i"
	C:\MinGW\bin\g++.exe $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E "C:\Users\chamchigod\Documents\computer programming\HW5\problem2\TestHelper.cpp" > CMakeFiles\problem2.dir\TestHelper.i

CMakeFiles/problem2.dir/TestHelper.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/problem2.dir/TestHelper.s"
	C:\MinGW\bin\g++.exe $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S "C:\Users\chamchigod\Documents\computer programming\HW5\problem2\TestHelper.cpp" -o CMakeFiles\problem2.dir\TestHelper.s

# Object files for target problem2
problem2_OBJECTS = \
"CMakeFiles/problem2.dir/main.obj" \
"CMakeFiles/problem2.dir/CSI.obj" \
"CMakeFiles/problem2.dir/TestHelper.obj"

# External object files for target problem2
problem2_EXTERNAL_OBJECTS =

problem2.exe: CMakeFiles/problem2.dir/main.obj
problem2.exe: CMakeFiles/problem2.dir/CSI.obj
problem2.exe: CMakeFiles/problem2.dir/TestHelper.obj
problem2.exe: CMakeFiles/problem2.dir/build.make
problem2.exe: CMakeFiles/problem2.dir/linklibs.rsp
problem2.exe: CMakeFiles/problem2.dir/objects1.rsp
problem2.exe: CMakeFiles/problem2.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir="C:\Users\chamchigod\Documents\computer programming\HW5\problem2\cmake-build-debug\CMakeFiles" --progress-num=$(CMAKE_PROGRESS_4) "Linking CXX executable problem2.exe"
	$(CMAKE_COMMAND) -E cmake_link_script CMakeFiles\problem2.dir\link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
CMakeFiles/problem2.dir/build: problem2.exe
.PHONY : CMakeFiles/problem2.dir/build

CMakeFiles/problem2.dir/clean:
	$(CMAKE_COMMAND) -P CMakeFiles\problem2.dir\cmake_clean.cmake
.PHONY : CMakeFiles/problem2.dir/clean

CMakeFiles/problem2.dir/depend:
	$(CMAKE_COMMAND) -E cmake_depends "MinGW Makefiles" "C:\Users\chamchigod\Documents\computer programming\HW5\problem2" "C:\Users\chamchigod\Documents\computer programming\HW5\problem2" "C:\Users\chamchigod\Documents\computer programming\HW5\problem2\cmake-build-debug" "C:\Users\chamchigod\Documents\computer programming\HW5\problem2\cmake-build-debug" "C:\Users\chamchigod\Documents\computer programming\HW5\problem2\cmake-build-debug\CMakeFiles\problem2.dir\DependInfo.cmake" --color=$(COLOR)
.PHONY : CMakeFiles/problem2.dir/depend

