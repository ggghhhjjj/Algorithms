/* 
 * Copyright (c) 2015, George Shumakov <george.shumakov@gmail.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package george.findwords;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is an example how to count words in a string. The idea is to use a word
 * pattern and to match it until reach the string (stream) ending.
 *
 * @author George Shumakov <george.shumakov@gmail.com>
 */
public class FindWords {

    /**
     * That is a simple definition of a language word. Assumptions is a language
     * word is every sequence of characters, surrounded or separated by any of
     * the folowing cases:
     * <pre>
     * <ol>
     *  <li>begining or ending of a string</li>
     *  <li>white spaces</li>
     *  <li>one of the following symbols <code>,!.?;:()[]{}'</code>
     * </ol>
     * </pre>
     */
    private static final Pattern WORDPTRN = Pattern.compile("[^\\s,!.?;:()\\[\\]{}']+");

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        checkCLIParams(args);
//        final String sentence = "hello   2,I'm here яаоьяаоьяа оеул.Аз съм!{Euстра} [12] По-добре (лошо) %1";
        final String sentence = ",, as'sd'fg   !  ,,";
        System.out.printf("The entered word is '%s'", sentence);
        Matcher m = WORDPTRN.matcher(sentence);
        while (m.find()) {
            System.out.printf("--------\nStrat: %d, End: %d, Word:'%s'\n--------", m.start(), m.end(), m.group());
        }

    }

    private static void checkCLIParams(String[] args) {
        
    }

}
