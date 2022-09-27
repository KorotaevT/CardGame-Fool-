package ru.vsu.cs.korotaev;

import java.util.*;

public class AdjMatrixGraph implements Graph {

    private boolean[][] adjMatrix;
    private int vCount = 0;
    private int eCount = 0;

    public AdjMatrixGraph(int vertexCount) {
        adjMatrix = new boolean[vertexCount][vertexCount];
        vCount = vertexCount;
    }

    @Override
    public int vertexCount() {
        return vCount;
    }

    @Override
    public int edgeCount() {
        return eCount;
    }

    @Override
    public void addAdge(int v1, int v2) {
        int maxV = Math.max(v1, v2);
        if (maxV >= vertexCount()) {
            adjMatrix = Arrays.copyOf(adjMatrix, maxV + 1);
            for (int i = 0; i <= maxV; i++) {
                adjMatrix[i] = i < vCount ? Arrays.copyOf(adjMatrix[i], maxV + 1) : new boolean[maxV + 1];
            }
            vCount = maxV + 1;
        }
        if (!adjMatrix[v1][v2]) {
            adjMatrix[v1][v2] = true;
            eCount++;
            if (!(this instanceof Digraph)) {
                adjMatrix[v2][v1] = true;
            }
        }
    }

    @Override
    public void removeAdge(int v1, int v2) {
        if (adjMatrix[v1][v2]) {
            adjMatrix[v1][v2] = false;
            eCount--;
            if (!(this instanceof Digraph)) {
                adjMatrix[v2][v1] = false;
            }
        }
    }

    @Override
    public Iterable<Integer> adjacencies(int v) {
        return new Iterable<Integer>() {
            Integer nextAdj = null;

            @Override
            public Iterator<Integer> iterator() {
                for (int i = 0; i < vCount; i++) {
                    if (adjMatrix[v][i]) {
                        nextAdj = i;
                        break;
                    }
                }

                return new Iterator<Integer>() {
                    @Override
                    public boolean hasNext() {
                        return nextAdj != null;
                    }

                    @Override
                    public Integer next() {
                        Integer result = nextAdj;
                        nextAdj = null;
                        for (int i = result + 1; i < vCount; i++) {
                            if (adjMatrix[v][i]) {
                                nextAdj = i;
                                break;
                            }
                        }
                        return result;
                    }
                };
            }
        };
    }

    @Override
    public boolean isAdj(int v1, int v2) {

        return adjMatrix[v1][v2];
    }

    public List<Integer> dfs(Graph graph, int from) {
        boolean[] visited = new boolean[graph.vertexCount()];
        List<Integer> answer = new LinkedList<Integer>();
        Stack<Integer> stack = new Stack<Integer>();
        stack.push(from);
        answer.add(from);
        visited[from] = true;
        while (!stack.empty()) {
            Integer curr = stack.pop();
            answer.add(curr);
            for (Integer v : graph.adjacencies(curr)) {
                if (!visited[v]) {
                    stack.push(v);
                    visited[v] = true;
                }
            }
        }
        return answer;
    }


    public List<Integer> bfs(Graph graph, int from) {
        boolean[] visited = new boolean[graph.vertexCount()];
        List<Integer> answer = new LinkedList<Integer>();
        Queue<Integer> queueWork = new LinkedList<Integer>();
        queueWork.add(from);
        answer.add(from);
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
}
