## Solving a cryptarithmetic puzzle
Using a brute force dummy algorithm 

## Using this tool

## The Algorithm
```java
bool ExhaustiveSolve(puzzleT puzzle, string lettersToAssign, int mode)
{
    if (lettersToAssign.empty()) // no more choices to make
        return PuzzleSolved(puzzle); // checks arithmetic to see if works
    for (int digit = 0; digit <= mode; digit++) { // try all digits
        if (AssignLetterToDigit(lettersToAssign[0], digit)) {
            if (ExhaustiveSolve(puzzle, lettersToAssign.substr(1)))
                return true;
            UnassignLetterFromDigit(lettersToAssign[0], digit);
        }
    }
    return false; // nothing worked, need to backtrack
}
```