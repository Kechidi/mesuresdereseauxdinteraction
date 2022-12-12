package org.example;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.stream.file.FileSourceEdge;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        System.setProperty("org.graphstream.ui", "swing");
        Graph g = new DefaultGraph("Graphe");
        FileSourceEdge fs = new FileSourceEdge();

        fs.addSink(g);
        g.display();

        try {
            fs.readAll("/home/tkechidi@mmtt.fr/MesuresRI/donnees.txt");
        } catch (IOException e) {
            System.out.println(e);

        }
    }
}