package com.tutorialspoint;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.rmi.server.RemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ContactDao extends RemoteObject {
	
	public List<Contact> getAllContacts() {
		List<Contact> contactList = null;
		try {
			File file = new File("Contacts1.dat");
			if (!file.exists()) {
				contactList = new ArrayList<Contact>();
				//createTable();
				Contact contact = new Contact("278215", "AAw", "Michael", "dunder@mail.gr");
				Contact aCon1 = new Contact("2310248531", "123Qa", "Jan", "Jan@gmail.com");
				Contact aCon2 = new Contact("1234", "123Az", "Azzuro", "azzurro@gmail.com");
				//insertInDB("1234", "123Az", "Azzuro", "azzurro@gmail.com");
				//insertInDB("0246", "123By", "Bayeern", "Johnas@gmail.com");
				
				contactList.add(contact);
				contactList.add(aCon1);
				contactList.add(aCon2);
				saveContactList(contactList);
			} else {
				FileInputStream fis = new FileInputStream(file);
				ObjectInputStream ois = new ObjectInputStream(fis);
				contactList = (List<Contact>) ois.readObject();
				ois.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return contactList;
	}
	
	public void  selectFromDB() throws RemoteException {
		//Hashtable<String, ArrayList<String>> storePhone = new Hashtable<String, ArrayList<String>>();
		System.out.println("Testing SQLite Base.");
		List<Contact> contactList = new ArrayList<Contact>();
		try {
			Class.forName("org.sqlite.JDBC");
			String url = "jdbc:sqlite:test.db";
			Connection con = DriverManager.getConnection(url);
			Statement st = con.createStatement();
			
			String query = "select * from contact";
			ResultSet rs = st.executeQuery(query);
			
			while (rs.next()) {
				String phone = rs.getString("phone");
				String id = rs.getString("id");
				String name = rs.getString("name");
				String email = rs.getString("email");
				
				/*Contact aContact = new Contact(phone, id, name, email);
				contactList.add(aContact);*/
				/*ArrayList<String> array = new ArrayList<String>();
				array.add(id);
				array.add(name);
				array.add(email);
				storePhone.put(phone, array);*/
				
			}

			rs.close();
			st.close();
			con.close();

		} catch(Exception e) {
			System.out.println(e);
		}
		//return contactList;
		
	}
	
	public int addContact(Contact aContact) throws RemoteException {
		List<Contact> contactList = getAllContacts();
		boolean contactExists = false;
		insertInDB("test", "test", "test", "testtset");
		for(Contact contact: contactList) {
			if(contact.getPhone().equals(aContact.getPhone()) ) {
				contactExists = true;
				break;
			}
		}
		if(!contactExists) {
			contactList.add(aContact);
			saveContactList(contactList);
			String phone, id, name, mail;
			phone = aContact.getPhone();
			id = aContact.getId();
			name = aContact.getName();
			mail = aContact.getMail();
			insertInDB(phone, id, name, mail);
			return 1;
		}
		return 0;
	}
	
	public void insertInDB(String phone, String id, String name, String mail) throws RemoteException{
		try {
			Class.forName("org.sqlite.JDBC");
			
			String url = "jdbc:sqlite:test.db";
			Connection con = DriverManager.getConnection(url);
			
			
			Statement st = con.createStatement();
			String sql = "insert into contact (phone, id, name, email) values ('"+phone+"', '"+id+"', '"+name+"', '"+mail+"');";
			st.execute(sql);
				//st.execute("insert into contact values ('2310221525', '1a23s', 'John', 'John@gmail.com')");
                //st.execute("insert into contact values ('2310248531', '123Qa', 'Jan', 'Jan37@gmail.com')");

			String query = "select * from contact";
			ResultSet rs = st.executeQuery(query);
			/*while (rs.next()) {
				System.out.println(rs.getString(1));
			}*/	

			rs.close();
			st.close();
			con.close();

		} catch(Exception e) {
			System.out.println(e);
		}
		System.out.println();
	}
	
	
	public int updateContact(Contact aContact) {
		List<Contact> contactList = getAllContacts();
				for(Contact contact: contactList) {
					if(contact.getPhone().equals(aContact.getPhone()) ) {
						int index = contactList.indexOf(contact);
						contactList.set(index, contact);
						saveContactList(contactList);
						
						String aPhone, anId, aName, anEmail;
						aPhone = contact.getPhone();
						anId = contact.getId();
						aName = contact.getName();
						anEmail = contact.getMail();
						try {
							updateDB(aPhone, anId, aName, anEmail);
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						return 1;
					}
				}
		return 0;
	}
	
	public void updateDB(String phone, String id, String name, String email) throws RemoteException {
		System.out.println("Testing SQLite Base.");
		try {
			Class.forName("org.sqlite.JDBC");
			String url = "jdbc:sqlite:test.db";
			Connection con = DriverManager.getConnection(url);

			Statement st = con.createStatement();

			String sql = "update contact set id = '"+id+"',name='"+name+"',email = '"+email+"' where phone='"+phone+"' ";
			st.executeUpdate(sql);

			String query = "select * from contact";
			ResultSet rs = st.executeQuery(query);
/*			while (rs.next()) {
				String phone = rs.getString("phone");
				String id = rs.getString("id");
				String name = rs.getString("name");
				String email = rs.getString("email");
				System.out.println("PHONE = " + phone);
				System.out.println("ID = " + id);
				System.out.println("NAME = " + name);
				System.out.println("EMAIL = " + email);
				System.out.println();
				
			}*/	

			rs.close();
			st.close();
			con.close();

		} catch(Exception e) {
			System.out.println(e);
		}

		//System.out.println("End of updating testing.");
		//System.out.println();
	}
	
	public int deleteContact(String phone) {
		List<Contact> contactList = getAllContacts();
		for(Contact contact: contactList) {
			if(contact.getPhone().equals(phone)) {
				int index = contactList.indexOf(contact);
				contactList.remove(index);
				saveContactList(contactList);
				
				String aPhone;
				aPhone = contact.getPhone();				
				try {
					deleteDB(aPhone);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return 1;
			}
		}
		return 0;
	}
	
	//delete contact from DB
	public void deleteDB(String phone) throws RemoteException {
		System.out.println("Testing SQLite Base.");
		try {
			Class.forName("org.sqlite.JDBC");
			
			String url = "jdbc:sqlite:test.db";
			Connection con = DriverManager.getConnection(url);
			
			Statement st = con.createStatement();
			
			String sql = "delete from contact where phone ='"+phone+"' ";
			st.executeUpdate(sql);
            			
			String query = "select * from contact";
			ResultSet rs = st.executeQuery(query);

			rs.close();
			st.close();
			con.close();

		} catch(Exception e) {
			System.out.println(e);
		}

		System.out.println("End of deleting testing.");
		System.out.println();
		
	}

	
	public Contact getContact(String phone) {
		List<Contact> contacts = getAllContacts();
		for(Contact contact: contacts) {
			if(contact.getPhone().equals(phone)) {
				return contact;
			}
		}
		return null;
	}
	
	private void saveContactList(List<Contact> contactList) {
		try {
			File file = new File("Contacts.dat");
			FileOutputStream fos;
			fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(contactList);
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void createTable() {
		//System.out.println("Testing SQLite Base.");
		try {
			Class.forName("org.sqlite.JDBC");
			
			String url = "jdbc:sqlite:test.db";
			Connection con = DriverManager.getConnection(url);
			
			Statement st = con.createStatement();

			st.execute("create table contact(PHONE varchar(10) primary key not null, ID varchar(10), NAME varchar(60), EMAIL varchar(60) )");

			st.close();
			con.close();

		} catch(Exception e) {
			System.out.println(e);
		}

		//System.out.println("End of creating testing.");
		System.out.println();
	}
	
	// get Contact From DB
	public ArrayList<String> selectContactFromDB(String phone) throws RemoteException {
		ArrayList<String> array = new ArrayList<String>();
		try {
			Class.forName("org.sqlite.JDBC");
			
			String url = "jdbc:sqlite:test.db";
			Connection con = DriverManager.getConnection(url);
			
			Statement st = con.createStatement();
			
			String query = "select * from contact where phone ='"+phone+"' ";
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				String id = rs.getString("id");
				String name = rs.getString("name");
				String email = rs.getString("email");
				//ArrayList<String> array = new ArrayList<String>();
				array.add(phone);
				array.add(id);
				array.add(name);
				array.add(email);
			}
		}
		catch (Exception e) {
			System.out.println("----Exception e. AddressBookImpl/selectContactFromDB.----");
		}
		
		System.out.println();
		return array;
	}
	
}
