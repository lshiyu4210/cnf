import java.util.ArrayList;

public class Clause {

    private ArrayList<Integer> data;

    public Clause(ArrayList<Integer> data) {
        this.data = data;
    }

    public Clause(int[] list) {
        data = new ArrayList<Integer>();
        for (int i = 0; i < list.length; i++) {
            data.add(list[i]);
        }
    }

    public ArrayList<Integer> getData() {
        return data;

    }

    public int getLength() {
        return data.size();

    }

}
