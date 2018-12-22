package eventrev.net.model;

import java.io.Serializable;



public class ResponseData implements Serializable {

    private String message;


    public ResponseData(ResponseType type) {
        this.message = type.getMessage();
    }

    public static ResponseData type(ResponseType responseType){
        return new ResponseData(responseType);
    }
}
