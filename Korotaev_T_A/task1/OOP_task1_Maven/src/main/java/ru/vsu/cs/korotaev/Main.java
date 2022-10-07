package ru.vsu.cs.korotaev;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args){
        AdjMatrixGraph adj = new AdjMatrixGraph(5);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i<adj.getTable().length; i++){
            for (int e = 0; e<adj.getTable()[i].length; e++){
                sb.append(adj.getTable()[i][e]).append("; ");
            }
            sb.append("\n");
        }
        System.out.println(sb + "\n");
        sb = new StringBuilder();
        adj.addVertex();
        adj.addAdge(1, 5);
        adj.addAdge(4, 2);
        adj.addAdge(5, 3);
        adj.addAdge(0, 1);
        adj.addAdge(1, 0);
        adj.addAdge(1, 2);
        adj.addAdge(2, 3);
        adj.addAdge(0, 4);
        adj.removeAdge(1, 2);

        for (int i = 0; i<adj.getTable().length; i++){
            for (int e = 0; e<adj.getTable()[i].length; e++){
                sb.append(adj.getTable()[i][e]).append("; ");
            }
            sb.append("\n");
        }
        System.out.println(sb + "\n");
        sb = new StringBuilder();

        List<Integer> l = new ArrayList<>();
        l = Graph.dfs(adj, 0);
        for(int i = 0; i<l.size(); i++){
            sb.append(l.get(i) + "; ");
        }
        sb.append("- обход в глубину");
        System.out.println(sb);
        sb = new StringBuilder();

        l = Graph.bfs(adj, 0);
        for(int i = 0; i<l.size(); i++){
            sb.append(l.get(i) + "; ");
        }
        sb.append("- обход в ширину");
        System.out.println(sb);
    }
}
