set terminal png 10
set encoding utf8
set output "Distances.png"
set  title 'Distribution distances  '

set key top right
plot "DataDistance.dat" t"Distribution des distances" with linespoints ls 2