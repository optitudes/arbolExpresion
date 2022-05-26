package arbolexpresion;

public class Tree {
	NodeTree rootNode;
	Stack<NodeTree> stack= new Stack<NodeTree>();
	char[] secondOperators= {'*','/'};
	char[] thirdOperators= {'+','-'};
	char[] allOperators={'*','/','+','-'};

	public Tree(NodeTree rootNode) {
		super();
		this.rootNode = rootNode;
	}


	public Tree() {
		super();
	}


	public NodeTree getRootNode() {
		return rootNode;
	}

	public void setRootNode(NodeTree rootNode) {
		this.rootNode = rootNode;
	}

	@Override
	public String toString() {
		return "Arbol [rootNode=" + rootNode + "]";
	}
	
	/*
	 * método que inserta todos los nodos de la pila en el arbol
	 */
	public void insertAll() throws GenerateNodeException{

		while(!stack.isEmpty()){
			NodeTree nodeAux= stack.pop();
			insert(nodeAux);
		}
	}

	/*
	 * método que inserta un nodo en el arbol
	 */
	private void insert(NodeTree nodeAux) throws GenerateNodeException {
		if(rootNode==null){rootNode=nodeAux;}
		else{
			rootNode.insertar(nodeAux);
		}
	}



	/*
	 * método que imprimer el arbol en preorder
	 */
	public void showPreorder(){
		if(rootNode!=null)
			rootNode.showPreorder();
	}



	/*
	 * método que genera los nodos del arbol(usando la expresion aritmetica)
	 */
	public void generarNodos(String expresion) throws SintaxError, StackError, GenerateNodeException {
		this.stack.clear();
		generateStack(expresion);
		insertAll();

	}

	/*
	 * metodo que  llenena la pila
	 */
	private void generateStack(String expresion) throws SintaxError, StackError, GenerateNodeException {
		String expresionAux;
		expresionAux=removeSpaces(expresion);
		
		generateRelativeExpresion(expresionAux);
		if(stack.isEmpty())
			throw new StackError("No se pudo generar el stack de expresiones");
	}




	/*
	 * metodo que genera los nodos de la pila, usando un análisis de sintaxix(genera errores con expresiones como
	 *  2+2+2+2 ya que el metodo string.replace("old","new") borra las coincidencias de la subcadena
	 *  es el metodo más largo y mas interesante para optimizar y/o mejorar
	 */
	private void generateRelativeExpresion(String expresion)throws SintaxError, GenerateNodeException {
		Stack<Character> stackAux= new Stack<Character>();
		String relativeExpresion="";
		System.out.println("palabra completa :"+expresion);

		//ciclo que filtra los parentesis
		while(expresion.contains("(") ){

			relativeExpresion="";
			
			for (int i = expresion.length()-1; i >=0; i--) {
				if(expresion.charAt(i)=='('){
					stackAux.push('(');

					for (int j = (i+1); j <expresion.length(); j++) {
						char charAtt=expresion.charAt(j);
						if(charAtt=='('){stackAux.push(charAtt);}
						if(charAtt==')'){stackAux.unstack();if(stackAux.isEmpty()){break;} }
						relativeExpresion+=charAtt;
					}
					System.out.println("frase filtrada"+relativeExpresion);
					this.stack.push(generateTreeNode(relativeExpresion));

					if(!stackAux.isEmpty())
						throw new SintaxError("sobra un ["+stackAux.pop()+"]");
					expresion=expresion.replace("("+relativeExpresion+")", "");
					break;

				}
			}
		}

		/*
		 * ciclo que filtra los operadores de segundo nivel
		 */
		while(validateSecondOperators(expresion)){

			relativeExpresion="";

			for (int i = expresion.length()-1; i >=0; i--) {
				if(validateSecondOperators(""+expresion.charAt(i))){

					if(expresion.length()==1){
						this.stack.push(generateTreeNode(""+expresion.charAt(0)));
						expresion="";
						break;
					}

					String leftExpresion=getLeftExpresion(expresion,i);
					String rightExpresion=getRightExpresion(expresion,i);
					
					if(!leftExpresion.isEmpty() || !rightExpresion.isEmpty() && expresion.length()>1){
						relativeExpresion=leftExpresion+expresion.charAt(i)+rightExpresion;
						System.out.println("frase filtrada :" +relativeExpresion);
						this.stack.push(generateTreeNode(relativeExpresion));
						expresion=expresion.replace(relativeExpresion, "");
						break;
					}
									}
			}
		}

		/*
		 * ciclo que filtra los operadores de tercer nivel
		 */
		while(validateThirdOperator(expresion)){

			relativeExpresion="";

			for (int i = expresion.length()-1; i >=0; i--) {
				if(validateThirdOperator(""+expresion.charAt(i))){

					if(expresion.length()==1){
						this.stack.push(generateTreeNode(""+expresion.charAt(0)));
						expresion="";
						break;
					}


					String leftExpresion=getLeftExpresion(expresion,i);
					String rightExpresion=getRightExpresion(expresion,i);
					
					if(!leftExpresion.isEmpty() || !rightExpresion.isEmpty() && expresion.length()>1){
						relativeExpresion=leftExpresion+expresion.charAt(i)+rightExpresion;
						System.out.println("frase filtrada :" +relativeExpresion);
						this.stack.push(generateTreeNode(relativeExpresion));
						expresion=expresion.replace(relativeExpresion, "");
						break;
					}
				}
			}
		}
	}

