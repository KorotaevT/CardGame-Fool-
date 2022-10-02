package ru.vsu.cs.korotaev;

import java.util.List;

public interface Graph {

    int vertexCount();

    int edgeCount();

    void addAdge(int v1, int v2);

    void addVertex();

    void removeAdge(int v1, int v2);

    void randomGraph(int vCount, int connectionProb);

    Iterable<Integer> adjacencies(int v);

    default boolean isAdj(int v1, int v2) {
        for (Integer adj : adjacencies(v1)) {
            if (adj == v2) {
                return true;
            }
        }
        return false;
    }

    List<Integer> bfs(Graph graph, int from);

    List<Integer> dfs(Graph graph, int from);
}
