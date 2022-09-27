package ru.vsu.cs.korotaev;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class AdjMatrixDigraph extends AdjMatrixGraph implements Digraph {

    public AdjMatrixDigraph(int vertexCount) {
        super(vertexCount);
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
