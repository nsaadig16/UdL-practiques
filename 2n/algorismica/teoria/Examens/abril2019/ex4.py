def senars (l : list[int], acc : int = 0) -> int:
    if not l:
        return acc
    if l[0] % 2 == 1:
        return senars(l[1:], acc + 1)
    else:
        return senars(l[1:], acc)