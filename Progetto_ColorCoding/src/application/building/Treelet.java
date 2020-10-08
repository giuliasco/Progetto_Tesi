package application.building;

import java.math.BigInteger;
import java.util.*;

public class Treelet
{
    //bits 0-3: size of the tree, excluding the root
    //bits 4-7: size of the subtree rooted in the last child
    //bits 8-11: number of copies of the subtree rooted in the last child
    //bits 12-27: color's bitmask
    //bits 28-57: DFS visit of the tree


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
        int size1 = (int)(t1 & 0xFL);
        int size2 = (int)(t2 & 0xFL);

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
        //vincolo sui colori, possono avere solo un colore in comune ossia il nodo della radice
        if( Long.bitCount(t1 & t2 & 0xFFFF000L)!=1 )
            return -1;


        int size1 = (int)(t1 & 0xFL); //Size of t1 and t2 except for their roots
        int size2 = (int)(t2 & 0xFL);
        int total_size = size1 + size2; // Size of the resulting tree except for its root

        long structure1 = (t1>>28) & 0xFFFFFFFFL;
        long structure2 = (t2>>28) & 0xFFFFFFFFL;



        /*
           verifico che preso il primo sottoalbero radicato nella radice di t2 unita a t1 la
           loro suddivisione rispetta il fattore di bilanciamento definito in caso negativo non procedo all'unione, mentre
           in caso positivo procedo nelle verifiche di accettabilità dell'unione bilanciata.
           */
        StructureInfo info2 = new StructureInfo(structure2);
        assert(info2.children.get(0).size()!=0);
        int z = info2.children.get(0).get(0); //Sottoalbero di t2 radicato nell'ultimo figlio della radice di t2
        int len_z = info2.structure_start[z] - info2.structure_end[z] + 1;
        int size_z = len_z/2 + 1;

        if ( 3*size1 > 2*total_size    ||   3*(size1 + size_z) <= 2 * total_size  )
            return -1;


        StructureInfo info1 = new StructureInfo(structure1);
        int v = info1.children.get(0).get(info1.children.get(0).size() - 1 ); //Ultimo figlio della radice di t1
        int len_v = info1.structure_start[v] - info1.structure_end[v] + 1;
        long subtree = (structure1 >> info1.structure_end[v]) & ( (1L<<len_v) - 1 ); //Sottoalbero di t1 radicato in v


        long subtree_z = (structure2 >> info2.structure_end[z]) & ( (1L<<len_z) - 1 );
        if(subtree_z > subtree)
            return -1;


        //calcolo il nuovo valore di bilanciamento per l'albero bilanciato che ci occorerà successivamente per i conteggi in fase di normalizzazione
        int copies_of_subtree_in_t2 = 0;
        for( Integer x : info2.children.get(0))
        {
            int l = info2.structure_start[x] - info2.structure_end[x] + 1;
            long subtree2 = (structure2 >> info2.structure_end[x]) & ( (1L<<l) - 1 );

            if(subtree != subtree2)
                break; //non appena trovo un sottoalbero non uguale a subtree esco dal ciclo

            copies_of_subtree_in_t2++;
        }

        long structure = structure1<<(2*size2) | structure2;
        

        //controllo se la struttura ottenuta ha come radice il centroide dell'albero
        StructureInfo info = new StructureInfo(structure);
        int[] c = centroids(info);
        if(c[0]!=0) //se la radice di t1 e t2 non è un centroide dell'albero risultante dal merge
            return -1; 

        long equal_rooted_tree = 1;
        //se l'albero ha più di un centroide
        if(c[1]!= -1)
        {
            long rerooted = reroot(structure, c[1], info);

            if(structure > rerooted)
                return -1;
            
            if(rerooted == structure ) 
                equal_rooted_tree=2;
        }

        /*
           Se t1 e t2 possono essere uniti a formare t, modifico la struttura di t e:
           - bit da 0 a 3 salvo il valore equal_rooted_tree, che mi dice e l'albero ha due centroidi le cui rispettive radicazioni risultano uguali.
           - bit da 4 a 7 salvo il numero di copie che trovo in t2 a partire dal primo sottoalbero radicato nella radice rispetto all'ultimo sottoalbero radicato nella radice di t1.
           - bit da 8-11 memorizzo il numero di copie in t1 dell'ultimo sottoalbero radicato in un figlio delle radice di t1
           - bit 12-27 colori
           - bit 28-57 struttura

           Un volta aver calcolato e salvato entrambi i valori li posso usare in fase di normalizzazione per normalizzare il numero di occorrenze di ogni albero facendo il coefficiente binomiale.
           */

        long t = equal_rooted_tree |  (copies_of_subtree_in_t2<<4)  |   (t1 & 0xF00) |  ((t1 | t2) & 0xFFFF000L)  | structure <<28;
        assert(t<=0x0FFFFFFFFFFFFFFFL);

