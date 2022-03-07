/*
* Date: 2021-10-20.
* File Name: MyConnectedComponents.Java
* Author: Emelie Åslund
*
*/

package ea224qb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import graphs.DirectedGraph;
import graphs.Node;

public class MyConnectedComponents<E> implements graphs.ConnectedComponents<E>{

	/** Iterate through the graph and set the node.num to 0 on all nodes. */
	private void clearNum(DirectedGraph<E> graph) {
		Iterator<Node<E>> it = graph.iterator();
		
		while (it.hasNext()) {
			Node<E> node = it.next();
			node.num = 0;
		}
	}
	
	/**
	 * Two nodes a and b are directly connected if their exist an edge (a,b) 
	 * or an edge (b,a). Two nodes a and k are connected if there exist a sequence
	 * of nodes [a,b,c,d, ... j,k] such that [a,b], [b,c], [c,d], [d,e], ..., [j,k]
	 * are all directly connected.
	 */
	private List<Node<E>> connectedList(DirectedGraph<E> graph, Node<E> root) {
		List<Node<E>> visited = new ArrayList<Node<E>>();
		Stack<Node<E>> stack = new Stack<Node<E>>();

		stack.push(root); // push root to stack
		
		while (!stack.isEmpty()) {
			Node<E> node = stack.pop();
			
			if (node.num == 0) {
				node.num = 1; 
				
				// iterate through all the successor nodes
				Iterator<Node<E>> it = node.succsOf();
				
				while (it.hasNext()) {
					Node<E> sNode = it.next();
					
					if (sNode.num == 0) {
						// push successor onto stack
						stack.push(sNode);
					}
				}
				
				// iterate through all the predecessor nodes
				it = node.predsOf();
				
				while (it.hasNext()) {
					Node<E> pNode = it.next();
					
					if (pNode.num == 0) {
						// push predecessor onto stack
						stack.push(pNode);
					}
				}
				visited.add(node);
				
			}
			
		}
		return visited;
	}
	
	/** 
	 * Looping through the graph, and all the nodes, 
	 * and if a node has not yet been visited, call connectedList. 
	 */
	@Override
	public Collection<Collection<Node<E>>> computeComponents(DirectedGraph<E> dg) {
		clearNum(dg);
		
		Collection<Collection<Node<E>>> connected = new ArrayList<Collection<Node<E>>>();
		
		Iterator<Node<E>> it = dg.iterator(); 
		
		// loop through all nodes in graph
		while (it.hasNext()) {
			Node<E> node = it.next();
			
			if (node.num == 0) {
				// call connectedList if node has not been visited
				List<Node<E>> nodesReachable;
				nodesReachable = connectedList(dg, node);
				connected.add(nodesReachable);
			}
		
		}
		return connected;
	}
}
