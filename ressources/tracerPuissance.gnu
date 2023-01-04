set terminal png
set xlabel 'k'
set ylabel 'p(k)'
set output 'loiPuissance.png'

set logscale xy
set yrange [1e-6:1]

# on va fitter une fonction linéaire en log-log

f(x) = lc - gamma * x
fit f(x) 'donnee_distribution_des_degres.dat' using (log($1)):(log($2)) via lc, gamma

c = exp(lc)
power(k) = c * k ** (-gamma)

plot 'donnee_distribution_des_degres.dat' title 'DBLP', \
  power(x) title 'Loi Puissance'


