/*
* Date: 2021-10-11.
* File Name: MyNode.Java
* Author: Emelie Åslund
*
*/

package ea224qb;

import java.util.HashSet;
import java.util.Iterator;

import graphs.Node;

public class MyNode<E> extends graphs.Node<E> {
	private HashSet<Node<E>> succSet;
	private HashSet<Node<E>> predSet;
	
	protected MyNode(E item) {
		super(item);
		succSet = new HashSet<Node<E>>();
		predSet = new HashSet<Node<E>>();
	}
	
	/**
	 * Returns a string representing <tt>this</tt> node by applying
	 * <tt>toString()</tt> on the key item.
	 */
	public String toString() {
		String outputVal = "";
		
		outputVal += "Node Data: " + item().toString();
		outputVal += " | Pred Nodes:";
		
		for (Node<E> node : predSet) {
			outputVal += " " + node.item().toString();
		}
		
		outputVal += " | Succ Nodes:";
		
		for (Node<E> node : succSet) {
			outputVal += " " + node.item().toString();
		}
		
		return outputVal;
	}
	
	/**
	 * Returns true if this node has node as successor, 
	 * otherwise false.
	 * @param a possible successor node
	 * @return boolean
	 */
	@Override
	public boolean hasSucc(Node<E> node) {
		return succSet.contains(node);
	}
	
	/**
	 * Returns the number of successors (i.e. outgoing edges)
	 * of this node.
	 * @return node out-degree
	 */
	@Override
	public int outDegree() {
		return succSet.size();
	}

	/**
	 * Returns an iterator over all successor nodes.
	 * @return successor node iterator
	 */
	@Override
	public Iterator<Node<E>> succsOf() {
		return succSet.iterator();
	}

	/**
	 * Returns true if this node has node as predecessor, 
	 * otherwise false.
	 * @param a possible predecessor node
	 * @return boolean
	 */
	@Override
	public boolean hasPred(Node<E> node) {
		return predSet.contains(node);
	}

	/**
	 * Returns the number of predecessors (i.e. incoming edges)
	 * of this node.
	 * @return node out-degree
	 */
	@Override
	public int inDegree() {
		return predSet.size();
	}

	/**
	 * Returns an iterator over all predecessor nodes.
	 * @return predecessor node iterator
	 */
	@Override
	public Iterator<Node<E>> predsOf() {
		return predSet.iterator();
	}

	/** Adds node succ as a successor to this node. */
	@Override
	protected void addSucc(Node<E> succ) {
		succSet.add(succ);
	}

	/** Removes node succ as a successor to this node. */
	@Override
	protected void removeSucc(Node<E> succ) {
		succSet.remove(succ);
	}

	/**  Adds node pred as a predecessor to this node. */
	@Override
	protected void addPred(Node<E> pred) {
		predSet.add(pred);
	}

	/** Removes node pred as a predecessor to this node. */
	@Override
	protected void removePred(Node<E> pred) {
		predSet.remove(pred);
	}

	/**
	 * Disconnects this node from all adjacent nodes. That is, removes all successor, 
	 * and predecessor, nodes to this node.
	 */
	@Override
	protected void disconnect() {
		for (Node<E> pred: predSet) {
			((MyNode<E>)pred).removeSucc(this);
		}
		
		for (Node<E> succ: succSet) {
			((MyNode<E>)succ).removePred(this);
		}
		
		predSet.clear();
		succSet.clear();
	}

}