        return t;

    }


    public static int normalization_factor(long t)
    {
        return (int)((t>>8) & 0xFL);
    }


    public static int normalization_factor_balanced(long t)
    {
        int k = (int)((t >> 4) & 0xF);
        int n = (int)((t >> 8) & 0xF) + k;
        
        int res = 1;

        if (k > n - k)
            k = n - k;

        for (int i = 0; i < k; ++i)
        {
            res *= (n - i);
            res /= (i + 1);
        }
        
        return res  * (int)(t & 0xF);
    }


    public static long get_structure(long t)
    {
        return (t>>28) & 0xFFFFFFFFL;
    }



    /*
       Riradica il treelet rappresentato da t nel nodo con indice v.
       I nodi sono indicizzati in ordine DFS dalla radice di t (rispetto alla relazione d'ordine dei sottoalberi)
       */
    public static long reroot(long structure, int v, StructureInfo info)
    {
        if(v==0)
            return structure;

        if(info==null)
            info = new StructureInfo(structure);

        int path[] = new int[16]; //Index of the vertices in the path from v to the root in t
        int path_length = 0;
        do 
        {
            path[path_length++]=v;
            v = info.parent[v];
        }
        while(v != -1);


        long parent_structure=0;
        long parent_structure_length=0;

        //Reroot!
        for(int i=path_length-1; i>=0; i--)
        {
            int u = path[i];  
            //System.out.println("Processing vertex " + u);

            long new_structure = 0;
            long new_structure_length = 0;

            int idx;
            for(idx=0; idx < info.children.get(u).size(); idx++)
            {
                int z = info.children.get(u).get(idx);
                int len = info.structure_start[z] - info.structure_end[z] + 1;
                long s =  (structure >> info.structure_end[z]) & ( (1L<<len) - 1 );

                if(parent_structure>s)
                    break;
            }

            //System.out.println("Idx: " + idx);

            for(int j=0; j<idx; j++)
            {
                int z = info.children.get(u).get(j);
                if(i>0 && z == path[i-1])
                    continue;

                //System.out.println("Appending children " + z);
                int len = info.structure_start[z] - info.structure_end[z] + 1;
                new_structure = append_subtree(new_structure, structure>>info.structure_end[z], len);
                new_structure_length += len + 2;
            }



            if(u!=0)
            {
                //System.out.println("Appending parent");
                new_structure = append_subtree(new_structure, parent_structure, parent_structure_length);
                new_structure_length += parent_structure_length + 2;
            }

            for(int j=idx; j<info.children.get(u).size(); j++)
            {
                int z = info.children.get(u).get(j);
                if(i>0 && z == path[i-1])
                    continue;

                //System.out.println("Appending children " + z);
                int len = info.structure_start[z] - info.structure_end[z] + 1;
                new_structure = append_subtree(new_structure, structure>>info.structure_end[z], len);
                new_structure_length += len + 2;
            }

            parent_structure = new_structure;
            parent_structure_length = new_structure_length;

            //System.out.println("Structure after processing vertex " + u + ": " + Long.toBinaryString(parent_structure) + " len: " + parent_structure_length);
        }

        return parent_structure;
    }     


    //Appends a the tree encoded by the fist n bits of s as a child of the root of tree t 
    private static long append_subtree(long t, long s, long n)
    {    
        return ((((t<<1) | 1L)<<n) | (s & ((1L<<n)-1)) )<<1;
    }



    public static int[] centroids(StructureInfo info)
    {        
        int[] centroids = {-1, -1};
        int ncentroids=0;
        for(int i=0; i<info.number_of_vertices; i++)
        {
            boolean is_centroid=true; 
            int vertices_towards_parent = info.number_of_vertices-1;
            for(int u : info.children.get(i))
            {
                int size = (info.structure_start[u] - info.structure_end[u] + 1)/2 + 1; //number of vertices in the subtree of s rooted in u

                if(2*size > info.number_of_vertices)
                {
                    is_centroid = false;
                    continue;
                }

                vertices_towards_parent -= size;
            }

            if(is_centroid && 2*vertices_towards_parent<=info.number_of_vertices)
                centroids[ncentroids++]=i;

            if(ncentroids==2)
                break;
        }

        return centroids;
    } 



    public static long isomorphism_class_representative(long structure)
    {
        StructureInfo info = new StructureInfo(structure);
        int[] c = centroids(info);

        if(c[1]==-1)
            return reroot(structure, c[0], info);

        long t0 = reroot(structure, c[0], info);
        long t1 = reroot(structure, c[1], info);

        if(t0 < t1)
            return t0;

        return t1;
    }
}



class StructureInfo
{
    public int[] parent = new int[16]; //p[i] contiene l'incide del padre del nodo i, oppure -1 se il padre non esiste
    public ArrayList<ArrayList<Integer>> children = new ArrayList<ArrayList<Integer>>(); //children[i][j] contiene l'indice del j-esimo figlio del nodo i

    public int structure_start[] = new int[16]; //structure_start[i] contiene la posizione del primo bit (più significativo) che rappresenta il sottoalbero radicato nel vertice i nella struttura di t
    public int structure_end[] = new int[16]; //structure_length[i] contiene la posizione dell'ultimo bit (meno significiativo) che rappresenta il sottoalbero radicato nel vertice i nella struttura di t
    //Normalmente structure_start[i]>=structure_end[i]. Se la rappresentazione del sottoalbero di t radicato in i è vuota allora structure_end[i]=structure_start[i]-1.
    //structure_start[0] e structure_end[0] non sono definiti (e non sono mai usati)

    public int number_of_vertices;

    StructureInfo(long structure)
    {        
        int size = Long.bitCount(structure); //Number of edges in the structure

        for(int i=0; i<=size; i++)
            children.add(new ArrayList<Integer>());

        parent[0]=-1;
        structure_start[0]=2*size;
        structure_end[0]=0;

        int current=0; //The index of the vertex we are currently visiting
        int last_vertex=0; //The index of the last discovered vertex

        for(int i=0; i<2*size; i++)
        {
            if( ((structure>>(2*size-1-i)) & 1L) != 0)
            {
                //New edge directed away from the root
                parent[++last_vertex]=current;
                children.get(current).add(last_vertex);
                current=last_vertex;
                structure_start[current] = 2*size-2-i;
            }
            else
            {
                //Edge going back towards the parent
                structure_end[current] = 2*size-i;
                current=parent[current];
            }
        }

        number_of_vertices=size+1;
    }   
}

