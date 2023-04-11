import java.util.Arrays;

public class TestHashing {

    public static void main(String[] args) {
        runHashingLab07Ex02IW();
    }

    public static void runHashingLecture16Page6() {
        var size = 11;
        var linear = new HashtableLinearProbing(size, key -> key % 11);
        var quadratic = new HashtableQuadraticProbing(size, key -> key, 0, 1);
        var doubleHashing = new HashtableDoubleHashing(size, key -> key % 11, key -> key % 5);

        var inputs = new int[]{49, 22, 6, 52, 76, 33, 34, 13, 29, 11, 83};
        for (var input : inputs) {
            linear.put(input, input, true);
            quadratic.put(input, input, true);
            doubleHashing.put(input, input, true);
        }

        System.out.printf("""
                +---------------------------------------------------------+
                | Hashing with open addressing (Lecture 16, page 6)       |
                +---------------------------------------------------------+
                Inserting: %s
                
                > Linear probing:       %s
                > Quadratic probing:    %s
                > Double hashing:       %s
                """, Arrays.toString(inputs), linear, quadratic, doubleHashing);
    }

    public static void runHashingLab07Ex01IW(){
        var m = 9;
        // actual exercise: hashing with chaining
        var chaining = new HashingWithChaining<Integer, Integer>(9, key -> key);

        // hashing with open adressing is not actually part of the exercise!
        var linear = new HashtableLinearProbing(m, key -> key % 9);
        var quadratic = new HashtableQuadraticProbing(m, key -> key % 9, 1, 3);
        var doubleHashing = new HashtableDoubleHashing(m, key -> key % 9, key -> 1 + (key % (m - 1)));

        var inputs = new int[]{ 5, 28, 19, 15, 20, 33, 12, 17, 10 };
        for (var input : inputs) {
            chaining.put(input, input);
            linear.put(input, input, true);
            quadratic.put(input, input, true);
            doubleHashing.put(input, input, true);
        }

        System.out.printf("""
                
                +---------------------------------------------------------+
                | Hashing with open addressing (Lab 07 [IW], Exercise 01) |
                +---------------------------------------------------------+
                Inserting: %s
                
                > Linear probing:       %s
                > Quadratic probing:    %s
                > Double hashing:       %s
                """, Arrays.toString(inputs), linear, quadratic, doubleHashing);



        System.out.printf("""
                
                +---------------------------------------------------------+
                | Hashing with chaining (Lab 07 [IW], Exercise 01)        |
                +---------------------------------------------------------+
                Inserting: %s
                
                %s
                """, Arrays.toString(inputs), chaining);
    }

    public static void runHashingLab07Ex02IW(){
        var m = 11;
        var linear = new HashtableLinearProbing(m, key -> key);
        var quadratic = new HashtableQuadraticProbing(m, key -> key, 1, 3);
        var doubleHashing = new HashtableDoubleHashing(m, key -> key, key -> 1 + (key % (m-1)));

        var inputs = new int[]{ 10, 22, 31, 4, 15, 28, 17, 88, 59 };
        for (var input : inputs) {
            linear.put(input, input, true);
            quadratic.put(input, input, true);
            doubleHashing.put(input, input, true);
        }

        System.out.printf("""
                +---------------------------------------------------------+
                | Hashing with open addressing (Lab 07 [IW], Exercise 02) |
                +---------------------------------------------------------+
                Inserting: %s
                
                > Linear probing:       %s
                > Quadratic probing:    %s
                > Double hashing:       %s
                """, Arrays.toString(inputs), linear, quadratic, doubleHashing);
    }
}
