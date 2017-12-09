package cn.e3.testFastDFS;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

import cn.e3.utils.FastDFSClient;

public class MyFastDFS {

	/**
	 * 需求:根据fastDFS提供的客户端API测试上传图片
	 * @throws Exception 
	 */
	@Test
	public void fastDFSTest01() throws Exception{
		String client = "D:\\develops\\workspace\\workspaceFramework2\\e3-manager\\"
				+ "e3Project\\e3-manager-web\\src\\main\\resources\\conf\\client.conf";
		
		String pic = "C:\\Users\\Juan\\Pictures\\Java学习路线图.png";
		
		ClientGlobal.init(client);
		
		//创建trackerServer
		TrackerClient tClient = new TrackerClient();
		//从客户端中获取服务端对象
		TrackerServer trackerServer = tClient.getConnection();
		
		//创建StorageClient
		StorageClient sClient = new StorageClient(trackerServer, null);
		//上传
		String[] urls = sClient.upload_file(pic, "jpg", null);
		
		for (String url : urls) {
			System.out.println(url);
		}
	}
	
	/**
	 * 需求:根据fastDFS提供的工具类测试上传图片
	 * @throws Exception 
	 */
	@Test
	public void fastDFSTest02() throws Exception{
		String client = "D:\\develops\\workspace\\workspaceFramework2\\e3-manager\\"
				+ "e3Project\\e3-manager-web\\src\\main\\resources\\conf\\client.conf";
		
		String pic = "C:\\Users\\Juan\\Pictures\\java发展路线.jpg";
		
		
		FastDFSClient fClient = new FastDFSClient(client);
		String string = fClient.uploadFile(pic);
		
			System.out.println(string);
	}
}
