package application;

import application.building.*;
import org.apache.commons.cli.*;

import java.sql.Timestamp;


public class Main {

	public static void main(String[] args ) throws ParseException {


			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			System.out.println( "["+timestamp+"] " + "AVVIO DELL'ESECUZIONE");


		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();
		Options options = new Options();
		options.addOption("F", true, "Input graph");
		options.addOption("k", true, "Size of the treelet to count");
		options.addOption("t", true, "Number of threads to use");
		options.addOption("s", true, "Seed for the random coloring. Use 0 for round-robin coloring.");
		options.addOption("help", false, "display this help and exit");
		options.addOption("balanced", false, "[OPTIONAL] use balanced treelet decompositions");
		options.addOption("p",true, "path to save output file");
		CommandLine cmd = parser.parse( options, args);


		if(!cmd.hasOption("k") || !cmd.hasOption("t") || !cmd.hasOption("F") || !cmd.hasOption("s") || !cmd.hasOption("p"))
			formatter.printHelp( " ", options );
		else {

			String file = cmd.getOptionValue("F");
			Graph graph = new Graph(file);

			int c = Integer.parseInt(cmd.getOptionValue("k"));
			int k = Integer.parseInt(cmd.getOptionValue("k"));
			int thread = Integer.parseInt(cmd.getOptionValue("t"));
			long seed = Long.parseLong(cmd.getOptionValue("s"));
			String path = cmd.getOptionValue("p");

			if (cmd.hasOption("help")) {
				formatter.printHelp(" ", options);
				return;
			}


			Table b = new Table(graph, c, k, thread, seed, cmd.hasOption("balanced"));

			try {
				b.build();
				b.write_file(path);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());
		System.out.println( "["+timestamp1+"] " + "FINE DELL'ESECUZIONE");
	}
}






