k-core Decomposition in Social Networks
Implementation of the Batagelj–Zaveršnik algorithm for k-core decomposition and shell index computation in undirected networks, developed as part of the Social Networks course project at the Faculty of Technical Sciences, University of Novi Sad.
Overview
This project implements k-core decomposition algorithms from scratch in Java, tests them on synthetic and real-world networks, and performs empirical analysis of k-core structures and their correlation with centrality measures.
Key Features:

Custom implementation of Batagelj–Zaveršnik algorithm (O(n+m) complexity)
Baseline straightforward algorithm for validation
Synthetic network generators (Erdős–Rényi, Barabási–Albert, Core-Periphery models)
Analysis of real-world networks (Protein Pathways, Email communication, Facebook networks)
Centrality measure computations and approximations for large graphs
Correlation analysis (Spearman, Pearson) between shell index and centrality measures
Visualization tools for network structure and correlations

Project Structure:
src/
├── algorithms/
│   ├── KCoreDecomposition.java          # Abstract base class
│   ├── BatageljZaversnik.java           # Main algorithm implementation
│   └── StraightforwardKCore.java        # Baseline algorithm for validation
├── models/
│   ├── Node.java                         # Node representation
│   ├── Edge.java                         # Edge representation
│   └── Network.java                      # Abstract network analysis class
├── generators/
│   ├── ErdosRenyiModel.java             # Random graph generator
│   ├── BarabasiAlbertModel.java         # Scale-free network generator
│   └── CorePeripheryModel.java          # Core-periphery structure generator
├── networks/
│   ├── ProteinPathways.java             # Protein-protein interaction network
│   ├── EmailEuCore.java                 # Email communication network
│   ├── FacebookAthletes.java            # Facebook athletes pages network
│   └── ...                               # Other real-world networks
├── utils/
│   ├── GraphUtils.java                  # Graph manipulation utilities
│   ├── ApproximationUtils.java          # Centrality approximation methods
│   ├── GraphFileWriter.java             # CSV export functionality
│   └── BatchNetworkAnalysis.java        # Parallel processing for multiple networks
└── tests/
    └── KCoreTests.java                  # Test suite for small networks

Key Components
1. Batagelj–Zaveršnik Algorithm
Efficient k-core decomposition with O(n+m) time complexity:

Bucket-based approach: nodes organized by degree
Iterative removal of nodes with insufficient connections
Shell index assignment for each node

2. Network Generators
Erdős–Rényi (ER): Random graph with edge probability p
Barabási–Albert (BA): Scale-free network with preferential attachment
Core-Periphery (CP): Networks with dense core and sparse periphery (discrete and continuous models)
3. Centrality Measures

Degree centrality: Number of connections
Closeness centrality: Average distance to all other nodes (with harmonic approximation for large graphs)
Betweenness centrality: Number of shortest paths passing through a node (with sampling approximation)
Eigenvector centrality: Importance based on connections to important nodes (power iteration method)

4. Performance Optimizations
For large networks (>100k nodes):

Approximation algorithms for closeness and betweenness (sampling-based)
Iterative methods for eigenvector centrality
Parallel processing support via BatchNetworkAnalysis
Selective metric computation (skip expensive calculations for very large graphs)

Results Summary
Algorithm Performance:

Batagelj–Zaveršnik is 10-20x faster than straightforward algorithm on 10k-20k node networks
Both algorithms produce identical results (validation confirmed)

Synthetic Networks:

ER networks: Uniform shell index distribution, max k-core grows with density
BA networks: Strong core-periphery structure, hub nodes in maximum k-core
CP networks: Dense core with high k-core values, periphery nodes drop off quickly

Real-World Networks:

Protein Pathways (21k nodes): Max k-core = 105, nearly complete subgraph in maximum core
EmailEuCore (1k nodes): Max k-core = 34, single connected component throughout
FacebookAthletes (14k nodes): Max k-core = 31, ~95% density in maximum core (almost a clique)

Correlation Analysis:
Shell Index vs Degree: 0.95-0.99 (very strong correlation across all networks)
Shell Index vs Closeness: 0.75-0.94 (strong correlation)
Shell Index vs Betweenness: 0.75-0.91 (strong correlation)
Shell Index vs Eigenvector: 0.75-0.97 (strong correlation)
Key insight: High shell index strongly correlates with all centrality measures, but exceptions exist (e.g., high-degree nodes connected only to peripheral nodes have low shell index).
Technologies Used

Language: Java
Graph Library: JUNG (Java Universal Network/Graph Framework)
Visualization: JFreeChart, Swing
Data Processing: Apache Commons Math (for correlations)
