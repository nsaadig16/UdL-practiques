from __future__ import annotations
import argparse
import re
from utils import read_sms, split_train_and_test, print_output ,STOPWORDS
from random import Random

def tokenize_sms(message : str):
    message = message.strip()
    features = []
    if re.search(r"[$€£]",message):
        features.append("TAG:HAS_CURRENCY_SYMBOL")
    if re.search(r'(\+\d{1,3}[\s.-]?)?\(?\d{2,4}\)?[\s.-]?\d{3}[\s.-]?\d{3,4}', message):
        features.append("TAG:HAS_PHONE_NUMBER")
    if re.search(r'https?://|www\.', message):
        features.append("TAG:HAS_URL")
    tokens = re.findall(r"\b\w+\b",message)
    if any(s.isupper() and len(s) > 1 for s in tokens):
        features.append("TAG:HAS_ALL_CAPS")
    tokens = [t.lower() for t in tokens if t.lower() not in STOPWORDS]
    return tokens + features

class MultinomialNaiveBayesClassifier:
    def __init__(self, assumed_probability=1, weight=1, threshold=2):
        self.assumed_probability = assumed_probability
        self.weight = weight
        self.threshold = threshold

    def fit(self, observations, labels):
        spam_docs, ham_docs = 0, 0
        self.spam_f_count, self.ham_f_count = {}, {}

        for observation, label in zip(observations, labels):
            if label == 'spam':
                spam_docs += 1
            else:
                ham_docs += 1
                
            for feature in observation:
                if label == "spam":
                    self.spam_f_count[feature] = (
                        self.spam_f_count.get(feature, 0) + 1
                    )
                else:
                    self.ham_f_count[feature] = self.ham_f_count.get(feature, 0) + 1
        
        self.spam_features = sum(self.spam_f_count.values())
        self.ham_features = sum(self.ham_f_count.values())
        self.prior_prob_spam = spam_docs / (spam_docs + ham_docs)
        self.prior_prob_ham = ham_docs / (spam_docs + ham_docs)

        return self
    
    def predict(self, observations):
        predicted = []
        for doc in observations:
            spam_probability = self.prior_prob_spam * self._doc_given_spam(doc)
            ham_probability = self.prior_prob_ham * self._doc_given_ham(doc)
            if (spam_probability / ham_probability) >= self.threshold:
                predicted.append("spam")
            else:
                predicted.append("ham")
        return predicted

    def _doc_given_spam(self, document):
        prob = 1
        for feature in document:
            prob *= self._weighted_f_given_spam(feature)
        return prob
    
    def _doc_given_ham(self, document):
        prob = 1
        for feature in document:
            prob *= self._weighted_f_given_ham(feature)
        return prob
    
    def _weighted_f_given_spam(self, feature):
        numer = (self.weight * self.assumed_probability) + (self._count(feature) * self._f_given_spam(feature))
        denom = self._count(feature) + self.weight
        return numer / denom
    
    def _f_given_spam(self, feature):
        return self.spam_f_count.get(feature, 0) / self.spam_features

    def _weighted_f_given_ham(self, feature):
        numer = (self.weight * self.assumed_probability) + (self._count(feature) * self._f_given_ham(feature))
        denom = self._count(feature) + self.weight
        return numer / denom
    
    def _f_given_ham(self, feature):
        return self.ham_f_count.get(feature, 0) / self.ham_features
    
    def _count(self, feature):
        return self.spam_f_count.get(feature, 0) + self.ham_f_count.get(feature, 0)

    def score(self, data, labels) -> float:
        predicted = self.predict(data)
        correct = sum(
            1 if pred == expected else 0 for pred, expected in zip(predicted, labels)
        )
        return correct / len(data)



###############################################
#                 CLI Code                    #
###############################################


def main(args):
    # Set the random generator
    rng = Random(args.seed)

    # Load the dataset
    messages, labels = read_sms(args.dataset)

    # Tokenize the messages
    messages = [tokenize_sms(msg) for msg in messages]

    # Split the dataset into training and test sets
    # NOTE: consider args.test_ratio and args.seed
    train_messages, train_labels, test_messages, test_labels = split_train_and_test(
        rng=rng,
        ratio=args.test_ratio,
        observations=messages,
        labels=labels
    )

    # Instantiate the decision tree classifier
    mnb = MultinomialNaiveBayesClassifier(
        assumed_probability=args.assumed_probability,
        weight=args.weight,
        threshold=args.threshold
    )

    # Train the classifier using the training data
    mnb = mnb.fit(train_messages, train_labels)

    # Predict over the test set
    predicted = mnb.predict(test_messages)

    # Evaluate these predictions using the accuracy score and print the information
    score = mnb.score(test_messages, test_labels)

    if args.quiet:
        print(score)
    else:
        print_output(test_labels, predicted, score)
        

def parse_args():
    parser = argparse.ArgumentParser()
    parser.add_argument(
        "dataset", type=str, help="Path to the CSV file containing the dataset."
    )
    parser.add_argument(
        "--assumed_probability",
        type=float,
        default=1,
        help="Value for the 'assumed_probability' parameter.",
    )
    parser.add_argument(
        "--weight",
        type=int,
        default=1,
        help="Value for the 'weight' parameter.",
    )
    parser.add_argument(
        "--test-ratio", type=float, default=0.3, help="Ratio for the test set split."
    )
    parser.add_argument("--seed", type=int, default=123456, help="RNG Seed.")
    parser.add_argument("--threshold", type=float, default=1, help="Threshold for the classification.")
    parser.add_argument("--quiet","-q",action="store_true", help="Only show the score.")
    return parser.parse_args()


if __name__ == "__main__":
    args = parse_args()
    main(args)
