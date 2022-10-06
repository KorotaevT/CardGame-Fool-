package ru.vsu.cs.korotaev;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AdjMatrixGraphTest {

    @Test
    void vertexCount() {
        AdjMatrixGraph adj = new AdjMatrixGraph(5);
        assertEquals(5, adj.vertexCount());
    }

    @Test
    void edgeCount() {
        AdjMatrixGraph adj = new AdjMatrixGraph(5);
        assertEquals(0, adj.edgeCount());
    }

    @Test
    void addAdge() {
        AdjMatrixGraph adj = new AdjMatrixGraph(5);
        adj.addAdge(1, 2);
        assertEquals(true, adj.getTable()[1][2]);
    }

    @Test
    void addVertex() {
        AdjMatrixGraph adj = new AdjMatrixGraph(5);
        adj.addVertex();
        assertEquals(6, adj.vertexCount());
    }

    @Test
    void removeAdge() {
        AdjMatrixGraph adj = new AdjMatrixGraph(5);
        adj.addAdge(1, 2);
        adj.removeAdge(1, 2);
        assertEquals(false, adj.getTable()[1][2]);
    }

    @Test
    void isAdj() {
        AdjMatrixGraph adj = new AdjMatrixGraph(5);
        adj.addAdge(1,2);
        assertEquals(adj.getTable()[1][2], adj.isAdj(1, 2));
    }

    @Test
    void dfs() {
        AdjMatrixGraph adj = new AdjMatrixGraph(8);
        adj.addAdge(0, 1);
        adj.addAdge(1, 2);
        adj.addAdge(0, 4);
        adj.addAdge(2, 3);
        adj.addAdge(2, 5);
        adj.addAdge(4, 6);
        adj.addAdge(4, 7);
        assertEquals(new ArrayList<>(Arrays.asList(0, 4, 7, 6, 1, 2, 5, 3)), Graph.dfs(adj, 0));

    }

    @Test
    void bfs() {
        AdjMatrixGraph adj = new AdjMatrixGraph(7);
        adj.addAdge(0, 1);
        adj.addAdge(1, 2);
        adj.addAdge(0, 4);
        adj.addAdge(4, 3);
        adj.addAdge(4, 5);
        adj.addAdge(5, 6);
        assertEquals(new ArrayList<>(Arrays.asList(0, 1, 4, 2, 3, 5, 6)), Graph.bfs(adj, 0));
    }
}