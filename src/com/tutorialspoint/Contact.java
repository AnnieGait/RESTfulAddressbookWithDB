package com.tutorialspoint;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name = "contact") 

public class Contact extends UnicastRemoteObject implements Serializable {
	private static final long serialVersionUID = 1L; 
	private String phone;
	private String id;
	private String name;
	private String mail;
	
	public Contact() throws RemoteException{}
	
	public Contact(String phone, String id, String name, String mail) throws RemoteException{
		this.phone = phone;
		this.id = id;
		this.name = name;
		this.mail = mail;
	}

	public String getPhone() {
		return phone;
	}
	
	@XmlElement
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getId() {
		return id;
	}
	@XmlElement
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	@XmlElement
	public void setName(String name) {
		this.name = name;
	}

	public String getMail() {
		return mail;
	}

	@XmlElement
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	
	

}
