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

		long t1 = Long.parseLong("1010101010100000000001111111000100100110",2);
		long t2 = Long.parseLong("101010100000011111000000000100100100",2);
		long t = Treelet.balance_merge(t2,t1);
		System.out.println(t>>28);

		/*
		FARE DEI TEST PIU SIGNIFICATIVI....
		 */

		/*CommandLineParser parser = new DefaultParser();
		Options options = new Options();
		options.addOption("F",true, "input file path");
		options.addOption("k",true,"size of searched treelet");
		options.addOption("t",true,"Number of thread that it can use");
		options.addOption("C", true, "1 if it's a random coloration, 2 if it's round robin");
		options.addOption("help",false, "return list of command");
		options.addOption("balanced",false, "[OPTIONAL] make a balance search of k-treelet");
		options.addOption("p",true,"[OPTIONAL] path to save output file");
		CommandLine cmd = parser.parse( options, args);

		if(cmd.hasOption("help"))
		{
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp( "", options );
		}

		else {
			String file = cmd.getOptionValue("F");

			Graph graph = new Graph(file);

			int c = Integer.parseInt(cmd.getOptionValue("k"));

			int k = Integer.parseInt(cmd.getOptionValue("k"));

			int thread = Integer.parseInt(cmd.getOptionValue("t"));

			int coloration = Integer.parseInt(cmd.getOptionValue("C"));

			Table b = new Table(graph, c, k, thread, coloration);

			try {
				if (cmd.hasOption("balanced")) b.flag=true;

				b.build();

				if (cmd.hasOption("p")) {
					String path = cmd.getOptionValue("p");
					b.write_file(path);
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}*/
	}
}






