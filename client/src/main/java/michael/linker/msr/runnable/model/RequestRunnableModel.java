package michael.linker.msr.runnable.model;

import java.util.List;
import java.util.stream.Collectors;

public record RequestRunnableModel(String url,
                                   Double getRequestProbability,
                                   List<String> getRequestIdList,
                                   List<String> updateRequestIdList,
                                   Long changeAmount) {
    public RequestRunnableModel(String url,
                                Long readQuota,
                                Long writeQuota,
                                List<Long> readIdList,
                                List<Long> writeIdList,
                                Long changeAmount) {
        this(
                url,
                readQuota.doubleValue() / (readQuota.doubleValue() + writeQuota.doubleValue()),
                readIdList.stream().map(Object::toString).collect(Collectors.toList()),
                writeIdList.stream().map(Object::toString).collect(Collectors.toList()),
                changeAmount
        );
    }
}
