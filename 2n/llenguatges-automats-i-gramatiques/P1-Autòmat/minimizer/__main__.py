from minimizer.automaton import Automaton
import argparse, sys

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="Minimize a finite deterministic automaton.")
    parser.add_argument("filename", help="The input file to process.")
    parser.add_argument("-o", "--output", help="The output file to save the minimized automaton.")
    parser.add_argument("-s", "--show", action="store_true", help="Draw the input automaton.")
    parser.add_argument("-d", "--draw", action="store_true", help="Draw the minimized automaton.")

    args = parser.parse_args()

    try:
        # Minimize the automaton
        automaton = Automaton(args.filename)
        if not args.filename.endswith('.txt'):
            raise ValueError("Input file must have a .txt extension.")
        if args.show:
            # Draw the input automaton
            automaton.draw("Input Automaton")
        automaton.minimize()
        if args.draw:
            # Draw the automaton
            automaton.draw("Minimized Automaton")
        elif args.output:
            # Save the automaton to a file
            if args.output.endswith('.txt'):
                automaton.save(args.output)
            else:
                raise ValueError("Output file must have a .txt extension.")
        else:
            # Print the result to the terminal
            print(automaton)
    except ValueError as e:
        print(f"\033[31mError:\033[0m {e}")
        sys.exit(1)