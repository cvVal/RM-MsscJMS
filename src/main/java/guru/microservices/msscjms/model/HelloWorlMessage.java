package guru.microservices.msscjms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HelloWorlMessage implements Serializable {

    static final long serialVersionUID = 8429293120744570165L;

    private UUID id;
    private String message;
}