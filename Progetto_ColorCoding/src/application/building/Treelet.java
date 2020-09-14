package application.building;

import java.util.ArrayList;
import java.util.Collections;

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

    public static long merge(long t1, long t2)
    {
        long size1 = t1 & 0xFL;
        long size2 = t2 & 0xFL;

        long structure1 = (t1>>29) & 0xFFFFFFFFL;
        long structure2 = (t2>>29) & 0xFFFFFFFFL;

        long ncopies = 1;
        if(size1!=0)
        {
            int size_last1 = (int)((t1 >> 4) & 0xF);
            long mask = (1<<(2*size_last1-2))-1;            //tramite la maschera, risalgo ai bit che rappresentano
            long subtree1 = (structure1>>1) & mask;         //la posizione nella struttura del sottoalbero piu esterno

            if(structure2 > subtree1)
                return -2;

            if(structure2 == subtree1)
                ncopies = ((t1>>8) & 0xFL)+1;  //beta
        }

        if( (t1 & t2 & 0xFFFF000L)!=0 ) //se i due alberi hanno qualche colore uguale
            return -1;

        long structure = (structure1 << (2*size2+2)) | (1<<(2*size2+1)) | (structure2<<1);
        long t = (size1+size2+1) | ((size2+1)<<4) | (ncopies<<8) | ((t1 | t2) & 0xFFFF000L) | (structure<<29);
        assert(t<=0x0FFFFFFFFFFFFFFFL);
        return t;
    }

    public static int normalization_factor(long t)
    {
        return (int)((t>>8) & 0xFL);
    }


   private ArrayList<ArrayList<Integer>> nodes_structure(long t)
    {

        int size = (int) (t & 0xFL) ;
        long structure = (t>>28) & 0xFFFFFFFF;
        ArrayList<ArrayList<Integer>> node_counter = new ArrayList<ArrayList<Integer>>(Collections.nCopies(size+1, new ArrayList<Integer>()));

        int root[] = new int[size+1];
        int counter = 0;
        int current_value = counter;
        int previous_value = -1;
        for ( int i= 0 ; i <= size ; i++)
        {

            if ((structure >> (2*size - i)) ==  1)
            {
                previous_value = current_value;
                counter++;
                current_value=counter;
                root[current_value] = previous_value;
                node_counter.get(previous_value).add(current_value);

            }

            if(((structure >> (2*size - i)) ==  1) && (previous_value == -1)) break;

            current_value = previous_value;
            previous_value = root[current_value];

        }

        return node_counter;

        }


        /*public int search_centroid(long t)
        {
            ArrayList<ArrayList<Integer>> nodeList = nodes_structure(t);

            return  medium_value;
        }*/
}

