import java.util.*;

public class KnowledgeBase {

    private ArrayList<Clause> knowledge;
    private int clauseNum;
    private int literalNum;

    public KnowledgeBase(int clauseNum, int literalNum) {
        this.knowledge = new ArrayList<Clause>();
        this.clauseNum = clauseNum;
        this.literalNum = literalNum;

    }

    public void append(Clause c) {
        knowledge.add(c);
        assert (knowledge.size() < clauseNum) : "KB overflows";
    }

    public void append(Clause[] list) {
        // knowledge.add(c);
        for (int i = 0; i < list.length; i++) {
            knowledge.add(list[i]);
        }
        assert (knowledge.size() < clauseNum) : "KB overflows";
    }

    public ArrayList<Clause> getKnowledge() {
        return knowledge;
    }

    public int getLiteralNum() {
        return literalNum;
    }

    public int getClauseNum() {
        return clauseNum;
    }

    public void printClause() {

        String cnf = "";
        for (int j = 0; j < knowledge.size(); j++) {
            Iterator<Integer> it = knowledge.get(j).getData().iterator();
            int length = knowledge.get(j).getData().size();
            String clause = "{";
            for (int i = 0; i < length; i++) {
                int next = it.next();
                if (i == length - 1) {
                    clause += next;
                } else {
                    clause += next + ",";
                }
            }
            clause += "}";
            if (j == knowledge.size() - 1) {
                cnf += clause;
            } else {
                cnf += clause + ",";
            }
        }
        System.out.println(cnf);
    }

    // public void append(int[][] inputArray) {
    // for (int[] i : inputArray){
    // knowledge.append(new Clause())
    // }
}
