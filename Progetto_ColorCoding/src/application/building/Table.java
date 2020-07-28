package application.building;

import com.sun.org.apache.xpath.internal.operations.Bool;
import sun.reflect.generics.tree.Tree;

import java.sql.Timestamp;
import java.util.*;

public class Table {

    public ArrayList<ArrayList<HashMap<Treelet, Integer>>> table;

    public Table() {
    }

    ;

    public void optGraph(Graph graph, int c, int k) {
        table = new ArrayList<ArrayList<HashMap<Treelet, Integer>>>(Collections.nCopies(k+1, new ArrayList<HashMap<Treelet, Integer>>()));
        int h = 2;
        int[] color = graph.colorGraph(c);
        ArrayList<HashMap<Treelet, Integer>> vectorTree = new ArrayList<HashMap<Treelet, Integer>>(Collections.nCopies(graph.V, new HashMap<Treelet, Integer>()));
        for (int v = 0; v < graph.V; v++) {
            ColorNode node = new ColorNode(v, color[v]);
            Treelet tree = new Treelet(node);
            HashMap<Treelet, Integer> occ = new HashMap<Treelet, Integer>();  //da rivedere perchè deve partire dall'uno per la dimensione non dallo zero trovare una soluzione
            occ.put(tree, 1);
            vectorTree.set(v, occ);
        }
        table.set(1, vectorTree);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println("inizio programmazione dinamica " + timestamp);
        while (h <= k) {
            ArrayList<HashMap<Treelet, Integer>> vector = new ArrayList<HashMap<Treelet, Integer>>(Collections.nCopies(graph.V, new HashMap<Treelet, Integer>()));
            //prendo gli archi uv del grafo
            for (int u = 0; u < graph.V; u++) {
                for (int i = 0; i < graph.adj.get(u).size(); i++) {
                    int v = graph.adj.get(u).get(i);
                    for (int j = 1; j == h - 1; j++) {
                        if (!table.get(j).get(u).isEmpty()) {
                            for (Treelet t1 : table.get(j).get(u).keySet()) {
                                if (!table.get(h - j).get(v).isEmpty()) {
                                    for (Treelet t2 : table.get(h - j).get(v).keySet()) {
                                        ArrayList<Integer> interColor = new ArrayList<Integer>(t1.color);
                                        interColor.retainAll(t2.color);
                                        if (interColor.isEmpty()) {
                                            if (t1.subtree.isEmpty() || t1.subtree.getLast() <= t2.num) {
                                                Treelet t3 = new Treelet();
                                                t3 = t3.mergeTreelets(t1, t2);
                                                int occ;
                                                if (vector.get(u).containsKey(t3)){
                                                    occ = vector.get(u).get(t3);
                                                    occ += table.get(j).get(u).get(t1) * table.get(h-j).get(v).get(t2);
                                                }else {
                                                    occ = table.get(j).get(u).get(t1) * table.get(h-j).get(v).get(t2);
                                                }
                                                vector.get(u).put(t3,occ);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
           ArrayList<HashMap<Treelet, Integer>> normVector = new ArrayList<HashMap<Treelet, Integer>>(Collections.nCopies(graph.V, new HashMap<Treelet, Integer>()));
            for (int m=0 ; m<vector.size(); m++){
                for (Treelet t : vector.get(m).keySet()){
                    int norm = vector.get(m).get(t)/ t.beta;
                    normVector.get(m).put(t,norm);
                }
            }
            table.set(h,vector);
            h++;
        }
    }
}
