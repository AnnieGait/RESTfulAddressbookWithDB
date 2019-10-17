package com.tutorialspoint;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
@Path("/ContactService")

public class ContactService  {
	
	ContactDao contactDao = new ContactDao();
	private static final String SUCCESS_RESULT = "<result>success</result>"; 
	private static final String FAILURE_RESULT = "<result>failure</result>"; 
	   
	@GET
	@Path("/contacts")
	@Produces(MediaType.APPLICATION_XML)
	public List<Contact> getContacts() {
		return contactDao.getAllContacts();
	}
	
	@GET
	@Path("/contacts/{contactid}")
	@Produces(MediaType.APPLICATION_XML) 
	public Contact getContact(@PathParam("contactid") String contactid) {
		return contactDao.getContact(contactid);
	}
	
	@PUT
	@Path("/contacts")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String createContact(@FormParam("id") String id, 
			@FormParam("mail") String mail,
			@FormParam("name") String name, 
			@FormParam("phone") String phone,
		    @Context HttpServletResponse servletResponse) throws IOException{
		Contact contact = new Contact(phone, id, name, mail);
		int result = contactDao.addContact(contact);
		if(result == 1){ 
	         return SUCCESS_RESULT; 
	     } 
	     return FAILURE_RESULT; 
		
	}
	
	@POST
	@Path("/contacts")
	@Produces(MediaType.APPLICATION_XML)
	 @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String updateContact(@FormParam("id") String id, 
			@FormParam("mail") String mail,
			@FormParam("name") String name, 
			@FormParam("phone") String phone,
		    @Context HttpServletResponse servletResponse) throws IOException {
		Contact contact = new Contact(phone, id, name, mail);
		int result = contactDao.updateContact(contact);
		if(result == 1) {
			return SUCCESS_RESULT;
		}
		return FAILURE_RESULT;
	}
	
	@DELETE 
	@Path("/contacts/{contactphone}") 
	@Produces(MediaType.APPLICATION_XML) 
	public String deleteContact(@PathParam("contactphone") String contactphone) {
		int result = contactDao.deleteContact(contactphone);
		if(result == 1) { 
	       return SUCCESS_RESULT; 
	    } 
	    return FAILURE_RESULT;
	}
	
	@OPTIONS 
	@Path("/contacts") 
	@Produces(MediaType.APPLICATION_XML) 
	public String getSupportedOperations(){ 
	   return "<operations>GET, PUT, POST, DELETE</operations>"; 
	} 
	
 
/*	   @OPTIONS 
	   @Path("/users") 
	   @Produces(MediaType.APPLICATION_XML) 
	   public String getSupportedOperations(){ 
	      return "<operations>GET, PUT, POST, DELETE</operations>"; 
	   } */
	


}
