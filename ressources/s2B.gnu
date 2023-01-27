set terminal png 10
set encoding utf8
set output "Scenario2BarabasiAlbert.png"

set  title 'Scénario 2 pour un graphe BarabasiAlbert '

set xrang[0:84]

set ylabel 'nombre de personnes infectées'
set xlabel 'nombre total de jours'
set key top left
plot "s2B.dat" t"Scénario 2 pour un graphe BarabasiAlbert"