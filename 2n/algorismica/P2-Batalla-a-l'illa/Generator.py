import os
import random
from itertools import combinations
from math import hypot

def dist(p1, p2):
    return hypot(p1[0] - p2[0], p1[1] - p2[1])

def maximitza_distancia(punts):
    millor_distancia = -1
    millors_equips = []
    n = len(punts) // 2

    for equip1 in combinations(range(len(punts)), n):
        equip1 = set(equip1)
        equip2 = set(range(2 * n)) - equip1
        min_dist = min(
            dist(punts[i], punts[j]) for i in equip1 for j in equip2
        )

        equip1 = sorted([i + 1 for i in equip1])
        equip2 = sorted([j + 1 for j in equip2])

        if min_dist == millor_distancia and equip1 not in millors_equips:
            # Si la distancia es la mateixa, afegim els equips a la llista
            millors_equips.append( equip1 )
            millors_equips.append( equip2 )
        elif min_dist > millor_distancia:
            # Si trobem una millor distancia, actualitzem la millor distancia i els equips
            # i esborrem els equips anteriors
            millor_distancia = min_dist
            millors_equips = [ equip1 ]
            millors_equips.append( equip2 )

    return millor_distancia, millors_equips

def generar_exemples(inici, fi, num_exemples=40):
    '''Genera exemples d'entrada i sortida per al problema de la batalla de nois.
    Els exemples es guarden en carpetes separades per als exemples i les solucions.'''''

    # Crear carpetes
    carpeta_entrada = "exemples"
    carpeta_sortida = "solucions"
    os.makedirs(carpeta_entrada, exist_ok=True)
    os.makedirs(carpeta_sortida, exist_ok=True)

    # Generar arxius
    for n_nois in range(inici, fi):
        print("Generant exemples amb", n_nois, "nois")
        for i in range(1, num_exemples+1):
            nom_arxiu_entrada = os.path.join(carpeta_entrada, f"batalla-{n_nois}-{i}.txt")
            nom_arxiu_sortida = os.path.join(carpeta_sortida, f"solucio-{n_nois}-{i}.txt")

            # Generar posicions aleatòries per als nois
            nois = []
            amplada = n_nois
            llargada = n_nois
            for noi in range(n_nois*2):
                while True:
                    x = random.randint(0, amplada)
                    y = random.randint(0, llargada)
                    if (x, y) not in nois:
                        break
                nois.append((x, y))

            with open(nom_arxiu_entrada, "w") as f:
                f.write(f"{n_nois}\n")
                for x, y in nois:
                    f.write(f"{x} {y}\n")

            distancia, equips = maximitza_distancia(nois)

            with open(nom_arxiu_sortida, 'w') as outfile:
                outfile.write(f"{distancia:.6f}\n")
                for equip in equips:
                    outfile.write(' '.join(map(str, equip)) + '\n')

if __name__ == "__main__":
    import sys
    if len(sys.argv) > 1:
        generar_exemples(int(sys.argv[1]), int(sys.argv[2])+1)
    else:
        generar_exemples(10, 20)