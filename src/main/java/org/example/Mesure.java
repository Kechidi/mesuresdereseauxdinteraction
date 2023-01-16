package org.example;


import org.graphstream.algorithm.Toolkit;
import org.graphstream.algorithm.generator.BarabasiAlbertGenerator;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.RandomGenerator;
import org.graphstream.graph.BreadthFirstIterator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.file.FileSourceEdge;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static org.graphstream.algorithm.Toolkit.*;

public class Mesure {
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
        String filename = "./ressources/donnee_distribution_des_degres.dat";
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



        //distanceMoyenne(g);

        System.out.println(" ************************!!!!! 6- 1 Mesures de base d'un graphe généré aléatoirement  !!!!!!!!!!****************** \n ");


        ReseauAleatoire(317080,6);


        System.out.println(" ************************!!!!! 6- 2 Mesures de base d'un graphe généré avec Barabasi-Albert  !!!!!!!!!!****************** \n ");
        BarabasiAlbertNetwork();
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

        //sauvegarder les données dans DataDistance.dat
        String filename1 = "./ressources/DataDistance.dat";
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

    /**
     * 6 - 1 Création de graphe aléatoire ayant la même taille que le réseau DBLP
     * @param Noeuds
     * @param degreeMoyen
     * @return
     */
    public static Graph ReseauAleatoire(int Noeuds, int degreeMoyen) {
        System.setProperty("org.graphstream.ui", "user.dir");
        // instanciation d'un graphe g
        Graph g = new SingleGraph("RandomGraph");
        // instanciation du générateur nommé gen (cette instanciation a pour but la création des graphiques aléatoires quelque soit la taille)
        Generator gen = new RandomGenerator(degreeMoyen, false, false);
        gen.addSink(g);
        gen.begin();
        //l'ajout d'un nouveau noeud
        for (int i = 0; i < Noeuds; i++)
            gen.nextEvents();
        gen.end();

        //Affichage des mesures de base pour un graphe générer aléatoirement en utilisant les méthodes de la classe Toolkit
        System.out.println("Le nombre de noeuds pour un graphe  généré aléatoirement => " + g.getNodeCount());
        System.out.println("Le nombre de liens pour un graphe généré aléatoirement => " + g.getEdgeCount());
        System.out.println("Le degré moyen d'un graphe généré aléatoirement => " + Toolkit.averageDegree(g));
        System.out.println("Connexité du graphe aléatoire => " + Toolkit.isConnected(g));
        System.out.println("Le coefficient de clustering d'un graphe généré aléatoirement => " + Toolkit.averageClusteringCoefficient(g));

        //Distribution des degrés du graphe aléatoire

        int n = g.getNodeCount();
        int[] dd = Toolkit.degreeDistribution(g);
        //Enregistrement des données dans un fichier.dat
        String filename = "./ressources/dataDegreeDist_graphe_alea.dat";
        try {
            String filepath = System.getProperty("user.dir") + File.separator + filename;
            FileWriter fw = new FileWriter(filepath);
            BufferedWriter bw = new BufferedWriter(fw);
            for (int i = 0; i < dd.length; i++) {
                String line = "";
                bw.write(String.format(Locale.US, "%6d%20.8f%n", i, (double)dd[i] / n));
                System.out.printf("%6d%20.8f%n",i, (double) dd[i]/n);
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return g;
    }


    /* 6 - 2 Création d'un réseau avec la méthode d'attachement préférentiel (Barabasi-Albert) ayant la même taille que le réseau DBLP  */

    public static void BarabasiAlbertNetwork() {
        Graph graphBAN = new SingleGraph("BAN");
        Generator gen = new BarabasiAlbertGenerator(6); // création d'un réseau dont le degré est 6

        gen.addSink(graphBAN);
        gen.begin();
        while (graphBAN.getNodeCount() < 317080 && gen.nextEvents()) ;
        gen.end();
        // Distribution des degrés pour BAN
        int n= graphBAN.getNodeCount();
        int [] ddBAN = Toolkit.degreeDistribution(graphBAN);
        String filename = "./resssources/dataDegreeDist_gaphe_BAN.dat";
        try {
            String filepath = System.getProperty("user.dir") + File.separator + filename;
            FileWriter fw = new FileWriter(filepath);
            BufferedWriter bw = new BufferedWriter(fw);
            for (int i = 0; i < ddBAN.length; i++) {
                String line = "";
                bw.write(String.format(Locale.US, "%6d%20.8f%n", i, (double)ddBAN[i] / n));
                System.out.printf("%6d%20.8f%n",i, (double) ddBAN[i]/n);
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //L'affichage prend quelques secondes vu la taille
        System.out.println("le nombre de noeud de ce graphe generer avec le generateur barbasi est => " + graphBAN.getNodeCount());
        System.out.println("le nombre de liens de ce graphe avec le generateur barbasi est => " + graphBAN.getEdgeCount());
        System.out.println("Degré moyen du graphe BAN => " + Toolkit.averageDegree(graphBAN));
        System.out.println("Connexité du graphe aléatoire => " + Toolkit.isConnected(graphBAN));
        double accBan = Toolkit.averageClusteringCoefficient(graphBAN);
        System.out.println("Le coefficient de clustering => " + accBan);
        //graphBAN.display();

    }











}









