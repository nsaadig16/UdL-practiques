import argparse
from math import sqrt
from random import Random
from utils import read_csv
from typing import List
from rich import print


def squared_euclidean(point1: List[float], point2: List[float]) -> float:
    return sum((a - b) ** 2 for a, b in zip(point1, point2))


def euclidean(point1: List[float], point2: List[float]) -> float:
    return sqrt(squared_euclidean(point1, point2))


class CentroidManager:
    def __init__(self, k: int, numattr: int) -> None:
        self.k = k
        self.attr = numattr
        self.reset()

    def reset(self):
        self.centroids = [[0.0] * self.attr for _ in range(self.k)]
        self.count = [0] * self.k

    def add(self, cluster_idx: int, attributes: List[float]):
        for i, val in enumerate(attributes):
            self.centroids[cluster_idx][i] += val
        self.count[cluster_idx] += 1

    def compute_means(self) -> List[List[float]]:
        return [
            [x / self.count[i] if self.count[i] > 0 else x for x in centroid]
            for i, centroid in enumerate(self.centroids)
        ]

    def generate_random(self, min_val: float, max_val: float, rng: Random) -> List[List[float]]:
        return [[rng.uniform(min_val, max_val) for _ in range(self.attr)] for _ in range(self.k)]


class KMeans:
    def __init__(
        self,
        k: int = 4,
        n_restarts: int = 1,
        distance: str = "euclidean",
        rng=None,
    ):
        self.k = k
        self.n_restarts = n_restarts
        self.distance = distance
        self.rng = rng if rng is not None else Random()

    def fit(self, observations):
        assert len(observations) > 0
        self.best_sum_ = float("inf")
        self.centroids_ = []
        self.distances_ = []
        self.X_assignments_ = []

        min_val, max_val = self._get_data_range(observations)

        for n in range(self.n_restarts):

            distance_fn = self._get_fn(self.distance)
            cm = CentroidManager(self.k, len(observations[0]) - 1)

            centroids = cm.generate_random(min_val, max_val, self.rng)
            prev_assignments = None

            while True:

                distances = []
                X_assignments = []

                # Compute the closest centroid to each point
                for o in observations:
                    point = o[1:]
                    min_distance = float("inf")
                    closest_idx = -1

                    for i, c in enumerate(centroids):
                        distance = distance_fn(point, c)
                        if distance < min_distance:
                            min_distance = distance
                            closest_idx = i

                    X_assignments.append(closest_idx)
                    distances.append(min_distance)

                sum_distances = sum(distances)

                # Check if the assignments have converged
                if X_assignments == prev_assignments:
                    break

                prev_assignments = X_assignments

                # Create the new centroids
                cm.reset()
                for assign, obs in zip(X_assignments, observations):
                    cm.add(assign, obs[1:])

                centroids = cm.compute_means()

            if sum_distances < self.best_sum_:
                self.centroids_ = centroids
                self.distances_ = distances
                self.X_assignments_ = X_assignments
                self.best_sum_ = sum_distances
                self.n_ = n + 1
        return self

    def _get_data_range(self, observations: List) -> tuple:
        min_val = float("inf")
        max_val = float("-inf")
        for obs in observations:
            for val in obs[1:]:
                min_val = min(min_val, val)
                max_val = max(max_val, val)
        return min_val, max_val

    def _get_fn(self, distance: str):
        if distance == "euclidean":
            return euclidean
        else:
            return squared_euclidean

###############################################
#                 CLI Code                    #
###############################################


def main(args):
    # Set the random generator
    rng = Random(args.seed)

    # Load the dataset
    dataset = read_csv(args.dataset, skip_headers=True)

    # Instantiate KMeans
    kmeans = KMeans(
        k=args.k,
        n_restarts=args.n_restarts,
        distance=args.distance,
        rng=rng
    )

    # Train the clustering model
    kmeans.fit(dataset)

    # Print metrics
    if args.quiet:
        print(kmeans.best_sum_)
    else:
        print("\n[bold green]K-Means Clustering Results[/bold green]")
        print(f"Best restart: {kmeans.n_}/{args.n_restarts}")
        print(f"Sum of distances: {kmeans.best_sum_:.4f}")
        print("\nCluster sizes:")
        for i in range(args.k):
            count = kmeans.X_assignments_.count(i)
            print(f"  Cluster {i}: {count} points")
        
        if any(kmeans.X_assignments_.count(i) == 0 for i in range(args.k)):
            print("\n[yellow]Warning: Some clusters are empty[/yellow]")


def parse_args():
    parser = argparse.ArgumentParser()
    parser.add_argument(
        "dataset", type=str, help="Path to the CSV file containing the dataset."
    )
    parser.add_argument(
        "--k", type=int, default=4, help="Value for the 'k' parameter of KMeans."
    )
    parser.add_argument(
        "--n-restarts",
        type=int,
        default=1,
        help="Number of restarts to find the minimum sum of distances.",
    )
    parser.add_argument(
        "--distance",
        type=str,
        choices=["euclidean", "squared-euclidean"],
        default="euclidean",
        help="Distance metric used by KMeans.",
    )
    parser.add_argument("--seed", type=int, default=123456, help="RNG Seed.")
    parser.add_argument("--quiet","-q",action="store_true", default=False, help="Only prints the sum of distances.")
    return parser.parse_args()


if __name__ == "__main__":
    args = parse_args()
    main(args)
