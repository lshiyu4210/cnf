import java.util.ArrayList;
import java.util.Scanner;

public class ModelChecking {

    public static boolean TTEntails(KnowledgeBase world, Clause query) {
        int symbols = 1;
        boolean[] model = new boolean[world.getLiteralNum()];
        return TTCheckAll(world, query, symbols, model);
    }

    private static boolean TTCheckAll(KnowledgeBase world, Clause query, int symbols, boolean[] model) {

        if (symbols > world.getLiteralNum()) {
            if (PLTrue(world, model)) {
                return PLTrue(query, model);
            } else {
                return true;
            }
        } else {
            boolean[] newModelT = model.clone(), newModelF = model.clone();
            newModelT[symbols - 1] = true;
            newModelF[symbols - 1] = false;
            return (TTCheckAll(world, query, symbols + 1, newModelT)
                    && TTCheckAll(world, query, symbols + 1, newModelF));
        }
    }

    public static boolean PLTrue(KnowledgeBase world, boolean[] model) {
        for (Clause c : world.getKnowledge()) {
            if (!PLTrue(c, model)) {
                return false;
            }
        }
        return true;
    }

    public static boolean PLTrue(Clause query, boolean[] model) {
        for (int i : query.getData()) {
            if (model[Math.abs(i) - 1] == (i > 0)) {
                return true;
            }
        }
        return false;
    }

    public static void testQ1() {
        ArrayList<Integer> s1 = new ArrayList<Integer>();
        s1.add(1);
        Clause c1 = new Clause(s1);

        // ArrayList<Integer> s2 = new ArrayList<Integer>();
        // s2.add(-1);
        // s2.add(2);
        // Clause c2 = new Clause(s2);
        int[] a = new int[] { -1, 2 };
        Clause c2 = new Clause(a);

        KnowledgeBase kb = new KnowledgeBase(2, 2);
        kb.append(c1);
        kb.append(c2);

        ArrayList<Integer> s3 = new ArrayList<Integer>();
        s3.add(2);
        Clause query = new Clause(s3);
        System.out.println("Clause:");
        kb.printClause();
        System.out.println("KB entails Q is " + TTEntails(kb, query));

    }

