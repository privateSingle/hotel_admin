package cn.li.utils;

import cn.li.pojo.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class FileUploadUtil {

    /**
     * 文件上传
     * @param file 文件
     * @param path 文件上传路径
     * @return
     * @throws IOException
     */
    public static String fileUpload(MultipartFile file,String path) throws IOException {
        File file1 = new File(path);
        //判断文件目录是否存在
        if (!file1.exists()){
            file1.mkdirs();
        }
        String fileName = file.getOriginalFilename();     //获取上传文件名
        //使用UUID将文件名设置为唯一的
        String uuid = UUID.randomUUID().toString().replace("-", "");
        //拼接uuid到文件名
        fileName = uuid + "_" + fileName;
        //执行上传
        file.transferTo(new File( path+fileName));
        return fileName;
    }

    /**
     * 删除文件方法
     * @param filePath
     * @return
     */
    public static boolean delFileUpload(String filePath){
        File file = new File(filePath);
        try {
            if (file.exists()) {//判断路径是否存在
                file.delete();
                return true;
            } else {
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除多个文件方法
     * @param lists
     * @param path
     * @return
     */
    public static boolean delFileUpload(String path, List<String> lists) throws Exception{
        for (int i = 0; i < lists.size(); i++) {
            if(lists.get(i) != null && lists.get(i).trim().length() > 0) {
                File file = new File(path + lists.get(i));
                if (file.exists()) {//判断路径是否存在
                    file.delete();
                } else {
                    return false;
                }
            }
        }
        return true;
    }
}
