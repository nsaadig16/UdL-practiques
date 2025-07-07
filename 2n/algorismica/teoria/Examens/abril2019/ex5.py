def senars(l : list[int]) -> int:
    acc = 0
    while l:
        if l[0] % 2 == 1:
            acc += 1
        l = l[1:]
    return acc