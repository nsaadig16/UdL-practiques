def hamming (s1 : str, s2 : str) -> int:
    acc = 0
    while s1:
        if s1[0] != s2[0]:
            acc += 1    
        s1 = s1[1:]
        s2 = s2[1:]
    return acc