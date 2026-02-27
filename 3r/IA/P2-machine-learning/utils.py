from random import Random
from rich import print as richprint
from rich.table import Table

def read_csv(file_name, skip_headers : bool = False):
    table = []
    with open(file_name) as f:
        for i, line in enumerate(f):
            line = line.strip()
            if skip_headers and i == 0:
                continue
            parsed = []
            for entry in line.split(","):
                entry = _cast_to(entry)
                parsed.append(entry)
            table.append(parsed)
    return table


def split_observations_and_labels(table):
    data, labels = [], []
    for row in table:
        data.append(row[:-1])
        labels.append(row[-1])
    return data, labels

def split_train_and_test(rng : Random, ratio : float, observations : list, labels : list):
    pairs = list(zip(observations, labels))
    rng.shuffle(pairs)
    split_idx = int(len(pairs) * ratio)
    train_pairs = pairs[split_idx:]
    test_pairs = pairs[:split_idx]
    train_obs, train_lab, test_obs, test_lab = [], [], [], []
    for pair in train_pairs:
        train_obs.append(pair[0])
        train_lab.append(pair[1])
    for pair in test_pairs:
        test_obs.append(pair[0])
        test_lab.append(pair[1])
    return train_obs, train_lab, test_obs, test_lab

def _cast_to(value_str):
    """
    Given a value represented as a string, try to convert it
    to a more specific type (int, float) or fail back to string.
    """
    try:
        return int(value_str)
    except ValueError:
        pass
    try:
        return float(value_str)
    except ValueError:
        pass
    return value_str


def read_sms(file_name):
    messages, labels = [], []
    with open(file_name) as f:
        for line in f:
            label, message = line.strip().split("\t", maxsplit=1)
            messages.append(message)
            labels.append(label)
    return messages, labels

def print_output(test_labels, predicted, score):
    table = Table(title="Results",title_style="bold")

    table.add_column("Classes", justify="center")
    table.add_column("Total", justify="right")
    table.add_column("Correct", justify="right", style="bright_green")
    table.add_column("Wrong", justify="right", style="bright_red")

    labels = set(test_labels)
    total = {label : 0 for label in labels}
    correct = {label : 0 for label in labels}

    for expected, actual in zip(test_labels, predicted):
        if expected == actual:
            correct[actual] += 1
        total[actual] += 1 
    
    for label in total.keys():
        total_l = total[label]
        correct_l = correct[label]
        incorrect_l = total_l - correct_l
        table.add_row(
            label,
            str(total_l),
            str(correct_l),
            str(incorrect_l)
        )
    
    richprint(table)
    richprint(
        f"[bright_blue]\tScore: [{'green' if score > 0.75 else 'red'}]{score * 100:.2f}%"
    )

STOPWORDS = [
    "i",
    "me",
    "my",
    "myself",
    "we",
    "our",
    "ours",
    "ourselves",
    "you",
    "your",
    "yours",
    "yourself",
    "yourselves",
    "he",
    "him",
    "his",
    "himself",
    "she",
    "her",
    "hers",
    "herself",
    "it",
    "its",
    "itself",
    "they",
    "them",
    "their",
    "theirs",
    "themselves",
    "what",
    "which",
    "who",
    "whom",
    "this",
    "that",
    "these",
    "those",
    "am",
    "is",
    "are",
    "was",
    "were",
    "be",
    "been",
    "being",
    "have",
    "has",
    "had",
    "having",
    "do",
    "does",
    "did",
    "doing",
    "a",
    "an",
    "the",
    "and",
    "but",
    "if",
    "or",
    "because",
    "as",
    "until",
    "while",
    "of",
    "at",
    "by",
    "for",
    "with",
    "about",
    "against",
    "between",
    "into",
    "through",
    "during",
    "before",
    "after",
    "above",
    "below",
    "to",
    "from",
    "up",
    "down",
    "in",
    "out",
    "on",
    "off",
    "over",
    "under",
    "again",
    "further",
    "then",
    "once",
    "here",
    "there",
    "when",
    "where",
    "why",
    "how",
    "all",
    "any",
    "both",
    "each",
    "few",
    "more",
    "most",
    "other",
    "some",
    "such",
    "no",
    "nor",
    "not",
    "only",
    "own",
    "same",
    "so",
    "than",
    "too",
    "very",
    "s",
    "t",
    "can",
    "will",
    "just",
    "don",
    "should",
    "now",
]