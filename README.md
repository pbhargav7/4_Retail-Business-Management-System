# Retail Business Management System
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=flat-square&logo=openjdk&logoColor=white)![Oracle](https://img.shields.io/badge/Oracle-F80000?style=flat-square&logo=oracle&logoColor=white)![Visual Studio Code](https://img.shields.io/badge/Visual%20Studio%20Code-0078d7.svg?style=flat-square&logo=visual-studio-code&logoColor=white)
>Written by:
>- _Bhargav Patel_
## Table of Contents
1. [Overview](#overview)
2. [Build & Run](#buildrun)
## Overview
The project contains five subdirectories:
- `.vscode`
- `bin`
- `lib`
- `scripts`
- `src`

The directory tree is shown below: 
```
├───.vscode
│       settings.json
│
├───bin
│       P2DB.class
│       P2Util.class
│       Project2.class
│
├───lib
│       ojdbc11.jar
│       ojdbc6.jar
│
├───scripts
│       1_RBMSTablesScript23.txt
│       2_proc.sql
│       3_trigger.sql
│
└───src
        P2DB.java
        P2Util.java
        Project2.java
```
### `.vscode`
>This directory contains a `settings.json` file for building with VS Code.

### `bin`
>This directory contains Java bytecode files that can be executed by the JVM. 
>This is the output path when building the project from Java source code.
>The file `Project2.class` contains the `main` method.

### `lib`
>This directory contains the Oracle libraries needed to build the project.
>This is the include path when building from VS Code.

### `scripts`
>This directory contains the scripts that build or restore the database to its original state.
>These scripts can be run on the database to refresh it after making changes.
>The Java program does not use these files -- they are executed manually to set up the database for testing and are put in this folder for the sake of convenience.
>The scripts should be run in the following order:
>1. `1_RBMSTablesScript23.txt`
>2. `2_proc.sql`
>3. `3_trigger.sql`

### `src`
>This directory contains the Java source code files for the project.
>The file `Project2.java` contains the `main` method.
## Build/Run
Building the project in VS Code with Java extensions is trivial, as the output paths and include paths are already set up to build from this environment.
Moreover, the precompiled bytecode files are already included in the `bin` folder, so building the project isn't necessarily needed to run the program.
Running the program from VS Code is also trivial. The project's `main` function is written in `src/Project2.java` and compiles to `bin/Project2.class`.



