package mx.tesseract.util;


import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import mx.tesseract.admin.model.Colaborador;

public class Correo {

    public static void enviarCorreo(Colaborador obj, int sub) throws AddressException, MessagingException {

            String mailServidor = "tesseractv1escom@gmail.com";
            String passwordServidor = "contrasenaglobal123";
            // Propiedades de la conexión.
            Properties props = new Properties();  
	        props.put("mail.smtp.starttls.enable","true");
	        props.put("mail.smtp.host","smtp.gmail.com");  
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.port", "587");

            Session session=Session.getDefaultInstance(props, null);
        session.setDebug(true);

            // Se construye el mensaje a enviar
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mailServidor));
			// FIXME Sacar el mail de contactos

			//prueba de usuario
            //la contraseña con tamaño 6
            message.addRecipients(Message.RecipientType.TO,
            InternetAddress.parse(obj.getCorreoElectronico()));
            String contenido;
            if (sub == 0) {

                message.setSubject("TESSERACT: Información de la cuenta");
                contenido = "<center>"
                			//+ "<div style=\"color: #084B8A; border: 1px dotted black;\">"
                			+ "<div style=\"color: #750e35; border: 1px dotted black;\">"
                				+ "<table>"
			                		+ "<tr>"
										+ "<td>"
												+ "<h2><b>Bienvenido(a) a TESSERACT</b></h2>"
											
										+ "</td>" + "</tr>"
								+ "</table>"
							+ "</div>"
							//+ "<div style=\"color: #FFFFFF;background-color: #084B8A;opacity: .85;\">"
							+ "<div style=\"color: #FFFFFF;background-color: #750e35;opacity: .85;\">"
                				+ "<table>"
                					+ "<tr>" 
                						+ "<td>"
                							+"Los datos con los que deberá iniciar sesión son:"
                						+ "</td>" + "</tr>"
                					+ "<tr>" 
                						+ "<td>"
                							+ "Nombre de usuario: " + obj.getCorreoElectronico()
                						+ "</td>" + "</tr>"
                					+ "<tr>" 
                						+ "<td>"
                							+ "Contraseña: " + obj.getContrasenia()
                							+ "</td>" + "</tr>"
                				+ "</table>"
                			+ "</div>"
                			+ "</center>";
                        

                message.setContent(contenido, "text/html");

            }
            if (sub == 1) {
                message.setSubject("TESSERACT: Recuperación de contraseña");
                contenido = //"<center><div style=\"color: #084B8A; border: 1px dotted black;\"><table>"
                		"<center><div style=\"color: #084B8A; border: 1px dotted black;\"><table>"
                        + "<tr>"
                        + "<td>"
                        + "<h2><b>Hola "
                        + obj.getNombre()
                        + "</td>" + "</tr>"
                        //+ "<div style=\"color: #FFFFFF;background-color: #084B8A;opacity: .85;\">Recientemente ha solicitado recuperar la contraseña de su cuenta del sistema <i>TESSERACT</i>."
                        + "<div style=\"color: #FFFFFF;background-color: #750e35;opacity: .85;\">Recientemente ha solicitado recuperar la contraseña de su cuenta del sistema <i>TESSERACT</i>."
                        + "<tr>" + "<td>"
                        + "Contraseña: " + obj.getContrasenia()
                        + "</td>" + "</tr>"
                        + "</div>"
                        + "</table></center>";

                message.setContent(contenido, "text/html");

            }
            //send the message
            
            Transport t = session.getTransport("smtp");
            t.connect(mailServidor, passwordServidor);
            
            //t.sendMessage(message, message.getAllRecipients());
            t.sendMessage(message, message.getAllRecipients());
            System.out.println("Correo enviado...");

            // Cierre.
            t.close();
    }
}
