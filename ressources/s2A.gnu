set terminal png 10
set encoding utf8
set output "Scenario2Aleatoire.png"

set  title 'Scénario 2 pour un graphe aléatoire '

set xrang[0:84]

set ylabel 'nombre de personnes infectées'
set xlabel 'nombre total de jours'
set key top left
plot "s2A.dat" t"Scénario 2 pour un graphe aléatoire"