/*
* Date: 2021-10-14.
* File Name: MyDFS.Java
* Author: Emelie Åslund
*
*/

package ea224qb;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import graphs.DirectedGraph;
import graphs.Node;

public class MyDFS<E> implements graphs.DFS<E> {

	/** Iterate through the graph and set the node.num to 0 on all nodes. */
	private void clearNum(DirectedGraph<E> graph) {
		Iterator<Node<E>> it = graph.iterator();
		
		while (it.hasNext()) {
			Node<E> node = it.next();
			node.num = 0;
		}
	}
	
//	private List<Node<E>> postOrderList(DirectedGraph<E> g, Node<E> root, int postOrderNumber) {
//		List<Node<E>> visited = new ArrayList<Node<E>>();
//		Stack<Node<E>> stack = new Stack<Node<E>>();
//		int depthFirstNumber = postOrderNumber;
//		
//		stack.push(root);
//		while (!stack.isEmpty()) {
//			Node<E> node = stack.peek();
//			System.out.println("visiting node: " + node + " node num: " + node.num);
//			System.out.println("Stack size: " + stack.size());
//			boolean childrenAlreadyVisited = true;
//			
//			if (node.num == 0 || node.num == -1) {
//				Iterator<Node<E>> it = node.succsOf();
//				
//				while (it.hasNext()) {
//					Node<E> sNode = it.next();
//					
//					if (sNode.num == 0) {
//						System.out.println("pushing node: " + sNode.item());
//						sNode.num = -1;
//						stack.push(sNode);
//						childrenAlreadyVisited = false;
//					}
//				}
//				if (childrenAlreadyVisited) {
//					node = stack.pop();
//					System.out.println("adding node: " + node.item());
//					node.num = depthFirstNumber;
//					depthFirstNumber++;
//					visited.add(node);
//				}
//			} else {
//				stack.pop();
//			}
//		}
//		return visited;
//	}
	
	/**
     * Returns the nodes visited by a depth first search starting from
     * the given root node. Each visited node is also attached with 
     * a depth-first number.
     */ 
	private List<Node<E>> dfsList(DirectedGraph<E> graph, Node<E> root, int dfsNumber) {
		List<Node<E>> visited = new ArrayList<Node<E>>();
		Stack<Node<E>> stack = new Stack<Node<E>>();
		int depthFirstNumber = dfsNumber;
		
		stack.push(root); // push root to stack
		
		while (!stack.isEmpty()) {
			Node<E> node = stack.pop();
			
			if (node.num == 0) {
				node.num = depthFirstNumber;
				depthFirstNumber++;
				
				// iterate through all the successor nodes
				Iterator<Node<E>> it = node.succsOf();
				
				while (it.hasNext()) {
					Node<E> sNode = it.next();
					
					if (sNode.num == 0) {
						// push successor onto stack
						stack.push(sNode);
					}
				}
				visited.add(node);
			}
		}
		return visited;
	}
	
	/** Clear all nodes and returns dfsList */ 
	@Override
	public List<Node<E>> dfs(DirectedGraph<E> graph, Node<E> root) {
		clearNum(graph);
		
		return dfsList(graph, root, 1);
	}

    /**
     * Returns the nodes visited by a depth first search starting from
     * an arbitrary set of nodes. All nodes are visited. Each visited node is 
     * also attached with a depth-first number.
     */   
	@Override
	public List<Node<E>> dfs(DirectedGraph<E> graph) {
		clearNum(graph);
		
		List<Node<E>> visited = new ArrayList<Node<E>>();
		List<Node<E>> tempList = new ArrayList<Node<E>>();
		
		Iterator<Node<E>> it = graph.iterator();
		
		while (it.hasNext()) {
			Node<E> node = it.next();
			if (node.num == 0) {
//				System.out.println("root node: " + node.item());
				tempList = dfsList(graph, node, visited.size()+1);
				visited.addAll(tempList);
			}
		}
		
		return visited;
	}
	
	private List<Node<E>> postOrderListWithDepthNum(DirectedGraph<E> g, Node<E> root, int postOrderNumber, boolean attach_dfs_number) {
		List<Node<E>> visited = new ArrayList<Node<E>>();
		Stack<Node<E>> stack = new Stack<Node<E>>();
		int depthFirstNumber = postOrderNumber;
		
		stack.push(root);
		while (!stack.isEmpty()) {
			Node<E> node = stack.pop();
			boolean childrenAlreadyVisited = true;
			
			if (node.num == 0 || node.num == -1) {
				if (attach_dfs_number) {
					// set node.num to depthFirstNumber and increment it
					node.num = depthFirstNumber;
					depthFirstNumber++;
				}
				
				// iterate through all the successor nodes
				Iterator<Node<E>> it = node.succsOf();
				boolean childPushed = false;
				
				while (it.hasNext()) {
					Node<E> sNode = it.next();
					
					if (sNode.num == 0) {
						sNode.num = -1;
						if (childPushed == false) {
							// if no children, push parent to stack
							stack.push(node);
							childPushed = true;
						}
						// push successor onto stack
						stack.push(sNode);
						childrenAlreadyVisited = false;
					}
				}
				// if the successor has already been visited
				if (childrenAlreadyVisited) {
					if (attach_dfs_number == false) {
						// set node.num to depthFirstNumber and increment it
						node.num = depthFirstNumber;
						depthFirstNumber++;
					}
					// add visited node onto the array list
					visited.add(node);
				}
			}
		}
		return visited;
	}
	
