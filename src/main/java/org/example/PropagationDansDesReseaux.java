package org.example;

import org.graphstream.algorithm.Toolkit;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.stream.file.FileSourceEdge;

import java.io.IOException;

public class PropagationDansDesReseaux{

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
        double beta = 1. / 7;
        double mu = 1./ 14;
        System.out.println("Le taux de propagation du virus est : " + beta / mu);
        System.out.println(" le seuil épidémique du réseau est :");
        double degreMoyen = Toolkit.averageDegree(g);
        //
        System.out.println(" le seuil épidémique du réseau est :"+ degreMoyen);
        System.out.println("Le seuil théorique d'un réseau aléatoire du même degré moyen : " + 1 / (degreMoyen + 1));


    }
}




