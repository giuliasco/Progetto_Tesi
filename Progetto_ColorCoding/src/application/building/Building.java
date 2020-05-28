package application.building;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class Building {
    public LinkedList<HashMap<HashMap<Integer , HashSet<Integer>>,Integer>> table = new LinkedList<HashMap<HashMap<Integer,HashSet<Integer>>, Integer>>();
    public LinkedList<LinkedList<Treelet>> vectorTree = new LinkedList<LinkedList<Treelet>>();
    public LinkedList<HashMap<HashMap<Integer , HashSet<Integer>>,Double>> betaTable = new LinkedList<HashMap<HashMap<Integer,HashSet<Integer>>, Double>>();
    public Building(){};

    public void optGraph(Graph graph, int k){
        int h=2;
        int[] color = graph.colorGraph(k);
        //inizializzazione della table con i treelet di un solo nodo
        for(int v=0; v<graph.V ;v++){
            ColorNode node = new ColorNode(v, color[v]);
            Treelet tree = new Treelet(node);
            HashMap<Integer,HashSet<Integer>> treeletMap = new HashMap<Integer, HashSet<Integer>>();
            HashMap< HashMap<Integer,HashSet<Integer>>, Integer> occMap = new HashMap< HashMap<Integer,HashSet<Integer>>, Integer>();
            treeletMap.put(tree.num,tree.color);
            occMap.put(treeletMap,1);
            LinkedList<Treelet> treeList = new LinkedList<Treelet>();
            treeList.add(tree);
            vectorTree.add(treeList);
            table.add(occMap);
        }

        //parte dinamica .... anche se da rivedere bene perchè mi escono i valori sfalzati su alcune cose....
        while(h<=k){
            for (int u=0; u<graph.V; u++){
                LinkedList<Treelet> tmp = new LinkedList<Treelet>();
                for(int i=0; i<graph.adj.get(u).size() ; i++ ){
                    int v = graph.adj.get(u).get(i);
                    for(Treelet x : vectorTree.get(u)){
                        if( x.size<h ){
                            for(Treelet y : vectorTree.get(v)){
                                if( y.size == (h-x.size)){
                                    HashSet<Integer> interColor = new HashSet<Integer>(x.color);
                                    interColor.retainAll(y.color);
                                    if(interColor.isEmpty() ){
                                        if(x.subtree.isEmpty() || x.subtree.getLast()<=y.num){
                                            Treelet z = new Treelet();
                                            z=z.mergeTreelets(x,y);
                                            HashMap<Integer,HashSet<Integer>> map = new HashMap<Integer, HashSet<Integer>>();
                                            HashMap<Integer,HashSet<Integer>> map1 = new HashMap<Integer, HashSet<Integer>>();
                                            HashMap<Integer,HashSet<Integer>> map2 = new HashMap<Integer, HashSet<Integer>>();
                                            map.put(z.num,z.color);
                                            map1.put(x.num,x.color);
                                            map2.put(y.num,y.color);
                                            int  j =0;
                                            if(table.get(u).containsKey(map)){
                                                j = table.get(u).get(map);
                                                j += 1;
                                            }else {
                                                j = 1;
                                            }
                                            table.get(u).put(map,j);
                                            tmp.add(z);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                for(Treelet t : tmp){
                   vectorTree.get(u).add(t);
                }
            }
            h++;
        }

    }

    public void setBetaTable(){
        for (int v=0;v<vectorTree.size() ; v++){
            HashMap<HashMap<Integer,HashSet<Integer>>,Double> betaMap = new HashMap<HashMap<Integer, HashSet<Integer>>, Double>();
            for ( Treelet t : vectorTree.get(v)){
                Double d ;
                HashMap<Integer,HashSet<Integer>> map = new HashMap<Integer, HashSet<Integer>>();
                map.put(t.num,t.color);
                d = Double.valueOf(table.get(v).get(map))/Double.valueOf(t.beta);
                betaMap.put(map,d);
                betaTable.add(betaMap);
            }
        }
    }

}
