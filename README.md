# k-algorithms Decomposition in Social Networks

Implementation of the **Batagelj–Zaveršnik algorithm** for k-algorithms decomposition and shell index computation in 
undirected networks, as part of the *Social Networks* course project. This project focuses on algorithm implementation, 
testing on synthetic and real networks, and empirical analysis of k-algorithms structures. The project is done in Java
using the JUNG library.

---

##  Project Goals

- Implement Batagelj–Zaveršnik algorithm for k-algorithms decomposition (without using existing library implementations) _**DONE**_
- Implement a baseline straightforward algorithm for validation _**DONE**_
- Generate synthetic networks: 
    - **Erdős–Rényi** (random graph) _**DONE**_
    - **Barabási–Albert** (scale-free network) _**DONE**_
    - **Core-Periphery model** (manually implemented) _**DONE**_
- Test algorithms on small synthetic networks (15–20 nodes) _**DONE**_
- Perform large-scale tests on synthetic networks (>100 nodes) _**DONE**_
- Analyze real-world social networks (>1k nodes) from publicly available datasets _**DONE**_
- Empirical analysis of k-algorithms structures and correlations with centrality measures: 
    - Degree, closeness, betweenness, eigenvector centrality _**DONE**_
    - Spearman correlation between shell index and centrality measures _**DONE**_
- Empirical analysis of k-cores in networks _**DONE**_
- Graphical visualizations of correlations and k-algorithms structures _**DONE**_