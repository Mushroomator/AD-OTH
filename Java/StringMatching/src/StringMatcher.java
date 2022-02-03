import java.util.Arrays;
import java.util.HashSet;

/**
 * @implNote Only works for lower case letters. Python implementation can handle Unicode and prints out each and every step nicely, so you may want to choose that.
 */
public class StringMatcher {

    public static void main(String[] args){

        String ss2018SearchStr = "363645636363645632136";
        String ss2018SearchPattern = "3636";
        String exerciseStr = "algorithmen und datenstrukturen";
        String exercisePattern = "daten";
        String searchStr = ss2018SearchStr;
        String pattern = ss2018SearchPattern;

        System.out.println("""
                +-------------------------------
                | String Matcher               |
                +-------------------------------
                Search text: %s
                Pattern: %s
                """.formatted(searchStr, pattern));

        // Naive algorithm
        int naMatches = naiveSearch(searchStr, pattern);
        System.out.println("Naive algorithm: \n\tMatch count: %d".formatted(naMatches));

        // Boyer-Moore algorithm
        int bmMatches = boyerMooreSearch(searchStr, pattern);
        System.out.println("Boyer-Moore algorithm: \n\tMatch count: %d".formatted(bmMatches));
    }

    /**
     * Naive string matching implementation.
     * Runtime:
     *  - Best Case = Average Case: O(n) the first character does not match every time
     *  - Worst Case: O(n * m) Each time all characters match except the last
     * @param text text to be searched
     * @param pattern pattern to be found in text
     * @return number of matches of pattern in text.
     */
    public static int naiveSearch(char[] text, char[] pattern){
        int count = 0;

        for(int i = 0; i < text.length - pattern.length + 1; i++){
            int j = 0;
            boolean match = true;
            while(j < pattern.length){
                if(text[i+j] != pattern[j]) {
                    match = false;
                    break;
                }
                j++;
            }
            if(match) count++;
        }

        return count;
    }

    /**
     * Naive string matching implementation.
     * Runtime:
     *  - Best Case = Average Case: O(n) the first character does not match every time
     *  - Worst Case: O(n * m) Each time all characters match except the last
     * @param text text to be searched
     * @param pattern pattern to be found in text
     * @return number of matches of pattern in text.
     */
    public static int naiveSearch(String text, String pattern){
        return naiveSearch(text.toCharArray(), pattern.toCharArray());
    }

    /**
     * More or less identical implementation from memory.
     * @param text
     * @param pattern
     * @return
     */
    public static int boyerMooreSearchTest(char[] text, char[] pattern){
        int count = 0;
        int[] shift = new int[28];
        Arrays.fill(shift, pattern.length);

        // fill shift vals
        for(int i = 0; i < pattern.length; i++) shift[getIndex(pattern[i])] = pattern.length - 1 - i;

        int textIdx = pattern.length - 1;
        int patternIdx = pattern.length - 1;
        while (textIdx < text.length){
            if(text[textIdx] == pattern[patternIdx]){
                if(patternIdx == 0){
                    // It's a match! (whole pattern found)
                    count++;
                    textIdx += pattern.length;
                    patternIdx = pattern.length - 1;
                }
                else {
                    // This character is the same, check next one
                    textIdx--;
                    patternIdx--;
                }
            } 
            else {
                int shiftVal = shift[getIndex(text[textIdx])];
                int numComparisons = pattern.length - patternIdx;
                // Check if there have already been more comparisons than shift values
                // Shift by the greater to shift by max. positions
                textIdx += Math.max(numComparisons, shiftVal);
                // In any case, start pattern comparison at end of pattern again
                patternIdx = pattern.length - 1;
            }
        }
        return count;
    }



    /**
     * Boyer-Moore string matching implementation.
     * Runtime:
     *  - Best Case = Average Case: O(n/m) the first character does not match every time
     *  - Worst Case: O(n * m) Each time all characters match (except the last)
     * @param text text to be searched
     * @param pattern pattern to be found in text
     * @return number of matches of pattern in text.
     */
    public static int boyerMooreSearch(char[] text, char[] pattern){
        int count = 0;
        int sizeOfAlphabet = 28;
        int[] shift = new int[sizeOfAlphabet];
        // Alternative
        //Arrays.fill(shift, pattern.length);
        for(int i = 0; i < shift.length; i++) shift[i] = pattern.length;
        for(int i = 0; i < pattern.length; i++) shift[getIndex(pattern[i])] = pattern.length - i - 1;

        int i = pattern.length - 1, j = pattern.length - 1;
        while (i < text.length){
            if(text[i] == pattern[j]){
                // Character matched
                if(j == 0){
                    // all characters match -> pattern match found
                    count++; // increase match counter
                    i = i + pattern.length; //
                    j = pattern.length - 1; //
                } else {
                    // a character in the middle of the pattern matched -> check next character
                    i--;
                    j--;
                }
            } else {
                // get shift value from shift table
                int shiftVal = shift[getIndex(text[i])];
                // in case a character has a very small shift value and
                // the mismatch occurred very late (almost at beginning of pattern)
                // shift by the number of already compared values to get the biggest possible shift
                // e.g. pattern="gstring" text="sgtring"
                // comparison would show that "string" is in there and the mismatch would occur when "g" and "s" are compared
                // "g" has shift value of 0 so no shift would be applied -> same comparison would be carried out again -> again shift of 0 -> infinite loop
                // But as the previous 6 characters have matched we can certainly shift by 5 for the next comparison
                if(pattern.length - j > shiftVal){
                    i = i + pattern.length - j;
                } else {
                    i = i + shiftVal;
                }
                // reset pattern index
                j = pattern.length - 1;
            }
        }

        return count;
    }

    public static int boyerMooreSearch(String text, String pattern){
        return boyerMooreSearch(text.toCharArray(), pattern.toCharArray());
    }

    /**
     * Get index of character
     * @param c character
     * @return index
     */
    private static int getIndex(char c){
        if(c == ' ') return 27;
        if(c == ',') return 26;
        return (int)c - (int)'a';
    }
}
