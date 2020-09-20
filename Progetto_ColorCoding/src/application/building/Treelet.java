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



       /*public static int search_centroid(long t)
        {
            ArrayList<ArrayList<Integer>> nodeList = nodes_structure(t) ;
            int size = (int)(t * 0xFL) + 1 ;
            int medium_value = size / 2 ;
            int alfa[] = new int[size];
            int centroid=0 ;

            for (int i=0 ; i< size ; i++)
            {

               alfa[i] = seach_centroid1(nodeList,i);

            }


            return centroid;

        }


        private static int seach_centroid1(ArrayList<Integer[]>)*/

}

