package application.building;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Vector;

public class BuildClass {
    public int occ = 1;
    public Vector<HashMap<Integer, Integer>> occVector = new Vector<HashMap<Integer, Integer>>();
    public Vector<HashSet<Treelet>> vectorTree = new Vector<HashSet<Treelet>>();
    public LinkedList<LinkedList<Double>> betaOcc = new LinkedList<LinkedList<Double>>();

    public BuildClass(){
        this.occ = 1;
        this.occVector = new Vector<HashMap<Integer, Integer>>();
        this.vectorTree = new Vector<HashSet<Treelet>>();
        this.betaOcc = new LinkedList<LinkedList<Double>>();

    }
    //metodo tutte le hash map con tutti i treelet colorati che possono essere creati a partire da un grafo e le loro occorrenze.

    public void optGraph(Graph g, int k) {
        int color[] = g.colorGraph(k);

        //per ogni nodo creiamo l'albero composto dal solo nodo con occorrenza 1;
        for (int v = 0; v < g.V; v++) {
            ColorNode c = new ColorNode(v, color[v]);
            Treelet t = new Treelet(c);
            HashMap<Integer, Integer> opt = new HashMap<Integer, Integer>();
            HashSet<Treelet> treeSet = new HashSet<Treelet>();
            treeSet.add(t);
            vectorTree.add(treeSet);
            opt.put(t.num, occ);
            occVector.add(opt);
        }

        //parte dinamica per la creazione della tavola hash per gli alberi che trovo nel grafo
        for (int h = 2; h == k; h++) {
            for (int u = 0; u < g.adj.size(); u++) {
                for (int v = 0; v < g.adj.get(u).size(); v++) {
                    for (Treelet t1 : vectorTree.get(u)) {
                        for (Treelet t2 : vectorTree.get(v)) {
                            while (t1.size < h & t2.size <= (h - t1.size)) {
                                Treelet t3 = new Treelet();
                                t3 = t3.mergeTreelets(t1, t2);
                                if (!t3.isEmpty()) {
                                    int occ1 = occVector.get(u).get(t1.num);
                                    int occ2 = occVector.get(v).get(t2.num);
                                    occ = occ1 * occ2;
                                    vectorTree.get(u).add(t3);
                                    occVector.get(u).put(t3.num, occ);
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    //metodo per calcolare il risultato con beta
    public LinkedList<LinkedList<Double>> betaOpt(Graph g, int k) {
        optGraph(g, k);
        for (int v = 0; v < g.adj.size(); v++) {
            for (Treelet tree : vectorTree.get(v)) {
                LinkedList<Double> betaList = new LinkedList<Double>();
                if (tree.size == k) {
                    Double betaOcc = Double.valueOf(occVector.get(v).get(tree.num) / tree.beta);
                    betaList.addLast(betaOcc);
                }
                betaOcc.addLast(betaList);
            }
        }
        return betaOcc;
    }


}
