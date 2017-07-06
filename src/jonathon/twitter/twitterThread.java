package jonathon.twitter;

public class twitterThread {

	//多线程开始
   public static void main(String[] args){
	System.out.println("Start~");
	
	TwitterID twi1=new TwitterID();
	TwitterID twi2=new TwitterID();
	
	Thread t1 = new Thread(twi1,"first");
	Thread t2 = new Thread(twi2,"second");
			
	  t1.start();  // t2.start();

}
}

