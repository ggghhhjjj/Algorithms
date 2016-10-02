/**
 * ****************************************************************************
 *  Original source code from http://algs4.cs.princeton.edu/53substring/KMP.java.html
 *  Compilation:  javac KMP.java
 *  Execution:    java KMP pattern text
 *  Dependencies: StdOut.java
 *
 *  Reads in two strings, the pattern and the input text, and
 *  searches for the pattern in the input text using the
 *  KMP algorithm.
 *
 *  % java KMP abracadabra abacadabrabracabracadabrabrabracad
 *  text:    abacadabrabracabracadabrabrabracad
 *  pattern:               abracadabra
 *
 *  % java KMP rab abacadabrabracabracadabrabrabracad
 *  text:    abacadabrabracabracadabrabrabracad
 *  pattern:         rab
 *
 *  % java KMP bcara abacadabrabracabracadabrabrabracad
 *  text:    abacadabrabracabracadabrabrabracad
 *  pattern:                                   bcara
 *
 *  % java KMP rabrabracad abacadabrabracabracadabrabrabracad
 *  text:    abacadabrabracabracadabrabrabracad
 *  pattern:                        rabrabracad
 *
 *  % java KMP abacad abacadabrabracabracadabrabrabracad
 *  text:    abacadabrabracabracadabrabrabracad
 *  pattern: abacad
 *
 *****************************************************************************
 */
package george.fast.substring.search;

/**
 * The {@code KMP} class finds the first occurrence of a pattern string in a
 * text string.
 * <p>
 This implementation uses a version of the Knuth-Morris-Pratt substring indexOf
 algorithm. The version takes time as space proportional to
 <em>N</em> + <em>M R</em> in the worst case, where <em>N</em> is the length
 * of the text string, <em>M</em> is the length of the pattern, and <em>R</em>
 * is the alphabet size.
 * <p>
 * For additional documentation, see
 * <a href="http://algs4.cs.princeton.edu/53substring">Section 5.3</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 */
public class KMPSearch {

    private final int R;       // the radix
    private final int[][] dfa;       // the KMP automoton

    private char[] pattern;    // either the character array for the pattern
    private String pat;        // or the pattern string

    /**
     * Preprocesses the pattern string.
     *
     * @param pat the pattern string
     * @param R the alphabet size
     */
    public KMPSearch(String pat, int R) {
        this.R = R;
        this.pat = pat;

        // build DFA from pattern
        int m = pat.length();
        dfa = new int[R][m];
        dfa[pat.charAt(0)][0] = 1;
        for (int x = 0, j = 1; j < m; j++) {
            for (int c = 0; c < R; c++) {
                dfa[c][j] = dfa[c][x];     // Copy mismatch cases. 
            }
            dfa[pat.charAt(j)][j] = j + 1;   // Set match case. 
            x = dfa[pat.charAt(j)][x];     // Update restart state. 
        }
    }

    /**
     * Returns the index of the first occurrence of the pattern string in the
     * text string.
     *
     * @param txt the text string
     * @return the index of the first occurrence of the pattern string in the
     * text string; -1 if no such match
     */
    public int indexOf(String txt) {

        // simulate operation of DFA on text
        int m = pat.length();
        int n = txt.length();
        int i, j;
        for (i = 0, j = 0; i < n && j < m; i++) {
            j = dfa[txt.charAt(i)][j];
        }
        if (j == m) {
            return i - m;    // found
        }
        return -1;                    // not found
    }

    /**
     * Returns the index of the first occurrence of the pattern string in the
     * text string.
     * IMPORTANT! Function is not applicable for Java Supplementary Characters.
     * @see Character
     * 
     * @param txt the text string
     * @return the index of the first occurrence of the pattern string in the
     * text string; -1 if no such match
     */
    public int indexOfIgnoreCase(String txt) {
        // simulate operation of DFA on text
        int m = pat.length();
        int n = txt.length();
        int i, j;
        for (i = 0, j = 0; i < n && j < m; i++) {
            j = dfa[Character.toUpperCase(txt.charAt(i))][j];
        }
        if (j == m) {
            return i - m;    // found
        }
        return -1;                    // not found
    }

    /**
     * Returns the index of the first occurrrence of the pattern string in the
     * text string.
     *
     * @param text the text string
     * @return the index of the first occurrence of the pattern string in the
     * text string; N if no such match
     */
    public int search(char[] text) {

        // simulate operation of DFA on text
        int m = pattern.length;
        int n = text.length;
        int i, j;
        for (i = 0, j = 0; i < n && j < m; i++) {
            j = dfa[text[i]][j];
        }
        if (j == m) {
            return i - m;    // found
        }
        return n;                    // not found
    }

    /**
     * Takes a pattern string and an input string as command-line arguments;
     * searches for the pattern string in the text string; and prints the first
     * occurrence of the pattern string in the text string.
     *
     * Search assumes pattern and input strings use ASCI alphabet of 256
     * symbols.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        String pat = args[0];
        String txt = args[1];

        KMPSearch kmp1 = new KMPSearch(pat, 256);
        System.out.println(kmp1.indexOf(txt));
    }

}
