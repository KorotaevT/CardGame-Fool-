package ru.vsu.cs.korotaev;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class AdjMatrixGraphTest {

    @Test
    void vertexCount() {
        AdjMatrixDigraph diadj = new AdjMatrixDigraph(5);
        AdjMatrixGraph adj = new AdjMatrixGraph(5);
        assertEquals(5, adj.vertexCount());
        assertEquals(5, diadj.vertexCount());
    }

    @Test
    void edgeCount() {
        AdjMatrixDigraph diadj = new AdjMatrixDigraph(5);
        AdjMatrixGraph adj = new AdjMatrixGraph(5);
        assertEquals(0, adj.edgeCount());
        assertEquals(0, diadj.edgeCount());
    }

    @Test
    void addAdge() {
        AdjMatrixDigraph diadj = new AdjMatrixDigraph(5);
        AdjMatrixGraph adj = new AdjMatrixGraph(5);
        adj.addEdge(1, 2);
        diadj.addEdge(1, 2);
        assertTrue(adj.getTable()[1][2]);
        assertTrue(adj.getTable()[2][1]);
        assertTrue(diadj.getTable()[1][2]);
    }

    @Test
    void addVertex() {
        AdjMatrixDigraph diadj = new AdjMatrixDigraph(5);
        AdjMatrixGraph adj = new AdjMatrixGraph(5);
        adj.addVertex();
        diadj.addVertex();
        assertEquals(6, adj.vertexCount());
        assertEquals(6, adj.vertexCount());
    }

    @Test
    void removeAdge() {
        AdjMatrixDigraph diadj = new AdjMatrixDigraph(5);
        AdjMatrixGraph adj = new AdjMatrixGraph(5);
        adj.addEdge(1, 2);
        adj.removeEdge(1, 2);
        diadj.addEdge(1, 2);
        diadj.removeEdge(1, 2);
        assertFalse(adj.getTable()[1][2]);
        assertFalse(adj.getTable()[2][1]);
        assertFalse(diadj.getTable()[1][2]);
    }

    @Test
    void isAdj() {
        AdjMatrixDigraph diadj = new AdjMatrixDigraph(5);
        AdjMatrixGraph adj = new AdjMatrixGraph(5);
        adj.addEdge(1,2);
        diadj.addEdge(1,2);
        assertEquals(adj.getTable()[1][2], adj.isAdj(1, 2));
        assertEquals(adj.getTable()[2][1], adj.isAdj(2, 1));
        assertEquals(diadj.getTable()[1][2], diadj.isAdj(1, 2));
        assertEquals(diadj.getTable()[2][1], diadj.isAdj(2, 1));
    }

    @Test
    void dfs() {
        AdjMatrixDigraph diadj = new AdjMatrixDigraph(5);
        AdjMatrixGraph adj = new AdjMatrixGraph(8);
        adj.addEdge(0, 1);
        adj.addEdge(1, 2);
        adj.addEdge(0, 4);
        adj.addEdge(2, 3);
        adj.addEdge(2, 5);
        adj.addEdge(4, 6);
        adj.addEdge(4, 7);
        diadj.addEdge(0, 1);
        diadj.addEdge(1, 2);
        diadj.addEdge(0, 4);
        diadj.addEdge(2, 3);
        diadj.addEdge(2, 5);
        diadj.addEdge(4, 6);
        diadj.addEdge(4, 7);
        assertEquals(new ArrayList<>(Arrays.asList(0, 4, 7, 6, 1, 2, 5, 3)), Graph.dfs(adj, 0));
        assertEquals(new ArrayList<>(Arrays.asList(0, 4, 7, 6, 1, 2, 5, 3)), Graph.dfs(diadj, 0));

    }

    @Test
    void bfs() {
        AdjMatrixDigraph diadj = new AdjMatrixDigraph(5);
        AdjMatrixGraph adj = new AdjMatrixGraph(7);
        adj.addEdge(0, 1);
        adj.addEdge(1, 2);
        adj.addEdge(0, 4);
        adj.addEdge(4, 3);
        adj.addEdge(4, 5);
        adj.addEdge(5, 6);
        diadj.addEdge(0, 1);
        diadj.addEdge(1, 2);
        diadj.addEdge(0, 4);
        diadj.addEdge(4, 3);
        diadj.addEdge(4, 5);
        diadj.addEdge(5, 6);
        assertEquals(new ArrayList<>(Arrays.asList(0, 1, 4, 2, 3, 5, 6)), Graph.bfs(adj, 0));
        assertEquals(new ArrayList<>(Arrays.asList(0, 1, 4, 2, 3, 5, 6)), Graph.bfs(diadj, 0));
    }
}