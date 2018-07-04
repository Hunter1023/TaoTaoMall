package com.taotao.test.fastdfs;

import com.taotao.common.utils.FastDFSClient;
import org.csource.fastdfs.*;
import org.junit.Test;

public class TestFastDFS {

    @Test
    public void testUpload() throws Exception {
        //初始化全局配置，加载一个配置文件
        ClientGlobal.init("C:\\Users\\Hunter\\Desktop\\taotao\\taotao-manager-web\\src\\main\\resources\\properties\\fastdfs-client.conf");

        // 创建一个TrackerClient对象
        TrackerClient trackerClient = new TrackerClient();
        // 创建一个TrackerServer对象
        TrackerServer trackerServer = trackerClient.getConnection();
        // 声明一个StorageServer对象，null
        StorageServer storageServer = null;
        // 获得StorageClient对象
        StorageClient storageClient = new StorageClient(trackerServer, storageServer);
        // 直接调用StorageClient的方法上传文件即可
        String[] strings = storageClient.upload_file(
                "C:\\Users\\Hunter\\Desktop\\特叔'sBlog\\source\\uploads\\Dubbo的架构.png",
                "png", null);
        for (String string : strings) {
            System.out.println(string);
        }
    }

    @Test
    public void testFastDFSClient() throws Exception {
        FastDFSClient client = new FastDFSClient("C:\\Users\\Hunter\\Desktop\\taotao\\taotao-manager-web\\src\\main\\resources\\properties\\fastdfs-client.conf");
        String uploadFile = client.uploadFile("C:\\Users\\Hunter\\Desktop\\-4f764652eafc7f8b.png",
                "png");

        System.out.println(uploadFile);
    }


}
