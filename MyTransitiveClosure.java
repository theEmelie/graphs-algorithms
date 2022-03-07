/*
* Date: 2021-10-20.
* File Name: MyTransitiveClosure.Java
* Author: Emelie Åslund
*
*/

package ea224qb;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import graphs.DirectedGraph;
import graphs.Node;

public class MyTransitiveClosure<E> implements graphs.TransitiveClosure<E> {

	/** Computes the transitive closure for the graph. */
	@Override
	public Map<Node<E>, Collection<Node<E>>> computeClosure(DirectedGraph<E> dg) {
		Map<Node<E>, Collection<Node<E>>> closure = new HashMap<Node<E>, Collection<Node<E>>>();
		
		Iterator<Node<E>> it = dg.iterator();
		MyDFS<E> myDFS = new MyDFS<E>(); 
		
		// loop through all nodes in graph
		while (it.hasNext()) {
			Node<E> node = it.next();
			
			List<Node<E>> nodesReachable;
			
			// get all reachable nodes using dfs
			nodesReachable = myDFS.dfs(dg, node);
			
			// add reachable nodes for this node to map
			closure.put(node, nodesReachable);
		}
		
		return closure;
	}

}
