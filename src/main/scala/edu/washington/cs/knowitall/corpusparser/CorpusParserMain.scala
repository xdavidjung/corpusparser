package edu.washington.cs.knowitall.corpusparser

import com.nicta.scoobi.Scoobi._
import edu.washington.cs.knowitall.ollie.Ollie
import edu.washington.cs.knowitall.ollie.OllieExtractionInstance
import edu.washington.cs.knowitall.tool.parse.MaltParser
import java.io.File
import edu.washington.cs.knowitall.tool.parse.graph.DependencyGraph

object CorpusParserMain extends ScoobiApp {

  def run() {
    if (args.length != 2) usage

    // get the cl arguments
    val input = args(0);
    val output = args(1);

    // initialize malt parser
    // val maltConfig: File =
    lazy val malt = new MaltParser(new File(getClass().getResource("engmalt.linear-1.7.mco").getFile()))

    val lines = fromTextFile(input);

    val graphs = lines.map(line => malt.dependencyGraph(line).toString)
    persist(toTextFile(graphs, output))
  }

  def usage() {
    System.err.println("Usage: hadoop jar <this.jar> <inputfile> <outputfile>");
    System.exit(0);
  }
}
