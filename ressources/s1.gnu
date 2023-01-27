set terminal png 10
set encoding utf8
set output "Scenario1.png"

set  title 'Scenario 1 '

set xrang[0:84]

set ylabel 'nombre de personnes infectées'
set xlabel 'nombre total de jours'
set key top left
plot "s1.dat" t"Scenario 1"