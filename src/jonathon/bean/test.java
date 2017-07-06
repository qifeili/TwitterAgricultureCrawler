package jonathon.bean;

import org.apache.log4j.Logger;

public class test {

	
	public static void info() {
		
		Logger logger =Logger.getLogger(test.class);
		// TODO 自动生成的方法存根 
		
		logger.debug("debug");
		logger.info("ddkklkdsuccesss!!!!!!!!!!!!!!!!");
		logger.warn("warn");
		logger.error("error");
	//	logger.fatal("fatal");
		
		try {
			int m= 8/0;System.out.println(m);
		} catch (Exception e) {
			// TODO: handle exception
			logger.fatal("除数怎么能为0呢  Jonathon");
		}
		
	}

}
