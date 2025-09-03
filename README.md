# k-core Decomposition in Social Networks

Implementation of the **Batagelj–Zaveršnik algorithm** for k-core decomposition and shell index computation in 
undirected networks, as part of the *Social Networks* course project. This project focuses on algorithm implementation, 
testing on synthetic and real networks, and empirical analysis of k-core structures. The project is done in Java
using the JUNG library.

---

## 🚀 Project Goals

- Implement Batagelj–Zaveršnik algorithm for k-core decomposition (without using existing library implementations)
- Implement a baseline straightforward algorithm for validation
- Generate synthetic networks:
    - **Erdős–Rényi** (random graph)
    - **Barabási–Albert** (scale-free network)
    - **Core-Periphery model** (manually implemented)
- Test algorithms on small synthetic networks (15–20 nodes)
- Perform large-scale tests on synthetic networks (>100 nodes)
- Analyze real-world social networks (>1k nodes) from publicly available datasets
- Empirical analysis of k-core structures and correlations with centrality measures:
    - Degree, closeness, betweenness, eigenvector centrality
    - Spearman correlation between shell index and centrality measures
- Graphical visualizations of correlations and k-core structures