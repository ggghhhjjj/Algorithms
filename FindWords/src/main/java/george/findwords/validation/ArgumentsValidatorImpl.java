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
package george.findwords.validation;

/**
 * Validate command line arguments.
 * 
 * Command line arguments could be as following:
 * <pre>
 * <dl>
 *  <dt>[filename]</dt>
 *  <dd>An optional argument for an input file name. Text will be read from file. If argument is not passed text will be expected from the console to be entered by user.</dd>
 *  <dt>[-o filename] </dt>
 * </dl>
 * </pre>
 * @author George Shumakov <george.shumakov@gmail.com>
 */
public class ArgumentsValidatorImpl implements ArgumentsValidator {

    private final String[] args;
    
    public ArgumentsValidatorImpl(final String[] args) {
        this.args = args;
    }
    
    @Override
    public boolean validate() {
        if (null == args || args.length < 1) {
            throw new ValidationException("No arguments.");
        }
        return false;
    }
    
}
