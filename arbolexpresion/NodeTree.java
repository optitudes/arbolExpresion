package arbolexpresion;
import java.util.Arrays;

public class NodeTree {
	String value;
	char[] allOperators={'*','/','+','-'};
	NodeTree nodeLeft;
	NodeTree nodeRight;
	NodeTree fatherNode;

	public NodeTree(String value) {
		this.value=value;
		this.nodeLeft = null;
		this.nodeRight = null;
		this.fatherNode = null;

	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public char[] getAllOperators() {
		return allOperators;
	}

	public void setAllOperators(char[] allOperators) {
		this.allOperators = allOperators;
	}

	public NodeTree getNodeLeft() {
		return nodeLeft;
	}

	public void setNodeLeft(NodeTree nodeLeft) {
		this.nodeLeft = nodeLeft;
	}

	public NodeTree getNodeRight() {
		return nodeRight;
	}

	public void setNodeRight(NodeTree nodeRight) {
		this.nodeRight = nodeRight;
	}

	public NodeTree getFatherNode() {
		return fatherNode;
	}

	public void setFatherNode(NodeTree fatherNode) {
		this.fatherNode = fatherNode;
	}
	

	@Override
	public String toString() {
		return "NodeTree [value=" + value + ", allOperators=" + Arrays.toString(allOperators) + ", nodeLeft=" + nodeLeft
				+ ", nodeRight=" + nodeRight + ", fatherNode=" + fatherNode + "]";
	}

	/*
	 * metodo para insertar un elemento en el arbol de manera recursiva
	 */
	public void insertar(NodeTree nodeAux) {

		if(nodeLeft==null){nodeLeft=nodeAux;}
		else{
			if(nodeLeft.verifyConditions()){nodeLeft.insertar(nodeAux);}
			else{
				if(nodeRight==null){nodeRight=nodeAux;}
				else{if(nodeRight.verifyConditions()){nodeRight.insertar(nodeAux);}}
			}
		}
	}

	/*
	 * método que verifica las condiciones para insertar un nuevo nodo
	 */
	private boolean verifyConditions() {
		if(isOperator()){
			if(nodeLeft==null || nodeRight==null)
					return true;
		}
		return false;
	}


	/*
	 * metodo que valida si el valor de este nodo es un operador
	 */
	private boolean isOperator() {
		if(this.value.length()>1)
			return false;
		for (int i = 0; i < allOperators.length; i++) {
			if(value.charAt(0)==allOperators[i])
				return true;
		}
		return false;
	}

	/*
	 * metodo para imprimir el arbol en preorder
	 */
	public void showPreorder(){
		System.out.println(value);
		if(nodeLeft!=null)
			nodeLeft.showPreorder();
		if(nodeRight!=null)
			nodeRight.showPreorder();
	}

	/*
	 * metodo que verifica si el nodo tiene hijos
	 */
	public boolean hasChildren() {
		if(nodeLeft!=null || nodeRight!=null)
			return true;
		return false;
	}

	/*
	 * metodo que obtiene el total de la operacion aritmetica
	 */
	public Double getTotal() throws ValueNumberException, NullPointerException{
		if(isOperator()){
			Double leftNumber=getLeftNumber();
			Double rightNumber=getRighNumber();
			return makeOperation(leftNumber,rightNumber);
		}
		return null;
	}

	private Double makeOperation(Double leftNumber, Double rightNumber) throws ValueNumberException {
		switch(value.charAt(0)){
		case '*': return leftNumber*rightNumber;
		case '/': return leftNumber/rightNumber;
		case '+': return leftNumber+rightNumber;
		case '-': return leftNumber+rightNumber;
		}
		throw new ValueNumberException("Error al procesar los valores(generar total{method node})");
	}

	/*
	 * metodo que obtiene elnúmero de la derecha
	 */
	private Double getRighNumber() throws ValueNumberException {
		if(nodeRight!=null){
			if(nodeRight.isOperator()){
				return nodeRight.getTotal();
			}
			else{
				return  nodeRight.getValueNumber();
			}
		}
		return null;
	}

	/*
	 * metodo que obtiene el numero de la izquierda
	 */
	private Double getLeftNumber() throws ValueNumberException {
		if(nodeLeft!=null){
			if(nodeLeft.isOperator()){
				return nodeLeft.getTotal();
			}
			else{
				return nodeLeft.getValueNumber();
			}
		}
		return null;
		
	}

	/*
	 * metodo que parsea el número
	 */
	private Double getValueNumber() throws ValueNumberException {
			try {
				double aux2=Double.parseDouble(value);
				return aux2;
			} catch (Exception e2) {
			}
		throw new ValueNumberException("Error al covertir +["+value+"] a numero");
	

}
}

