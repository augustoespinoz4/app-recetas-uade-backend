package yummly_app.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServicio {

    @Autowired
    private JavaMailSender mailSender;


	public void enviarCodigoRecuperacion(String destinatario, String codigo) {
	    MimeMessage mensaje = mailSender.createMimeMessage();
	
	    try {
	        MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");
	        helper.setTo(destinatario);
	        helper.setSubject("Tu código de verificación");
	        helper.setFrom("equipochascomus@gmail.com");
	
	        String cuerpoHtml = """
	            <html>
	            <body style="font-family: Arial, sans-serif; color: #333;">
	                <p>Hola,</p>
	                <p>Usá este código para continuar con la recuperación de tu cuenta:</p>
	                <p style="font-size: 24px; font-weight: bold; background-color: #f2f2f2; 
	                          padding: 10px; display: inline-block; border-radius: 8px;">
	                    %s
	                </p>
	                <p>Este código es válido por 10 minutos.</p>
	                <p>Si no solicitaste este código, podés ignorar este mensaje.</p>
	                <p>Saludos,<br/>Yummly App</p>
	            </body>
	            </html>
	            """.formatted(codigo);
	
	        helper.setText(cuerpoHtml, true); // true = HTML
	        mailSender.send(mensaje);
	
	    } catch (MessagingException e) {
	        // Manejar error, por ejemplo loguearlo
	        e.printStackTrace();
	    }
	}

    
	public void enviarMensajeBienvenida(String destinatario, String alias) {
	    MimeMessage mensaje = mailSender.createMimeMessage();

	    try {
	        MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");
	        helper.setTo(destinatario);
	        helper.setSubject("¡Bienvenido a la aplicación!");
	        helper.setFrom("equipochascomus@gmail.com");

	        String cuerpoHtml = """
	            <html>
	            <body style="font-family: Arial, sans-serif; color: #333;">
	                <p>Hola <strong>%s</strong>,</p>
	                <p>¡Gracias por registrarte en nuestra aplicación!</p>

	                <p>Tu registración fue exitosa. Ahora podés:</p>
	                <ul>
	                    <li>Iniciar sesión con tu alias: <strong>%s</strong></li>
	                    <li>Completar tu perfil con nombre, apellido y una contraseña segura</li>
	                </ul>

	                <p style="margin-top: 16px;">
	                    👉 Una vez completado, vas a poder disfrutar de todas las funcionalidades como visitante.
	                </p>

	                <p>Saludos,<br/>Yummly App</p>
	            </body>
	            </html>
	            """.formatted(alias, alias);

	        helper.setText(cuerpoHtml, true);
	        mailSender.send(mensaje);

	    } catch (MessagingException e) {
	        e.printStackTrace();
	    }
	}

    
	public void enviarConfirmacionRegistroCompleto(String destinatario, String nombre) {
	    MimeMessage mensaje = mailSender.createMimeMessage();

	    try {
	        MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");
	        helper.setTo(destinatario);
	        helper.setSubject("¡Registro completado con éxito!");
	        helper.setFrom("equipochascomus@gmail.com");

	        String nombreUsuario = (nombre != null && !nombre.isBlank()) ? nombre : "usuario";

	        String cuerpoHtml = """
	            <html>
	            <body style="font-family: Arial, sans-serif; color: #333;">
	                <p>Hola <strong>%s</strong>,</p>
	                <p>Tu registro ha sido completado exitosamente. 🎉</p>

	                <p>Ahora podés acceder a todas las funcionalidades disponibles para los usuarios visitantes.</p>

	                <p>¡Gracias por formar parte de nuestra comunidad!</p>

	                <p>Saludos,<br/>Yummly App</p>
	            </body>
	            </html>
	            """.formatted(nombreUsuario);

	        helper.setText(cuerpoHtml, true);
	        mailSender.send(mensaje);

	    } catch (MessagingException e) {
	        e.printStackTrace();
	    }
	}

}

