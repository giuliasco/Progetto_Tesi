package application.building;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class Treelet
{
    //bits 0-3: size of the tree, excluding the root
    //bits 4-7: size of the subtree rooted in the last child
    //bits 8-11: number of copies of the subtree rooted in the last child
    //bits 12-27: color's bitmask
    //bits 28-59: DFS visit of the tree


    public static long singleton(int color)
    {
        return  1L<<(12+color);
    }

    /*
    Merge della versione non ottimizzata.
    Le proprietà che devono avere i due alberi per poter essere uniti sono:
    1)che l'albero radicato nell'ultimo figlio della radice di t1 abbia una struttura maggiore o uguale a quella di t2
    nel caso di uguaglianza allora incremento il valore del fattore di  normalizzazione
    2) l'insieme dei colori di t1 e t2 non devono condividere neanche un colore.

     */
    public static long merge(long t1, long t2)
    {
        long size1 = t1 & 0xFL;
        long size2 = t2 & 0xFL;

        long structure1 = (t1>>28) & 0xFFFFFFFFL;
        long structure2 = (t2>>28) & 0xFFFFFFFFL;

        long ncopies = 1;
        if(size1!=0)
        {
            int size_last1 = (int)((t1 >> 4) & 0xF);
            long mask = (1<<(2*size_last1-2))-1;
            long subtree1 = (structure1>>1) & mask;

            if(structure2 > subtree1)
                return -2;

            if(structure2 == subtree1)
                ncopies = ((t1>>8) & 0xFL)+1;  //beta
        }

        if( (t1 & t2 & 0xFFFF000L)!=0 )
            return -1;

        long structure = (structure1 << (2*size2+2)) | (1<<(2*size2+1)) | (structure2<<1);
        long t = (size1+size2+1) | ((size2+1)<<4) | (ncopies<<8) | ((t1 | t2) & 0xFFFF000L) | (structure<<28);
        assert(t<=0x0FFFFFFFFFFFFFFFL);
        return t;
    }


    /*
    Merge della versione ottimizzata, in questo caso affinchè i due alberi siano uniti devono essere:
    1)in questo caso i due alberi condividono certamente il colore della radice che è la stessa
    (come faccio a rappresentare questa cosa, senza far si che ci sia un problema??)
    2) la dimensione della struttura dell'albero radicato nell'ultimo figlio della radice di t1,
    deve essere maggiore o uguale a quello dell'albero nel primo figlio di t2.
    3) la dimensione dei nodi in T1-1 deve essere non superiore ai 2/3 dei nodi totali????non ho
    capito bene questa cosa
    4) una volta effettuato il merge controllare che la radice è il centroide(per ora condidero che sia
    unico)
     */
    //bits 0-3: size of the tree, excluding the root
    //bits 4-7: size of the subtree rooted in the last child
    //bits 8-11: size of the subtree rooted in the first child
    //bits 12-27: color's bitmask
    //bits 28-59: DFS visit of the tree

    public static long balance_merge(long t1, long t2)
    {
        long size1 = t1 & 0xFL;
        long size2 = t2 & 0xFL;

        long structure1 = (t1>>28) & 0xFFFFFFFFL;
        long structure2 = (t2>>28) & 0xFFFFFFFFL;

        if(size1 > (2 * (size1 + size2 + 1 )) / 3)
            return -1;

        if(size1==0 ) return -1;

        int size_last1 = (int)((t1 >> 4) & 0xF);
        long mask = (1<<(2*size_last1-2))-1;
        long subtree1 = (structure1>>1) & mask;

        if(size2>0)
        {
            int size_first2= (int) ((t2>>8) & 0xF);
            long mask1 = (1<<(2*size_first2-2))-1;
            long subtree2 = (structure2 >> (size2 - size_first2 + 1)) & mask1;

            if (subtree2 > subtree1)
                return -2;

        }

        if( (t1 & t2 & 0xFFFF000L)!=0 )
            return -1;

        long structure = structure1<<(2*size2) | structure2;
        long size_last2 = (t2>>4) & 0xF;
        long size_first1=(t1>>8) & 0xF;

        long t = (size1 + size2) | size_last2 << 4 | size_first1 << 8 |  ((t1 | t2) & 0xFFFF000L) | structure <<28;
        assert(t<=0x0FFFFFFFFFFFFFFFL);

        if(!is_centroid(t)) return -1;

        return t;
    }

    public static int normalization_factor(long t)
    {
        return (int)((t>>8) & 0xFL);
    }



   private static ArrayList<ArrayList<Integer>> nodes_structure(long t)
    {

        int size = (int) (t & 0xFL) ;
        long structure = (t>>28) & 0xFFFFFFFFL;

        ArrayList<ArrayList<Integer>> node_counter = new ArrayList<ArrayList<Integer>>(size+1);
        while(node_counter.size()<size+1) node_counter.add(new ArrayList<Integer>());

        int root[] = new int[size+1];
        int counter = 0;
        int current_value = counter;
        int previous_value = -1;
        root[0]=previous_value;

        for ( int i= 0 ; i < 2*size ; i++)
        {

            long mask=1L;
            int structure_bit = (int) (structure >> (2*size - 1 - i) & mask);

            if (structure_bit ==  1)
            {
                previous_value = current_value;
                counter++;
                current_value=counter;
                root[current_value] = previous_value;
                node_counter.get(previous_value).add(current_value);

            } else
            {
                current_value = previous_value;
                previous_value = root[current_value];
            }
        }

        return node_counter;

        }



       private static boolean is_centroid(long t) {
           ArrayList<ArrayList<Integer>> nodeList = nodes_structure(t);
           int size = (int) (t & 0xFL) ;
           int medium_value = (size + 1 ) / 2;
           int values[] = new int[size];
           int alfa=0;

           if(!nodeList.get(0).isEmpty())
           {
               for (int i = 0; i < size; i++)
               {
                   values[i]=build_alfa(i,nodeList);
               }
           }

           for (int j=0; j<values.length; j++)
           {
               if (j==0 | alfa <values[j]) alfa=values[j];
           }

           if (alfa > medium_value) return false;

           return true;
       }

       private static int build_alfa(int i, ArrayList<ArrayList<Integer>> nodeList)
       {
           int value;

           if (nodeList.get(nodeList.get(0).get(i)).isEmpty()) return value=1;

           value = nodeList.get(nodeList.get(0).get(i)).size();
           for(Integer j : nodeList.get(nodeList.get(0).get(i)))
           {
              value += build_alfa(j, nodeList);
           }
           return value;
       }









}

