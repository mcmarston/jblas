// --- BEGIN LICENSE BLOCK ---
/*
 * Copyright (c) 2009, Mikio L. Braun
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *
 *     * Redistributions in binary form must reproduce the above
 *       copyright notice, this list of conditions and the following
 *       disclaimer in the documentation and/or other materials provided
 *       with the distribution.
 *
 *     * Neither the name of the Technische Universität Berlin nor the
 *       names of its contributors may be used to endorse or promote
 *       products derived from this software without specific prior
 *       written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
// --- END LICENSE BLOCK ---

package org.jblas.benchmark;

import org.jblas.DoubleMatrix;
import org.jblas.NativeBlas;
import org.jblas.SimpleBlas;

import static org.jblas.DoubleMatrix.randn;

/**
 *
 */
class NativeDoubleSVDBenchmark implements Benchmark {

    public String getName() {
        return "native svd, double precision";
    }

    public BenchmarkResult run(int size, double seconds) {
        int counter = 0;
        long ops = 0;

        int rows = size * 10;
        DoubleMatrix A = randn(rows, size);
        DoubleMatrix B = randn(size * 10, 1);

        Timer t = new Timer();
        t.start();
        while (!t.ranFor(seconds)) {
            DoubleMatrix a = A.dup();
            DoubleMatrix b = B.dup();
            SimpleBlas.gelsd (A, B);
            counter++;
            ops += 1;
        }
        t.stop();

        return new BenchmarkResult(ops, t.elapsedSeconds(), counter)
        {
            @Override
            void printResult() {
                System.out.printf("%6.3f solutions per second (%d iterations in %.1f seconds)%n",
                        numOps / duration,
                        iterations,
                        duration);
            }
        };
    }
}
