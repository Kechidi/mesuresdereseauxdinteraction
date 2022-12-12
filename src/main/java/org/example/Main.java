package org.example;

import org.graphstream.algorithm.Toolkit;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.stream.file.FileSourceEdge;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

import static org.graphstream.algorithm.Toolkit.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        System.setProperty("org.graphstream.ui", "swing");
        Graph g = new DefaultGraph("Graphe");
        FileSourceEdge fs = new FileSourceEdge();

        fs.addSink(g);
        //g.display();

        try {
            fs.readAll("/home/tkechidi@mmtt.fr/MesuresRI/donnees.txt");
        } catch (IOException e) {
            System.out.println(e);

        }
        System.out.println("******************************Quelques mesures de base:**************************");


        System.out.println("Le nombre de noeud est:" + g.getNodeCount());
        System.out.println("le nombre de liens est :" + g.getEdgeCount());
        System.out.println("Le degré moyen est  :" + averageDegree(g));
        System.out.println("Le coefficient de clustering :" + averageClusteringCoefficient(g));


        System.out.println("coefficient de clustering pour un réseau aléatoire de la même taille et du même degré moyen  :" + averageDegree(g) / g.getNodeCount());


        //le reseau est_il connexe ?
        System.out.println("******************************le graphe est il connexe?**************************");

        if (isConnected(g))
            System.out.println("Oui! le graphe est connexe");
        else
            System.out.println("Non! le graphe n'est pas connexe");


        System.out.println(" La connexité d'un graphe aléatoire avec la même taille & le degré moyen   " + (averageDegree(g)> Math.log(g.getNodeCount())));

        System.out.println(" Un réseau aléatoire avec cette même taille  devient connexe à partir du degré moyen " + Math.log(g.getNodeCount()));

        System.out.println("*****************************Calcul de la distribution des degrés**************************");
        int[] destrib_Degres =degreeDistribution(g);
        String filename = "donnee_distribution_des_degres.dat";
        try {
            String filepath = System.getProperty("user.dir") + File.separator + filename;
            FileWriter fw = new FileWriter(filepath);
            BufferedWriter bw = new BufferedWriter(fw);
            for (int i = 0; i < destrib_Degres.length; i++) {

                if (destrib_Degres[i] != 0) {
                    System.out.printf(Locale.US, "%6d%20.8f%n", i, (double)destrib_Degres[i] / g.getNodeCount());
                    bw.write(i+" "+ (double)destrib_Degres[i] / g.getNodeCount()+ "\n");
                }


            }
            bw.close();
            System.out.println("Finish");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}







