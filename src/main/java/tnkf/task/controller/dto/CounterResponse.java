package tnkf.task.controller.dto;

import lombok.Value;

import java.util.HashMap;
import java.util.Map;

/**
 * ConterRespose.
 *
 * @author Aleksandr_Sharomov
 */
@Value
public class CounterResponse {
    private Integer totalSuccess;
    private Integer total;
    private Map<String, Integer> byCurrency = new HashMap<>();

    public static class Builder {
        private Integer totalSuccess = 0;
        private Integer total = 0;
        private Map<String, Integer> byCurrency = new HashMap<>();

        public Builder addCounterValue(String code, Integer value) {
            if ("all".equals(code)){
                this.total = value;
            } else if ("success".equals(code)) {
                this.totalSuccess = value;
            } else {
                byCurrency.put(code, value);
            }

            return this;
        }

        public CounterResponse build() {
            CounterResponse counterResponse = new CounterResponse(totalSuccess, total);
            counterResponse.getByCurrency().putAll(byCurrency);
            return counterResponse;
        }
    }
}
