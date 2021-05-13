import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;


/**
 * Project SDK is Java 16
 */
public class RegMachineInterpreter {
    // RAM of register machine; NOTE: program storage is separated from data storage in this implementation
    // ram[0] is single register used to store the result of operations
    private final int[] ram;
    // program counter
    private int pc;
    // in memory storage for code to execute
    private final ArrayList<String> code;

    // path to register machine program file
    static final String defaultPath = "/home/tom/Dev/Personal/Studium/AD-SoSe21/Lab02/src/regMachineProg.txt";

    public static void main(String[] args) {
        RegMachineInterpreter interpreter;
        if(args.length != 2){
            System.out.println("No path specified. Default path is used.");
            interpreter = new RegMachineInterpreter();
        }
        else {
            interpreter = new RegMachineInterpreter(args[1]);
        }
        interpreter.run();
    }



    public RegMachineInterpreter(String path){
        super();
        this.code = readCode(path);
        // if end of program is not supplied, add it so program termination is guaranteed when reaching EOF
        if(!this.code.get(this.code.size() - 1).startsWith("HLT")){
            this.code.add("HLT 0");
        }
        this.ram = new int[256];  // 256 * 4 bytes of RAM;
        this.pc = 0;
    }

    public RegMachineInterpreter(){
        this(defaultPath);
    }

    public void run(){
        // no need to check for the pc as it cannot be out of bounds (HLT is always the last command and will terminate the program and JMP commands are checked before executed)
        while(true){
            String line = this.code.get(this.pc);
            this.pc++;
            // parse command
            String[] ops = line.split(" ");
            // execute command if it is not a comment or empty line
            if(!ops[0].startsWith("#") && !ops[0].equals("\n")) {
                int arg = parseOp(ops[1]);
                doOp(ops[0], arg);
            }
        }
    }

    public ArrayList<String> readCode(String path){
        ArrayList<String> result = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(
                    new FileReader(path));
            String s;
            while ( (s = br.readLine()) != null){
                // ignore line comments marked with "#" at the beginning
                result.add(s);
            }
            br.close();
            System.out.println("---------------");

        } catch (FileNotFoundException ex){
            System.out.printf("File %s does not exist.%n", path);
            System.exit(1);
        }
        catch (Exception ex){
            System.out.printf("Failed to read file %s.%n", path);
            System.exit(1);
        }
        return result;
    }

    public int parseOp(String arg){
        int result = 0;
        try {
            result = Integer.parseInt(arg);
        } catch (NumberFormatException ex){
            System.out.printf("Syntax error in line %d. \nCannot parse argument %s as integer.", this.pc, arg);
            System.exit(1);
        }
        return result;
    }

    public void doOp(String cmd, int arg){
        // only command where "arg" is not a memory address
        if(cmd.equalsIgnoreCase("LDK")){
            ldk(arg);
            return;
        }
        // memory protection
        if(arg < 0 || arg > this.ram.length){
            System.out.printf("Fatal error: address %d is no valid memory address.%n", arg);
            System.exit(1);
        }
        int val = this.ram[arg];
        switch (cmd.toUpperCase(Locale.ROOT)) {
            case "JMP" -> jmp(arg);
            case "JEZ" -> jez(arg);
            case "JNE" -> jne(arg);
            case "JLZ" -> jlz(arg);
            case "JLE" -> jle(arg);
            case "JGZ" -> jgz(arg);
            case "JGE" -> jge(arg);
            case "INP" -> inp(arg);
            case "OUT" -> out(arg);
            case "STA" -> sta(arg);
            case "LDA" -> lda(arg);
            case "ADD" -> add(val);
            case "SUB" -> sub(val);
            case "MUL" -> mul(val);
            case "DIV" -> div(val);
            case "HLT" -> hlt(arg);
            default -> {
                System.out.printf("Unknown command %s.%n", cmd);
                System.exit(1);
            }
        }
    }

    /**
     * Subtract x from register value
     * @param x subtrahend
     */
    public void sub(int x){
        this.ram[0] = this.ram[0] - x;
    }

    /**
     * Add x to register value
     * @param x summand
     */
    public void add(int x){
        this.ram[0] = this.ram[0] + x;
    }

    /**
     * Multiply register by x
     * @param x factor
     */
    public void mul(int x){
        this.ram[0] = this.ram[0] * x;
    }

    /**
     * Divide x from register value
     * @param x divisor
     */
    public void div(int x){
        if(x == 0) {
            System.out.println("Fatal error: Cannot divide by 0.");
            System.exit(1);
        }
        this.ram[0] = this.ram[0] / x;
    }

    /**
     * Jump to specified line
     * @param line line number to jump to
     */
    public void jmp(int line){
        checkValidLine(line);
        this.pc = --line;
    }

    /**
     * Jump to line if register value equals zero
     * @param line line to jump to
     */
    public void jez(int line){
        checkValidLine(line);
        if(this.ram[0] == 0){
            this.pc = --line;
        }
    }

    /**
     * Jump to line if register has is not equal to zero
     * @param line line to jump to
     */
    public void jne(int line){
        checkValidLine(line);
        if(this.ram[0] != 0){
            this.pc = --line;
        }
    }

    /**
     * Jump to line if register has value less than zero
     * @param line line to jump to
     */
    public void jlz(int line){
        checkValidLine(line);
        if(this.ram[0] < 0){
            this.pc = --line;
        }
    }

    /**
     * Jump to line if register has value less than or equal to zero
     * @param line line to jump to
     */
    public void jle(int line){
        checkValidLine(line);
        if(this.ram[0] <= 0){
            this.pc = --line;
        }
    }

    /**
     * Jump to line if register has value greater than zero
     * @param line line to jump to
     */
    public void jgz(int line){
        checkValidLine(line);
        if(this.ram[0] > 0){
            this.pc = --line;
        }
    }

    /**
     * Jump to line if register has value greater than or equal to zero
     * @param line line to jump to
     */
    public void jge(int line){
        checkValidLine(line);
        if(this.ram[0] >= 0){
            this.pc = --line;
        }
    }

    private void checkValidLine(int line){
        if(line > this.code.size() || line < 1){
            System.out.printf("Fatal error: Line %d does not exist.%n", line);
            System.exit(1);
        }
    }

    /**
     * Load data from address in register.
     * @param address address which value to load
     */
    public void lda(int address){
        this.ram[0] = this.ram[address];
    }

    /**
     * Load constant in register
     * @param constant constant to load
     */
    public void ldk(int constant){
        this.ram[0] = constant;
    }

    /**
     * Halt program
     */
    public void hlt(int code){
        if(code != 0){
            System.out.println("Register machine program exited with code " + code);
            System.exit(0);
        }
        System.out.println("Result: " + this.ram[0]);
        System.out.println("Register machine program finished with exit code 0.");
        System.exit(0);
    }

    /**
     * Store register value at address
     */
    public void sta(int address){
        this.ram[address] = this.ram[0];
    }

    /**
     * Output value at address
     * @param address address index
     */
    public void out(int address){
        System.out.println(this.ram[address]);
    }

    /**
     * Read input from standard input and store at address. Does not use register (as opposed to STA command) --> register value remains unchanged.
     * @param address address to store input
     */
    public void inp(int address){
        System.out.println("Input: ");
        Scanner sc = new Scanner(System.in);
        int val = 0;
        try {
            val = sc.nextInt();
        } catch (Exception ex){
            System.out.println("Invalid input. Only integers accepted.");
            System.exit(1);
        }
        this.ram[address] = val;
    }
}
