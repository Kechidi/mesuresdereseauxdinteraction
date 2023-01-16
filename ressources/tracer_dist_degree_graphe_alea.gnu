set terminal png 10
set encoding utf8
set output "destributionDegre_graphe_aleatoire.png"

set  title 'Destribution des degrés pour un graphe aléatoire ayant les mêmes caractéristiques du réseau DBLP '


set ylabel 'P(k) '
set xlabel 'K'
set key top left
plot "dataDegreeDist_graphe_alea.dat" t"DBLP linéaire" with linespoints ls 2