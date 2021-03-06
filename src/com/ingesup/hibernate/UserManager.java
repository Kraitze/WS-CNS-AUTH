package com.ingesup.hibernate;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.query.Query;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.ingesup.model.User;

public class UserManager {
	
	/**
	 * Return the id of a user with a given User mail, as a User
	 * @return
	 */
	@SuppressWarnings({"deprecation","rawtypes"})
	public static User getUser(String mail){
		
		try {

			Query query = HibernateUtilAuth.getSession().createQuery("from User where mail=:uMail");
			query.setParameter("uMail", mail);

			User idUser = (User) query.uniqueResult();

			return idUser;
			
		} catch (HibernateException e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Search a user by the given User Object
	 * @param user
	 * @return
	 */
	@SuppressWarnings({"rawtypes"})
	public static User get(User user) {

		try {

			Query query = HibernateUtilAuth.getSession().createQuery("from User where mail=:mail and password=:password");
			query.setParameter("mail", user.getMail().toLowerCase());
			query.setParameter("password", Hashing.sha1().hashString(user.getPassword(), Charsets.UTF_8 ).toString());
			
			User aliveUser = null;
			
			try {
				aliveUser = (User) query.getSingleResult();
			} catch (Exception e){
				// Nothing to display, this exception only occure if no result has been found
				// TODO: use the deprecated uniqueResult() that does not throw exception and just return null if no result
			}

			return aliveUser;
		} catch (HibernateException e){
			e.printStackTrace();
			return null;
		}
	}
	
	public static User get(Integer userId){
		
		try{
			
			Query query = HibernateUtilAuth.getSession().createQuery("from User where id=:id");
			query.setParameter("id", userId);
			
			return (User) query.uniqueResult();
			
		} catch(HibernateException e){
			e.printStackTrace();
			return null;
		}
		
	}

	/**
	 * Get all user
	 * @return
	 */
	public static List<User> getAll(){
		
		try{
			
			Query query = HibernateUtilAuth.getSession().createQuery("from User");
			
			List<User> userList = (List<User>) query.list();
			
			return userList;
			
		} catch(HibernateException e){
			e.printStackTrace();
			return null;
		}
		
	}
}
