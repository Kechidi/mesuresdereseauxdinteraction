# MesuresDeRéseauxDinteraction

# Introduction

Nous allons analyser durant ce tp un réseau de collaboration scientifique en informatique.
Le réseau est extrait de DBLP, un réseau de co-auteurs où deux auteurs sont connectés (ce qui correspond aux liens ou arêtes) s'ils publient au moins un article ensemble, et donc chaque noeud représente un auteur qui publie.
Graphstream nous permettera de mesurer de nombreuses caractéristiques d'un réseau. Nous retrouveons la plus part de ces mesures implantées comme des méthodes statiques dans la classe Toolkit, que nous utiliseront par la suite.

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
Dans le cas contraire,  elle renvoie false. </br>
![PLOT](./ressources/connexe.png)


3-2 Un réseau aléatoire malgré qu'il soit de la même taille et du même degré moyen ce dernier n'est pas connexe, il n'est pas vérifié dans son intégralité. </br>

3-3 Un réseau aléatoire avec cette même taille devient connexe si le degré moyen est supérieur à => **12.666909386951092** </br>


## Génération de fichier de données


4-1 Ce fichier [Données_DistributionDegres](./ressources/donnee_distribution_des_degres.dat) contient les resultats obtenus pour la distribution des degrès, ces données sont tracées via l'outil Gnuplot, le graphe obtenu correspond donc à la probabilité qu'un sommet(noeud) choisi au hasard ait un degré k en fonction de k en échelle lineaire ainsi qu'en échelle log-log.  </br>

*En echelle linéaire* ce [script](./ressources/plot_dd.gnu) a permit de tracer le graphe suivant :
![distribution des degres](./ressources/distributionDegLin.png)

*En echelle en log log* l'utilisation de ce [script](./ressources/plot_log_log.gnu) a permit de tracer ce graphe :
![distribution des degres en log](./ressources/distDegreLog.png)


4-2 Oui, on observe une ligne droite en échelle log-log. </br>

4-3 **Analyse du graphe**: </br>

La ligne droite en échelle log-log signifie que la distribution des degrès suit une loi de Puissance.
P(k)= k-γ , où γ est une constante. </br>

4-4 **La loi de Puissance** : </br>
Afin de générer la loi de puissance que suit cette distribution , le même principe a été utilisé , via l'outil Gnuplot nous avons obtenu  grace au [script](./ressources/tracerPuissance.gnu) le graphe

![graphe de la loi de puissance](./ressources/loiPuissance.png)

**La distribution de Poisson** : </br>
Cette dernière a également été tracer via l'outil Gnuplot , ainsi que le [script](./ressources/tracerPoisson.gnu) , le graphe obtenu correspond a l'image

![graphe de la loi de Poisson](./ressources/poisson.png)  </br>

Selon les commande **fit** et **gnuplot** on voit  que `gamma=  2.70539  +/- 0.04437  (1.64%) `  .  </br>

# Distance moyenne dans le réseau  </br>

`Rappel => La distance entre deux noeuds d'un graphe est la longueur d'un plus court chemin entre ces deux noeuds. La longueur d'un chemin est sa longueur en nombre d'arrêtes. ` </br>

5-1 Comme le calcul des plus courts chemins entre toutes paires de noeuds peut prendre plusieurs heures pour une telle taille de réseau, c'est pourquoi on se limitera  à un échantillon de 1000 noeuds choisis aléatoirement  en faisant un parcours en largeur. </br>

Pour permet de faire ce calcul, et les résultats obtenus sont stockés dans le fichier  [DataDistance](./ressources/DataDistance.dat) . </br>
Ces résultats sont tracés via Gnuplot grâce au [script](./ressources/distance.gnu) . </br>

Ce qui nous mène au graphe suivant

![Distribution des distances ](./ressources/Distances.png)


Nous obtenons egalement le résultat de la distance moyenne calculée pour 1000 sommets choisis au hasard qui est de => **6.787408571969219** . </br>

5-2 D'après la distance moyenne obtenue , en effet , l'hypothèse des six degrés de séparation est confirmée . </br>

5-3 Il s'agit également d'un réseau petit monde, car on obteint une plus courte distance entre deux noeuds aléatoires . </br>

5-4 La distance moyenne dans un réseau aléatoire avec les mêmes caractéristiques est de => **6.700611818856679** .</br>

5-5 **Analyse du graphe des distributions des distances** </br>

Dans la courbe obtenue on observe que selon les données du sommet on peut effectuer la remarque qu'il s'agit de la même distance que partage plusieurs noeuds.
Ainsi nous pouvons déduire que cette distribution suit bien une loi Binomiale. </br>

# Générer un réseau aléatoire & et un réseau avec Barabasi-Albert </br>

