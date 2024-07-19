# Functional Graphs

## Project Overview
Functional Graphs is a Scala project designed to implement various graph data structures and algorithms using functional programming principles. The project includes the integration of the ZIO 2 library for handling side effects and asynchronous computations.

### Key Components
Graph Data Structure**: Includes directed, undirected, and weighted graphs.
Graph Operations**: Implementation of algorithms such as Depth First Search (DFS), Breadth First Search (BFS), Topological Sort, Cycle Detection, Floyd-Warshall, and Dijkstra's Algorithm.
ZIO 2 Application Integration**: An application that demonstrates the use of these graph structures and operations.


### Prerequisites
- Scala 2.13.10
- sbt 
- java version: java 11.0.21

## Instructions
- compilation : sbt compile
- running the app : sbt "app/run"
- testing : sbt test

### Structure
- app directory :  subproject containing the main application code and tests
- core directory: subproject containing the core library code and tests, focusing on graph data structures and operations
- project directory: contains sbt-specific configuration files.
