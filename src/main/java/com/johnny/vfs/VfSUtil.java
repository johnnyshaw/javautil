package com.johnny.vfs;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.FileType;
import org.apache.commons.vfs2.VFS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *@Title:VFS操作文件工具：
 *@Description:
 *@Since:2014年8月26日
 *@Version:1.1.0
 */
public class VfSUtil {

    private static Logger logger = LoggerFactory.getLogger(VfSUtil.class);

    private static FileSystemManager fsManager = null;
    
    static {
        try {
            fsManager = VFS.getManager();
        } catch (FileSystemException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 上传文件到保存响应的路径下：
     * @param in
     * @param path
     * @return
     */
    public static boolean saveFile(InputStream in, String path) throws IOException {
        FileObject fo = null;
        OutputStream out = null;
        try {
            fo = fsManager.resolveFile(path);
            if (!fo.exists()) {
                fo.createFile();
            }
            out = fo.getContent().getOutputStream();
            IOUtils.copy(in, out);
            out.flush();
        } finally{
            try {
                if(out!=null){
                    out.close();
                }
                if(out!=null){
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * 从指定的文件获取文件流：
     * @param path
     * @return
     */
    public static InputStream loadFile(String path) {
        FileObject fo = null;
        InputStream in = null;
        try {
            fo = fsManager.resolveFile(path);
            if (!fo.exists()) {
                return null;
            }
            in = fo.getContent().getInputStream();
        } catch (FileSystemException e) {
            logger.error(e.getMessage());
        }
        return in;
    }

    /**
     * 从指定的文件夹下，获取所有下级文件名称
     * @param path
     * @return
     */
    public static List<String> getSubFileNames(String path) {
        List<String> fileNames = new ArrayList<String>();
        FileObject fo = null;
        try {
            fo = fsManager.resolveFile(path);
            if (fo.exists() && FileType.FOLDER.equals(fo.getType())) {
                FileObject[] subFos = fo.getChildren();
                if (subFos != null && subFos.length > 0) {
                    for (FileObject obj : subFos) {
                        fileNames.add(obj.getName().getBaseName());
                    }
                }
            }
        } catch (FileSystemException e) {
            logger.error(e.getMessage());
        }
        return fileNames;
    }

}
