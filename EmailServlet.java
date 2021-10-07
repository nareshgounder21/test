package com.ltfssolr.site.core.servlets;

import com.day.cq.mailer.MessageGateway;
import com.day.cq.mailer.MessageGatewayService;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.apache.felix.scr.annotations.Reference;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component(service = Servlet.class, property = {
		"sling.servlet.methods=" + HttpConstants.METHOD_POST, "sling.servlet.paths=" + "/bin/email" })
public class EmailServlet extends SlingAllMethodsServlet {
	
	private static final long serialVersionUID = 1L;

	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	@Reference
	private MessageGatewayService messageGatewayService;
	
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);

	}

	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		try {
			
			//int emailLevel = Integer.parseInt(request.getParameter("complaintLevel"));
			String emailId = "nareshgounder990@gmail.com,nareshjd185@gmail.com";

			String[] emailIdRecipients = new String[] {};
			emailIdRecipients = emailId.split(",");

			for (int i = 0; i < emailIdRecipients.length; i++) {
				MessageGateway<Email> messageGateway;

				// Set up the Email message
				Email email = new SimpleEmail();

				// Set the mail values
				String emailToRecipients =emailIdRecipients[i];
				StringBuilder msgBody = new StringBuilder(100);
				msgBody.append("TeknoPoint Multimedia Pvt Ltd");
				email.addTo(emailToRecipients);
				email.setSubject("Test");
				email.setMsg("Test Msg - " +msgBody.toString());

				// Inject a MessageGateway Service and send the message
				messageGateway = messageGatewayService.getGateway(Email.class);
				
				// Check the logs to see that messageGateway is not null
				messageGateway.send(email);
				
				log.info("email sent");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Email Error", e);
		}
	}

}
