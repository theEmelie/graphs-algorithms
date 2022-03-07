/*
* Date: 2021-10-13.
* File Name: MyGraph.Java
* Author: Emelie Åslund
*
*/

package ea224qb;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import graphs.Node;

public class MyGraph<E> implements graphs.DirectedGraph<E> {
	private HashSet<Node<E>> graphSet;
	
	public MyGraph() {
		graphSet = new HashSet<Node<E>>();
	}
	
	/**
	 * Adds a node representing <tt>item</tt> if not added before.
	 * Exception is thrown if <tt>item</tt> is null. It returns the
	 * node representing <tt>item</tt> (new or previously constructed).
	 * @param item, 
	 * @return Node representing <tt>item</tt>
	 */
	@Override
	public Node<E> addNodeFor(E item) throws NullPointerException {
		Node<E> myNode = null;
		//System.out.println("Adding node " + item);
		
		if (item == null) {
			throw new NullPointerException("Item is null");
		} else {
			for (Node<E> node: graphSet) {
				if (node.item() == item) {
					myNode = node;
					return myNode;
				}
			}
			myNode = new MyNode<E>(item);
			graphSet.add(myNode);
			//System.out.println(myNode);
		}
		return myNode;
	}

	/**
	 * Returns the node representing <tt>item</tt>.
	 * Exception is thrown if <tt>item</tt> is null or if no
	 * node representing <tt>item</tt> is found.
	 * @param item
	 * @return Node representing <tt>item</tt>
	 */
	@Override
	public Node<E> getNodeFor(E item) throws NullPointerException, IllegalArgumentException {
		Node<E> myNode = null;
		//System.out.println("Start of function: " + item);
		if (item == null) {
			throw new NullPointerException("Item is null");
		} else {
			//System.out.println("Graph set contains: " + item);
			for (Node<E> node: graphSet) {
				if (node.item() == item) {
					myNode = node;
					return myNode;
				}
			}
			//System.out.println("contains doesnt return item");
			throw new IllegalArgumentException ("Item not in graph");
		}
	}
	
	/**
	 * Adds an edge between the nodes represented by <tt>from</tt>
	 * and <tt>to</tt>  if not added before. The nodes representing 
	 * <tt>from</tt> and <tt>to</tt> are added if not added before.
	 * Exception is thrown if <tt>from</tt> or <tt>to</tt> is null. 
	 * It returns <tt>true</tt> if edge not added before, otherwise <tt>false</tt>.
	 * @param from, source node
	 * @param to, target node
	 * @return <tt>true</tt> if edge not added before, otherwise <tt>false</tt>.
	 */
	@Override
	public boolean addEdgeFor(E from, E to) throws NullPointerException {
		Node<E> fromNode = null;
		Node<E> toNode = null;
		boolean edgeNotAddedBefore = true;
		//System.out.println("Adding edge from: " + from + " to: " + to);
		
		if (from == null || to == null) {
			throw new NullPointerException("To or From is null");
		} else {
			for (Node<E> node: graphSet) {
				if (node.item() == from) {
					fromNode = node;
				}
			}
			
			// if fromNode is null, call addNodeFor
			if (fromNode == null) {
				//System.out.println("Adding new from node");
				fromNode = addNodeFor(from);
			}	
		
			for (Node<E> node: graphSet) {
				if (node.item() == to) {
					toNode = node;
				}
			}
			
			// if toNode is null, callAddFor
			if (toNode == null) {
				toNode = addNodeFor(to);
			}
			
			// check if fromNode has successor, if it does not, call addSucc
			if (fromNode.hasSucc(toNode)) {
				edgeNotAddedBefore = false;
			} else {
				//System.out.println("Add succ");
				((MyNode<E>)fromNode).addSucc(toNode);
				//System.out.println("From node: " + fromNode);
			}
			
			// check if toNode has predecessor, if it does not, call addPred
			if (toNode.hasPred(fromNode)) {
				edgeNotAddedBefore = false;
			} else {
				//System.out.println("Add pred");
				((MyNode<E>)toNode).addPred(fromNode);
				//System.out.println("To node: " + toNode);
			}		
		}
		return edgeNotAddedBefore;
	}

