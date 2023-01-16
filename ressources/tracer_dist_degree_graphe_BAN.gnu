set terminal png 10
set encoding utf8
set output "destributionDegre_lineaire_BAN.png"

set  title 'Destribution des degrés pour graphe BAN '


set ylabel 'P(k) '
set xlabel 'K'
set key top left
plot "dataDegreeDist_gaphe_BAN.dat" t"BAN linéaire" with linespoints ls 2
