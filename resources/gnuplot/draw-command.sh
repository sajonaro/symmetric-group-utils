#!/usr/local/bin/gnuplot -persist

#set object rectangle from screen 0,0 to screen 1,1 behind fillcolor rgb 'blue' fillstyle solid noborder
set terminal GNUTERM


set grid 
set notitle

set xlabel "--X-->"
set ylabel "--Y-->"
set zlabel "--Z-->"
set ztics rotate by -90

splot [-10:10] [-10:10] [-10:10] '.dat' notitle with linespoints

pause -1 "Hit any key to continue"