	/**
	 * Returns <tt>true</tt> if the node representing <tt>item</tt> is 
	 * contained in the graph, otherwise <tt>false</tt>.
	 * Exception is thrown if <tt>item</tt> is null. 
	 * @param item, node to be checked.
	 */
	@Override
	public boolean containsNodeFor(E item) throws NullPointerException {		
		if (item == null) {
			throw new NullPointerException("Item is null");
		}
		
		for (Node<E> node: graphSet) {
			if (node.item() == item) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns the number of nodes in the graph. 
	 * @return number of nodes
	 */	
	@Override
	public int nodeCount() {
		return graphSet.size();
	}

	/**
	 * Returns an iterator over all nodes in the graph. 
	 * @return graph nodes iterator
	 */
	@Override
	public Iterator<Node<E>> iterator() {
		return graphSet.iterator();
	}

	/**
	 * Returns an iterator over all nodes with no in-edges. 
	 * @return heads iterator
	 */
	@Override
	public Iterator<Node<E>> heads() {
		HashSet<Node<E>> headSet = new HashSet<Node<E>>();
		
		for (Node<E> node : graphSet) {
			if (node.inDegree() == 0) {
				headSet.add(node);
			}
		}
		
		return headSet.iterator();
	}

	/**
	 * The number of nodes with no in-edges. 
	 * @return number of head nodes.
	 */
	@Override
	public int headCount() {
		int headCount = 0;
		
		for (Node<E> node : graphSet) {
			if (node.inDegree() == 0) {
				headCount++;
			}
		}
		
		return headCount;
	}

	/**
	 * Returns an iterator over all nodes with no out-edges. 
	 * @return tails iterator
	 */
	@Override
	public Iterator<Node<E>> tails() {
		HashSet<Node<E>> tailSet = new HashSet<Node<E>>();
		
		for (Node<E> node : graphSet) {
			if (node.outDegree() == 0) {
				tailSet.add(node);
			}
		}
		
		return tailSet.iterator();
	}

	/**
	 * The number of nodes with no out-edges. 
	 * @return number of head nodes.
	 */
	@Override
	public int tailCount() {
		int tailCount = 0;
		
		for (Node<E> node : graphSet) {
			if (node.outDegree() == 0) {
				tailCount++;
			}
		}
		
		return tailCount;
	}

	/**
	 * Returns a list over all node items currently used in the graph. 
	 * @return list of items
	 */
	@Override
	public List<E> allItems() {
		List<E> itemList = new ArrayList<E>();
		
		for (Node<E> node : graphSet) {
			itemList.add(node.item());
		}
		
		return itemList;
	}

	/**
	 * Returns the number of graph edges. 
	 * @return edge count
	 */
	@Override
	public int edgeCount() {
		//System.out.println("running edge count");
		int edgeCount = 0;
		
		for (Node<E> node : graphSet) {
			//System.out.println("node: " + node);
			edgeCount+= node.inDegree();
		}
		//System.out.println("edge count: " +edgeCount);
		
		return edgeCount;
	}
	
	/**
	 * Removes the node represented by <tt>item</item> and 
	 * all its connecting edges. Exception is thrown if <tt>item</tt> 
	 * is null  or if no node representing <tt>item</tt> is found. 
	 * 
	 * @param item, node to be removed.
	 */
	@Override
	public void removeNodeFor(E item) throws NullPointerException, IllegalArgumentException {
		if (item == null) {
			throw new NullPointerException("Item is null");
		}
		
		for (Node<E> node : graphSet) {
			if (node.item() == item) {
				((MyNode<E>)node).disconnect(); // Disconnect the edges
				graphSet.remove(node); // Remove the node
				return;
			}
		}
		throw new IllegalArgumentException ("Item not in graph");
	}

	/**
	 * Returns <tt>true</tt> if an edge between the nodes represented 
	 * by <tt>from</tt> and <tt>to</tt> is added to the graph. 
	 * Exception is thrown if <tt>from</tt> or <tt>to</tt> is null. 
	 * @param from, source node item
	 * @param to, target node item
	 * @return <tt>true</tt> if edge in graph, otherwise <tt>false</tt>.
	 */
	@Override
	public boolean containsEdgeFor(E from, E to) throws NullPointerException {
		if (from == null || to == null) {
			throw new NullPointerException("From or To is null");
		}
		
		for (Node<E> fromNode : graphSet) {
			if (fromNode.item() == from) {
				for (Node<E> toNode : graphSet) {
					if (toNode.item() == to) {
						if (fromNode.hasSucc(toNode)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	/**
	 * Removes the edge between the nodes represented 
	 * by <tt>from</tt> and <tt>to</tt> if it exist.
	 * Returns <tt>true</tt> if an edge between the nodes represented 
	 * by <tt>from</tt> and <tt>to</tt>  is found and successfully removed. 
	 * Exception is thrown if <tt>from</tt> or <tt>to</tt> is null. 
	 * @param from, source node item
	 * @param to, target node item
	 * @return <tt>true</tt> if edge in graph and successfully removed, otherwise <tt>false</tt>.
	 */
	@Override
	public boolean removeEdgeFor(E from, E to) throws NullPointerException {
		boolean edgeDeleted = false;
		
		if (from == null || to == null) {
			throw new NullPointerException("From or To is null");
		}
		
		for (Node<E> fromNode : graphSet) {
			if (fromNode.item() == from) {
				for (Node<E> toNode : graphSet) {
					if (toNode.item() == to) {
						if (fromNode.hasSucc(toNode)) {
							//System.out.println("Removing edge: from: " + from + " to: " + to);
							((MyNode<E>)fromNode).removeSucc(toNode);
							((MyNode<E>)toNode).removePred(fromNode);
							edgeDeleted = true;
						}
						
//						if (fromNode.hasPred(toNode)) {
//							((MyNode<E>)fromNode).removePred(toNode);
//							((MyNode<E>)toNode).removeSucc(fromNode);
//							edgeDeleted = true;
//						}
						return edgeDeleted;
					}
				}
			}
		}
		return edgeDeleted;
	}
	
	/**
	 * A textual representation of the graph content (nodes and edges) constructed
	 * by applying <tt>toString()</tt> on the nodes.
	 * 
	 */
	@Override
	public String toString() {
		String outputVal = "";
		for (Node<E> node : graphSet) {
			outputVal += node.toString() + "\n";
		}
		return outputVal;
	}

}
