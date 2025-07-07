-- Resolució al problema de la batalla a l'illa
import System.Environment (getArgs)
import System.IO
import Data.List (sort)
import Data.Bits
import Text.Printf
import System.CPUTime

-- Tipus de dades
type Punt = (Int, Int)
type Mascara = Int

-- Decorador per mesurar temps d'execució
timeCost :: String -> IO a -> IO a
timeCost name action = do
    start <- getCPUTime
    result <- action
    end <- getCPUTime
    let diff = fromIntegral (end - start) / (10^12)
    printf "Execution time of %s: %.6f seconds\n" name (diff :: Double)
    return result

-- Llegeix l'entrada del fitxer
llegirEntrada :: String -> IO (Int, [Punt])
llegirEntrada nomFitxer = do
    contents <- readFile nomFitxer
    let linies = lines contents
        n = read (head linies)
        punts = map ((\[x,y] -> (x,y)) . map read . words) (tail linies)
    return (n, punts)

-- Escriu la sortida al fitxer
escriureSortida :: String -> Double -> [Int] -> IO ()
escriureSortida nomFitxer distancia equip1 = do
    writeFile nomFitxer $ unlines 
        [ printf "%.6f" distancia
        , unwords (map show (sort equip1))
        ]

-- Calcula la distància euclidiana entre dos punts
distancia :: Punt -> Punt -> Double
distancia (x1, y1) (x2, y2) = sqrt $ fromIntegral ((x1-x2)^2 + (y1-y2)^2)

-- Genera els índexs d'un equip a partir d'una màscara de bits
indicesEquip :: Mascara -> Int -> [Int]
indicesEquip mask total = [i | i <- [0..total-1], testBit mask i]

-- Calcula totes les distàncies entre dos equips
distanciesEntreEquips :: [Punt] -> Mascara -> Mascara -> Int -> [Double]
distanciesEntreEquips punts roigMask blauMask total = 
    [ distancia (punts !! i) (punts !! j) 
    | i <- indicesEquip roigMask total
    , j <- indicesEquip blauMask total
    ]

-- Troba la distància mínima entre dos equips
minDist :: [Punt] -> Mascara -> Mascara -> Int -> Double -> Double
minDist punts roigMask blauMask total millor = 
    case distanciesEntreEquips punts roigMask blauMask total of
        [] -> 0
        dists -> foldl1 min dists

-- Compta els bits activats en una màscara
countBits :: Mascara -> Int
countBits = popCount

-- Algoritme de backtracking per trobar la millor assignació
backtrack :: [Punt] -> Int -> Int -> Int -> Mascara -> Mascara -> (Double, Mascara) -> (Double, Mascara)
backtrack punts n total i roigMask blauMask millor@(millorDist, _)
    | countBits roigMask > n || countBits blauMask > n = millor
    | i == total = 
        if countBits roigMask == n
        then let d = minDist punts roigMask blauMask total millorDist
             in if d > millorDist then (d, roigMask) else millor
        else millor
    | otherwise = 
        let -- Assigna el jugador i a l'equip roig
            millor1 = backtrack punts n total (i+1) (setBit roigMask i) blauMask millor
            -- Assigna el jugador i a l'equip blau
            millor2 = backtrack punts n total (i+1) roigMask (setBit blauMask i) millor1
        in millor2

-- Resol el problema principal
resoldre :: Int -> [Punt] -> IO (Double, [Int])
resoldre n punts = do
    let total = 2 * n
        -- Trencament de simetria: fixa el jugador 0 a l'equip roig
        (millorDist, millorMask) = backtrack punts n total 1 (setBit 0 0) 0 (0, 0)
        equipRoig = [i + 1 | i <- [0..total-1], testBit millorMask i]
    return (millorDist, equipRoig)

-- Funció principal
main :: IO ()
main = do
    args <- getArgs
    (fitxerEntrada, fitxerSortida) <- case args of
        [entrada, sortida] -> return (entrada, sortida)
        _ -> do
            putStrLn "Ús: programa fitxer_entrada fitxer_sortida"
            return ("entrada.txt", "sortida.txt")
    
    (n, punts) <- llegirEntrada fitxerEntrada
    (distanciaResult, equip1) <- timeCost "resoldre" (resoldre n punts)
    escriureSortida fitxerSortida distanciaResult equip1