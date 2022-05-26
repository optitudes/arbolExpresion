package arbolexpresion;

public class Tree {
	NodeTree rootNode;
	Stack<String> stack= new Stack<String>();
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
	
	public void insertAll(){

		while(!stack.isEmpty()){

			String value=stack.pop();
			String operator="";
			String leftExpresion="";
			String rightExpresion="";




			for (int i = 0; i <value.length(); i++) {
				if(isOperator(value.charAt(i))){
					operator=""+value.charAt(i);
					leftExpresion=getLeftExpresion(value, i);
					rightExpresion=getRightExpresion(value, i);

					insert(operator);
					if(!leftExpresion.isEmpty())
						insert(leftExpresion);
					if(!rightExpresion.isEmpty())
						insert(rightExpresion);
				}
			}
		}
	}
	
	private void insert(String value) {
		if(rootNode==null){rootNode=new NodeTree(value);}
		else{rootNode.insertar(value);}
		
	}


	public void showPreorder(){
		if(rootNode!=null)
			rootNode.showPreorder();
	}



	public void generarNodos(String expresion) throws SintaxError, StackError {
		validarExpresion(expresion);
		this.stack.clear();
		generateStack(expresion);
		insertAll();

	}
	private void validarExpresion(String expresion) {

		
	}


	private void generateStack(String expresion) throws SintaxError, StackError {
		String expresionAux;
		expresionAux=removeSpaces(expresion);
		
		generateRelativeExpresion(expresionAux);
		if(stack.isEmpty())
			throw new StackError("No se pudo generar el stack de expresiones");
	}


	private String rmRelativeExpresion(String expresionAux) {
		// TODO Auto-generated method stub
		return null;
	}



	private void generateRelativeExpresion(String expresion)throws SintaxError {
		Stack<Character> stackAux= new Stack<Character>();
		String relativeExpresion="";
		System.out.println("palabra completa :"+expresion);

		//ciclo que filtra los parentesis
		while(expresion.contains("(") || expresion.contains(")")){

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
					this.stack.push(relativeExpresion);

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
					String leftExpresion=getLeftExpresion(expresion,i);
					String rightExpresion=getRightExpresion(expresion,i);
					if(!leftExpresion.isEmpty() || !rightExpresion.isEmpty() && expresion.length()>1){
						relativeExpresion=leftExpresion+expresion.charAt(i)+rightExpresion;
						System.out.println("frase filtrada :" +relativeExpresion);
						this.stack.push(relativeExpresion);
						expresion=expresion.replace(relativeExpresion, "");
						break;
					}
					if(expresion.length()==1){
						this.stack.push(""+expresion.charAt(0));
						expresion="";
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
					String leftExpresion=getLeftExpresion(expresion,i);
					String rightExpresion=getRightExpresion(expresion,i);
					
					if(!leftExpresion.isEmpty() || !rightExpresion.isEmpty() && expresion.length()>1){
						relativeExpresion=leftExpresion+expresion.charAt(i)+rightExpresion;
						System.out.println("frase filtrada :" +relativeExpresion);
						this.stack.push(relativeExpresion);
						expresion=expresion.replace(relativeExpresion, "");
						break;
					}
					if(expresion.length()==1){
						this.stack.push(""+expresion.charAt(0));
						expresion="";
						
					}
					
									}
			}
		}
	}

	private boolean validateThirdOperator(String expresion) {
		for (int i = 0; i < thirdOperators.length; i++) {
			if(expresion.contains(""+thirdOperators[i]))
				return true;
		}
		return false;
	}


		private String getRightExpresion(String expresion, int i) {
			String rightExpresion="";
			for (int j = (i+1); j <expresion.length() ; j++) {
				if(isOperator(expresion.charAt(j)))
					break;
				rightExpresion+=""+expresion.charAt(j);
			}
		return rightExpresion;
	}


		private String getLeftExpresion(String expresion, int i) {
			String leftExpresion="";
			for (int j = (i-1); j >=0; j--) {
				if(isOperator(expresion.charAt(j)))
					break;
				leftExpresion+=""+expresion.charAt(j);
				
			}
		return leftExpresion;
	}


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


	private String removeSpaces(String expresion) {
		while(expresion.contains(" ")){
			expresion=expresion.replaceAll(" ","");
		}
		return expresion;
	}


	public String getTotal() throws ValueNumberException {
		if(rootNode!=null)
			return ""+rootNode.getTotal();
		return null;

	}

}
