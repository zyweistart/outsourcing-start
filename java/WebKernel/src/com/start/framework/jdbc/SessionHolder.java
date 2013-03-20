package com.start.framework.jdbc;

public final class SessionHolder {
	
	private static ThreadLocal<Session> dsSessionHolder = new ThreadLocal<Session>();

	public static Session getCurrentSession() {
		Session session=dsSessionHolder.get();
		if(session==null){
			session=new Session();
			dsSessionHolder.set(session);
		}
		return session;
	}
	
}