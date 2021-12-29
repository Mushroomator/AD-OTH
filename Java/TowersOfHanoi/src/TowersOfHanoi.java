public class TowersOfHanoi {

    private static int numDisks = 3;
    private static final String msg = "%9d. Step: [%d --{%c}-> %d] \tMove disk %c from %s (%d) to %s (%d).";
    private static final String alphabet = "abcdefghijklmnopqrstuvwxyz";
    private static int steps = 0;

    public static void main(String[] args){
        if(args.length > 1){
            try{
                numDisks = Integer.parseUnsignedInt(args[1]);
                if(numDisks > 26) {
                    // for simplicity: only allow 26 values (all chars in the alphabet)
                    System.err.println("Max. number of disks is 26.");
                    return;
                }
            } catch (NumberFormatException e){
                System.err.println("Argument must be a positive integer.");
                return;
            }
        }
        System.out.println("""
        +------------------------------------
        | \"Towers of Hanoi\" solver
        +------------------------------------
         Number of disks: %d
         Disk labels: %s
         
         Legend:
            
            ( 1 )        ( 2 )          ( 3 )
                
             |a|           |              |
            | b |          |              |
           |  c  |         |              |
         -----------   -----------   -----------  
            
            Start        Utility         End
        
        Please follow the instructions below to solve the problem:
        """.formatted(numDisks, alphabet.substring(0, numDisks)));
        // Solve "Tower of Hanoi"-Problem of given size.
        solve(numDisks);
        return;
    }

    /**
     * Solve "Towers of Hanoi" by giving clear instructions on which moves are to perform
     * to move all disks from the left most tower to the right most of the three towers.
     * Do not grant client access to this method, as wrong parameters (different sizes for towers and/ or size of towers not matching number of disks) could be given.
     * This method is purely to solve the problem using recursion and must not be called by the client.
     * @param numDisks amount of disks on start tower
     * @param startTower start tower
     * @param utilTower utility tower
     * @param endTower end/ target tower where all disks should be stored at the end
     */
    private static void solve(int numDisks, Tower startTower, Tower utilTower, Tower endTower){
        char[] startT = startTower.getDisks();
        char[] endT = endTower.getDisks();

        if(numDisks == 1) {
            endT[0] = startT[0];
            printInstruction(startTower, endTower, endT[0]);
        } else {
            solve(numDisks - 1, startTower, endTower, utilTower);
            endT[numDisks - 1] = startT[numDisks - 1];
            printInstruction(startTower, endTower, endT[numDisks - 1]);
            solve(numDisks -1, utilTower, startTower, endTower);
        }
    }

    private static void printInstruction(Tower from, Tower to, char disk){
        steps++;
        System.out.println(msg.formatted(steps,
                from.getId(),
                disk,
                to.getId(),
                disk,
                from.getName(), from.getId(),
                to.getName(), to.getId() ));
    }

    /**
     * Solve "Towers of Hanoi" by giving clear instructions on which moves are to perform
     * to move all disks from the left most tower to the right most of the three towers.
     * @param numDisks amount of disks on start tower
     */
    public static void solve(int numDisks){
        Tower startTower = new Tower("Start Tower", 1, alphabet.substring(0, numDisks).toCharArray());
        Tower utilTower = new Tower("Utility Tower", 2, new char[numDisks]);
        Tower endTower = new Tower("End Tower", 3, new char[numDisks]);

        solve(numDisks, startTower, utilTower, endTower);
    }
}
