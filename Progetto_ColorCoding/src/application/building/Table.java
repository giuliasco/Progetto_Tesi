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
    public int coloration;
    private int[] color;

    private int nthreads;
    private AtomicInteger next_vertex = new AtomicInteger(0);;

    public Table(Graph graph, int c, int k, int nthreads , int coloration)
    {
        this.graph = graph;
        this.colors = c;
        this.k = k;
        this.nthreads = nthreads;
        this.coloration = coloration;

        table = new ArrayList<ArrayList<ArrayList<Entry>>>();
        table.add(null);
        for(int i=1; i<=k; i++)
        {
            table.add(new ArrayList<ArrayList<Entry>>(graph.V));
            while( table.get(i).size()< graph.V ) table.get(i).add(new ArrayList<Entry>());
        }

    }

    private void log(String s)
    {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println( "["+timestamp+"] " + s);
    }



    public void build() throws InterruptedException
    {
        do_build1();

        for(int i=2; i<=k; i++)
        {

            final int size = i;
            next_vertex.set(0);

            Timestamp ts = new Timestamp(System.currentTimeMillis());
            log("AVVIO CREAZIONE TABELLA PER H = " + i);

            ArrayList<Thread> threads = new ArrayList<Thread>();
            for(int j = 0; j < nthreads; j++)
            {
                threads.add(new Thread(new Runnable() {
                    @Override
                    public void run() {
                        do_build(size);
                    }
                }));
            }

            for(Thread t : threads)
                t.start();

            for(Thread t : threads)
                t.join();

            log("FINE CREAZIONE TABELLA");

        }
    }

    public void build_balanced() throws InterruptedException
    {
        do_build1();

        int h=(int)Math.ceil((double)2*k/3);
        for(int i=2; i<=h ; i++)
        {

            final int size = i;
            next_vertex.set(0);

            Timestamp ts = new Timestamp(System.currentTimeMillis());
            log("AVVIO CREAZIONE TABELLA PER H = " + i);

            ArrayList<Thread> threads = new ArrayList<Thread>();
            for(int j = 0; j < nthreads; j++)
            {
                threads.add(new Thread(new Runnable() {
                    @Override
                    public void run() {
                        do_build(size);
                    }
                }));
            }

            for(Thread t : threads)
                t.start();

            for(Thread t : threads)
                t.join();

            log("FINE CREAZIONE TABELLA");
        }

        log("AVVIO CREAZIONE TABELLA PER H = " + k);
        next_vertex.set(0);
        ArrayList<Thread> threads1 = new ArrayList<Thread>();
        for (int j = 0 ; j < nthreads ; j++)
        {
            threads1.add(new Thread(new Runnable() {
                @Override
                public void run() {
                    balance_build(h);
                }
            }));
        }
        for(Thread t : threads1)
            t.start();

        for(Thread t : threads1)
            t.join();

        log("FINE CREAZIONE TABELLA");

    }


    private void do_build1()
    {
        color= graph.colorGraph(colors , coloration);


        log("AVVIO CREAZIONE TABELLA PER H = 1");

        for (int v = 0; v < graph.V; v++)
        {
            ArrayList<Entry> l = new ArrayList<Entry>(1);
            l.add(new Entry(Treelet.singleton(color[v]), 1L));
            table.get(1).set(v,l);
        }

        log("FINE CREAZIONE TABELLA");
    }





    private void do_build(int h)
    {

        while(true)
        {
            int u = next_vertex.getAndIncrement();
            if(u >= graph.V)
                break;


            Long2LongMap map = new Long2LongOpenHashMap();
            for( int v : graph.adj.get(u) )
                process_edge(h, u, v, map);

            table.get(h).set(u ,normalize(map));
        }


    }


    private void balance_build(int size)
    {
        while(true)
        {
            int u = next_vertex.getAndIncrement();
            if(u >= graph.V)
                break;

            Long2LongMap map = new Long2LongOpenHashMap();
            for (Entry e1 : table.get(size).get(u))
            {
                for(Entry e2 : table.get(k-size+1).get(u))
                {
                    long t = Treelet.balance_merge(e1.treelet, e2.treelet);
                    if(t>=0)
                        map.put(t, map.getOrDefault(t, 0L) + e1.count * e2.count);
                }
            }

            table.get(k).set(u ,normalize(map));
        }

    }


    private void process_edge(int h, int u, int v, Map<Long,Long> map)
    {
        for (int j=1; j<=h-1; j++)
        {
            for (Entry e1 : table.get(j).get(u))
            {
                for(Entry e2 : table.get(h - j).get(v))
                {
                    long t = Treelet.merge(e1.treelet, e2.treelet,h,k);
                    if(t>=0 & h!=k)
                        map.put(t, map.getOrDefault(t, 0L) + e1.count * e2.count);
                    else if (t>=0 & h==k)
                        map.put(t, map.getOrDefault(t, 0L) + e1.count * e2.count * (int)((t>>4) & 0xFL));
                    else if(t==-2)
                        break;
                }
            }
        }
    }

    private ArrayList<Entry> normalize(Map<Long,Long> map)
    {
        ArrayList<Entry> l = new ArrayList<Entry>(map.size());
        for(Map.Entry<Long, Long> e : map.entrySet())
            l.add(new Entry(e.getKey(), e.getValue()/Treelet.normalization_factor(e.getKey())));

        Collections.sort(l);
        return l;
    }




    public void write_file(String path)
    {
     String fileName = path +"/occorrenze.txt";
     String separator = "  ";
     try
     {
         File file = new File(fileName);
         if (file.exists())
             System.out.println("Il file " + fileName + " esiste");
         else if (file.createNewFile())
             System.out.println("Il file " + fileName + " è stato creato");
         else
             System.out.println("Il file " + fileName + " non può essere creato");

         FileWriter fw = new FileWriter(file);


         Long2LongMap map = new Long2LongOpenHashMap();

         for (int i=0 ; i < graph.V ; i++)
         {
             for (Entry e : table.get(k).get(i))
             {
                 map.put(e.treelet, map.getOrDefault(e.treelet, 0L) + e.count);
             }
         }

         for(Map.Entry<Long,Long> entry : map.entrySet())
         {
             String s1 = Long.toString((entry.getKey()>> 28) & 0xFFFFFFFFL );
             String s2 = Long.toString(entry.getValue());
             fw.append(s1);
             fw.append(separator);
             fw.append(s2);
             fw.append(separator);
             fw.append(System.lineSeparator());
         }
         fw.flush();
         fw.close();

     } catch (IOException e)
     {
         e.printStackTrace();
     }

    }

}




