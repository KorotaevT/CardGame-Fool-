package ru.vsu.cs.korotaev;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdjMatrixGraphTest {

    @Test
    void vertexCount() {
        AdjMatrixGraph adj = new AdjMatrixGraph(5);
        adj.vertexCount();
    }

    @Test
    void edgeCount() {
        AdjMatrixGraph adj = new AdjMatrixGraph(5);
        adj.edgeCount();
    }

    @Test
    void addAdge() {
        AdjMatrixGraph adj = new AdjMatrixGraph(5);
        adj.addAdge(1, 2);
    }

    @Test
    void addVertex() {
        AdjMatrixGraph adj = new AdjMatrixGraph(5);
        adj.addVertex();
    }

    @Test
    void removeAdge() {
        AdjMatrixGraph adj = new AdjMatrixGraph(5);
        adj.addAdge(1, 2);
        adj.removeAdge(1, 2);
    }

    @Test
    void randomGraph() {
        AdjMatrixGraph adj = new AdjMatrixGraph(5);
        adj.randomGraph(5, 60);
    }

    @Test
    void adjacencies() {
        AdjMatrixGraph adj = new AdjMatrixGraph(5);
        adj.addAdge(3, 4);
        adj.addAdge(4, 2);
        adj.adjacencies(4);
    }

    @Test
    void isAdj() {
        AdjMatrixGraph adj = new AdjMatrixGraph(5);
        adj.addAdge(1,2);
        adj.isAdj(1, 2);
    }

    @Test
    void dfs() {
        AdjMatrixGraph adj = new AdjMatrixGraph(5);
        adj.randomGraph(5, 60);
        adj.dfs(adj, 0);
    }

    @Test
    void bfs() {
        AdjMatrixGraph adj = new AdjMatrixGraph(5);
        adj.randomGraph(5, 60);
        adj.bfs(adj, 0);
    }
}