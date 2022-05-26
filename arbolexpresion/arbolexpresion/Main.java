package arbolexpresion;

import javax.swing.JOptionPane;
import arbolexpresion.Utilidades;
import javafx.scene.control.Alert.AlertType;

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
		} catch (SintaxError | StackError | ValueNumberException | GenerateNodeException | NullPointerException e) {
			JOptionPane.showMessageDialog(null, "Error cause :"+e.getCause()+" msm:"+e.getMessage());
			
			
		}
		
		

	}

}
