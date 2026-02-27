from __future__ import annotations
from math import hypot
from typing import Callable, List
from dataclasses import dataclass

@dataclass(frozen=True)
class Point:
    label : str
    x : float
    y: float

    @classmethod
    def from_points(cls, label : str, point_to_add: List[Point]):
        new_x = 0
        new_y = 0
        num_values = 0
        for p in point_to_add:
            new_x += p.x
            new_y += p.y
            num_values += 1
        return Point(label,new_x / num_values, new_y / num_values) 
    


def euclidean(point1: Point, point2: Point) -> float:
    return hypot((point2.x - point1.x), (point2.y - point1.y))


def squared_euclidean(point1: Point, point2: Point) -> float:
    return (point1.x - point2.x) ** 2 + (point1.y - point2.y) ** 2


def manhattan(point1: Point, point2: Point) -> float:
    return abs(point1.x - point2.x) + abs(point1.y - point2.y)

DISTANCES = [manhattan, euclidean, squared_euclidean]


class PointDistancer:
    def __init__(self, distancefn: Callable[[Point,Point],float]):
        self.distancefn = distancefn

    def distance(self, point: Point, centroid: Point):
        return self.distancefn(point, centroid)
