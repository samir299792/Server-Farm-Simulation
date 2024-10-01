# Server Farm Simulation

## Project Overview
This project simulates job distribution in a server farm using different dispatching strategies to optimize load balancing and minimize job processing delays. The following dispatchers were implemented and compared:
- **Random Dispatcher**: Jobs are assigned randomly to servers.
- **Round Robin Dispatcher**: Jobs are assigned in a circular sequence to servers.
- **Shortest Queue Dispatcher**: Jobs are assigned to the server with the shortest queue.
- **Least Work Dispatcher**: Jobs are assigned to the server with the least remaining workload.

Additionally, a **Dynamic Dispatcher** was developed to switch strategies in real time based on system load to further optimize performance.

## Features
- Simulates job distribution across multiple servers.
- Analyzes performance metrics like average waiting time and job completion rates.
- Supports dynamic dispatcher strategy switching to adapt to varying loads.
  
## Results Summary
- The **Least Work Dispatcher** achieved the lowest average waiting time under high loads.
- The **Dynamic Dispatcher** outperformed static strategies, reducing average waiting time by up to 56%.

## How to Run
1. Clone the repository.
2. Run the simulation by executing:
   ```bash
   javac ServerFarmSimulation.java
   java ServerFarmSimulation
