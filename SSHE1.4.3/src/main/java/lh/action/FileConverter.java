package lh.action;

import java.io.File;  
import javax.servlet.http.HttpServletRequest;  

import lh.util.document.FlashPaper;

import org.apache.struts2.ServletActionContext;  
import com.opensymphony.xwork2.ActionSupport;  
/** 
 * @说明 上传执行 
 * @author cuisuqiang 
 * @version 1.0 
 * @since  关注新浪微博：@java小强 
 */  
@SuppressWarnings("serial")  
public class FileConverter extends ActionSupport {  
    private File file;  
    private String fileFileName;  
    private String fileContentType;  
      
//    @SuppressWarnings("deprecation")  
    public String execute() {  
        HttpServletRequest request = ServletActionContext.getRequest();  
        String tempName = FlashPaper.converter(file, fileContentType,fileFileName, request);  
        // 把转换的路径放到请求中  
        request.setAttribute("tempName", tempName);  
        return "suc";  
    }  
    /** 
     * 获得不同文件的后缀 
     */  
    public void setFileContentType(String fileContentType) {  
        if (fileContentType.contains("msword")) {  
            this.fileContentType = ".doc";  
        } else if (fileContentType.contains("excel")) {  
            this.fileContentType = ".xls";  
        } else if (fileContentType.contains("pdf")) {  
            this.fileContentType = ".pdf";  
        } else if (fileContentType.contains("powerpoint")) {  
            this.fileContentType = ".ppt";  
        } else {  
            this.fileContentType = ".doc";  
        }  
    }  
    public File getFile() {  
        return file;  
    }  
    public void setFile(File file) {  
        this.file = file;  
    }  
    public String getFileContentType() {  
        return fileContentType;  
    }  
    public String getFileFileName() {  
        return fileFileName;  
    }  
    public void setFileFileName(String fileFileName) {  
        this.fileFileName = fileFileName;  
    }  
}  