    /** Clear all nodes and returns postOrderListWithDepthNum */
	@Override
	public List<Node<E>> postOrder(DirectedGraph<E> g, Node<E> root) {
		clearNum(g);
		
		return postOrderListWithDepthNum(g, root, 1, false);
	}

    /**
     * Returns a list of ALL nodes in the graph ordered as 
     * post-order of the depth first forest resulting from
     * depth first search starting at arbitrary start nodes.
     * </p>
     * The algorithm also attaches a post-order number
     * to each visited node. 
     */ 
	@Override
	public List<Node<E>> postOrder(DirectedGraph<E> g) {
		clearNum(g);
		
		List<Node<E>> visited = new ArrayList<Node<E>>();
		List<Node<E>> tempList = new ArrayList<Node<E>>();
		
		Iterator<Node<E>> it = g.iterator();
		
		while (it.hasNext()) {
			Node<E> node = it.next();
			if (node.num == 0) {
				//System.out.println("root node: " + node.item());
				tempList = postOrderListWithDepthNum(g, node, visited.size() + 1, false);
				visited.addAll(tempList);
			}
		}
		
		return visited;
	}
	
    /**
     * Returns a list of ALL nodes in the graph ordered as 
     * post-order of the depth first forest resulting from
     * depth first search starting at arbitrary start nodes.
     * </p>
     * The algorithm attaches a depth-first number if <tt>attach_dfs_number</tt> 
     * is <tt>true</tt>, otherwise it attaches a post-order number. 
     */ 
	@Override
	public List<Node<E>> postOrder(DirectedGraph<E> g, boolean attach_dfs_number) {
		clearNum(g);
		
		if (attach_dfs_number) {
			List<Node<E>> visited = new ArrayList<Node<E>>();
			List<Node<E>> tempList = new ArrayList<Node<E>>();
			
			Iterator<Node<E>> it = g.iterator();
			
			while (it.hasNext()) {
				Node<E> node = it.next();
				if (node.num == 0) {
					tempList = postOrderListWithDepthNum(g, node, visited.size() + 1, true);
					visited.addAll(tempList);
				}
			}
			return visited;
		} else {
			return postOrder(g);
		}
	}

    /**
     * Returns <tt>true</tt> if the graph contains one or more cycles, 
     * otherwise <tt>false</tt>
     */
	@Override
	public boolean isCyclic(DirectedGraph<E> graph) {
		clearNum(graph);
		
		Stack<Node<E>> stack = new Stack<Node<E>>();
		
		Iterator<Node<E>> it = graph.iterator();
		
		while (it.hasNext()) {
			clearNum(graph);
			Node<E> root = it.next();
			stack.push(root);
			
			while (!stack.isEmpty()) {
				Node<E> node = stack.peek();
				if (node.num != 1) {
					node.num = 1;
					
					Iterator<Node<E>> itSucc = node.succsOf();
					
					// If a successor points at an already visited node
					// then it is a cycle
					while (itSucc.hasNext()) {
						Node<E> succNode = itSucc.next();
						if (succNode.num == 0) {
							stack.push(succNode);
						} else if (succNode.num == 1) {
							return true;
						}
					}
				} else if (node.num == 1) {
					stack.pop();
					node.num = 2;
				}
			}
		}
		
		return false;
	}

	/**
     * Returns a list of all nodes in the graph ordered topological.
     * The algorithm assumes that the graph is acyclic. The result for
     * graphs with cycles are undefined. 
     */
	private List<Node<E>> topSortRecursive(Node<E> node, List<Node<E>> visited) {
		node.num = 1;
		
		Iterator<Node<E>> it = node.succsOf();
		
		while (it.hasNext()) {
			Node<E> sNode = it.next();
			
			if (sNode.num == 0) {
				visited = topSortRecursive(sNode, visited);
			}
		}
		visited.add(0, node);
		
		return visited;
		
	}
	
	/**
     * Returns a list of all nodes in the graph ordered topological.
     * The algorithm assumes that the graph is acyclic. The result for
     * graphs with cycles are undefined. 
     */
	@Override
	public List<Node<E>> topSort(DirectedGraph<E> graph) {
		clearNum(graph);
		
		List<Node<E>> visited = new ArrayList<Node<E>>();
		
		Iterator<Node<E>> it = graph.iterator();
		
		while (it.hasNext()) {
			Node<E> node = it.next();
			
			if (node.num == 0) {
				// call topSortRecursive if node has not been visited
				visited = topSortRecursive(node, visited);
			}
		}
		return visited;
	}

}