    public static void testQ2() {
        // P11 - 1, P12 - 2, P13 - 3
        // P21 - 4, P22 - 5
        // P31 - 6
        // B11 - 7, B12 - 8, B21 - 9
        KnowledgeBase kb = new KnowledgeBase(15, 9);

        // R1
        Clause c1 = new Clause(new int[] { -1 });

        kb.append(c1);

        // R2
        Clause c2 = new Clause(new int[] { -7, 2, 4 }), c3 = new Clause(new int[] { -2, 7 }),
                c4 = new Clause(new int[] { -4, 7 });

        kb.append(new Clause[] { c2, c3, c4 });

        // R3
        Clause c5 = new Clause(new int[] { -8, 1, 5, 6 }), c6 = new Clause(new int[] { -1, 8 }),
                c7 = new Clause(new int[] { -5, 8 }), c8 = new Clause(new int[] { -6, 8 });

        kb.append(new Clause[] { c5, c6, c7, c8 });

        // R7
        Clause c9 = new Clause(new int[] { -9, 1, 5, 3 }), c10 = new Clause(new int[] { -1, 9 }),
                c11 = new Clause(new int[] { -5, 9 }), c12 = new Clause(new int[] { -3, 9 });

        kb.append(new Clause[] { c9, c10, c11, c12 });

        System.out.println("The agent starts at [1, 1]. R1, R2, R3, R7 added to knowledge base.");

        // R4
        Clause c13 = new Clause(new int[] { -7 });
        kb.append(c13);

        // examine
        System.out.println("Examine queries.");
        Clause q1 = new Clause(new int[] { -2 });
        System.out.println("KB entails ~P1,2 is " + TTEntails(kb, q1));

        Clause q2 = new Clause(new int[] { -4 });
        System.out.println("KB entails ~P2,1 is " + TTEntails(kb, q2));

        Clause q3 = new Clause(new int[] { 5 });
        System.out.println("KB entails P2,2 is " + TTEntails(kb, q3));

        Clause q4 = new Clause(new int[] { -5 });
        System.out.println("KB entails ~P2,2 is " + TTEntails(kb, q4));

        System.out.println();

        // R5
        Clause c14 = new Clause(new int[] { 8 });
        kb.append(c14);

        System.out.println("The agent moves to [2, 1]. R5 added.");

        // examine
        System.out.println("Examine queries.");
        Clause q5 = new Clause(new int[] { 5, 6 });
        System.out.println("KB entails P2,2 V P3,1 is " + TTEntails(kb, q5));

        Clause q6 = new Clause(new int[] { 5 });
        System.out.println("KB entails P2,2 is " + TTEntails(kb, q6));

        Clause q7 = new Clause(new int[] { -5 });
        System.out.println("KB entails ~P2,2 is " + TTEntails(kb, q7));

        Clause q8 = new Clause(new int[] { 6 });
        System.out.println("KB entails P3,1 is " + TTEntails(kb, q8));

        Clause q9 = new Clause(new int[] { -6 });
        System.out.println("KB entails ~P3,1 is " + TTEntails(kb, q9));

        System.out.println();

        // R6
        Clause c15 = new Clause(new int[] { -9 });
        kb.append(c15);
        System.out.println("The agent moves to [1, 2]. R6 added.");

        // examine
        System.out.println("Examine queries.");
        Clause q10 = new Clause(new int[] { -5 });
        System.out.println("KB entails ~P2,2 is " + TTEntails(kb, q10));

        Clause q11 = new Clause(new int[] { 6 });
        System.out.println("KB entails P3,1 is " + TTEntails(kb, q11));
    }

    public static void testQ3() {
        System.out.println("testQ3");

        KnowledgeBase kb = new KnowledgeBase(6, 5);

        System.out.println("Literals:");
        System.out.println("1 - mythical	2 - immortal	3 - mammal	4 - horned	5 - magical");
        // R1
        Clause c1 = new Clause(new int[] { -1, 2 });

        // R2
        Clause c2 = new Clause(new int[] { 1, -2 }), c3 = new Clause(new int[] { 1, 3 });

        // R3
        Clause c4 = new Clause(new int[] { -2, 4 }), c5 = new Clause(new int[] { -3, 4 });

        // R4
        Clause c6 = new Clause(new int[] { -4, 5 });

        kb.append(new Clause[] { c1, c2, c3, c4, c5, c6 });

        System.out.println("Clauses:");
        kb.printClause();
        System.out.println();

        // examine
        Clause q1 = new Clause(new int[] { 1 });
        if (TTEntails(kb, q1)) {
            System.out.println("We can prove that the unicorn is mythical.");
        } else {
            System.out.println("We can't prove that the unicorn is mythical.");
        }

        Clause q2 = new Clause(new int[] { 5 });
        if (TTEntails(kb, q2)) {
            System.out.println("We can prove that the unicorn is magical.");
        } else {
            System.out.println("We can't prove that the unicorn is magical.");
        }

        Clause q3 = new Clause(new int[] { 4 });
        if (TTEntails(kb, q3)) {
            System.out.println("We can prove that the unicorn is horned.");
        } else {
            System.out.println("We can't prove that the unicorn is horned.");
        }
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        while (true) {
            System.out.println("Choose problem from {1,2,3}. Type -1 to exit.");
            String testNum = s.next();

            if (testNum.equals("1")) {
                testQ1();
            } else if (testNum.equals("2")) {
                testQ2();
            } else if (testNum.equals("3")) {
                testQ3();
            } else if (testNum.equals("-1")) {
                break;
            } else {
                System.out.println("Invalid input. Type again.");
            }
            System.out.println();
        }
        s.close();
    }
}
