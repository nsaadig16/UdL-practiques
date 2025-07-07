
INICI=4
FI=9

practica=batalla

$(practica): $(practica).hs
	ghc $<

instances: 
	python3 Generator.py $(INICI) $(FI)

test: $(practica).py $(practica)
	python3 Checker.py $(practica).py $(INICI) $(FI)
	python3 Checker.py ./$(practica) $(INICI) $(FI)

zip:
	$(RM) scripts.zip
	zip -r scripts.zip Makefile Checker.py $(practica).py $(practica).hs Generator.py

clean:
	$(RM) $(practica) $(practica).o $(practica).hi

