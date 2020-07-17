package application.building;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.*;

public class Table {

    public ArrayList<ArrayList<HashMap<Treelet, Integer>>> table = new ArrayList<ArrayList<HashMap<Treelet, Integer>>>();
    public Table(){};
    public void optGraph (Graph graph, int c, int k){
        int h=2;
        int[] color = graph.colorGraph(c);
        for (int v=0; v<graph.V; v++){
            ColorNode node = new ColorNode(v,color[v]);
            Treelet tree = new Treelet(node);
            HashMap<Treelet,Integer> occ = new HashMap<Treelet,Integer>();  //da rivedere perchè deve partire dall'uno per la dimensione non dallo zero trovare una soluzione
            occ.put(tree,1);
            ArrayList<HashMap<Treelet,Integer>> vectorTree = new ArrayList<HashMap<Treelet, Integer>>();
            vectorTree.add(null);
            vectorTree.add(occ);
            table.add(vectorTree);
        }

        Boolean flag = true;

        while (h<=k){
            //prendo l'arco uv
            for (int u = 0; u < graph.V; u++) {
                for (int i = 0; i < graph.adj.get(u).size(); i++) {
                    int v = graph.adj.get(u).get(i);
                    for (int j = 1; j < h; j++) {
                            for (Treelet t1 : table.get(u).get(j).keySet()) {
                                    for (Treelet t2 : table.get(v).get(h - j).keySet()) {
                                        ArrayList<Integer> interColor = new ArrayList<Integer>(t1.color);
                                        interColor.retainAll(t2.color);
                                        if (interColor.isEmpty()) {
                                            if (t1.subtree.isEmpty() || t1.subtree.getLast() <= t2.num) {
                                                Treelet t3 = new Treelet();
                                                t3 = t3.mergeTreelets(t1, t2);
                                                int H;
                                                /*
                                                 int occ;
                                                if (!table.get(u).containsKey(z.hashCode())) {
                                                    occ = table.get(u).get(x.hashCode()) * table.get(v).get(y.hashCode());
                                                } else {
                                                    occ = table.get(u).get(z.hashCode());
                                                    occ += table.get(u).get(x.hashCode()) * table.get(v).get(y.hashCode());
                                                }

                                                H += table.get(u).get(j).get(t1) * table.get(v).get(h - j).get(t2);
                                                HashMap<Treelet, Integer> map = new HashMap<Treelet, Integer>();
                                                map.put(t3, H);*/
                                                if (flag) {
                                                    H = table.get(u).get(j).get(t1) * table.get(v).get(h - j).get(t2);
                                                    HashMap<Treelet, Integer> map = new HashMap<Treelet, Integer>();
                                                    map.put(t3, H);
                                                    table.get(u).add(map);
                                                    flag = false;
                                                } else {
                                                    if (table.get(u).get(h).containsKey(t3)) {  //DA RIVEDERE
                                                        H = table.get(u).get(h).get(t3);
                                                        H += table.get(u).get(j).get(t1) * table.get(v).get(h - j).get(t2);
                                                        table.get(u).get(h).put(t3, H);
                                                    }

                                                }
                                            }
                                        }
                                    }

                            }
                       /*for (Treelet tree : table.get(u).get(h).keySet()){
                           int norm = table.get(u).get(h).get(tree) / tree.beta;
                           table.get(u).get(h).put(tree,norm);
                       }*/

                    }
                }
                flag=true;
            }
            h++;
        }
    }
    /*public ArrayList<HashMap<Integer, Integer>> table = new ArrayList<HashMap<Integer, Integer>>();
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
                                      /*  if (x.subtree.isEmpty() || x.subtree.getLast() <= y.num) {
                                            Treelet z = new Treelet();
                                            z = z.mergeTreelets(x, y);


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

    }*/


}
/*
DA FARE
Mi occorre vedere bene come non salvare 70000 volte gli stessi alberi
e un modo per stamparli in maniera efficiente

 */