package org.example;


import org.graphstream.graph.BreadthFirstIterator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.stream.file.FileSource;
import org.graphstream.stream.file.FileSourceEdge;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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
            fs.readAll("./ressources/donnees.txt");
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


        System.out.println(" La connexité d'un graphe aléatoire avec la même taille & le degré moyen   " + (averageDegree(g) > Math.log(g.getNodeCount())));

        System.out.println(" Un réseau aléatoire avec cette même taille  devient connexe à partir du degré moyen " + Math.log(g.getNodeCount()));

        System.out.println("*****************************Calcul de la distribution des degrés**************************");
        int[] destrib_Degres = degreeDistribution(g);
        String filename = "donnee_distribution_des_degres.dat";
        try {
            String filepath = System.getProperty("user.dir") + File.separator + filename;
            FileWriter fw = new FileWriter(filepath);
            BufferedWriter bw = new BufferedWriter(fw);
            for (int i = 0; i < destrib_Degres.length; i++) {

                if (destrib_Degres[i] != 0) {
                    System.out.printf(Locale.US, "%6d%20.8f%n", i, (double) destrib_Degres[i] / g.getNodeCount());
                    bw.write(i + " " + (double) destrib_Degres[i] / g.getNodeCount() + "\n");
                }


            }
            bw.close();
            System.out.println("Finish");
        } catch (IOException e) {
            e.printStackTrace();
        }



        distanceMoyenne(g);
    }



    // 5- Calcul de la distance moyenne d'un réseau , en utilisant le parcours en largeur pour 1000 sommets choisis alearoirement :

    public static void distanceMoyenne(Graph graph)
    {
        List<Node> l = randomNodeSet(graph, 1000);
        HashMap<Integer, Integer> distancesMap = new HashMap<Integer, Integer>();
        double distance = 0;
        int a = 0;
        for (int i = 0; i < 1000; i++) {
            BreadthFirstIterator bfi = new BreadthFirstIterator(l.get(i));
            while (bfi.hasNext()) {
                Node Noeud = bfi.next();
                int key = bfi.getDepthOf(Noeud);
                if (distancesMap.containsKey(key)) {
                    int t = distancesMap.get(key);
                    int value = 1 + t;
                    distancesMap.put(key, value);
                } else {
                    distancesMap.put(key, 1);
                }
                distance += bfi.getDepthOf(bfi.next());
                a++;
            }
        }
        double distMoy = distance / (a);

        //Le calcul prend un peu de temps
        System.out.println("La distance moyenne calculée avec 1000 noeuds au hasard :" + distMoy);
        System.out.println("La distance moyenne dans un réseau aléatoire avec les mêmes caractéristiques est :" + Math.log(graph.getNodeCount()) / Math.log(averageDegree(graph)));

        //Enregistrer les données dans fichier.dat
        String filename1 = "DataDistance.dat";
        try {
            String filepath = System.getProperty("user.dir") + File.separator + filename1;
            FileWriter fw = new FileWriter(filepath);
            BufferedWriter bw = new BufferedWriter(fw);
            for (Integer name : distancesMap.keySet()) {
                bw.write(String.format(Locale.US, "%6d%20.8f%n", name, (double) distancesMap.get(name) / a));
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }










}









