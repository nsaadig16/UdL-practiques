senars :: [Int] -> Int
senars [] = 0
senars (x:xs)
    | x `mod` 2 == 1 = 1 + senars xs
    | otherwise = senars xs
