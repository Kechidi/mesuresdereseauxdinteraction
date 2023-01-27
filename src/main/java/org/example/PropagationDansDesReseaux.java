package org.example;

import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.RandomGenerator;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.file.FileSourceEdge;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import static org.graphstream.algorithm.Toolkit.averageDegree;

public class PropagationDansDesReseaux{

    public static <SimulationPropagation> void main(String[] args) {
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
        System.out.println(" le seuil épidémique du réseau est : ");
        double degreMoyen = averageDegree(g);
        //
        System.out.println(" le seuil épidémique du réseau est :"+ degreMoyen);
        System.out.println("Le seuil théorique d'un réseau aléatoire du même degré moyen : " + 1 / (degreMoyen + 1));







        SimulationScenario1(g);




    }



    //Méthode pour calculer les individus contaminés sachant que la probabilité de contaminer un collaborateur est 1/7
    public static int patientPositif(Node n, int nbPos) {
        Random r = new Random();
        if (r.nextInt(7) + 1 == 1 && !n.hasAttribute("immunise")) {
            if (!n.hasAttribute("infected")) {
                n.setAttribute("infected", true);
                nbPos++;
            }
        }
        return nbPos;
    }

    //Méthode pour calculer les individus guéris sachant que la probabilité de mettre à jour l'anti virus est 1/14
    public static int patientNegatif(Node n, int nbPos) {
        Random r = new Random();
        if (r.nextInt(14) + 1 == 1) {
            if (n.hasAttribute("infected")) {
                n.removeAttribute("infected");
                nbPos--;
            }
        }
        return nbPos;
    }

    /**
     * Simulation du premier scénario : On ne fait rien pour empêcher l'épidémie
     *
     * @return
     */
    public static void SimulationScenario1(Graph g) {
        // Selon les hypothèses l'épidémie comence avec un individu infecté qui est le patient 0
        Node n = g.getNode(0);
        n.setAttribute("infected", true);
        int nbPos = 1;
        //Comme il y'a 7 jours par semaine et que nos calculs se font sur 12 semaines => 7 x 12 = 84 d'où 84 jours
        for (int i = 1; i <= 84; i++) {
            for (Node s : g) {
                if (s.hasAttribute("infected")) {
                    for (Edge e : s) {
                        nbPos = patientPositif(e.getOpposite(s), nbPos);
                    }
                }
                nbPos = patientNegatif(s, nbPos);
            }
            System.out.println(i + " " + nbPos);
        }

    }



    public static void writeData(String filename, String liste){
        try {
            FileWriter file = new FileWriter(""+filename+".dat");
            file.write(liste);

            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}




