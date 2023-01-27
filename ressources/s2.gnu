set terminal png 10
set encoding utf8
set output "Scenario2.png"

set  title 'Scenario 2 '

set xrang[0:84]

set ylabel 'nombre de personnes infectées'
set xlabel 'nombre total de jours'
set key top left
plot "S2.dat" t"Scenario 2"