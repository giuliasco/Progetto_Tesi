package application;
import java.util.*;
import application.building.*;
import org.apache.commons.cli.*;

import java.util.concurrent.atomic.AtomicInteger;

public class Main {

	public static void main(String[] args ) throws ParseException {

		CommandLineParser parser = new DefaultParser();
		Options options = new Options();
		options.addOption("F",true, "input file path");
		options.addOption("c",true,"number of colors needed ");
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
			formatter.printHelp( "Command require for operation", options );
		}

		else {
			String file = cmd.getOptionValue("F");

			Graph graph = new Graph(file);

			int c = Integer.parseInt(cmd.getOptionValue("c"));

			int k = Integer.parseInt(cmd.getOptionValue("k"));

			int thread = Integer.parseInt(cmd.getOptionValue("t"));

			int coloration = Integer.parseInt(cmd.getOptionValue("C"));

			Table b = new Table(graph, c, k, thread, coloration);

			try {
				if (cmd.hasOption("balanced")) b.build_balanced();

				else b.build();

				if (cmd.hasOption("p")) {
					String path = cmd.getOptionValue("p");
					b.write_file(path);
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}






