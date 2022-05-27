package arbolexpresion;

import javax.swing.JOptionPane;

public class Main {

	public static void main(String[] args) {
		Tree arbolExpresion= new Tree();
		String resultado;
		String expresion=JOptionPane.showInputDialog("Ingrese la operacion aritmetica");
		try {
			if(!expresion.isEmpty()){arbolExpresion.generarNodos(expresion);}
			arbolExpresion.showPreorder();
			resultado=arbolExpresion.getTotal();
			JOptionPane.showMessageDialog(null, "resultado :"+resultado);
		} catch ( ValueNumberException  | NullPointerException e) {
			JOptionPane.showMessageDialog(null, "Error cause :"+e.getCause()+" msm:"+e.getMessage());
			
			
		}
		
		

	}

}