Le modèle de Barabási–Albert (BA) est un algorithme pour la génération
aléatoire de réseaux sans échelle à l'aide d'un mécanisme d'attachement préférentiel.
On pense que plusieurs systèmes naturels ou humains, tel que l'Internet, le world wide web,
les réseaux de citations, et certains réseaux sociaux sont approximativement sans échelle. Ils contiennent en tout cas quelques nœuds (appelés hubs ou moyeux)
avec un degré inhabituellement élevé par rapport aux autres nœuds du réseau. Le modèle BA tente d'expliquer l'existence de tels nœuds dans de véritables réseaux. L'algorithme est
nommé d'après ses inventeurs Albert-László Barabási et Réka Albert et est un cas particulier d'un modèle plus général appelé modèle de Price

6-1 Afin de générer un réseau aléatoire, GraphStream nous le permet avec son générateur *RandomGenerator()* en précisant le nombre de noeuds et le degré moyen souhaité. Quant au nombre de liens, ceci varie
entre une réalisation et une autre. </br>


**Résultats** </br>

Le nombre de noeuds pour un graphe  généré aléatoirement => **317080** </br>
Le nombre de liens pour un graphe généré aléatoirement => **950399** </br>
Le degré moyen d'un graphe généré aléatoirement => **5.994563102722168** </br>
Connexité du graphe aléatoire => **false** </br>
Le coefficient de clustering d'un graphe généré aléatoirement => **3.7999301117284935E-5** </br>

Le [script](./ressources/tracer_dist_degree_graphe_alea.gnu) a permit le tracage du graphe de la distribution des degrés en echelle linéaire suivant


![Distribution des degres pour un graphe aleatoire en lineaire ](./ressources/destributionDegre_graphe_aleatoire.png)

Egalement le [script](./ressources/tracer_dist_degree_alea_log.gnu) pour l'echelle en log log

![Distribution des degres pour un graphe aléatoire en log ](./ressources/destributionDegre_log_graphe_alea.png)

6-2 Un graphe contenant le modèle de Barabasi-Albert est un graphe qui génère aléatoirement des réseaux sans échelle à l'aide d'un mécanisme d'attachement préférentiel permettant de connecter les noeuds.

Ce genre de graphe a été implémenté dans ce Tp , et en ayant les mêmes caractéristiques que le réseau DBLP on obtient les résultats suivants : </br>
Le nombre de noeud de ce graphe generer avec le generateur barbasi est => **317080** </br>
Le nombre de liens de ce graphe avec le generateur barbasi est =>**1110112** </br>
Degré moyen du graphe BAN => **7.002094268798828** </br>
Connexité du graphe aléatoire => **true** </br>
Le coefficient de clustering => **4.3767738485098553E-4**


Le [script](./ressources/tracer_dist_degree_graphe_BAN.gnu) a permit le tracage du graphe de la distribution des degrés en echelle linéaire suivant

![Distribution des degres pour BAN en lineaire ](./ressources/destributionDegre_lineaire_BAN.png)

Egalement le [script](./ressources/tracer_dist_degree_graphe_BAN_log.gnu) pour l'echelle en log log

![Distribution des degres pour BAN en log ](./ressources/destributionDegre_BAN_log.png)

# Comparaison


|                                     |                         DBPL |                                                                                                                                      Aléatoire |                                                                                                             Préférentiel |
|:------------------------------------|-----------------------------:|-----------------------------------------------------------------------------------------------------------------------------------------------:|-------------------------------------------------------------------------------------------------------------------------:|
| Nombre de noeuds : $`N`$            |                   $`317080`$ |                                                                                                                                     $`317087`$ |                                                                                                               $`317080`$ |
| Nombre de liens : $`L`$             |                  $`1049866`$ |                                                                                                                                     $`950399`$ |                                                                                                              $`1110112`$ |
| Degré moyen : $`\langle k \rangle`$ |         $`6.62208890914917`$ |                                                                                                                          $`5.994563102722168`$ |                                                                                                                     $`7.002094268798828`$ |
| Coefficient de clustering $`C_{i}`$ |                         $`2.0884599814397534E-5`$ |                                                                                                                                           $`3.7999301117284935E-5`$ |                                                                                                $`4.3767738485098553E-4`$ |
| Connexité                           |                          Oui |                                                                                                                                            Non |                                                                                                                      Oui |


D'après ce tableau de comparaison : on voit que le graphe préférentiel a plus de liens, concernant le graphe aléatoire c'est celui qui possède le plus faible coefficient de clustering . </br>
De plus le graphe ayant le mécanisme d'attachement préférentiel intègre deux concepts généraux importants : la croissance & l'attachement préférentiel. Et ces deux concepts existent tout deux largement dans les réseaux réels.
La croissance signifie que le nombre de noeuds dans le réseau augmente avec le temps . </br>
L'attachement préférentiel signifie que plus un noeud est connecté, plus il est suceptible de recevoir de nouveauc liens. </br>
Les noeuds avec un degré plus élevé ont une plus grande capacité à saisir les liens ajoutés au réseau.  </br>