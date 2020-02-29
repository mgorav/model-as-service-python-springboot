package com.gonnect.hr.model;

import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizeConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastConfigurer {

    static Logger log = LoggerFactory.getLogger(HazelcastConfigurer.class);


    @Bean
    public Config hazelCastConfig() {

        log.info("Configuring hazelcast. The network port will be randomly assigned so that you can run multiple instances on your local machine without any issue");

        Config config = new Config();
        config.setInstanceName("hazelcast-instance")
                .addMapConfig(
                        new MapConfig()
                                .setName("map-config")
                                .setMaxSizeConfig(new MaxSizeConfig(200, MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE))
                                .setEvictionPolicy(EvictionPolicy.LRU)
                                .setTimeToLiveSeconds(-1));

        config.getNetworkConfig().setPort(5970).setPortAutoIncrement(true); //autoincr=true so that new instance will use new port
        config.getNetworkConfig().getJoin().getAwsConfig().setEnabled(false);
        config.getNetworkConfig().getJoin().getTcpIpConfig().setEnabled(false);
        config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(true)
                .setMulticastPort(5980).setMulticastTimeToLive(30).setMulticastTimeoutSeconds(5);

        // don't wait for a number of member nodes to be up before starting this member
        config.getProperties().setProperty("hazelcast.initial.min.cluster.size", "0");


        return config;
    }
}
