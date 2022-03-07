/*
* Date: 2021-10-15.
* File Name: MyGML.Java
* Author: Emelie Åslund
*
*/

package ea224qb;

import java.util.Iterator;

import graphs.DirectedGraph;
import graphs.GML;
import graphs.Node;

public class MyGML<E> extends graphs.GML<E> {

	/** Constructor */
	public MyGML(DirectedGraph<E> dg) {
		super(dg);
	}

	/**
	 * The GML mark-up string constructor.
	 */
	public String toGML() {
		String gml = "";
		String edges = "";
		gml += "graph [\n";
		gml += "\tdirected 1\n";

		Iterator<Node<E>> it = graph.iterator();
		
		while (it.hasNext()) {
			Node<E> node = it.next();
			gml += "\tnode [\n";
			gml += "\t\tid " + node.item() + "\n";
			gml += "\t\tlabel \"node " + node.item() + "\"\n";
			gml += "\t]\n";
			
			if (node.outDegree() > 0) {
				Iterator<Node<E>> succIt = node.succsOf();
				while (succIt.hasNext()) {
					Node<E> succNode = succIt.next();
					edges += "\tedge [\n";
					edges += "\t\tsource " + node.item() + "\n";
					edges += "\t\ttarget " + succNode.item() + "\n";
					edges += "\t]\n";
				}
			}
		}
		gml += edges;
		gml += "]\n";
		return gml;
	}
	
	/** Main function for testing */
	public static void main(String args[]) {
		DirectedGraph<Integer> dg = new MyGraph<Integer>();
		
		dg.addEdgeFor(0,1);
		dg.addEdgeFor(0,2);
		dg.addEdgeFor(1,3);
		dg.addEdgeFor(2,3);
		dg.addEdgeFor(2,2); // reflexive
		dg.addEdgeFor(3,1); //cycle
		dg.addEdgeFor(3,4);
		dg.addEdgeFor(4,5);
		dg.addEdgeFor(5,3); //cycle
		dg.addEdgeFor(4,6); //leaf
		
		System.out.println(dg);
		GML<Integer> gml = new MyGML<Integer>(dg);
	
		System.out.println(gml.toGML());
		
		gml.dumpGML();
	}

}
