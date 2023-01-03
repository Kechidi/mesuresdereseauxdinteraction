# MesuresDeRéseauxDinteraction

# Introduction

Nous allons analyser durant ce tp un réseau de collaboration scientifique en informatique.
Le réseau en question est extrait de DBLP, un réseau de co-auteurs où deux auteurs sont connectés (ce qui correspond aux liens ou arêtes) s'ils publient au moins un article ensemble, et donc chaque noeud représente un auteur qui publie.
Graphstream nous permettera de mesurer de nombreuses caractéristiques d'un réseau(comme le nombre de noeuds, de liens...). Nous retrouveons la plus part de ces mesures implantées comme des méthodes statiques dans la classe Toolkit, que nous utiliseront par la suite.

# Mesures de base dans le réseau

1-1 Afin de débuter correctement ce TP, il était necessaire de télécharger le fichier : _com-dblp.ungraph.txt_  qui contient toutes les données de DBLP.  GraphStream a su lire ce format après l'instanciation de *FileSourceEdge* et via la fonction *readAll()* , l'integralité du fichier est lu en une seule instruction.
2-1 Pour calculer les mesures de base , comme cité precedemment j'ai utilisé les méthodes se trouvant dans la classe Toolkit .
On retrouve le nombre de noeuds , de liens, le degré moyen  ainsi que le coefficient de clustering via les fonctions *getNodeCount()*, *getEdgeCount()*, *averageDegree()* et *Toolkit.averageClusteringCoefficient* qui renvoient respectivement ;




# Mesures de base dans le réseau



Nombres de noeuds: **317080**

Le degré moyen est: **6.62208890914917**

Le coefficient de clustering: **0.6324308280637396*

coefficient de clustering pour un réseau aléatoire de la même taille et du même degré moyen :2.0884599814397534E-5 

# Connexité d'un graphe 

3-1 Oui! le réseau DBLP est connexe, la méthode *isConnected()* qui prend en paramètre notre graphe nous permet de vérifier si tous les noeuds sont connectés entre eux.
Dans le cas contraire,  un false est renvoyé. </br>
![PLOT](/home/kechidi/mesuresdereseauxdinteraction/connexe.png)


3-2 Un réseau enre dans le régime connecté si la notion du degré moyen est supérieur au nombre de noeuds du graphe est vérifiée </br>
Soit`〈k〉> lnN(p>lnN/N)` </br>
Or dans un réseau aléatoire malgré qu'il soit de la même taille et du même degré moyen ce dernier n'est pas connexe, il n'est pas vérifié dans son intégralité. </br>

3-3 Un réseau aléatoire avec cette même taille devient connexe si le degré moyen est supérieur à => **12.666909386951092** </br>


