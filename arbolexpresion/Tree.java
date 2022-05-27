package arbolexpresion;

public class Tree {
	NodeTree rootNode;
	Stack<NodeTree> stack= new Stack<NodeTree>();
	String expresion;
	char[] secondOperators= {'*','/'};
	char[] thirdOperators= {'+','-'};
	char[] allOperators={'*','/','+','-','(',')'};

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
	public void insertAll(){

		while(!stack.isEmpty()){
			NodeTree nodeAux= stack.pop();
			insert(nodeAux);
		}
	}

	/*
	 * método que inserta un nodo en el arbol
	 */
	private void insert(NodeTree nodeAux)  {
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
	public void generarNodos(String expresion) {
		this.stack.clear();
		generateStack(expresion);
		insertAll();

	}

	/*
	 * metodo que  llenena la pila
	 */
	private void generateStack(String expresion){
		String expresionAux=removeSpaces(expresion);
		this.expresion=expresionAux;
		
		generateRelativeExpresion();
	}




	/*
	 * metodo que genera los nodos de la pila, usando un análisis de sintaxix(genera errores con expresiones como
	 *  2+2+2+2 ya que el metodo string.replace("old","new") borra las coincidencias de la subcadena
	 *  es el metodo más largo y mas interesante para optimizar y/o mejorar
	 */
	private void generateRelativeExpresion(){
		Stack<Character> stackAux= new Stack<Character>();
		String relativeExpresion="";
		String filtred="";

		char   charAtt;
		int    inicio;
		int    fin;

		

		while(!this.expresion.isEmpty()){
		System.out.println("palabra completa :"+expresion);
		relativeExpresion="";
		inicio=-1;
		fin=-1;


		//ciclo que filtra los parentesis
		for (int i = expresion.length()-1; i >=0; i--) {
			if(expresion.charAt(i)=='('){
				relativeExpresion="";
				for (int j = i+1; j <expresion.length(); j++) {
					charAtt=expresion.charAt(j);
					fin=j;
					if(charAtt==')')
						break;
					relativeExpresion+=charAtt;
				}
				inicio=i;
				System.out.println("frase filtrada del parentesis:"+relativeExpresion+" pos inicial:"+inicio+"pos final:"+fin);
				break;
			}
		}

		/*
		 *sección que filtra los operadores de segundo nivel
		 */
		if(relativeExpresion.isEmpty())
		{
			for (int i = expresion.length()-1; i >=0; i--) {
				if(validateSecondOperators(""+expresion.charAt(i))){
					filtred= generateExpresion(expresion,i);
					int[] rangeExpresion=getExpresionRange(expresion,i);

					rangeExpresion[0]=i-rangeExpresion[0];
					rangeExpresion[1]=i+rangeExpresion[1];
					
					System.out.println("frase filtrada (operador segundo orden) "+filtred+" inicial pos:"+rangeExpresion[0]+" fin pos: "+rangeExpresion[1]);
					stack.push(generateTreeNode(filtred));
					expresion=rmSubString(rangeExpresion[0],rangeExpresion[1]+1 , expresion);
					break;
				}
			}

		}else{
			for (int i = relativeExpresion.length()-1; i >=0; i--) {
				if(validateSecondOperators(""+relativeExpresion.charAt(i))){
					filtred= generateExpresion(relativeExpresion,i);
					int[] rangeExpresion=getExpresionRange(filtred,i);
					rangeExpresion[0]+=inicio;
					rangeExpresion[1]=fin;
					System.out.println("frase filtrada (operador segundo orden) "+filtred+" inicial pos:"+rangeExpresion[0]+" fin pos: "+rangeExpresion[1]);
					if(validateContent(relativeExpresion)){ stack.push(generateTreeNode(filtred));expresion=rmSubString(rangeExpresion[0], rangeExpresion[1], expresion);break;}
					else{ stack.push(generateTreeNode(filtred));expresion=rmSubString(rangeExpresion[0]-1, (rangeExpresion[1]+1),expresion);break;}
				}
			}
		}
		

		/*
		 * seccion que filtra los operadores de tercer nivel
		 */
		if(relativeExpresion.isEmpty())
		{
			for (int i = expresion.length()-1; i >=0; i--) {
				if(validateThirdOperator(""+expresion.charAt(i))){
					filtred= generateExpresion(expresion,i);
					int[] rangeExpresion=getExpresionRange(expresion,i);
					rangeExpresion[0]=i-rangeExpresion[0];
					rangeExpresion[1]=i+rangeExpresion[1];

					System.out.println("frase filtrada (operador tercer orden) "+filtred+" inicial pos:"+rangeExpresion[0]+" fin pos: "+rangeExpresion[1]);
					stack.push(generateTreeNode(filtred));
					expresion=rmSubString(rangeExpresion[0], rangeExpresion[1]+1, expresion);
					break;
				}
			}

		}else{
			for (int i = relativeExpresion.length()-1; i >=0; i--) {
				if(validateThirdOperator(""+relativeExpresion.charAt(i))){
					filtred= generateExpresion(relativeExpresion,i);
					int[] rangeExpresion=getExpresionRange(filtred,i);
					rangeExpresion[0]+=inicio;
					rangeExpresion[1]=fin;
					System.out.println("frase filtrada (operador tercer orden) "+filtred+" inicial pos:"+rangeExpresion[0]+" fin pos: "+rangeExpresion[1]);
					if(validateContent(relativeExpresion)){ stack.push(generateTreeNode(filtred));expresion=rmSubString(rangeExpresion[0], rangeExpresion[1], expresion);break;}
					else{ stack.push(generateTreeNode(filtred));expresion=rmSubString(rangeExpresion[0]-1, (rangeExpresion[1]+1),expresion);break;}
				}
			}
		}


		}
	}
	/**
	 * metodo que verifica si un String tiene más de un operador
	 * @param relativeExpresion
	 * @return true si tiene más, falso de lo contrario
	 */
	private boolean validateContent(String relativeExpresion) {
		int contador=0;

		for (int i = 0; i < relativeExpresion.length(); i++) {
			if(isOperator(relativeExpresion.charAt(i)))
				contador++;
			}
		if(contador!=1)
			return true;
		return false;
	}


	/*
	 * método que calcula el valor absoluto de la distancia del número de la izquierda y derecha con 
	 * respecto al operador
	 */
	private int[] getExpresionRange(String filtred, int i) {
		int[] range= {0,0}; 

		for (int j = (i+1); j < filtred.length(); j++) {
			if(isOperator(filtred.charAt(j)))
				break;
			range[1]++;
		}

		for (int j = (i-1); j >= 0; j--) {
			if(isOperator(filtred.charAt(j)))
				break;
			range[0]++;
		}

		return range;
	}


	/*
	 * método que genera una expresion usando el indice i como operador de la misma
	 * mediante ciclos se desplaza hacia la izquierda hasta llegar a otro operador y 
	 * captura todo lo que hay en el trayecto, lo mismo hace hacia la derecha
	 * al final retorna esa captura de caracteres
	 */
	private String generateExpresion(String expresion2, int i)  {
		String relativeExpresion;
		if(expresion2.length()==1){
			return expresion2;
		}

		String leftExpresion=getLeftExpresion(expresion2,i);
		String rightExpresion=getRightExpresion(expresion2,i);

		if(!leftExpresion.isEmpty() || !rightExpresion.isEmpty() && expresion2.length()>1){
			relativeExpresion=leftExpresion+expresion2.charAt(i)+rightExpresion;
			return relativeExpresion;
		}
		return "";

	}



	/*
	 * método que elimina un rango de caracteres de un string 
	 */
	private String rmSubString(int inicio, int fin, String string) {
		StringBuffer strB= new StringBuffer(string);
		strB.replace(inicio,fin, "");

		return strB.toString();
	}


	/*
	 * método que genera un nodo usando una expresion relativa
	 * 
	 */
	private NodeTree generateTreeNode(String relativeExpresion)  {

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
				break;
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
