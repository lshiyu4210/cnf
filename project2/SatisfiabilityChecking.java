import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class SatisfiabilityChecking {
    public static boolean[] GSAT(KnowledgeBase world, int maxTries, int maxFlip) {
        for (int i = 0; i < maxTries; i++) {
            boolean[] model = randomModel(world.getLiteralNum());
            int curMismatch = countMismatchClause(world, model);
            if (curMismatch == 0) {
                return model;
            }
            for (int j = 0; j < maxFlip; j++) {
                // int[] mismatchReduceCount = new int[world.getLiteralNum()];
                int maxReduce = -1, pos2Change = -1;
                for (int k = 0; k < model.length; k++) {
                    boolean[] changedModel = model.clone();
                    changedModel[k] = !changedModel[k];
                    int changedMismatch = countMismatchClause(world, changedModel);
                    if (changedMismatch == 0) {
                        return changedModel;
                    } else {
                        int mismatchReduced = curMismatch - changedMismatch;
                        if (mismatchReduced > maxReduce) {
                            maxReduce = mismatchReduced;
                            pos2Change = k;
                        }
                    }
                }
                if (pos2Change == -1) {
                    break;
                }
                curMismatch -= maxReduce;
                assert (curMismatch != 0) : "logic error";
                model[pos2Change] = !model[pos2Change];

            }

        }
        System.out.println("no satisfying assignment found");
        return null;
    }

    public static boolean[] WalkSAT(KnowledgeBase world, int maxTries, int maxFlip, double p) {
        for (int i = 0; i < maxTries; i++) {
            boolean[] model = randomModel(world.getLiteralNum());
            int curMismatch = countMismatchClause(world, model);
            if (curMismatch == 0) {
                return model;
            }
            for (int j = 0; j < maxFlip; j++) {
                // int[] mismatchReduceCount = new int[world.getLiteralNum()];
                int maxReduce = -1, pos2Change = -1;
                if (Math.random() <= p) {
                    Clause randomC = world.getKnowledge().get((int) Math.floor(Math.random() * world.getClauseNum()));
                    pos2Change = Math.abs(randomC.getData().get((int) Math.floor(Math.random() * randomC.getLength())))
                            - 1;
                    // boolean[] changedModel = model.clone();
                    model[pos2Change] = !model[pos2Change];
                    int changedMismatch = countMismatchClause(world, model);
                    if (changedMismatch == 0) {
                        return model;
                    } else {
                        curMismatch = changedMismatch;
                    }
                } else {
                    for (int k = 0; k < model.length; k++) {
                        boolean[] changedModel = model.clone();
                        changedModel[k] = !changedModel[k];
                        int changedMismatch = countMismatchClause(world, changedModel);
                        if (changedMismatch == 0) {
                            return changedModel;
                        } else {
                            int mismatchReduced = curMismatch - changedMismatch;
                            if (mismatchReduced > maxReduce) {
                                maxReduce = mismatchReduced;
                                pos2Change = k;
                            }
                        }
                    }

                    if (pos2Change == -1) {
                        break;
                    }
                    curMismatch -= maxReduce;
                    assert (curMismatch != 0) : "logic error";
                    model[pos2Change] = !model[pos2Change];

                }
            }
        }
        System.out.println("no satisfying assignment found");
        return null;
    }

    private static boolean[] randomModel(int length) {
        boolean[] result = new boolean[length];
        for (int i = 0; i < length; i++) {
            if (Math.random() >= 0.5) {
                result[i] = true;
            } else {
                result[i] = false;
            }
        }
        return result;
    }

    private static int countMismatchClause(KnowledgeBase world, boolean[] model) {
        int result = 0;
        for (Clause c : world.getKnowledge()) {
            boolean match = false;
            for (int i : c.getData()) {
                if (model[Math.abs(i) - 1] == (i > 0)) {
                    match = true;
                    break;
                }
            }
            if (!match) {
                result++;
            }
        }
        return result;
    }

    public static KnowledgeBase cnfReader(String filePath) throws FileNotFoundException {
        // String writerPath = args[1];
        File file = new File(filePath);
        Scanner scan = new Scanner(file);
        KnowledgeBase KB;
        int iteration = 0;
        int literalNum = 0;
        int clauseNum = 0;

        while (scan.hasNextLine()) {

            String command = scan.nextLine();
            if (command.charAt(0) == 'c')
                continue;
            else if (command.charAt(0) == 'p') {
                String[] problem = command.split(" ");
                literalNum = Integer.parseInt(problem[2]);
                clauseNum = Integer.parseInt(problem[3]);
                break;
            } else {
                System.out.println("CNF file format wrong!");
                break;
            }
        }

        KB = new KnowledgeBase(clauseNum, literalNum);
        iteration = clauseNum;

        for (int i = 0; i < iteration; i++) {

            ArrayList<Integer> a = new ArrayList<Integer>();
            while (scan.hasNext()) {
                int literal = scan.nextInt();
                if (literal != 0)
                    a.add(literal);
                else {
                    Clause newClause = new Clause(a);
                    KB.append(newClause);
                    break;
                }
            }
        }

        return KB;
    }

    public static void drawChessBoard(boolean[] model, int boardType) {

        switch (boardType) {

            case 4: {
                for (int i = 0; i < 4; i++) {
                    String row = "|";
                    for (int j = 0; j < 4; j++) {
                        if (model[j + i * 4] == true) {
                            row += "X|";
                        } else {
                            row += "_|";
                        }
                    }

                    System.out.println(row);

                }
                System.out.println("Satisfiable. See the truth table below:");
                System.out.println(Arrays.toString(model));
                break;
            }

            case 8: {
                for (int i = 0; i < 8; i++) {
                    String row = "|";
                    for (int j = 0; j < 8; j++) {
                        if (model[j + i * 8] == true) {
                            row += "X|";
                        } else {
                            row += "_|";
                        }
                    }
                    System.out.println(row);

                }
                System.out.println("Satisfiable. See the truth table below:");
                System.out.println(Arrays.toString(model));
                break;
            }

            case 12: {
                for (int i = 0; i < 12; i++) {
                    String row = "|";
                    for (int j = 0; j < 12; j++) {
                        if (model[j + i * 12] == true) {
                            row += "X|";
                        } else {
                            row += "_|";
                        }
                    }
                    System.out.println(row);

                }
                System.out.println("Satisfiable. See the truth table below:");
                System.out.println(Arrays.toString(model));
                break;
            }

        }

    }

    public static void testQ1(int maxTries, int maxFlip) {
        Clause c1 = new Clause(new int[] { 1, 3, -4 }), c2 = new Clause(new int[] { 4 }),
                c3 = new Clause(new int[] { 2, -3 });
        KnowledgeBase world2Test = new KnowledgeBase(3, 4);
        world2Test.append(new Clause[] { c1, c2, c3 });
        System.out.println(Arrays.toString(GSAT(world2Test, maxTries, maxFlip)));
    }

    public static void testQ1() {
        Clause c1 = new Clause(new int[] { 1, 3, -4 }), c2 = new Clause(new int[] { 4 }),
                c3 = new Clause(new int[] { 2, -3 });
        KnowledgeBase world2Test = new KnowledgeBase(3, 4);
        world2Test.append(new Clause[] { c1, c2, c3 });
        System.out.println(Arrays.toString(GSAT(world2Test, 5, 10)));
    }

    public static void testQ2(int n) throws FileNotFoundException {
        int maxTries = 0, maxFlip = 0;
        String filePath = "nqueens_" + n + ".cnf";
        KnowledgeBase world2Test = cnfReader(filePath);
        switch (n) {
            case 4: {
                maxTries = 50;
                maxFlip = 50;
                boolean[] chessBoard = GSAT(world2Test, maxTries, maxFlip);
                drawChessBoard(chessBoard, n);

                break;
            }
            case 8: {
                maxTries = 100;
                maxFlip = 50;
                boolean[] chessBoard = GSAT(world2Test, maxTries, maxFlip);
                drawChessBoard(chessBoard, n);
                break;
            }
            case 12: {
                maxTries = 200;
                maxFlip = 100;
                boolean[] chessBoard = WalkSAT(world2Test, maxTries, maxFlip, 0.175);
                if (chessBoard == null) {
                    System.out.println("Unsatisfiable with small probability. Please try again!");
                }
                // 12 queen has small chance of unsatisfiability
                else {
                    drawChessBoard(chessBoard, n);
                }
                break;
            }
            // case 16: {
            // maxTries = 1000;
            // maxFlip = 1000;
            // }
        }
        // world2Test.printClause();
    }

    public static void testQ2(int n, int maxTries, int maxFlip) throws FileNotFoundException {
        String filePath = "nqueens_" + n + ".cnf";
        KnowledgeBase world2Test = cnfReader(filePath);
        boolean[] chessBoard = GSAT(world2Test, maxTries, maxFlip);
        if (chessBoard == null) {
            System.out.println("Unsatisfiable with small probability. Please try again!");
        }
        // 12 queen has small chance of unsatisfiability
        else {
            drawChessBoard(chessBoard, n);
        }

    }

    public static void testQ2(int n, int maxTries, int maxFlip, double p) throws FileNotFoundException {
        String filePath = "nqueens_" + n + ".cnf";
        KnowledgeBase world2Test = cnfReader(filePath);
        boolean[] chessBoard = WalkSAT(world2Test, maxTries, maxFlip, p);
        if (chessBoard == null) {
            System.out.println("Unsatisfiable with small probability. Please try again!");
        }
        // 12 queen has small chance of unsatisfiability
        else {
            drawChessBoard(chessBoard, n);
        }

    }

    public static void testQ3(String filePath, int maxTries, int maxFlip) throws FileNotFoundException {
        KnowledgeBase world2Test = cnfReader(filePath);
        System.out.println(Arrays.toString(GSAT(world2Test, maxTries, maxFlip)));
    }

    public static void testQ3(String filePath, int maxTries, int maxFlip, double p) throws FileNotFoundException {
        KnowledgeBase world2Test = cnfReader(filePath);
        System.out.println(Arrays.toString(WalkSAT(world2Test, maxTries, maxFlip, p)));
    }

    public static void testQ3(String filePath) throws FileNotFoundException {
        KnowledgeBase world2Test = cnfReader(filePath);
        int maxTries = 0, maxFlip = 0;
        switch (filePath) {
            case "quinn.cnf": {
                maxTries = 50;
                maxFlip = 50;
                System.out.println(Arrays.toString(GSAT(world2Test, maxTries, maxFlip)));
                break;
            }
            case "par8-1-c.cnf": {
                maxTries = 500;
                maxFlip = 500;

                System.out.println(Arrays.toString(WalkSAT(world2Test, maxTries, maxFlip, 0.4)));
                break;
            }

            case "aim-50-1_6-yes1-4.cnf": {
                maxTries = 10000;
                maxFlip = 1000;
                System.out.println(Arrays.toString(WalkSAT(world2Test, maxTries, maxFlip, 0.55)));
                break;
            }

            case "zebra_v155_c1135.cnf": {
                maxTries = 1000;
                maxFlip = 1000;
                System.out.println(Arrays.toString(WalkSAT(world2Test, maxTries, maxFlip, 0.55)));
                break;
            }

        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner s = new Scanner(System.in);

        while (true) {
            System.out.println("Choose problem from {1,2,3}. Type -1 to exit.");
            String testNum = s.next();
            String testWay;

            if (testNum.equals("1")) {
                System.out.println(
                        "Do you want to set MAX-TRIES and MAX-FLIPS yourself? Answer: Y/N. (Suggested Default: 5, 10)");
                testWay = s.next();
                if (testWay.toUpperCase().equals("Y")) {
                    System.out.println("Enter MAX-TRIES.");
                    int maxTries = s.nextInt();
                    System.out.println("Enter MAX-FLIPS.");
                    int maxFlip = s.nextInt();
                    testQ1(maxTries, maxFlip);
                } else if (testWay.toUpperCase().equals("N")) {
                    testQ1();
                } else {
                    System.out.println("Invalid input.");
                }

            } else if (testNum.equals("2")) {
                System.out.println("Enter number of queens from {4,8,12}.");
                int qNum = s.nextInt();
                switch (qNum) {
                    case 4: {
                        System.out.println(
                                "Do you want to set MAX-TRIES and MAX-FLIPS yourself? Answer: Y/N. (Suggested Default: 50, 50)");
                        break;
                    }
                    case 8: {
                        System.out.println(
                                "Do you want to set MAX-TRIES and MAX-FLIPS yourself? Answer: Y/N. (Suggested Default: 100, 50)");
                        break;
                    }
                    case 12: {
                        System.out.println(
                                "Do you want to set MAX-TRIES and MAX-FLIPS yourself? Answer: Y/N. (Suggested Default: 200, 100) \nThis might take some time to run (or even turn out unsatisfiable) if you're very unlucky");
                        break;
                    }
                }
                testWay = s.next();
                if (testWay.toUpperCase().equals("Y")) {
                    System.out.println("Enter MAX-TRIES.");
                    int maxTries = s.nextInt();
                    System.out.println("Enter MAX-FLIPS.");
                    int maxFlip = s.nextInt();
                    switch (qNum) {
                        case 4: {
                            testQ2(qNum, maxTries, maxFlip);
                            break;
                        }
                        case 8: {
                            testQ2(qNum, maxTries, maxFlip);
                            break;
                        }
                        case 12: {
                            testQ2(qNum, maxTries, maxFlip, 0.175);
                            break;
                        }
                    }
                } else if (testWay.toUpperCase().equals("N")) {
                    testQ2(qNum);
                } else {
                    System.out.println("Invalid input. Try again.");
                }

            } else if (testNum.equals("3")) {
                System.out.println("Choose the problem you want to run.\n1. quinn.cnf\n2. par8-1-c.cnf");
                int probNum = s.nextInt();

                if (probNum == 1) {
                    System.out.println(
                            "Do you want to set MAX-TRIES and MAX-FLIPS yourself? Answer: Y/N. (Suggested Default: 50, 50)");
                    testWay = s.next();
                    if (testWay.toUpperCase().equals("Y")) {
                        System.out.println("Enter MAX-TRIES.");
                        int maxTries = s.nextInt();
                        System.out.println("Enter MAX-FLIPS.");
                        int maxFlip = s.nextInt();
                        testQ3("quinn.cnf", maxTries, maxFlip);
                    } else if (testWay.toUpperCase().equals("N")) {
                        testQ3("quinn.cnf");
                    } else {
                        System.out.println("Invalid input. Try again.");
                    }

                } else if (probNum == 2) {
                    System.out.println(
                            "Do you want to set MAX-TRIES and MAX-FLIPS yourself? Answer: Y/N. (Suggested Default: 500, 500)");
                    testWay = s.next();
                    if (testWay.toUpperCase().equals("Y")) {
                        System.out.println("Enter MAX-TRIES.");
                        int maxTries = s.nextInt();
                        System.out.println("Enter MAX-FLIPS.");
                        int maxFlip = s.nextInt();
                        testQ3("par8-1-c.cnf", maxTries, maxFlip, 0.4);
                    } else if (testWay.toUpperCase().equals("N")) {
                        testQ3("par8-1-c.cnf");
                    } else {
                        System.out.println("Invalid input. Try again.");
                    }

                } else {
                    System.out.println("Invalid input. Try again.");
                }

            } else if (testNum.equals("-1")) {
                break;
            } else {
                System.out.println("Invalid input. Try again.");
            }
            System.out.println();
        }
    }
}