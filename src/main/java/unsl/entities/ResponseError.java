package unsl.entities;

public class ResponseError {

    private int error;
    private String message;

    public ResponseError(int error, String msg) {
        this.error = error;
        this.message = msg;
    }

    public void ResponseError() {
        this.error = 500;
        this.message = "Internal Error";
    }

    public int getError() {
        return this.error;
    }

    public void setError(int httpStatus) {
        this.error = httpStatus;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
