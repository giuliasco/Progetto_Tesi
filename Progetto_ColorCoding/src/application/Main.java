package application;
import java.util.*;
import application.building.*;
import org.apache.commons.cli.*;

import java.util.concurrent.atomic.AtomicInteger;

public class Main {

	public static void main(String[] args ) throws ParseException {



/*      long t = 0b111010100101001111010011000100L;
        System.out.println(t);
        System.out.println(Treelet.reroot(t, 9));
        int[] centroids = Treelet.centroids(t);
        System.out.println("Centroids: " + centroids[0] + " " + centroids[1]);


        System.out.println("Representative 1: " + Treelet.isomorphism_class_representative(0b11001010) );
        System.out.println("Representative 2: " + Treelet.isomorphism_class_representative(0b11100100) );
        System.out.println("Representative 3: " + Treelet.isomorphism_class_representative(0b11010010) );*/


/*
		long t1 = Long.parseLong("1010101010100000000001111111011000100110",2);
		long t2 = Long.parseLong("101010100000011111000000010000100100",2);
		long t = Treelet.balance_merge(t1,t2);
		long struct = t>>28;
		long first = t & 0xFFF;
		System.out.println(struct + " " + first );
		System.out.println(Treelet.normalization_factor_balanced(t));
*/


		//System.out.println(Treelet.normalization_factor_balanced(0b001100100001));

		System.out.println(Treelet.reroot(15434, 1, null));

		/*
		FARE DEI TEST PIU SIGNIFICATIVI....
		 */


		CommandLineParser parser = new DefaultParser();
		Options options = new Options();
		options.addOption("F", true, "Input graph");
		options.addOption("k", true, "Size of the treelet to count");
		options.addOption("t", true, "Number of threads to use");
		options.addOption("s", true, "Seed for the random coloring. Use 0 for round-robin coloring.");
		options.addOption("help", false, "display this help and exit");
		options.addOption("balanced", false, "[OPTIONAL] use balanced treelet decompositions");
		options.addOption("p",true, "path to save output file");
		CommandLine cmd = parser.parse( options, args);

		if(cmd.hasOption("help"))
		{
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp( "", options );
			return;
		}

		String file = cmd.getOptionValue("F");

		Graph graph = new Graph(file);
/*
        int[] color = graph.colorGraph(6, 2);
        //long sum=0;
		for(int u=0; u<graph.V; u++)
		{
		    int[] c=new int[6];
            for(int i=0; i<6; i++)
                c[i]=0;
		    		    
            for( int v : graph.adj.get(u) )
                c[color[v]]++;
                
            long prod = 1;
		    c[color[u]]=1;
            for(int i=0; i<6; i++)
                prod*=c[i];
            
            System.out.println(u+" "+prod);
            //sum += prod;
        }
        //System.out.println(sum);
*/

		int c = Integer.parseInt(cmd.getOptionValue("k"));
		int k = Integer.parseInt(cmd.getOptionValue("k"));
		int thread = Integer.parseInt(cmd.getOptionValue("t"));
        long seed = Long.parseLong(cmd.getOptionValue("s"));

		Table b = new Table(graph, c, k, thread, seed, cmd.hasOption("balanced"));

		try
		{
			b.build();

			if (cmd.hasOption("p"))
			{
				String path = cmd.getOptionValue("p");
				b.write_file(path);
			}

		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}






