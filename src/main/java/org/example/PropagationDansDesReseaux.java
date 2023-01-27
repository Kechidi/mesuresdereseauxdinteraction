package org.example;

import org.graphstream.algorithm.Toolkit;
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

        System.out.println("||||||||||||||||||| 1-Taux de propagation du virus |||||||||||||||||| Seuil épidémique du réseau ||||||||||||||||||||  Comparaison avec le seuil théorique d'un réseau aléatoire du même degré moyen |||||||||||");


        double k = averageDegree(g);
        double G_DBLP = k / DegreeDistribution(g);
        double G_Alea = 1 / (k+1);
        System.out.println("Le seuil épidémique du réseau (DBLP) λc est =>  " + k +" / "+ DegreeDistribution(g) + " = " +  G_DBLP);
        System.out.println("Le seuil épidémique du réseau Aléatoire λc est => " + 1 +" / "+  (k+1) + " = " +  G_Alea);







        //SimulationScenario1(g);
        //SimulationScenario2(g);
        SimulationScenario3(g);
        //SimulationScenario1(ReseauAleatoire(317080,6));




    }


    /**
     * Distribution des degrés
     *
     * @return double
     */
    public static double DegreeDistribution(Graph g) {
        double k = 0.0;
        int i;
        //Création d'un tableau  contenant la distribution des degrés stockées, les résultats sont par la suite utilisés pour le calcul du seuil épidémique
        //=> Les valeurs contenues dans les cellules du tableau ce sont les degrés des noeuds du réseau
        int[] DegreeDist = Toolkit.degreeDistribution(g);
        //On parcours le tableau et si celui-ci n'est pas vide
        for (i = 0; i < DegreeDist.length; i++) {
            if (DegreeDist[i] > 0) {
                //On divise la racine carré des degrés par le nombre de noeud du réseau
                k += Math.pow(i, 2) * ((double) DegreeDist[i] / g.getNodeCount());
            }
        }
        return k;
    }


    public static Graph ReseauAleatoire(int Noeuds, int degreeMoyen) {
        System.setProperty("org.graphstream.ui", "user.dir");
        // instanciation d'un graphe nommé g
        Graph g = new SingleGraph("RandomGraph");
        // instanciation du générateur nommé gen (cette instanciation a pour but la création des graphiques aléatoires quelque soit la taille)
        Generator gen = new RandomGenerator(degreeMoyen, false, false);
        gen.addSink(g);
        gen.begin();
        //l'ajout d'un nouveau noeud
        for (int i = 0; i < Noeuds; i++)
            gen.nextEvents();
        gen.end();

        return g;

    }

    public static Graph BarabasiAlbertNetwork(int Noeuds, int degreeMoyen) {
        Graph graphBAN = new SingleGraph("BAN");
        Generator gen = new RandomGenerator(degreeMoyen, false, false);
        gen.addSink(graphBAN);
        gen.begin();
        //l'ajout d'un nouveau noeud
        for (int i = 0; i < Noeuds; i++)
            gen.nextEvents();
        gen.end();

        return graphBAN;
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


    /**
     * Simulation du 2ème scénario :On réussit à convaincre 50 % des individus de mettre à jour en permanence leur anti-virus (immunisation aléatoire).
     */
    public static void SimulationScenario2(Graph g) {
        int nbImmunise = 0;
        for (Node s : g) {
            //on immunise aléatoirement la moitié de la population
            if ((int) (Math.random() * 2 + 1) == 1) {
                s.setAttribute("immunise", true);
                nbImmunise += 1;
            }
        }
        Node n = g.getNode(0);
        n.setAttribute("infected", true);
        int nbInfecte = 1;
        for (int i = 0; i < 84; i++) {
            for (Node s : g) {
                if (s.hasAttribute("infected")) {
                    for (Edge e : s) {
                        nbInfecte = patientPositif(e.getOpposite(s), nbInfecte);
                    }
                }
                nbInfecte = patientNegatif(s, nbInfecte);
            }
            System.out.println(i + " " + nbInfecte);
        }

        double degreMoy = 0.0;
        double carredegreMoy;
        int somme = 0;
        for (Node s : g) {
            //Calcul du degré moyen
            if (!s.hasAttribute("immunise")) {
                degreMoy += s.getDegree();
                somme += Math.pow(s.getDegree(), 2);
            }
        }
        // Nombre de noeuds restants
        int nbNoeuds = g.getNodeCount() - nbImmunise;

        degreMoy /= nbNoeuds;
        carredegreMoy = (double) (somme / nbNoeuds);


        System.out.println("\n||Résultats des mesures du réseau après la simulation du scénario 2  ||:\n");
        System.out.println("Nombre de noeuds pouvant être infectés  => " + nbNoeuds);
        System.out.println("Degré moyen après modification  => " + degreMoy);
        System.out.println("Degré moyen au carré après modification ( <k²> ) => " + carredegreMoy);
        System.out.println("Nouveau seuil épidémique du réseau après modification ( <k> / <k²> ) => " + (degreMoy / carredegreMoy));

    }






    /**
     * Simulation du 3eme scénario : On réussit à convaincre 50 % des individus de convaincre
     * un de leurs contacts de mettre à jour en permanence son anti-virus (immunisation sélective).
     */
    public static void SimulationScenario3(Graph g){
        //Compteur pour les individus immunisés
        int nbImmunise = 0;
        for(Node noeud : g) {
            //Si un voisin est immunisé on incrémente le compteur
            if ((int)(Math.random() * 2 + 1) == 1 ) {
                //Le voisin est choisi aléatoirement
                int voisinAleat = (int)(Math.random() );
                noeud.getEdge(voisinAleat).getOpposite(noeud).setAttribute("immunise", true);
                nbImmunise += 1;
            }
        }
        Node s = g.getNode(0);
        s.setAttribute("infected", true);
        int nbPos= 1;
        for(int i = 0; i<84; i++){
            for (Node n : g){
                if(n.hasAttribute("infected")){
                    for(Edge e : n){
                        nbPos = patientPositif(e.getOpposite(n), nbPos);
                    }
                }
                nbPos = patientNegatif(n, nbPos);
            }
            //System.out.println("Jour "+ i + " nombre d'individus infectés : " + nbInfect + " nombre dindividus protégés : " + nbImmunise  );
            System.out.println(i +" "+ nbPos);
        }
        //Question 3
        double degreMoy = 0.0;
        double carreDegreMoy;
        int somme=0;
        for(Node n : g) {
            if(!n.hasAttribute("immunise")) {
                degreMoy += n.getDegree();
                somme += Math.pow(n.getDegree(), 2);
            }
        }
        // Nombre de noeuds restants
        int nbNoeuds = g.getNodeCount() - nbImmunise;
        degreMoy /= nbNoeuds;
        carreDegreMoy = (somme / nbNoeuds);

        System.out.println("\n *********Résultats des mesures du réseau après la simulation du scénario 3  \n");
        System.out.println("Nombre de noeuds pouvant  être infectés  => " + nbNoeuds);
        System.out.println("Degré moyen après modification ( <k> ) => " + degreMoy);
        System.out.println("Degré moyen au carré après modification ( <k²> ) => " + carreDegreMoy);
        System.out.println("Nouveau seuil épidémique du réseau après modification ( <k> / <k²> ) => " + (degreMoy / carreDegreMoy));
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




