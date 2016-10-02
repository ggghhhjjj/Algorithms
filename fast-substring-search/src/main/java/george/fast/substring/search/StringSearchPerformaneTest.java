/*
 * Copyright (c) 2016, George Shumakov <george.shumakov@gmail.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following ™disclaimer in the documentation
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
package george.fast.substring.search;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class compares performance of regex replacement vs string split.
 *
 * The test has been expired while developing a servlet filter which has to
 * parse HTTP request header authentication value.
 *
 * As you know the value consists from 2 words: words Basic and words with
 * encoded user name. The question was what is the faster way to obtain user
 * name:
 *
 * <code>
 * VALUE.split(" ")[1];
 * </code> or  <code>
 * VALUE.replaceFirst("[Bb]asic ", "")
 * </code>
 *
 * Test shows the second option is about 100% faster.
 *
 *
 * @author George Shumakov <george.shumakov@gmail.com>
 */
public class StringSearchPerformaneTest {

    private static final int JIT_OPTIMISATIONS = 20000;
    private static final int ITERATIOINS = 10000000;

    private static final String BASIC = "Basic ";
    private static final String bASIC = "basic ";
    private static final Pattern PATTERN = Pattern.compile("[Bb]asic (\\S+)");
    private static final KMPSearch KMPPATTERN = new KMPSearch(BASIC.toUpperCase(), 256);
    private static long aend;
    private static long astart;
    private static long bstart;
    private static long bend;
    private static long cstart;
    private static long cend;
    private static long dend;
    private static long dstart;
    private static long estart;
    private static long eend;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //JIT iteration
        testAMethod(JIT_OPTIMISATIONS);
        testBMethod(JIT_OPTIMISATIONS);
        testCMethod(JIT_OPTIMISATIONS);
        testDMethod(JIT_OPTIMISATIONS);
        testEMethod(JIT_OPTIMISATIONS);

        astart = System.currentTimeMillis();
        testAMethod(ITERATIOINS);
        aend = System.currentTimeMillis();

        bstart = System.currentTimeMillis();
        testBMethod(ITERATIOINS);
        bend = System.currentTimeMillis();
//
        cstart = System.currentTimeMillis();
        testCMethod(ITERATIOINS);
        cend = System.currentTimeMillis();

        dstart = System.currentTimeMillis();
        testDMethod(ITERATIOINS);
        dend = System.currentTimeMillis();

        estart = System.currentTimeMillis();
        testEMethod(ITERATIOINS);
        eend = System.currentTimeMillis();

        long total = eend - astart,
                atime = aend - astart,
                btime = bend - bstart,
                ctime = cend - cstart,
                dtime = dend - dstart,
                etime = eend - estart;
        double at = (double) atime / total * 100,
                ct = (double) ctime / total * 100,
                bt = (double) btime / total * 100,
                dt = (double) dtime / total * 100,
                et = (double) etime / total * 100;

        System.out.println(String.format("Total test duration %s.\n Method A time= %s\n Method B time= %s\n Method C time= %s\n Method D time= %s\n Method E time= %s\n A/total= %f%%, B/total= %f%%, C/total= %f%%, D/total= %f%%, E/total= %f%%", total, atime, btime, ctime, dtime, etime, at, bt, ct, dt, et));
    }

    private static String testAMethod(int iterations) {
        String encodedValue = null;
        for (int i = iterations; i-- > 0;) {
            encodedValue = getRandomString().split(" ")[1];
            preventJITOptimizationMethod(encodedValue);
        }
        return encodedValue;

    }

    private static String testBMethod(int iterations) {
        String encodedValue = null;
        for (int i = iterations; i-- > 0;) {
            encodedValue = getRandomString().replaceFirst("[Bb]asic ", "");
            preventJITOptimizationMethod(encodedValue);
        }
        return encodedValue;
    }

    private static String testCMethod(int iterations) {
        String encodedValue = null;
        for (int i = iterations; i-- > 0;) {
            Matcher matcher = PATTERN.matcher(getRandomString());
            if (matcher.matches()) {
                encodedValue = matcher.group(1);
                preventJITOptimizationMethod(encodedValue);
            } else {
                throw new IllegalStateException("Not correct flow");
            }
        }
        return encodedValue;
    }

    private static String testDMethod(int iterations) {
        String encodedValue = null;
        for (int i = iterations; i-- > 0;) {
            String value = getRandomString();
            if (-1 != value.indexOf(BASIC)) {
                encodedValue = value.substring(6);
                preventJITOptimizationMethod(encodedValue);
            } else if (-1 != value.indexOf(bASIC)) {
                encodedValue = value.substring(6);
                preventJITOptimizationMethod(encodedValue);
            }
        }
        return encodedValue;
    }

    private static String testEMethod(int iterations) {
        String encodedValue = null;
        for (int i = iterations; i-- > 0;) {
            String value = getRandomString();
            int index = KMPPATTERN.indexOfIgnoreCase(value);
            if (-1 != index) {
                encodedValue = value.substring(6);
            }
        }
        return encodedValue;
    }

    private static String getRandomString() {
//        final String rstr = BASIC + UUID.randomUUID().toString();
        return BASIC + "hello";
    }

    private static boolean preventJITOptimizationMethod(String str) {
        return str.charAt(0) == 'a';
    }
}
