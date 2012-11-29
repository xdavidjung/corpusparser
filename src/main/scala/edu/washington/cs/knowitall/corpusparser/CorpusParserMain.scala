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

    // lazily initialize malt parser
    lazy val malt = new MaltParser(getClass().getResource("engmalt.linear-1.7.mco"), None)

    val lines = fromTextFile(input).filter(x => x != "");

    try {
      val graphs = lines.map(line => malt.dependencyGraph(line).toString)
      persist(toTextFile(graphs, output))
    } catch {
      case emptyStringE: org.maltparser.core.symbol.SymbolException =>
        println("Empty string: " + emptyStringE)
      case e: Exception => 
        println(e);
      case _ => println("Some other exception.")
    }

  }

  def usage() {
    System.err.println("Usage: hadoop jar <this.jar> <inputfile> <outputfile>");
    System.exit(0);
  }
}
