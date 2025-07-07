-- Count ones in a list
countOnes ::  [Int] -> Int

countOnes [] = 0
countOnes (x:xs)
    | x == 1 = 1 + countOnes xs
    | otherwise = 0 + countOnes xs

countOnes2 (x:xs) = (if x==1 then 1 else 0) + countOnes xs