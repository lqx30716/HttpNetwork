package imaf.secidea.com.imafnetwork;

/**
 * Created by admin on 2017/1/19.
 */

public class RequestBodyType {

    private String contentType;

    public String getContentType() {
        return contentType;
    }
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    private RequestBodyType(String contentType) {
        this.contentType = contentType;
    }
    public static RequestBodyType parse(String contentType){
        return new RequestBodyType(contentType);
    }

}
