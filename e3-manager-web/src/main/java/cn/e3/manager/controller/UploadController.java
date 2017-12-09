package cn.e3.manager.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.e3.utils.FastDFSClient;
import cn.e3.utils.JsonUtils;
import cn.e3.utils.KindEditorModel;

@Controller
public class UploadController {

	@Value("${IMAGE_URL}")
	private String IMAGE_URL;
	/**
	 * 需求:使用fastDFS分布式文件系统上传图片
	 * 请求: /pic/upload
	 * 参数:uploadFile
	 * 返回值:KindEditorModel  ( String )
	 * @throws Exception 
	 */
	@RequestMapping("/pic/upload")
	@ResponseBody
	public String uploadFile(MultipartFile uploadFile) throws Exception{
		try {
			String originalFilename = uploadFile.getOriginalFilename();
			String extName = originalFilename.substring(originalFilename.lastIndexOf(".")+1);
			FastDFSClient fClient = new FastDFSClient("classpath:conf/client.conf");
			String url = fClient.uploadFile(uploadFile.getBytes(),extName);
			url=IMAGE_URL+url;
			KindEditorModel model = new KindEditorModel();
			model.setUrl(url);
			model.setError(0);
			String string = JsonUtils.objectToJson(model);
			return string;
		} catch (Exception e) {
			KindEditorModel model = new KindEditorModel();
			model.setError(1);
			model.setMessage("上传失败");
			String string = JsonUtils.objectToJson(model);
			e.printStackTrace();
			return string;
		}
	}
}
