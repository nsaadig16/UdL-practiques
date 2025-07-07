def countOnes(l : list[int], acc : int = 0) -> int:
    if not l:
        return acc
    if l[0] == 1:
        return countOnes( l[1:], acc + 1 )
    else:
        return countOnes( l[1:], acc )

def countOnes2(l : list[int], acc : int = 0) -> int:
    if not l:
        return acc
    return countOnes(l[1:], acc + (1 if l[0] == 1 else 0))


def countOnesIterative(l : list[int]) -> int:
    acc = 0
    while l:
        if l[0] == 1:
            acc += 1
        l = l[1:]
    return acc

def countOnesIterative2(l : list[int]) -> int:
    acc = 0
    while l:
        acc += 1 if l[0] == 1 else 0
        l = l[1:]
    return acc