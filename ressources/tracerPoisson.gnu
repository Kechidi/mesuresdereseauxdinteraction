set terminal png
set xlabel 'k'
set ylabel 'p(k)'
set output 'poisson.png'
set logscale xy
set yrange [1e-6:1]

# Poisson
lambda = 6.62208890914917
poisson(k) = lambda ** k * exp(-lambda) / gamma(k + 1)

plot 'donnee_distribution_des_degres.dat' title 'DBLP', \
  poisson(x) title 'Poisson law'
