# Makefile for compiling and running serial program

# Directories
SRC_DIR = src/serialAbelianSandpile
BIN_DIR = bin/serialAbelianSandpile

# Source files
JAVA_FILES = $(wildcard $(SRC_DIR)/*.java)

# Compiled class files
CLASS_FILES = $(patsubst $(SRC_DIR)/%.java, $(BIN_DIR)/%.class, $(JAVA_FILES))

# Compilation flags
JAVAC_FLAGS = -d bin -sourcepath src

# Main classses
MAIN_CLASS = serialAbelianSandpile.AutomatonSimulation
P_MAIN_CLASS = serialAbelianSandpile.ParallelAutoSim

# Default arguments (update these if needed)
#ARGS ?= input/65_by_65_all_4.csv output/s65_by_65_all_4.png  # Replace 'default_arguments' with your specific default arguments, if any
#ARGS ?= input/517_by_517_centre_534578.csv output/517_by_517_centre_534578.png
#ARGS ?= input/8_by_8_all_4copy.csv output/s8_by_8_all_4copy.png
#ARGS ?= input/16_by_16_one_100.csv output/s16_by_16_one_100.png
ARGS ?= input/517_by_517_centre_534578.csv output/s517_by_517_centre_534578.png

# Targets
.PHONY: all clean run directories

all: directories $(CLASS_FILES)

directories:
	@mkdir -p $(BIN_DIR)

$(BIN_DIR)/%.class: $(SRC_DIR)/%.java
	javac $(JAVAC_FLAGS) $<

clean:
	rm -rf bin/*

run: all
	java -classpath bin $(MAIN_CLASS) $(ARGS)

runp: all
	java -classpath bin $(P_MAIN_CLASS) $(ARGS)
