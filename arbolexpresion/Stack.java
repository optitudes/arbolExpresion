package arbolexpresion;

public class Stack<T> {
	int tamanio;
	NodeStack<T> topNode;



	public Stack(NodeStack<T> nextNode) {
		super();
		this.topNode = nextNode;
		this.tamanio=0;
	}

	public Stack() {
		super();
	}

	public int getTamanio() {
		return tamanio;
	}

	public void setTamanio(int tamanio) {
		this.tamanio = tamanio;
	}


	public NodeStack<T> getTopNode() {
		return topNode;
	}


	public void setTopNode(NodeStack<T> top) {
		this.topNode = top;
	}


	

	@Override
	public String toString() {
		return "Pila [tamanio=" + tamanio + ", top=" + topNode + "]";
	}

	public boolean isEmpty(){
		if(tamanio==0)
			return true;
		return false;
	}

	public void push(T value) {
		NodeStack<T> nodo= new NodeStack<T>(value);
		if(isEmpty()){topNode=nodo;tamanio++;}
		else{nodo.setNextNode(topNode);topNode=nodo;tamanio++;}
	}

	public void shoAll() {
		int pos=tamanio;
		for(NodeStack<T> nodo=topNode;nodo!=null;nodo=nodo.getNextNode(),pos--){
			System.out.println("Posicion"+pos+" ["+nodo.getValue()+"]");
		}
	}

	public T pop() {
		T value=null;
		if(!isEmpty()){value=topNode.getValue();topNode=topNode.getNextNode();tamanio--;}
		return value;
	}
	public void unstack(){
		if(!isEmpty()){topNode=topNode.getNextNode();tamanio--;}
		
	}

	public T getTop() {
		return topNode.getValue();
	}

	public boolean contains(T valueRef) {
		for(NodeStack<T> nodo=topNode;nodo!=null;nodo=nodo.getNextNode()){
			if(nodo.getValue()==valueRef)
				return true;
		}
		return false;
	}
	public void clear(){
		this.topNode=null;
		tamanio=0;
	}
}