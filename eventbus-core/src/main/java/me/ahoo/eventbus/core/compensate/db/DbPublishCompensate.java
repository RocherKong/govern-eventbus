package me.ahoo.eventbus.core.compensate.db;

import lombok.extern.slf4j.Slf4j;
import lombok.var;
import me.ahoo.eventbus.core.compensate.AbstractPublishCompensate;
import me.ahoo.eventbus.core.compensate.db.config.PublishConfig;
import me.ahoo.eventbus.core.consistency.ConsistencyPublisher;
import me.ahoo.eventbus.core.repository.PublishEventRepository;
import me.ahoo.eventbus.core.utils.Threads;

import java.util.Objects;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author ahoo wang
 */
@Slf4j
public class DbPublishCompensate extends AbstractPublishCompensate {

    private final PublishConfig publishConfig;
    private ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;
    private final String leaderId;

    public DbPublishCompensate(
            PublishConfig publishConfig,
            ConsistencyPublisher consistencyPublisher,
            PublishEventRepository publishEventRepository) {
        super(publishConfig, consistencyPublisher, publishEventRepository);
        this.publishConfig = publishConfig;
        this.leaderId = CompensateLeaderService.generateLeaderId();
    }

    @Override
    public void start0() {
        if (Objects.isNull(this.scheduledThreadPoolExecutor)) {
            this.scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1, Threads.defaultFactory("PublishCompensation"));
        }
        var scheduleConfig = publishConfig.getSchedule();
        scheduledThreadPoolExecutor.scheduleAtFixedRate(this::doWork, scheduleConfig.getInitialDelay(), scheduleConfig.getPeriod(), TimeUnit.SECONDS);
    }

    private void doWork() {
        if (!CompensateLeaderService.fightLeadership(publishEventRepository, leaderId, publishConfig.getLeader())) {
            return;
        }
        this.schedule();
    }

    @Override
    public void stop0() {
        if (Objects.nonNull(scheduledThreadPoolExecutor)) {
            scheduledThreadPoolExecutor.shutdown();
        }
        boolean succeeded = publishEventRepository.releaseLeadership(leaderId);
        if (log.isInfoEnabled()) {
            if (succeeded) {
                log.info("stop0 - Release leadership successfully, leaderId:[{}].", leaderId);
                return;
            }
            log.info("stop0 - Failed to release leadership, because I'm not a leader.leaderId:[{}].", leaderId);
        }
    }

}
