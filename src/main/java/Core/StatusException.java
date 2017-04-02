/**
 * @author jakubvacek
 */

package Core;

import org.springframework.http.HttpStatus;

//This class is used to pass http status acording to nature of exception
public class StatusException  extends Exception{
    public HttpStatus status;
    public StatusException(HttpStatus status){
        super(status.toString());
        this.status = status;
    }
    @Override
    public String toString() {
        return (status.getReasonPhrase());
    }
    
}