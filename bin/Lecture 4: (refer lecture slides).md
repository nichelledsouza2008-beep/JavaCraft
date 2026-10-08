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