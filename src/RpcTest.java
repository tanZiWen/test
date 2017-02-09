import java.net.InetSocketAddress;

public class RpcTest {
	public static void main(String[] args) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					RpcExporter.exporter("127.0.0.1", 8098);
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
		RpcImporter<EchoService> importer = new RpcImporter<EchoService>();
		EchoService echoService = importer.importer(EchoServiceImpl.class, new InetSocketAddress("127.0.0.1", 8098));
		System.out.println(echoService.echo("Area you ok?"));
		
	}
}
