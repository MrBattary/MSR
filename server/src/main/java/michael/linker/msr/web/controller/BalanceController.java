package michael.linker.msr.web.controller;

import lombok.extern.slf4j.Slf4j;
import michael.linker.msr.web.model.api.request.CreateBalanceRequest;
import michael.linker.msr.web.model.api.request.UpdateBalanceRequest;
import michael.linker.msr.web.model.api.response.GetBalanceResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * General balance controller. Uses IBalanceWebService impl.
 *
 * @see michael.linker.msr.web.service.balance.IBalanceWebService
 */
@Slf4j
@Controller
@RequestMapping(path = "${controller.balance.path}")
public class BalanceController {

    @PutMapping
    public ResponseEntity<?> createBalance(@RequestBody CreateBalanceRequest request) {
        // TODO (ML): Provide service logic
        return ResponseEntity
                .ok()
                .build();
    }

    @GetMapping(value = "/{balanceId}")
    @ResponseBody
    public ResponseEntity<GetBalanceResponse> getBalance(@PathVariable String balanceId) {
        // TODO (ML): Provide service logic
        final GetBalanceResponse response = new GetBalanceResponse();
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PostMapping(value = "/{balanceId}")
    public ResponseEntity<?> updateBalance(@PathVariable String balanceId,
                                           @RequestBody UpdateBalanceRequest request) {
        // TODO (ML): Provide service logic
        return ResponseEntity
                .ok()
                .build();
    }

    @DeleteMapping
    public ResponseEntity<?> removeAllBalances() {
        // TODO (ML): Provide service logic
        return ResponseEntity
                .ok()
                .build();
    }
}
