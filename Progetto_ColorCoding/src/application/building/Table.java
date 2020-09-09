package application.building;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import it.unimi.dsi.fastutil.longs.*;

public class Table
{

    private class Entry implements Comparable<Entry>
    {
        private long treelet;
        private long count;

        public Entry(long treelet, long count)
        {
            this.treelet = treelet;
            this.count = count;
        }


        @Override
        public int compareTo(Entry other)
        {
            if(this.treelet>other.treelet)
                return 1;

            if(this.treelet<other.treelet)
                return -1;

            return 0;
        }
    }




    public ArrayList< ArrayList<ArrayList<Entry>> > table; //size, vertex, list of treelets with counts
    private Graph graph;
    private int colors;
    private int k;



    public Table(Graph graph, int c, int k)
    {
        this.graph = graph;
        this.colors = c;
        this.k = k;

        table = new ArrayList<ArrayList<ArrayList<Entry>>>(Collections.nCopies(k+1,new ArrayList<ArrayList<Entry>>()));
        table.add(null);
        for(int i=1; i<=k; i++)
            table.set(i, new ArrayList<ArrayList<Entry>>(Collections.nCopies(graph.V, new ArrayList<Entry>())));
    }

    private void log(String s)
    {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println( "["+timestamp+"] " + s);
    }
    

	

    public void build()
    {
        int threadNumb = threadNumber();
        do_build1();
        for(int i=2; i<=k; i++)
        {
            AtomicInteger counter = new AtomicInteger(0);

            for (int j = 0; j < threadNumb; j++) {
                int finalI = i;
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (counter.getAndIncrement() < graph.V)

                            do_build(finalI, counter, threadNumb);

                    }
                });
                t.start();
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private void do_build1()
    {
        int[] color= graph.colorGraph(colors);


        log("AVVIO CREAZIONE TABELLA PER H = 1");

        for (int v = 0; v < graph.V; v++)
        {
            ArrayList<Entry> l = new ArrayList<Entry>(1);
            l.add(new Entry(Treelet.singleton(color[v]), 1L));
            table.get(1).set(v,l);
        }

        log("FINE CREAZIONE TABELLA");
    }
    

    private int threadNumber()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.print("inserisci il numero di procssori presenti sul tuo pc (inserire valore numerico) :   ");
        int number = Integer.parseInt(scanner.next()) - 1  ;
        return number;
    }


	
    private synchronized void do_build(int h, AtomicInteger vertex, int threadNumb)
    {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        log("AVVIO CREAZIONE TABELLA PER H = " + h);

        int u = vertex.get() - 1;
        Long2LongMap map = new Long2LongOpenHashMap();
        for( int v : graph.adj.get(u) )
            process_edge(h, u, v, map);
        table.get(h).set(u ,normalize(map));

       /*for (int u = 0; u < graph.V; u++)
        {

        }*/
        log("FINE CREAZIONE TABELLA");
    }


	
    private synchronized void process_edge(int h, int u, int v, Map<Long,Long> map)
    {
        for (int j=1; j<=h-1; j++)
        {
            for (Entry e1 : table.get(j).get(u))
            {
                for(Entry e2 : table.get(h - j).get(v))
                {
                    long t = Treelet.merge(e1.treelet, e2.treelet);
                    if(t>=0)
                        map.put(t, map.getOrDefault(t, 0L) + e1.count * e2.count);
                    else if(t==-2)
                        break;
                }
            }
        }
    }

    private synchronized ArrayList<Entry> normalize(Map<Long,Long> map)
    {
        ArrayList<Entry> l = new ArrayList<Entry>(map.size());
        for(Map.Entry<Long, Long> e : map.entrySet())
            l.add(new Entry(e.getKey(), e.getValue()/Treelet.normalization_factor(e.getKey())));

        Collections.sort(l);
        return l;
    }


    public void stampaTable (){
        for(int i=0; i<table.size(); i++)
        {
            for (int j=0; j<table.get(i).size();j++)
            {
                System.out.println(this.table.get(i).get(j).size());
            }
        }

    }



    public void writeToCsvFile()
    {
        Scanner scanner = new Scanner(System.in);

        System.out.print("scrivi il path di dove vuoi vengano salvati i file con i risultati : ");
        String path = scanner.next();
       // String fileName = path + "/treelet.txt";
        String fileName1 = path + "/totale.txt";
        String separator=" , ";
      /*  try {
            File file = new File(fileName);
            if (file.exists())
                System.out.println("Il file " + fileName + " esiste");
            else if (file.createNewFile())
                System.out.println("Il file " + fileName + " è stato creato");
            else
                System.out.println("Il file " + fileName + " non può essere creato");

            FileWriter fw = new FileWriter(file);

            fw.append("DIMENSIONE,NODO,TREELET,OCCORRENZE ");
            fw.append(System.lineSeparator());
            for (int i = 1 ; i < table.size(); i++)
            {
                for (int j = 0 ; j<table.get(i).size() ; j++)
                {
                    for (Entry e : table.get(i).get(j))
                    {
                        String s1 = Integer.toString(i);
                        String s2 = Integer.toString(j);
                        String s3 = Long.toString(e.treelet);
                        String s4 = Long.toString(e.count);
                        fw.append(s1);
                        fw.append(separator);
                        fw.append(s2);
                        fw.append(separator);
                        fw.append(s3);
                        fw.append(separator);
                        fw.append(s4);
                        fw.append(System.lineSeparator());
                    }
                    fw.append(System.lineSeparator());
                }
                fw.append(System.lineSeparator());
            }
            fw.flush();
            fw.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }*/

       try
        {
            File file1 = new File(fileName1);
            if (file1.exists())
                System.out.println("Il file " + fileName1 + " esiste");
            else if (file1.createNewFile())
                System.out.println("Il file " + fileName1 + " è stato creato");
            else
                System.out.println("Il file " + fileName1 + " non può essere creato");

            FileWriter fw1 = new FileWriter(file1);

            for (int i = 1 ; i < table.size(); i++)
            {
                int count=0;
                String dim = Integer.toString(i);
                for (int j=0;j<table.get(i).size();j++)
                {
                    count += table.get(i).get(j).size();
                    String countNode= Integer.toString(table.get(i).get(j).size());
                    String nodo = Integer.toString(j);
                    fw1.append("per il nodo " + nodo + " nella dimensione "+dim + " ho un numero di "+countNode + " Treelets");
                    fw1.append(System.lineSeparator());
                }

                String totalTree= Integer.toString(count);
                fw1.append("per la dimensione  " + dim + " ho un totale di : " + totalTree + " treelets " );
                fw1.append(System.lineSeparator());

            }

            fw1.flush();
            fw1.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }




}





