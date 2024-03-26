package ru.crpt.ismp;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class CrptApi {
    public static void main(String[] args) {
        SpringApplication.run(CrptApi.class, args);
    }
}

@RestController
@RequestMapping("/api/v3/lk/documents")
class CrptApiController {
    private final RequestProtector requestProtector = new RequestProtector(30, 5);

    @PostMapping("/create")
    public CrptApiEntity crptApi(@RequestBody CrptApiEntity crptApiEntity) throws InterruptedException {
        requestProtector.protect();
        return crptApiEntity;
    }
}

class RequestProtector {
    private final int timeUnitInSeconds;
    private final int requestLimit;
    private int quantityOfRequests = 0;

    public RequestProtector(int TIME_UNIT_IN_SECONDS, int REQUEST_LIMIT) {
        this.timeUnitInSeconds = TIME_UNIT_IN_SECONDS;
        this.requestLimit = REQUEST_LIMIT;
    }

    public void protect() throws InterruptedException {
        quantityOfRequests++;
        if (quantityOfRequests >= requestLimit) {
            TimeUnit.SECONDS.sleep(timeUnitInSeconds);
            quantityOfRequests--;
        }
    }
}

@Data
@AllArgsConstructor
class CrptApiEntity {
    private Map<String, String> description;
    private String doc_id;
    private String doc_status;
    private String doc_type;
    private boolean importRequest;
    private String owner_inn;
    private String participant_inn;
    private String producer_inn;
    private String production_date;
    private String production_type;
    private List<Map<String, String>> products;
    private String reg_date;
    private String reg_number;
}