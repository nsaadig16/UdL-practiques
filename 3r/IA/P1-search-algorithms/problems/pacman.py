import pygame
import math

from typing import Any

from hlogedu.search.problem import Problem, action, Categorical, Heuristic
from hlogedu.search.visualizer import SolutionVisualizer
from hlogedu.search.common import ClassParameter

# Visualization (you do not have to modify this!)
##############################################################################


class PacmanVisualizer(SolutionVisualizer):
    def __init__(self, screen, problem, zoom, speed):
        super().__init__(screen, problem, zoom, speed)
        self.grid = self.problem.grid
        self.cell_size = self.get_cell_size()
        self.rows = len(self.grid)
        self.cols = len(self.grid[0])
        self.last_action = "move(R)"  # default direction

    def draw_state(self, state: Any, mouth_angle: float = 0.25):
        (pac_r, pac_c), food = state
        self.screen.fill((0, 0, 0))

        self.draw_maze_walls()

        # draw food (if not eaten)
        if food is not None:
            fr, fc = food
            food_rect = pygame.Rect(
                fc * self.cell_size, fr * self.cell_size, self.cell_size, self.cell_size
            )
            pygame.draw.circle(
                self.screen, (255, 255, 255), food_rect.center, self.cell_size // 6
            )

        # draw pacman
        pac_rect = pygame.Rect(
            pac_c * self.cell_size,
            pac_r * self.cell_size,
            self.cell_size,
            self.cell_size,
        )
        radius = self.cell_size // 2 - 2
        draw_pacman(self.screen, pac_rect.center, radius, mouth_angle, self.last_action)

    def animate_transition(self, state: Any, action: Any, new_state: Any):
        (r1, c1), food = state
        (r2, c2), _ = new_state

        self.last_action = action  # update facing direction

        steps = 8
        for i in range(1, steps + 1):
            r = r1 + (r2 - r1) * (i / steps)
            c = c1 + (c2 - c1) * (i / steps)

            # mouth cycles between closed (0 rad) and open (~0.5 rad)
            phase = math.sin(i / steps * math.pi)
            mouth_angle = 0.5 * phase  # up to ~30°

            self.draw_state(((r, c), food), mouth_angle)
            pygame.display.flip()
            pygame.time.delay(self.get_delay())

    def draw_maze_walls(self):
        wall_blue = (0, 0, 255)
        bg_color = (20, 20, 40)  # dark navy background
        thickness = max(2, self.cell_size // 5)

        for r in range(self.rows):
            for c in range(self.cols):
                x = c * self.cell_size
                y = r * self.cell_size
                rect = pygame.Rect(x, y, self.cell_size, self.cell_size)

                if self.grid[r][c] != "%":
                    # fill non-wall cell with dark navy
                    pygame.draw.rect(self.screen, bg_color, rect)
                    continue

                # otherwise it's a wall: draw blue outline where it borders non-wall
                neighbors = [
                    (-1, 0, (x, y), (x + self.cell_size, y)),  # UP
                    (
                        1,
                        0,
                        (x, y + self.cell_size),
                        (x + self.cell_size, y + self.cell_size),
                    ),  # DOWN
                    (0, -1, (x, y), (x, y + self.cell_size)),  # LEFT
                    (
                        0,
                        1,
                        (x + self.cell_size, y),
                        (x + self.cell_size, y + self.cell_size),
                    ),  # RIGHT
                ]

                for dr, dc, start, end in neighbors:
                    nr, nc = r + dr, c + dc
                    if (
                        not (0 <= nr < self.rows and 0 <= nc < self.cols)
                        or self.grid[nr][nc] != "%"
                    ):
                        pygame.draw.line(self.screen, wall_blue, start, end, thickness)


def draw_pacman(surface, center, radius, mouth_angle, direction):
    """
    Draw a Pacman with a mouth opening/closing.

    mouth_angle: how wide the mouth is (in radians).
    direction: one of "U", "D", "L", "R".
    """
    x, y = center
    # Base rotation in radians
    angles = {
        "move(R)": 0,
        "move(U)": math.pi / 2,
        "move(L)": math.pi,
        "move(D)": -math.pi / 2,
    }
    rotation = angles.get(direction, 0)

    # Pacman body (arc)
    start_angle = rotation + mouth_angle
    end_angle = rotation - mouth_angle + 2 * math.pi

    pygame.draw.circle(surface, (255, 255, 0), center, radius)
    # Draw the mouth as a black triangle
    p1 = center
    p2 = (x + radius * math.cos(start_angle), y - radius * math.sin(start_angle))
    p3 = (x + radius * math.cos(end_angle), y - radius * math.sin(end_angle))
    pygame.draw.polygon(surface, (0, 0, 0), [p1, p2, p3])


# Problem
##############################################################################


class PacmanProblem(Problem):
    NAME = "Pacman"
    VISUALIZER = PacmanVisualizer
    PARAMS = [
        ClassParameter(
            "file",
            type=str,
            default=None,
            help="File with the maze in the Pacman Project format.",
        )
    ]

    def __init__(self, file: str):
        with open(file) as fh:
            self.grid = [line.strip() for line in fh]

        self.rows = len(self.grid)
        self.cols = len(self.grid[0])

        start = None
        food = None

        for r, row in enumerate(self.grid):
            for c, ch in enumerate(row):
                if ch == "P":
                    start = (r, c)
                elif ch == ".":
                    food = (r, c)

        if start is None:
            raise ValueError("Grid must contain 'P' for Pacman start")
        if food is None:
            raise ValueError("Grid must contain '.' for food")

        # state = (pacman_position, food_position | None)
        self.start_state = (start, food)

    def get_start_states(self):
        return [self.start_state]

    def is_goal_state(self, state):
        _, food = state
        return food is None

    def is_valid_state(self, _):
        return True

    @action(Categorical(["U", "D", "L", "R"]), cost=1)
    def move(self, state, direction):
        (r, c), food = state
        if direction == "U":
            r -= 1
        elif direction == "D":
            r += 1
        elif direction == "L":
            c -= 1
        elif direction == "R":
            c += 1
        else:
            raise ValueError(f"Unknown action: {direction}")

        new_food = food
        if (r, c) == food:
            new_food = None  # food eaten

        if 0 <= r <= self.rows and 0 <= c <= self.cols and self.grid[r][c] != "%":
            return ((r, c), new_food)
        return None

@PacmanProblem.heuristic
class Euclidean(Heuristic):
    def compute(self, state):
        pac_r, pac_c = state[0]
        food = state[1]
        if food is None:
            return 0
        food_r, food_c = food
        distance = math.hypot((food_r - pac_r),(food_c - pac_c))
        return distance

@PacmanProblem.heuristic
class Manhattan(Heuristic):
    def compute(self, state):
        pac_r, pac_c = state[0]
        food = state[1]
        if food is None:
            return 0
        food_r, food_c = food
        distance = abs(pac_r - food_r) + abs(pac_c - food_c)
        return distance