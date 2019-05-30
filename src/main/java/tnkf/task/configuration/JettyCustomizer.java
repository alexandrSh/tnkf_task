package tnkf.task.configuration;


import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.binder.jetty.JettyServerThreadPoolMetrics;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

/**
 * Jetty server customization.
 */
@Component
public class JettyCustomizer implements WebServerFactoryCustomizer<JettyServletWebServerFactory> {

    private final MeterRegistry meterRegistry;
    @Value("${jetty.maxThreads}")
    private int maxThreads;
    @Value("${jetty.minThreads}")
    private int minThreads;
    @Value("${jetty.idleTimeout}")
    private int idleTimeout;

    @Autowired
    public JettyCustomizer(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }


    @Override
    public void customize(JettyServletWebServerFactory jetty) {
        InstrumentedQueuedThreadPool instrumentedQueuedThreadPool = new InstrumentedQueuedThreadPool(
                meterRegistry,
                Tags.empty());
        instrumentedQueuedThreadPool.setName("jetty-thread-pool");
        jetty.setThreadPool(instrumentedQueuedThreadPool);
    }

    public class InstrumentedQueuedThreadPool extends QueuedThreadPool {

        private final MeterRegistry registry;
        private final Iterable<Tag> tags;

        public InstrumentedQueuedThreadPool(MeterRegistry registry,
                                            Iterable<Tag> tags) {
            super(maxThreads, minThreads, idleTimeout);
            this.registry = registry;
            this.tags = tags;
        }

        @Override
        protected void doStart() throws Exception {
            super.doStart();
            JettyServerThreadPoolMetrics threadPoolMetrics = new JettyServerThreadPoolMetrics(this, tags);
            threadPoolMetrics.bindTo(registry);
        }
    }
}