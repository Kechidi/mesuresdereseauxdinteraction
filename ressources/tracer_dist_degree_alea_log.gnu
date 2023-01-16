set terminal png 10
set encoding utf8
set output "destributionDegre_log_graphe_alea.png"

set  title 'Destribution des degrés pour un graphe aléatoire log-log '

set logscale x
set logscale y
set ylabel 'P(k) '
set xlabel 'K'
set key top right


set yrange [1e-6:1]

# Poisson
lambda = 6.62208890914917
poisson(k) = lambda ** k * exp(-lambda) / gamma(k + 1)

# on va fitter une fonction linéaire en log-log

f(x) = lc - gamma * x
fit f(x) "dataDegreeDist_graphe_alea.dat" using (log($1)):(log($2)) via lc, gamma

c = exp(lc)
power(k) = c * k ** (-gamma)

plot "dataDegreeDist_graphe_alea.dat" t 'destribution', \
  poisson(x) title 'loi de poisson', \
  power(x) title 'loi expo'