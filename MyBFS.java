/*
* Date: 2021-10-14.
* File Name: MyBFS.Java
* Author: Emelie Åslund
*
*/

package ea224qb;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import graphs.DirectedGraph;
import graphs.Node;

public class MyBFS<E> implements graphs.BFS<E> {

	/** Iterate through the graph and set the node.num to 0 on all nodes. */
	private void clearNum(DirectedGraph<E> graph) {
		Iterator<Node<E>> it = graph.iterator();
		
		while (it.hasNext()) {
			Node<E> node = it.next();
			node.num = 0;
		}
	}
	
    /**
     * Returns the nodes visited by a breadth-first search starting from
     * the given root node. Each visited node is also attached with 
     * a breadth-first number.
     */ 
	public List<Node<E>> bfsList(DirectedGraph<E> graph, Node<E> root, int dfsNumber) {
		List<Node<E>> visited = new ArrayList<Node<E>>();
		Queue<Node<E>> queue = new LinkedList<Node<E>>();
		int breadthFirstNumber = dfsNumber;
		
		queue.add(root); // add root to queue
		
		while (!queue.isEmpty()) {
			Node<E> node = queue.remove();
			
			// if node num is 0, it means node has not been visited
			if (node.num == 0) {
				node.num = breadthFirstNumber;
				breadthFirstNumber++;
				
				// iterate through the successor 
				Iterator<Node<E>> it = node.succsOf();
				
				while (it.hasNext()) {
					Node<E> sNode = it.next();
					
					// node has not been visited
					if (sNode.num == 0) {
						// add successor onto queue
						queue.add(sNode);
					}
				}
				// add visited node
				visited.add(node);
			}
		}
		return visited;
	
	}
	
	/** Clear all nodes and returns bfsList */
	@Override
	public List<Node<E>> bfs(DirectedGraph<E> graph, Node<E> root) {
		clearNum(graph);
		
		return bfsList(graph, root, 1);
	}
	
    /**
     * Returns the nodes visited by a breadth first search starting from
     * an arbitrary set of nodes. All nodes are visited. Each visited node is 
     * also attached with a breadth-first number.
     */  
	@Override
	public List<Node<E>> bfs(DirectedGraph<E> graph) {
		clearNum(graph);
		
		List<Node<E>> visited = new ArrayList<Node<E>>();
		List<Node<E>> tempList = new ArrayList<Node<E>>();
		
		// iterate through the graph
		Iterator<Node<E>> it = graph.iterator();
		
		while (it.hasNext()) {
			Node<E> node = it.next();
			
			if (node.num == 0) {
				// call bfsList if node has not been visited
				tempList = bfsList(graph, node, visited.size()+1);
				visited.addAll(tempList);
			}
		}
		
		return visited;
	}

}
