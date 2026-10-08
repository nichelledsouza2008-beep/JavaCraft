public class DfaChat {
    private static final int kNumStates = 7;  // 7 states: 0 start, 1 after '@', 2 in name, 3 after ':', 4 after ' ', 5 in text, 6 dead
    private static final int kNumSymbols = 5; // 5 symbols: '@' (col 1), name char (col 2), ':' (col 3), ' ' (col 4), text char (col 5)
    private static final int[][] kTransitionTable = {
            { 0, 1, 6, 6, 6, 6 },
            { 1, 6, 2, 6, 6, 6 },
            { 2, 6, 2, 3, 6, 6 },
            { 3, 6, 6, 6, 4, 6 },
            { 4, 6, 6, 6, 6, 5 },
            { 5, 5, 5, 5, 5, 5 },
            { 6, 6, 6, 6, 6, 6 }
    };
    private static final boolean[] kAcceptTable = {
            false, // 0 start
            false, // 1 after '@'
            false, // 2 in name
            false, // 3 after ':'
            false, // 4 after ' '
            true,  // 5 in text
            false  // 6 dead
    };

     private static int symbolOf(char ch, int state) {
        if (state >= 4) return 5;   // after ": " everything counts as text
        if (ch == '@') return 1;
        if (ch == ':') return 3;
        if (ch == ' ') return 4;
        return 2;                   // any other character is a name character
    }

    public static boolean simulateDFA(String input) {
        int state = 0;
        char[] inputArray = input.toCharArray();
        for (int i = 0; i < inputArray.length; i++) {
            char ch = inputArray[i];
            state = kTransitionTable[state][symbolOf(ch, state)];
        }
        return kAcceptTable[state];
    }
}





/*public class DfaChat {
    private static final int kNumStates = 7; // 4 states based on the table
    private static final int kNumSymbols = 5; // 2 symbols (0 and 1) based on the table
    private static final int[][] kTransitionTable = {
            { 0, 1, 6, 6, 6, 6 },
            { 1, 6, 2, 6, 6, 6 },
            { 2, 6, 2, 3, 6, 6 },
            { 3, 6, 6, 6, 4, 6 },
            { 4, 6, 6, 6, 6, 5 },
            { 5, 5, 5, 5 ,5 ,5 },
            { 6, 6, 6, 6, 6, 6 }
    };
    private static final boolean[] kAcceptTable = {
            false,
            false,
            false,
            false,
            true,
            false
    };

    public static boolean simulateDFA(String input) {
        int state = 0;
        char[] inputArray = input.toCharArray();
        for (int i = 0; i < inputArray.length; i++) {
            char ch = inputArray[i];
            if (ch != '@' && ch != 'n' && ch !=':' && ch != 't' ) {
                throw new IllegalArgumentException("Invalid input symbol: " + ch);
            }
            state = kTransitionTable[state][ch - '0'];
        }
        return kAcceptTable[state];
    }

    public static void main(String[] args) {
        String testInput = "@[name]:[text]"; // Example input
        boolean isAccepted = simulateDFA(testInput);
        System.out.println("The input " + testInput + " is " +
                (isAccepted ? "accepted" : "rejected") + " by the DFA.");
    }
}
*/