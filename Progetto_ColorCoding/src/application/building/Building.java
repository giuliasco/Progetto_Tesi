package application.building;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class Building {
    public ArrayList<HashMap<Integer, Integer>> table = new ArrayList<HashMap<Integer, Integer>>();
    public ArrayList<ArrayList<Treelet>> vectorTree = new ArrayList<ArrayList<Treelet>>();
    public ArrayList<HashMap<Integer,Double>> betaTable = new ArrayList<HashMap<Integer, Double>>();
    public Building(){};

    public void optGraph(Graph graph, int k) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println("inizio programmazione dinamica " + timestamp);
        int h = 2;
        int[] color = graph.colorGraph(k);
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
                                                occ = 1;
                                            }else {
                                                occ = table.get(u).get(z.hashCode());
                                                occ += 1;
                                            }

                                            table.get(u).put(z.hashCode(), occ);
                                            tmp.add(z);
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
        //Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());
        //System.out.println("fine programmazione dinamica " + timestamp1);
    }
   /* public void setBetaTable(){
        for (int v=0;v<vectorTree.size() ; v++){
            HashMap<HashMap<Integer,HashSet<Integer>>,Double> betaMap = new HashMap<HashMap<Integer, HashSet<Integer>>, Double>();
            for ( Treelet t : vectorTree.get(v)){
                Double d ;
                HashMap<Integer,HashSet<Integer>> map = new HashMap<Integer, HashSet<Integer>>();
                map.put(t.num,t.color);
                d = Double.valueOf(table.get(v).get(map))/Double.valueOf(t.beta);
                betaMap.put(map,d);
               // betaTable.add(betaMap);
            }
        }
    }*/

}
