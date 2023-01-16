set terminal wxt
set terminal png
set encoding utf8
set output "distDegreLog.png"
set logscale x 10
set logscale y 10
set xlabel "k"
set ylabel "p(k)"
set key on inside center top
plot"donnee_distribution_des_degres.dat" with linesp lt 1 pt 1
