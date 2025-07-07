def hamming( s1 : str, s2 : str, acc : int = 0) -> int:
    if not s1:
        return acc
    if s1[0] != s2[0]:
        return hamming(s1[1:], s2[1:], acc + 1)
    else:
        return hamming(s1[1:], s2[1:], acc)