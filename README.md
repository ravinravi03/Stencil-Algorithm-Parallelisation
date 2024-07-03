# Stencil Algorithm Parallelisation

This project explores the parallelisation of Iterative Stencil Loops using different implementations in Java.

## Overview

Iterative stencil algorithms are widely used in solving partial differential equations and image processing. This project focuses on parallelising these algorithms to improve performance.

### Implementations

- **Executor Service**: Utilises Java's `ExecutorService` framework for parallel execution.
- **Aparapi**: Leverages Aparapi for GPU-accelerated computations.
- **Pyjama**: Uses the Pyjama research framework from the University of Auckland which utilises OpenMP-like directives to parallelise Java code.

Only the main branch explores the Pyjama implementation, which is under active development.

## Getting Started

### Downloading the JAR

You can download the latest JAR release [here](https://github.com/ravinravi03/Stencil-Algorithm-Parallelisation/releases/download/v0.1.0-alpha/StencilParallelisation.jar).

### Running the JAR

To run the JAR file in your terminal:

1. Navigate to the directory where `StencilParallelisation.jar` is downloaded.
2. Execute the following command:

```
java -jar StencilParallelisation.jar
```

### Running from Source

To run from source, follow these steps:

1. Clone the repository:
```
git clone https://github.com/ravinravi03/Stencil-Algorithm-Parallelisation.git
```

2. Navigate into the project directory:
```
cd Stencil-Algorithm-Parallelisation/Stencil
```

3. Use Maven to execute the Java application:
```
mvn exec:java
```
