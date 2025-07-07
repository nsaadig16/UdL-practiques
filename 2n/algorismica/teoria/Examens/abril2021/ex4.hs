hamming :: String -> String -> Int
hamming "" "" = 0
hamming (x:xs) (y:ys)
    | x /= y = 1 + hamming xs ys
    | otherwise = hamming xs ys