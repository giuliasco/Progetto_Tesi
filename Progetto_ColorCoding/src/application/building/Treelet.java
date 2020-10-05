package application.building;

import java.util.*;

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
    public static long merge(long t1, long t2, int h,int k)
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
        if ( h == k )
        {
            t=build_centroidTree(t);
        }
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



        int[] delta_t1 = treelet_structure(t1,size1,size2);
        int[] delta_t2 = treelet_structure(t2,size2,size1);

        if (delta_t1.length < nodes_sequence(t1).get(0).size()-1 | delta_t2.length < nodes_sequence(t2).get(0).size()-1)
           return -1;


        if( Long.bitCount(t1 & t2 & 0xFFFF000L)!=1 )
            return -1;


        double ceil = Math.ceil((double)(size1 + size2 + 1) * 2 / 3);
        if (size1 <=(int) ceil & (size1 + delta_t2[0]) > (int) ceil)
        {
            int size_last1 = (int)((t1 >> 4) & 0xF);
            long mask = (1<<(2*size_last1-2))-1;
            long subtree1 = (structure1>>1) & mask;

            int size_first2= delta_t2[0];
            long mask1 = (1<<(2*size_first2-2))-1;
            long subtree2 = (structure2 >> (size2 - size_first2 + 1)) & mask1;

            if(subtree2 > subtree1)
                return -1;

            long structure = structure1<<(2*size2) | structure2;
            long t = (size1 + size2) | t2 & 0XF0 | 1<<8 |  ((t1 | t2) & 0xFFFF000L) | structure <<28;
            assert(t<=0x0FFFFFFFFFFFFFFFL);

            return t;
        }

        return -1;

    }



    public static int normalization_factor(long t)
    {
        return (int)((t>>8) & 0xFL);
    }



    private static int[] treelet_structure(long t, int size_t, int size_other)
    {

        long structure = (t>>28) & 0xFFFFFFFFL;

        int counter = 0;
        int[] delta =new int[nodes_sequence(t).get(0).size()];
        int j=0;

        for(int i=0; i<2*size_t ; i++)
        {
            long mask=1L;
            int structure_bit1 = (int) (structure >> (2*size_t - 1 - i) & mask);
            if (structure_bit1 == 1)
            {
                counter ++;
                delta[j] = delta[j] + 1 ;
            }
            else
            {
                counter --;
                if(counter == 0 )
                {
                    if( delta[j] > (size_t + size_other + 1 )/2)
                       break;
                    j++;
                }
            }
        }

        return delta;
    }


   private static ArrayList<ArrayList<Integer>> nodes_sequence(long t)
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



      private static long build_centroidTree(long t)
      {
          long structure = (t>>28) & 0xFFFFFFFFL;
          int size = (int)(t & 0xF) ;
          int counter = 0;
          int array_size = nodes_sequence(t).get(0).size();

          int centroid = -1;

          ArrayList<Integer> delta = new ArrayList<Integer>();
          ArrayList<String> list_subtree = new ArrayList<String>();
          int j=0;
          delta.add(0);
          list_subtree.add("");
          for(int i=0; i<2*size ; i++)
          {
              long mask=1L;
              int structure_bit = (int) (structure >> (2*size - 1 - i) & mask);
              if (structure_bit == 1)
              {
                  counter ++;
                  delta.set(j,delta.get(j)+1) ;
                  list_subtree.set(j,list_subtree.get(j)+ structure_bit);
              }
              else
              {
                  counter --;
                  list_subtree.set(j,list_subtree.get(j)+ structure_bit);
                  if(counter == 0 )
                  {
                      if( delta.get(j) > (size+1)/2 ) break;
                      if(delta.get(j) == (size+1)/2 ) centroid = j;
                      j++;
                      delta.add(0);
                      list_subtree.add("");
                  }
              }
          }

          if ( delta.size() < array_size + 1 ) return -1; //se non è centroide

          if((size +1)%2 == 0 & centroid>=0)  //se il numero di nodi è pari allora faccio questo controllo alrimenti non necessario per algoritmo di Jordan.
          {
              long t1= t & 0xFFFFFFFFFFFFF000L;


              long l = Long.parseLong(list_subtree.get(centroid),2) >> 1;
              list_subtree.remove(centroid);
              l -= Long.highestOneBit(l);
              String s = Long.toBinaryString(l) ;

              if(centroid == 0 )
              {
                  s = s + "1" ;

                  for (String s1 : list_subtree)
                      s= s + s1 ;

                  s = s + "0";
              }

              else
                  {
                      s = "0" + s;
                      String s2 = "";

                      for (String s1 : list_subtree)
                          s2 += s1;

                      s = "1" + s2 + s ;
                  }

              long t2 = Long.parseLong(s,2)<<28|t1 & 0xFFFFFFFL;

              if ((int) (t1>>28) == (int)(t2>>28))
              {

                 return t1 = t1 & 0xFFFFFFFFFFFFF0FL | 2<<4 ;
              }

              if ( (int) (t1>>28) > (int)(t2>>28)) return -1;

          }

          return t = t & 0xFFFFFFFFFFFFF0FL | 1<<4 ;
       }



}

