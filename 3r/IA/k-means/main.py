from rich import print
from rich.console import Console
from tabulate import tabulate
from os import listdir
from utils import PointDistancer, Point, DISTANCES
import pandas as pd
import questionary
from typing import List
from random import randint


def main():
    console = Console()
    console.clear()
    fn = read_fn()
    pdst = PointDistancer(fn)
    data = read_file()
    console.clear()
    print(tabulate(data,headers='keys',tablefmt='rounded_grid',showindex=False)) # type: ignore
    print()
    if not ask_continue("Do you wish to see the iterations?"):
        print("[bright_yellow]Done![/]")
        exit(0)
    points, centroids = get_points_and_centroids(data)
    prev: List[str] | None = None
    i = 0
    while True:
        print(f"[blue underline]Iteration {i}[/blue underline]")
        assignments = assign(points,centroids,pdst)
        cent = [c.label for c in assignments['Centroid']]
        if prev and prev == cent:
            print()
            print(f"Clustering done in {i} iterations!")
            break
        if not ask_continue("See next iteration?"):
            print("Done!")
            exit(0)
        prev = cent
        centroids = move_centroids(assignments)
        i += 1
        print()
    


def move_centroids(data: pd.DataFrame):
    cent = set(data["Centroid"])
    centroids = {c.label: [] for c in cent}
    for p, c in data.itertuples(index=False):
        centroids[c.label].append(p)
    new_centroids = []
    for c in centroids:
        new_centroids.append(Point.from_points(c,centroids[c]))
    return new_centroids


def assign(points: List[Point], centroids: List[Point], pdst: PointDistancer) -> pd.DataFrame:
    assignments = pd.DataFrame(columns=["Point", "Centroid"])
    for p in points:
        closest, min_dist = None, float("inf")
        for c in centroids:
            distance = pdst.distance(p, c)
            if min_dist == distance:
                closest = c if randint(0, 1) == 0 else closest  # Break ties randomly
            elif min_dist > distance:
                closest, min_dist = c, distance
        assignments.loc[len(assignments)] = [p, closest]
    display = assignments.map(lambda x: x.label)
    #print(display.to_string(index=False))
    print(tabulate(display,headers='keys',tablefmt='rounded_grid',showindex=False)) #type: ignore
    return assignments


def get_points_and_centroids(data: pd.DataFrame):
    points = []
    centroids = []
    for label, x, y in data.itertuples(index=False):
        p = Point(str(label), x, y)
        if p.label.startswith("C"):
            centroids.append(p)
        else:
            points.append(p)
    return points, centroids


def read_fn():
    choices = {f.__name__ : f for f in DISTANCES}
    f = questionary.select(
        message="Choose a distance function:",
        choices=list(choices.keys())
    ).ask()
    return choices[f]


def read_file() -> pd.DataFrame:
    f = questionary.select(
        message="Choose a data dile from the contents folder:",
        choices=listdir('contents')
    ).ask()
    csv = pd.read_csv(f"contents/{f}")
    csv['Item'] = csv['Item'].apply(str)
    return csv

def ask_continue(msg : str) -> bool:
    return questionary.confirm(
        message=msg,
    ).ask()

if __name__ == "__main__":
    main()
