package tw.com.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LockerCommand {

    private String lockerId;
    
    private String command;
}
