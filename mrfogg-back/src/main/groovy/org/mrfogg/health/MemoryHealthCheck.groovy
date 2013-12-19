package org.mrfogg.health

import static com.yammer.metrics.core.HealthCheck.Result
import static java.lang.Runtime.getRuntime
import com.yammer.metrics.core.HealthCheck
import groovy.util.logging.Log4j

/**
 * This class checks that the available memory is still more than 60% of
 * the total memory given to the JVM
 *
 *
 */
@Log4j
class MemoryHealthCheck extends HealthCheck {

    MemoryHealthCheck() {
        super('memory')
    }

    Result check() {
        return checkMemoryGreaterThan(60)
    }

    @SuppressWarnings('UnnecessaryGetter')
    Result checkMemoryGreaterThan(Integer percentage) {

        Runtime runtime = getRuntime()
        double free = runtime.freeMemory()
        double total = runtime.totalMemory()
        double result = (free / total) * 100

        log.info("Memory freeMemory: ${free}%")
        log.info("Memory totalMemory: ${total}%")
        log.info("Memory health check ${result}% of free memory")

        return result >= percentage ?
            Result.healthy("OK: Memory is at ${result}%") :
            Result.unhealthy("KO: Memory is at ${result}%")

    }

}
