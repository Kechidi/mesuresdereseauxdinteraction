set terminal png 10
set encoding utf8
set output "Scenario3.png"

set  title 'Scenario 3 '

set xrang[0:84]

set ylabel 'nombre de personnes infectées'
set xlabel 'nombre total de jours'
set key top left
plot "s3.dat" t"Scenario 3"