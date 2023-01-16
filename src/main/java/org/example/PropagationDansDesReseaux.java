package org.example;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.stream.file.FileSourceEdge;

import java.io.IOException;

public class PropagationDansDesReseaux {

    public static void main(String[] args) {
        System.setProperty("org.graphstream.ui", "swing");
        Graph g = new DefaultGraph("Graphe");
        FileSourceEdge fs = new FileSourceEdge();

        fs.addSink(g);
        //g.display();

        try {
            fs.readAll("./ressources/donnees.txt");
        } catch (IOException e) {
            System.out.println(e);

        }
    }
}
