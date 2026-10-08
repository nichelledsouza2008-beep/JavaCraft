Lecture 4: (refer lecture slides)
Operating system: - resource management
kernel - resource allocator

Interrupts:
-the disk (controller) interrupts the CPU to let it know it has finished the task

Cache - cacheing is the process of moving frequently used info closer to the (CPU)SSD.


Lecture 5: Theory of computationsl thinking
Formal theory of language:


Finite automata: 
automata (pl of automation) are mathematical computing devices. they are clean and straightforward. they process strings in finite time. it can have infintely many strings but not infinitely long strings.
* empty string is denoted by ε
* the double circle at q2 denotes the end of the input state - end state, final state, accepting state
* one speical state - start state, q0
* you can have zero accepting states
* an automaton reads strings built from an alphabet, and the collection of strings it accepts is its language

- after configuring through a automata model with the given values, if it doe not end in the accepting/end state, it is not accepting.
- these machines are always deterministic. ambiguous models can lead to the problem getting stuck.
- F or each state in the DFA, there must be exactly one transition defined for each symbol in Σ, that is it should be directed to only one destination and not more.
- you can complement regular languages (those that are accepted by DFA) by switching the accepting states
- you can take a no input transition - epsilon

Lecture 6:
LAN - does not restrict to a single building but more so geographically (like a campus)
- Redundancy works in favour of computer networks, so if one cuts then you can rely on another.
- Star like structure wont work well for bigger areas since there can be single point failure.
- https/http: protocols which are neccesary for computers to interpret and respond


Standardisation ensures computers can talk to eachother over protocols

//pre exam study sesh notes:
- Computing hardware:
1 bit - 2 patterns
Mathematically: n bits yields 2^n patterns
1 byte has 8 bits and this has 256 patterns (2^n)
One byte can hold a number between 0 and 255
adding 1 to 2147483647 goes to -2147483648, integer overflow
if leftmost bit is 0 - positive and if 1 - negative
ARM CPU uses RISC operation used in phones, gz measures the cpu clock cycle

**Dynamic Random Access Memory (DRAM)** is the primary physical hardware used for a computer's main memory (RAM). It acts as a temporary workspace where data and instructions are held while the CPU executes active programs.

** With the binary convention, 1 GB is a slightly bigger amount (1,024 MB instead of 1,000 MB), so the same “16 GB” holds a bit more.the official names for the binary units are KiB, MiB, GiB (kibibyte, mebibyte, gibibyte), while KB, MB, GB strictly mean the 1,000-based versions. Many operating systems still blur the two, which is why a “16 GB” drive often shows up as around 14.9 “GB” on your computer. The drive maker used 1,000-based units, and the computer displays it using 1,024-based ones.

- Alogrithms:
BFS is essential for solving problems where the shortest distance or fewest steps matter
* Binary Search: An efficient searching algorithm that repeatedly guesses the middle value of an ordered range, cutting the remaining search space in half with every step[2].
* Merge Sort: A divide-and-conquer sorting technique that splits a large, unsorted collection into smaller sub-lists, sorts those sub-lists individually, and merges them back together into a single sorted list[3].
* Breadth-First Search (BFS): A graph traversal method that explores outward layer-by-layer or "in waves," guaranteeing the discovery of the shortest path to a destination[4][6].
* Backtracking: A systematic navigation strategy that explores a path, steps back (undoes the move) upon hitting a dead end, and tries alternative paths until a goal is reached[4].
* Dynamic Programming: A problem-solving strategy that solves complex problems by building answers incrementally from solutions to smaller sub-problems[4].
* Monte Carlo Algorithm: A probabilistic method that relies on repeated random sampling and simulation trials to estimate outcomes or calculate probabilities[4].
* PageRank: A web-ranking algorithm that measures authority across network connections by treating hyper-links as votes, giving greater weight to links originating from trusted sources
