import os 

lines = open("train.csv").readlines()
print "num of lines:", len(lines)-2
lista = []
for line in lines[1:]:
	value = line.strip()[0]
	lista.append(int(value))
print "num of ones:", sum(lista)

	
