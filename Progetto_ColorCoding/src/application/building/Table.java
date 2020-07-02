package application.building;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class Table {
    public ArrayList<HashMap<Integer, Integer>> table = new ArrayList<HashMap<Integer, Integer>>();
    public ArrayList<ArrayList<Treelet>> vectorTree = new ArrayList<ArrayList<Treelet>>();
    public ArrayList<HashMap<Integer,Double>> betaTable = new ArrayList<HashMap<Integer, Double>>();
    public Table(){};

    public void optGraph(Graph graph,int c, int k) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println("inizio programmazione dinamica " + timestamp);
        int h = 2;
        int[] color = graph.colorGraph(c);
        //inizializzazione della table con i treelet di un solo nodo
        for (int v = 0; v < graph.V; v++) {
            ColorNode node = new ColorNode(v, color[v]);
            Treelet tree = new Treelet(node);
            HashMap<Integer, Integer> occMap = new HashMap<Integer, Integer>();
            occMap.put(tree.hashCode(), 1);
            ArrayList<Treelet> treeList = new ArrayList<Treelet>();
            treeList.add(tree);
            vectorTree.add(treeList);
            table.add(occMap);
        }

        //parte dinamica ....
        while (h <= k) {
            for (int u = 0; u < graph.V; u++) {
                LinkedList<Treelet> tmp = new LinkedList<Treelet>();
                for (int i = 0; i < graph.adj.get(u).size(); i++) {
                    int v = graph.adj.get(u).get(i);  //in questo modo considero l'arco uv
                    for (Treelet x : vectorTree.get(u)) { //alberi radicati un u
                        if (x.size < h) {
                            for (Treelet y : vectorTree.get(v)) { //alberi  in v
                                if (y.size == (h - x.size)) {
                                    HashSet<Integer> interColor = new HashSet<Integer>(x.color);
                                    interColor.retainAll(y.color);
                                    if (interColor.isEmpty()) { //se l'intersezione tra i due insiemi di colori è uguale a zero
                                        /*
                                        La dimensione dell'albero radicato in v è minore di quello radicato in u,
                                        però per poter effettuare l'unione devo controllare che la forma dell'ultimo figlio di x sia "minore o uguale"
                                        alla forma di y
                                         */
                                        if (x.subtree.isEmpty() || x.subtree.getLast() <= y.num) {
                                            Treelet z = new Treelet();
                                            z = z.mergeTreelets(x, y);

                                                int occ;
                                                if (!table.get(u).containsKey(z.hashCode())) {
                                                    occ = table.get(u).get(x.hashCode()) * table.get(v).get(y.hashCode());
                                                } else {
                                                    occ = table.get(u).get(z.hashCode());
                                                    occ += table.get(u).get(x.hashCode()) * table.get(v).get(y.hashCode());
                                                }
                                                table.get(u).put(z.hashCode(), occ);
                                            if (!tmp.contains(z)) {
                                                tmp.add(z);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    }
                for (Treelet t : tmp) {
                    vectorTree.get(u).add(t);
                }

            }
            h++;
        }
        Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());
        System.out.println("fine programmazione dinamica " + timestamp1);

        Timestamp timestamp2 = new Timestamp(System.currentTimeMillis());
        System.out.println("creazione tabella normalizzata " + timestamp2);

        for(int v=0;v<table.size();v++){
            HashMap <Integer,Double> norm = new HashMap<Integer, Double>();
            for(Integer x : table.get(v).keySet()){
                for(Treelet t : vectorTree.get(v)){
                    if(t.hashCode() == x){
                        Double d= Double.valueOf(table.get(v).get(x))/Double.valueOf(t.beta) ;
                        norm.put(x,d);

                    }
                }
            }
            betaTable.add(norm);
        }

    }


}
