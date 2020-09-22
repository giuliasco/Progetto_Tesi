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
    2) la dimensione della struttura dell'albero radicato nell'ultimo figlio della radice di t1,
    deve essere maggiore o uguale a quello dell'albero nel primo figlio di t2.
    3) la dimensione dei nodi in T1-1 deve essere non superiore ai 2/3 dei nodi totali
    4) una volta effettuato il merge controllare che la radice è il centroide(per ora condidero che sia
    unico)
   */

    public static long balance_merge(long t1, long t2)
    {
        int size1 = (int)(t1 & 0xFL);
        int size2 = (int)(t2 & 0xFL);

        long structure1 = (t1>>28) & 0xFFFFFFFFL;
        long structure2 = (t2>>28) & 0xFFFFFFFFL;

        /*
        verifico che la radice sia cenroide per l'albero risultante.
        Ho deciso di tenerli i conteggi delle grandezze dei sottoalberi perchè può risultare utile anche dopo
         */
        int counter = 0;
        int delta_t1[]=new int[nodes_structure(t1).get(0).size()];
        int j=0;

        for(int i=0; i<2*size1 ; i++)
        {
            long mask=1L;
            int structure_bit1 = (int) (structure1 >> (2*size1 - 1 - i) & mask);
            if (structure_bit1 == 1)
            {
                counter ++;
                delta_t1[j] = delta_t1[j] + 1 ;
            }
            else
            {
                counter --;
                if(counter == 0 )
                {
                   if( delta_t1[j] > (size1 + size2 + 1 )/2)
                       return -1;

                   j++;
                }
            }
        }

        int delta_t2[]=new int[nodes_structure(t2).get(0).size()];
        int k=0;

        for(int i=0; i<2*size1 ; i++)
        {
            long mask=1L;
            int structure_bit2 = (int) (structure2 >> (2*size2 - 1 - i) & mask);
            if (structure_bit2 == 1)
            {
                counter ++;
                delta_t2[k] = delta_t2[k] + 1 ;
            }
            else
            {
                counter --;
                if(counter == 0 )
                {
                    if( delta_t2[k] > (size1 + size2 + 1 )/2)
                        return -1;

                    k++;
                }
            }
        }

        //verifico i colori
        if( Long.bitCount(t1 & t2 & 0xFFFF000L)!=1 )
            return -1;

        //verifico che l'insieme A sia più grande di B secondo le regole insimeistiche date e solo allora creo t
        if (size1 <= (size1 + size2 + 1)*2/3 & (size1 + delta_t2[0]) > (size1 + size2 + 1)*2/3 )
        {
            int size_last1 = (int)((t1 >> 4) & 0xF);
            long mask = (1<<(2*size_last1-2))-1;
            long subtree1 = (structure1>>1) & mask;

            int size_first2= delta_t2[0];
            long mask1 = (1<<(2*size_first2-2))-1;
            long subtree2 = (structure2 >> (size2 - size_first2 + 1)) & mask1;

            if(subtree2 > subtree1)
                return -2;

            long structure = structure1<<(2*size2) | structure2;
            long t = (size1 + size2) | t2 & 0XF0 |  ((t1 | t2) & 0xFFFF000L) | structure <<28;
            assert(t<=0x0FFFFFFFFFFFFFFFL);
            return t;
        }

        return -8;

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



      /* private static boolean is_centroid(long t) {
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
       }*/









}

