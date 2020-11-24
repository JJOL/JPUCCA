
# JPUCCA and MASON
v0.1.0
---
A sample demonstration of a PUCCA Cellular Automaton integrated into a MASON simulation app through Java JNI.
The celullar automaton used in this sample is a parallel implementation of Conway's Game of Life.

## Usage
The project contains MASON project with an additional application for running a Game of Life simulation using as state update backend a PUCCA Parallel Automaton. This parallel automaton was coded using PUCCADevLib and it was included into a Java JNI library for using in a MASON model.

For further information and a walkthrough of the development and visualization of the same Game of Life automaton, read the [report](PUCCA_A_proposed_framework_for_the_development_of_parallelized_cellula_automata.pdf).

## System Requirements
To build and run the source code contained in this repository it is required as a minimum:
1. JDK 8
2. A JPUCCA_GoL_CA Compiled Shared Library
3. A NVIDIA Graphics Card with compute capanility above 20

## Brother Project
[PUCCADevLib](https://github.com/JJOL/PUCCADevLib).

## MASON Original Project
[MASON](https://github.com/eclab/mason).

## License
For MASON: [Academic Free License ("AFL") v. 3.0](LICENSE)
For JPUCCA: [GNU General Public License 3.0](JPUCCA_LICENSE)