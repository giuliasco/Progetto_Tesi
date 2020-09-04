package application.building;

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

            if(structure2 > subtree1) //ma me lo fai al contrario così da essere gia nell'ordine giusto poi nel calcolo dele decomposizioni bilanciate??
                return -2;

            if(structure2 == subtree1)
                ncopies = ((t1>>8) & 0xFL)+1;
        }

        if( (t1 & t2 & 0xFFFF000L)!=0 ) //se i due alberi hanno qualche colore uguale
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
}

