package jonathon.bean;

import org.apache.log4j.Logger;

public class test {

	
	public static void info() {
		
		Logger logger =Logger.getLogger(test.class);
		// TODO �Զ����ɵķ������ 
		
		logger.debug("debug");
		logger.info("ddkklkdsuccesss!!!!!!!!!!!!!!!!");
		logger.warn("warn");
		logger.error("error");
	//	logger.fatal("fatal");
		
		try {
			int m= 8/0;System.out.println(m);
		} catch (Exception e) {
			// TODO: handle exception
			logger.fatal("������ô��Ϊ0��  Jonathon");
		}
		
	}

}
