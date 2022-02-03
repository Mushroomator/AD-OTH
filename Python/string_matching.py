import sys


def init():

    ss2018_search_str = "363645636363645632136";
    ss2018_search_pattern = "3636";
    exercise_str = "algorithmen und datenstrukturen";
    exercise_pattern = "daten";
    special_case_trigger_str = "sgtring - now some irrelevant text to show how special case shifts"
    special_case_trigger_pattern = "gstring"
    search_str = special_case_trigger_str
    pattern = special_case_trigger_pattern

    print(f"""
+-------------------------------
| String Matcher               |
+-------------------------------
Search text: {search_str}
Pattern: {pattern}
    """)

    # Naive algorithm
    na_no_matches = naive_search(search_str, pattern)
    print(f"Naive algorithm: \n\tMatch count: {na_no_matches}")

    # Boyer-Moore algorithm
    bm_no_matches = boyer_moore_search(search_str, pattern)
    print(f"Boyer-Moore algorithm: \n\tMatch count: {bm_no_matches}")


def naive_search(text, pattern):
    """
    Naive string matching implementation.
    Runtime:
     - Best Case = Average Case: O(n) the first character does not match every time
     - Worst Case: O(n * m) Each time all characters match except the last

    :param text: text to be searched
    :type text: str
    :param pattern: pattern to be found in text
    :type pattern: str
    :return number of matches of pattern in text.
    """
    match_count = 0
    for i in range(len(text) - len(pattern) + 1):
        match = True
        j = 0
        for j in range(len(pattern)):
            if text[i + j] != pattern[j]:
                match = False
                break
        if match:
            match_count += 1
    return match_count


def boyer_moore_search(text, pattern):
    """
    Boyer-Moore string matching implementation.
    Runtime:
     - Best Case = Average Case: O(n/m) the first character does not match every time
     - Worst Case: O(n * m) Each time all characters match (except the last)

    :param text: text to be searched
    :type text: str
    :param pattern: pattern to be found in text
    :type pattern: str
    :return number of matches of pattern in text.
    """

    # build shift table for unicode text
    size_of_alphabet = sys.maxunicode
    pattern_length = len(pattern)
    shift = [pattern_length] * size_of_alphabet
    for i in range(len(pattern)):
        shift[ord(pattern[i])] = pattern_length - i - 1


    match_count = 0
    # define text and pattern index (start with last character of pattern)
    t_idx, p_idx = pattern_length - 1, pattern_length - 1
    print()
    print(text)
    while t_idx < len(text):
        print(" " * (t_idx - p_idx), end="")
        print(pattern)
        print(" " * t_idx, end="")
        print("-")
        if text[t_idx] == pattern[p_idx]:
            # print("Character matched!")
            # characters match
            if p_idx == 0:
                # print("Match found!")
                # all characters of pattern have matched -> occurrence found!
                # reset index so a new match can be found
                # i.e. p_idx to last character of pattern and
                #      t_idx to first character in text after found match
                p_idx = pattern_length - 1
                t_idx += pattern_length
                match_count += 1
            else:
                # compare next character
                p_idx -= 1
                t_idx -= 1
        else:
            # characters do not match
            shift_value = shift[ord(text[t_idx])]
            if pattern_length - p_idx > shift_value:
                # number of already compared characters before mismatch is greater than shift value
                # e.g. with text="sgtring" and pattern="gstring"
                t_idx += pattern_length - p_idx
                print(f"Special case triggered: shift value would be {shift_value} but already {pattern_length - p_idx} chars compared! Shift by 6!")
            else:
                # shift by shift value for given character
                t_idx += shift_value
            p_idx = pattern_length - 1
    return match_count


if __name__ == "__main__":
    init()
