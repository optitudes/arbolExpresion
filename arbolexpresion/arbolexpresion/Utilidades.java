package arbolexpresion;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class Utilidades {

	public static void mostrarMensaje(String titulo, String header, String contenido, AlertType alertType) {

		Alert alert = new Alert(alertType);
		alert.setTitle      (titulo);
		alert.setHeaderText (header);
		alert.setContentText(contenido);
		alert.showAndWait   ();
	}

	public static boolean mostrarMensajeConfirmacion(String mensaje) {
		Alert alert  = new Alert (Alert.AlertType.CONFIRMATION);
		alert.setTitle(null);
		alert.setHeaderText("Confirmacion");
		alert.setContentText(mensaje);
		Optional<ButtonType> action = alert.showAndWait();

		if(action.get() == ButtonType.OK){
			return true;

		}else{
			return false;
		}
	}
}
