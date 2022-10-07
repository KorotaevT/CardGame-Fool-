package ru.vsu.cs.korotaev;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.function.Consumer;

public interface Graph {

    int vertexCount();

    int edgeCount();

    void addEdge(int v1, int v2);

    void addVertex();

    void removeEdge(int v1, int v2);

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

    boolean[][] getTable();

    static List<Integer> bfs(Graph graph, int from) {
        boolean[] visited = new boolean[graph.vertexCount()];
        List<Integer> answer = new LinkedList<Integer>();
        Queue<Integer> queueWork = new LinkedList<Integer>();
        queueWork.add(from);
        visited[from] = true;
        while (queueWork.size() > 0) {
            Integer curr = queueWork.remove();
            answer.add(curr);
            for (Integer v : graph.adjacencies(curr)) {
                if (!visited[v]) {
                    queueWork.add(v);
                    visited[v] = true;
                }
            }
        }
        return answer;
    }

    static List<Integer> dfs(Graph graph, int from) {
        boolean[] visited = new boolean[graph.vertexCount()];
        List<Integer> answer = new LinkedList<Integer>();
        Stack<Integer> stack = new Stack<Integer>();
        stack.push(from);
        visited[from] = true;
        answer.add(from);
        while (!stack.empty()) {
            Integer curr = stack.pop();
            if(!answer.contains(curr)) {
                answer.add(curr);
            }
            for (Integer v : graph.adjacencies(curr)) {
                if (!visited[v]) {
                    stack.push(v);
                    visited[v] = true;
                }
            }
        }
        return answer;
    }
}
