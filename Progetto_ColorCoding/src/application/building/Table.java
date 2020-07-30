package application.building;


import sun.reflect.generics.tree.Tree;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

public class Table {

    public ArrayList<ArrayList<HashMap<Treelet, Integer>>> table;

    public Table() {
    }

    //METODO PER LA CREAZIONE DELLA TABELLA

    public void optGraph(Graph graph, int c, int k) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println("AVVIO METODO optGraph  " + timestamp);
        table = new ArrayList<ArrayList<HashMap<Treelet, Integer>>>(Collections.nCopies(k+1, new ArrayList<HashMap<Treelet, Integer>>()));
        int h = 2;
        int[] color = graph.colorGraph(c);
        ArrayList<HashMap<Treelet, Integer>> vectorTree = new ArrayList<HashMap<Treelet, Integer>>(Collections.nCopies(graph.V, new HashMap<Treelet, Integer>()));
        for (int v = 0; v < graph.V; v++) {
            ColorNode node = new ColorNode(v, color[v]);
            Treelet tree = new Treelet(node);
            HashMap<Treelet, Integer> occ = new HashMap<Treelet, Integer>();
            occ.put(tree, 1);
            vectorTree.set(v, occ);
        }
        table.set(1, vectorTree);
        Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());
        System.out.println("INIZIO DELL'IMPLEMENTAZIONE DINAMINCA  " + timestamp1);
        while (h <= k) {
            Timestamp timestamp2 = new Timestamp(System.currentTimeMillis());
            System.out.println("AVVIO CREAZIONE TABELLA PER H =  " + h +" ALLE " +  timestamp2);
            ArrayList<HashMap<Treelet, Integer>> vector = new ArrayList<HashMap<Treelet, Integer>>(Collections.nCopies(graph.V, new HashMap<Treelet, Integer>()));
            for (int u = 0; u < graph.V; u++) {
                HashMap<Treelet,Integer> map = new HashMap<Treelet,Integer>();
                for (int i = 0; i < graph.adj.get(u).size(); i++) {
                    int v = graph.adj.get(u).get(i);
                    for (int j = 1; j <= h - 1; j++) {
                        if (!table.get(j).get(u).isEmpty()) {
                            for (Treelet t1 : table.get(j).get(u).keySet()) {
                                if (!table.get(h - j).get(v).isEmpty()) {
                                    for (Treelet t2 : table.get(h - j).get(v).keySet()) {
                                        HashSet<Integer> interColor = new HashSet<Integer>(t1.color);
                                        interColor.retainAll(t2.color);
                                        if (interColor.isEmpty()) {
                                            if (t1.subtree.isEmpty() || t1.subtree.getLast() <= t2.num) {
                                                Treelet t3 = new Treelet();
                                                t3 = t3.mergeTreelets(t1, t2);
                                                if (map.containsKey(t3)){
                                                   int occ = map.get(t3);
                                                    occ += table.get(j).get(u).get(t1) * table.get(h-j).get(v).get(t2);
                                                    map.put(t3,occ);
                                                }else {
                                                   int occ = table.get(j).get(u).get(t1) * table.get(h-j).get(v).get(t2);
                                                    map.put(t3,occ);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                vector.set(u,map);
            }
           ArrayList<HashMap<Treelet, Integer>> normVector = new ArrayList<HashMap<Treelet, Integer>>(Collections.nCopies(graph.V, new HashMap<Treelet, Integer>()));
            for (int m=0 ; m<vector.size(); m++){
                HashMap<Treelet,Integer> normMap=new HashMap<Treelet,Integer>();
                for (Treelet t : vector.get(m).keySet()){
                    int norm = vector.get(m).get(t)/ t.beta;
                    normMap.put(t,norm);
                }
                normVector.set(m,normMap);
            }
            table.set(h,normVector);
            Timestamp timestamp3 = new Timestamp(System.currentTimeMillis());
            System.out.println("CONCLUSIONE CREAZIONE TABELLA PER H = " + h + " ALLE " + timestamp3);
            h++;

        }
        Timestamp timestamp4 = new Timestamp(System.currentTimeMillis());
        System.out.println("FINE METODO Opt E CREAZIONE DELLA TABELLA  " + timestamp4);
    }


   //METODO PER SCRITTURA SU FILE

    public void writeToCsvFile(ArrayList<ArrayList<HashMap<Treelet, Integer>>> test){
        String fileName = "/home/giulia/treelet.csv";
        String fileName1 = "/home/giulia/totale.txt";
        String separator=" , ";
        try {
            File file = new File(fileName);
            if (file.exists())
                System.out.println("Il file " + fileName + " esiste");
            else if (file.createNewFile())
                System.out.println("Il file " + fileName + " è stato creato");
            else
                System.out.println("Il file " + fileName + " non può essere creato");

            FileWriter fw = new FileWriter(file);

            fw.append("dimensione");
            fw.append(separator);
            fw.append("Nodo");
            fw.append(separator);
            fw.append("Treelet");
            fw.append(separator);
            fw.append("occorrenze normalizzate");
            fw.append(System.lineSeparator());
            for (int i = 1 ; i < test.size(); i++){
                for (int j = 0 ; j<test.get(i).size() ; j++){
                    for (Treelet t : test.get(i).get(j).keySet()){
                        String s1 = Integer.toString(i);
                        String s2 = Integer.toString(j);
                        String s3 = Integer.toString(t.num);
                        String s4 = Integer.toString(table.get(i).get(j).get(t));
                        fw.append(s1);
                        fw.append(separator);
                        fw.append(s2);
                        fw.append(separator);
                        fw.append(s3);
                        fw.append(separator);
                        fw.append(s4);
                        fw.append(System.lineSeparator());
                    }
                }
            }
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            File file1 = new File(fileName1);
            if (file1.exists())
                System.out.println("Il file " + fileName1 + " esiste");
            else if (file1.createNewFile())
                System.out.println("Il file " + fileName1 + " è stato creato");
            else
                System.out.println("Il file " + fileName1 + " non può essere creato");

            FileWriter fw1 = new FileWriter(file1);

            for (int i = 1 ; i < test.size(); i++){
                int count=0;
                String dim = Integer.toString(i);
                for (int j=0;j<test.get(i).size();j++){
                    count += test.get(i).get(j).size();
                    String countNode= Integer.toString(test.get(i).get(j).size());
                    String nodo = Integer.toString(j);
                    fw1.append("per il nodo " + nodo + " nella dimensione "+dim + " ho un numero di "+countNode + " Treelets");
                    fw1.append(System.lineSeparator());
                }

                String totalTree= Integer.toString(count);
                fw1.append("per la domensione  " + dim + " ho un totale di : " + totalTree + " treelets " );
                fw1.append(System.lineSeparator());

            }

            fw1.flush();
            fw1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*try (FileWriter writer = new FileWriter(fileName)){
            for (String[] strings : thingsToWrite) {
                for (int i = 0; i < strings.length; i++) {
                    writer.append(strings[i]);
                    if(i < (strings.length-1))
                        writer.append(separator);
                }
                writer.append(System.lineSeparator());
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}
