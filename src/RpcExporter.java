import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class RpcExporter {
	static Executor executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	public static void exporter(String hostName, int port) throws Exception {
		ServerSocket server = new ServerSocket();
		server.bind(new InetSocketAddress(hostName, port));
		try {
			while(true) {
				executor.execute(new ExporterTask(server.accept()));
			}
		}finally {
			server.close();
		}
	}
	private static class ExporterTask implements Runnable {
		Socket client = null;
		public ExporterTask(Socket client) {
			this.client = client;
		}
		@Override
		public void run() {
			ObjectInputStream inputStream = null;
			ObjectOutputStream outputStream = null;
			try {
				inputStream = new ObjectInputStream(client.getInputStream());
				String interfaceName = inputStream.readUTF();
				Class<?> service = Class.forName(interfaceName);
				String methodName = inputStream.readUTF();
				Class<?>[] parameterTypes = (Class<?>[])inputStream.readObject();
				Object[] argments = (Object[])inputStream.readObject();
				Method method = service.getMethod(methodName, parameterTypes);
				Object result = method.invoke(service.newInstance(), argments);
				outputStream = new ObjectOutputStream(client.getOutputStream());
				outputStream.writeObject(result);
			}catch (Exception e) {
				e.printStackTrace();
			}finally {
				if(outputStream != null) {
					try {
						outputStream.close();
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
				if(inputStream != null) {
					try {
						inputStream.close();
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
				if(client != null) {
					try {
						client.close();
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
