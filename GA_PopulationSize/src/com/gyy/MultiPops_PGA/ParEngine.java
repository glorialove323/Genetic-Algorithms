/*
 * ParEngine：执行run函数
 * 
 */

package com.gyy.MultiPops_PGA;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.gyy.MultiPops_PGA.SGA.SGASolver;

public class ParEngine {
    public static BestSoFar bestSoFar;

    public static ArrayList<Solvers> solvers;

    public static int iteration;

    public static long fitCalls;

    public static int parRuns;

    public static int nextSolver;

    public static int N0;

    public static int chromLen;

    public ParEngine() {
        parRuns = 1;
        nextSolver = 4;
        N0 = 20; // N0代的个体数目，即初始数目
        chromLen = 20;
    }

    public int RUN(int nRun) throws IOException {
        fitCalls = 0;
        iteration = 0;
        Stopper.setSuccess(false);
        solvers = new ArrayList<Solvers>();
        solvers.add(new SGASolver(N0));
        bestSoFar = new BestSoFar();

        PEA pea = new PEA();
        return pea.run(nRun);
    }

    protected class PEA {
        private int solverPosition;

        private int highestN;

        private int lastSolver;

        private PEA() {
        }

        private int run(int nRun) throws IOException {
            solverPosition = 0;
            highestN = N0;
            lastSolver = 0;
            do {
                Solvers currentSolver = solvers.get(solverPosition);
                if (currentSolver.getDummy()) {
                    if (solverPosition == 0) {
                        deleteSolvers(0);
                        if (solvers.isEmpty())
                            addNextSolver();
                    } else {
                        currentSolver.currentGeneration++;
                       // ParPress.printCurrentSolverInfo(currentSolver, solverPosition);
                        iteration++;
                        solverPosition++;
                    }
                } else {
                    iteration++;
                    boolean stopped = currentSolver.nextGeneration();
                    bestSoFar.updateBest(currentSolver, solverPosition);
                    ParPress.printData();
                    
                   // ParPress.printCurrentSolverInfo(currentSolver, solverPosition);

                    if (Stopper.fitnessCalls()) {
                       // ParPress.printRunFinalStats(currentSolver, highestN);
                        return 1;
                    }
                    if (stopped) {
                        stopSolvers(solverPosition, solverPosition);
                        if (solvers.isEmpty())
                            addNextSolver();
                        continue;
                    }
                    if (solverPosition > 0) {
                        boolean invariant = true;
                        int i = 0;
                        do {
                            Solvers previousSolver = solvers.get(i);
                            if (!previousSolver.getDummy())
                                invariant = previousSolver.getAvgFitness() > currentSolver.getAvgFitness()
                                        || previousSolver.getFitnessCalls() < currentSolver.getFitnessCalls();
                            if (!invariant) {
                                stopSolvers(i, solverPosition - 1);
                                break;
                            }
                            i++;
                        } while (invariant && i < solverPosition);
                    }
                    if (currentSolver.getCurrentGeneration() % nextSolver == 0) {
                        solverPosition++;
                        if (solverPosition > lastSolver)
                            addNextSolver();
                    } else
                        solverPosition = 0;
                }
            } while (true);
        }

        private void stopSolvers(int i, int j) {
            if (i == 0)
                deleteSolvers(j);
            else
                for (int k = i; k <= j; k++)
                    solvers.get(k).setDummy(true);
        }

        private void deleteSolvers(int j) {
            solvers.subList(0, j + 1).clear();
            lastSolver -= (j + 1);
            solverPosition = 0;
        }

        private void addNextSolver() {
            highestN *= 2;
            solvers.add(new SGASolver(highestN));
            lastSolver++;
        }
    }
}
