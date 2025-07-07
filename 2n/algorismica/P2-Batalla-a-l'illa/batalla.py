""""Resolució al problema de la batall a l'illa"""
import sys
import math
from functools import wraps

import time

def time_cost(func):
    """Decorador per mesurar el temps d'execució d'una funció."""
    @wraps(func)
    def wrapper(*args, **kwargs):
        start = time.perf_counter()
        result = func(*args, **kwargs)
        end = time.perf_counter()
        print(f"Execution time of {func.__name__}: {end - start:.6f} seconds")
        return result
    return wrapper


def llegir_entrada(nom_fitxer):
    """Llegeix l'entrada del fitxer i retorna el nombre de nois i les seves posicions."""
    with open(nom_fitxer, 'r', encoding='utf-8') as f:
        n = int(f.readline())
        punts = [tuple(map(int, línia.split())) for línia in f]
    return n, punts

def escriure_sortida(nom_fitxer, distancia, equip1):
    """Escriu la sortida al fitxer amb la distància màxima i els jugadors de l'equip roig."""
    with open(nom_fitxer, 'w', encoding='utf-8') as f:
        f.write(f"{distancia:.6f}\n")
        f.write(' '.join(map(str, sorted(equip1))) + '\n')

@time_cost
def resoldre(n, punts):
    """Resoldre el problema de la batalla de nois per maximitzar la distància entre els equips."""
    total = 2 * n
    millor = [0, 0]  # [distància, màscara_roig]

    # Generador que produeix els índexs d'un equip a partir d'una màscara de bits
    def indices_equip(mask):
        for i in range(total):
            if (mask >> i) & 1:
                yield i
    
    # Generador que produeix totes les parelles de distàncies entre equips
    def distancies_entre_equips(roig_mask, blau_mask):
        for i in indices_equip(roig_mask):
            for j in indices_equip(blau_mask):
                yield math.hypot(punts[i][0] - punts[j][0], punts[i][1] - punts[j][1])
    
    def min_dist(roig_mask, blau_mask):
        # Trobem la distància mínima usant el generador
        min_d = float('inf')
        for d in distancies_entre_equips(roig_mask, blau_mask):
            if d < min_d:
                min_d = d
                if min_d <= millor[0]:
                    return 0  # sortida anticipada
        return min_d

    def count_bits(x):
        return bin(x).count("1")

    def back(i, roig_mask, blau_mask):
        if count_bits(roig_mask) > n or count_bits(blau_mask) > n:
            return
        if i == total:
            if count_bits(roig_mask) == n:
                d = min_dist(roig_mask, blau_mask)
                if d > millor[0]:
                    millor[0] = d
                    millor[1] = roig_mask
            return

        # Assigna el jugador i a l'equip roig
        back(i + 1, roig_mask | (1 << i), blau_mask)
        # Assigna el jugador i a l'equip blau
        back(i + 1, roig_mask, blau_mask | (1 << i))

    # Trencament de simetria: fixa el jugador 0 a l'equip roig
    back(1, 1 << 0, 0)

    # Descodifica la màscara de bits en una llista
    equip_roig = [i + 1 for i in range(total) if (millor[1] >> i) & 1]
    return millor[0], equip_roig



def main():
    """Funció principal per llegir l'entrada, resoldre el problema i escriure la sortida."""
    if len(sys.argv) >= 3:
        fitxer_entrada = sys.argv[1]
        fitxer_sortida = sys.argv[2]
    else:
        fitxer_entrada = sys.stdin
        fitxer_sortida = sys.stdout

    n, punts = llegir_entrada(fitxer_entrada)
    distancia, equip1 = resoldre(n, punts)
    escriure_sortida(fitxer_sortida, distancia, equip1)

if __name__ == "__main__":
    main()