	/*
	 * método que genera un nodo usando una expresion relativa
	 * 
	 */
	private NodeTree generateTreeNode(String relativeExpresion) throws GenerateNodeException {

		NodeTree nodeAux=null;
		NodeTree nodeLeft;
		NodeTree nodeRight;
		String value=relativeExpresion;
		String operator="";
		String leftExpresion="";
		String rightExpresion="";

		for (int i = 0; i <value.length(); i++) {
			if(isOperator(value.charAt(i))){
				operator=""+value.charAt(i);
				leftExpresion=getLeftExpresion(value, i);
				rightExpresion=getRightExpresion(value, i);

				nodeAux=new NodeTree(operator);
				if(!leftExpresion.isEmpty()){nodeLeft=new NodeTree(leftExpresion);nodeAux.setNodeLeft(nodeLeft);}
				if(!rightExpresion.isEmpty()){nodeRight=new NodeTree(rightExpresion);nodeAux.setNodeRight(nodeRight);}
			}
		}
		return nodeAux;
	}


	/*
	 * método que valida si un string contiene un operador terciario(+,-)
	 */
	private boolean validateThirdOperator(String expresion) {
		for (int i = 0; i < thirdOperators.length; i++) {
			if(expresion.contains(""+thirdOperators[i]))
				return true;
		}
		return false;
	}


	/*
	 * metodo que obtiene todos los números hacia la derecha antes de un operador
	 */
	private String getRightExpresion(String expresion, int i) {
		String rightExpresion="";
		for (int j = (i+1); j <expresion.length() ; j++) {
			if(isOperator(expresion.charAt(j)))
				break;
			rightExpresion+=""+expresion.charAt(j);
		}
		return rightExpresion;
	}


	/*
	 * 
	 * metodo que obtiene todos los números hacia la izquierda antes d eun operador
	 */
	private String getLeftExpresion(String expresion, int i) {
		String leftExpresion="";
		for (int j = (i-1); j >=0; j--) {
			if(isOperator(expresion.charAt(j)))
					break;
				leftExpresion+=""+expresion.charAt(j);
				
			}
		return leftExpresion;
	}


	/*
	 * metodo que valida si un caracter es un operador
	 */
	private boolean isOperator(char charAtt) {
		for (int i = 0; i < allOperators.length; i++) {
			if(allOperators[i]==charAtt)
				return true;
		}
			return false;
		}


	/*
		 * método que valida si la exprresion contiene operadores de segundo nivel
		 */
	private boolean validateSecondOperators(String expresion) {
		for (int i = 0; i < secondOperators.length; i++) {
			if(expresion.contains(""+secondOperators[i]))
				return true;
		}
		return false;
	}

	

	/*
	 * metodo que elimina todos los espacios de una cadena
	 */
	private String removeSpaces(String expresion) {
		while(expresion.contains(" ")){
			expresion=expresion.replaceAll(" ","");
		}
		return expresion;
	}


	/*
	 * método que genera el total de la operacion aritmetica
	 */
	public String getTotal() throws ValueNumberException {
		if(rootNode!=null)
			return ""+rootNode.getTotal();
		return null;

	}

}
