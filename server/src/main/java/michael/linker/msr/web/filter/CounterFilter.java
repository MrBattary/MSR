package michael.linker.msr.web.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import michael.linker.msr.web.metric.Counter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@Order(1)
public class CounterFilter extends OncePerRequestFilter {
    @Value("${filter.counter.pattern}")
    private List<String> pattern;
    @Value("${filter.counter.format.value}")
    private String formatValue;
    @Value("${filter.counter.format.time}")
    private String formatTime;
    @Value("${filter.counter.format.concat}")
    private String formatConcat;
    @Value("${filter.counter.period.min}")
    private Integer periodMin;
    @Value("${filter.counter.period.sec}")
    private Integer periodSec;
    private static final Counter<String> counter = new Counter<>();

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        for(String allowedPath: pattern) {
            if(path.contains(allowedPath)) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        counter.count(request.getMethod());
        filterChain.doFilter(request, response);
    }

    @Scheduled(fixedRateString = "${filter.counter.period.calculatedMs}")
    public void countRequestsPerSecond() {
        Map<String, Long> counterMap = counter.getCountedMap();
        Long getCounter = counterMap.getOrDefault(HttpMethod.GET.name(), 0L);
        Long postCounter = counterMap.getOrDefault(HttpMethod.POST.name(), 0L);
        log.info(String.format(formatConcat,
                String.format(formatValue, getCounter, postCounter, (getCounter + postCounter)),
                String.format(formatTime, periodMin, periodSec))
        );
    }
}
