package michael.linker.msr.runnable.model;

import java.util.List;
import java.util.stream.Collectors;

public record RequestRunnableModel(String url,
                                   Double getRequestProbability,
                                   List<String> getRequestIdList,
                                   List<String> updateRequestIdList,
                                   Long changeAmount) {
    public RequestRunnableModel(String url,
                                Double readQuota,
                                Double writeQuota,
                                List<Long> readIdList,
                                List<Long> writeIdList,
                                Long changeAmount) {
        this(
                url,
                readQuota / (readQuota + writeQuota),
                readIdList.stream().map(Object::toString).collect(Collectors.toList()),
                writeIdList.stream().map(Object::toString).collect(Collectors.toList()),
                changeAmount
        );
    }
}
