import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;

import org.apache.poi.ss.formula.IStabilityClassifier;

/**
 * 对称秘钥与非对称密钥结合
 * @author bohua
 *
 */
public class Test {

    public Test(){
        //模仿加密
        try {
            AES aes=new AES();
            RSA rsa=new RSA();
            aes.generateKey();//获得key
            Key key=aes.getKey();
            System.out.println(key+"  kkkkk");
            //获取非对称秘钥
            KeyPair keyPair=rsa.generateKeyPair();
            rsa.setPrivateKey(keyPair.getPrivate());
            byte[] b=rsa.wrapKey(key);
            String str = "tanyuan";
            String encrypyStr = aes.encrypt(str);
            System.out.println(encrypyStr);//加密过程
            Decrypt(b, keyPair, encrypyStr);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ShortBufferException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (BadPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /**
       模仿解密过程
     * @param b
     * @param keyPair
     */
    private void Decrypt(byte[] b, KeyPair keyPair, String encrypyStr) {
        InputStream in2;
        try {
            AES aes2=new AES();
            RSA rsa2=new RSA();
            rsa2.setPublicKey(keyPair.getPublic());
            Key key2=rsa2.unwrapKey(b);
            aes2.setKey(key2);
            String str = aes2.decrypt(encrypyStr);
            System.out.println(str);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ShortBufferException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (BadPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static void remove(ArrayList<String> list) 
	{
		for (int i = 0; i < list.size(); i++) 
		{
			String s = list.get(i);
			if (s.equals("b")) 
			{
				list.remove(s);
			}
		}
	}
    public static void main(String[] args) {
    	ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(5));
         
        for(int i=0;i<15;i++){
            MyTask myTask = new MyTask(i);
            executor.execute(myTask);
            System.out.println("线程池中线程数目："+executor.getPoolSize()+"，队列中等待执行的任务数目："+
            executor.getQueue().size()+"，已执行玩别的任务数目："+executor.getCompletedTaskCount());
        }
        executor.shutdown();
//    	// 创建一个可重用固定线程数的线程池
//    	ExecutorService pool = Executors.newFixedThreadPool(2);
//    	// 创建实现了Runnable接口对象，Thread对象当然也实现了Runnable接口
//    	Thread t1 = new MyThread();
//    	Thread t2 = new MyThread();
//    	Thread t3 = new MyThread();
//    	Thread t4 = new MyThread();
//    	Thread t5 = new MyThread();
//    	// 将线程放入池中进行执行
//    	pool.execute(t1);
//    	pool.execute(t2);
//    	pool.execute(t3);
//    	pool.execute(t4);
//    	pool.execute(t5);
//    	// 关闭线程池
//    	pool.shutdown();
//    	
//    	ConcurrentMap< String, String> chp = new ConcurrentHashMap<>();
//    	chp.put("1", "1");
//    	System.out.println(chp.get("1"));
//    	ArrayList<String> list = new ArrayList<String>();
//		list.add("a");
//		list.add("b");
//		list.add("b");
//		list.add("c");
//		list.add("c");
//		list.add("c");
//		remove(list);
//		for (int i = 0; i < list.size(); i++) 
//		{
//			System.out.println("element : " + list.get(i));
//		}
    }
}
class MyThread extends Thread {
    @Override
    public void run() {
	System.out.println(Thread.currentThread().getName() + "正在执行。。。");
    }
}
class MyTask implements Runnable {
    private int taskNum;
     
    public MyTask(int num) {
        this.taskNum = num;
    }
     
    @Override
    public void run() {
        System.out.println("正在执行task "+taskNum);
        try {
            Thread.currentThread().sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("task "+taskNum+"执行完毕");
    }
}
