package arbolexpresion;

public class NodeStack<T> {
	T value;
	NodeStack<T> nextNode;
	

	
	public NodeStack(T value, NodeStack<T> nextNode) {
		super();
		this.value = value;
		this.nextNode = nextNode;
	}

	public NodeStack(T valor) {
		super();
		this.value = valor;
		this.nextNode=null;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public NodeStack<T> getNextNode() {
		return nextNode;
	}

	public void setNextNode(NodeStack<T> nextNode) {
		this.nextNode = nextNode;
	}

	@Override
	public String toString() {
		return "Nodo [value=" + value + ", nextNode=" + nextNode + "]";
	}
	
	

}
