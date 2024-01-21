# quarkus-kstreams-topologies

This minimal project demonstrates the issue of not being able to create more than one Kafka Streams topology in one application, using the method described in the [Using Kafka Streams](https://quarkus.io/guides/kafka-streams) guide, i.e. where topology is being created in a `@jakarta.enterprise.inject.Produces`-annotated method.

## To reproduce

Run the application in dev mode:

```shell script
./gradlew quarkusDev -i
```

Observe that the application fails to start, with the following seen in the logs:
```
2024-01-21 16:13:37,136 ERROR [io.qua.run.Application] (Quarkus Main Thread) Failed to start application (with profile [dev]): java.lang.RuntimeException: Failed to start quarkus
        at io.quarkus.runner.ApplicationImpl.doStart(Unknown Source)
        at io.quarkus.runtime.Application.start(Application.java:101)
        at io.quarkus.runtime.ApplicationLifecycleManager.run(ApplicationLifecycleManager.java:111)
        at io.quarkus.runtime.Quarkus.run(Quarkus.java:71)
        at io.quarkus.runtime.Quarkus.run(Quarkus.java:44)
        at io.quarkus.runtime.Quarkus.run(Quarkus.java:124)
        at io.quarkus.runner.GeneratedMain.main(Unknown Source)
        at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
        at java.base/java.lang.reflect.Method.invoke(Method.java:578)
        at io.quarkus.runner.bootstrap.StartupActionImpl$1.run(StartupActionImpl.java:113)
        at java.base/java.lang.Thread.run(Thread.java:1623)
Caused by: jakarta.enterprise.inject.AmbiguousResolutionException: Beans: [PRODUCER_METHOD bean [class=org.acme.qkt.TopologyProducerA, id=6BFQ4NfZqRuS74kLy3x4LX22jgo], PRODUCER_METHOD bean [class=org.acme.qkt.TopologyProducerB, id=na5ipbtejGCLdDVBsclVMcRoeQM]]
        at io.quarkus.arc.impl.InstanceImpl.bean(InstanceImpl.java:291)
        at io.quarkus.arc.impl.InstanceImpl.getInternal(InstanceImpl.java:309)
        at io.quarkus.arc.impl.InstanceImpl.get(InstanceImpl.java:190)
        at io.quarkus.kafka.streams.runtime.KafkaStreamsProducer.<init>(KafkaStreamsProducer.java:106)
        at io.quarkus.kafka.streams.runtime.KafkaStreamsProducer_Bean.doCreate(Unknown Source)
        at io.quarkus.kafka.streams.runtime.KafkaStreamsProducer_Bean.create(Unknown Source)
        at io.quarkus.kafka.streams.runtime.KafkaStreamsProducer_Bean.create(Unknown Source)
        at io.quarkus.arc.impl.AbstractSharedContext.createInstanceHandle(AbstractSharedContext.java:119)
        at io.quarkus.arc.impl.AbstractSharedContext$1.get(AbstractSharedContext.java:38)
        at io.quarkus.arc.impl.AbstractSharedContext$1.get(AbstractSharedContext.java:35)
        at io.quarkus.arc.impl.LazyValue.get(LazyValue.java:32)
        at io.quarkus.arc.impl.ComputingCache.computeIfAbsent(ComputingCache.java:69)
        at io.quarkus.arc.impl.ComputingCacheContextInstances.computeIfAbsent(ComputingCacheContextInstances.java:19)
        at io.quarkus.arc.impl.AbstractSharedContext.get(AbstractSharedContext.java:35)
        at io.quarkus.kafka.streams.runtime.KafkaStreamsProducer_Bean.get(Unknown Source)
        at io.quarkus.kafka.streams.runtime.KafkaStreamsProducer_Bean.get(Unknown Source)
        at io.quarkus.kafka.streams.runtime.KafkaStreamsProducer_ProducerMethod_getKafkaStreams_d5DLwLZ87HgycVRDmdD6cQd9k9w_Bean.doCreate(Unknown Source)
        at io.quarkus.kafka.streams.runtime.KafkaStreamsProducer_ProducerMethod_getKafkaStreams_d5DLwLZ87HgycVRDmdD6cQd9k9w_Bean.create(Unknown Source)
        at io.quarkus.kafka.streams.runtime.KafkaStreamsProducer_ProducerMethod_getKafkaStreams_d5DLwLZ87HgycVRDmdD6cQd9k9w_Bean.create(Unknown Source)
        at io.quarkus.arc.impl.AbstractSharedContext.createInstanceHandle(AbstractSharedContext.java:119)
        at io.quarkus.arc.impl.AbstractSharedContext$1.get(AbstractSharedContext.java:38)
        at io.quarkus.arc.impl.AbstractSharedContext$1.get(AbstractSharedContext.java:35)
        at io.quarkus.arc.impl.LazyValue.get(LazyValue.java:32)
        at io.quarkus.arc.impl.ComputingCache.computeIfAbsent(ComputingCache.java:69)
        at io.quarkus.arc.impl.ComputingCacheContextInstances.computeIfAbsent(ComputingCacheContextInstances.java:19)
        at io.quarkus.arc.impl.AbstractSharedContext.get(AbstractSharedContext.java:35)
        at io.quarkus.kafka.streams.runtime.KafkaStreamsProducer_ProducerMethod_getKafkaStreams_d5DLwLZ87HgycVRDmdD6cQd9k9w_Bean.get(Unknown Source)
        at io.quarkus.kafka.streams.runtime.KafkaStreamsProducer_ProducerMethod_getKafkaStreams_d5DLwLZ87HgycVRDmdD6cQd9k9w_Bean.get(Unknown Source)
        at io.quarkus.arc.impl.ArcContainerImpl.beanInstanceHandle(ArcContainerImpl.java:553)
        at io.quarkus.arc.impl.ArcContainerImpl.beanInstanceHandle(ArcContainerImpl.java:533)
        at io.quarkus.arc.impl.ArcContainerImpl.beanInstanceHandle(ArcContainerImpl.java:566)
        at io.quarkus.arc.impl.ArcContainerImpl.instance(ArcContainerImpl.java:338)
        at io.quarkus.kafka.streams.runtime.KafkaStreamsProducer_Observer_Synthetic_GBi-MxGEb8kh__7Q4XC-t4hECVU.notify(Unknown Source)
        at io.quarkus.arc.impl.EventImpl$Notifier.notifyObservers(EventImpl.java:346)
        at io.quarkus.arc.impl.EventImpl$Notifier.notify(EventImpl.java:328)
        at io.quarkus.arc.impl.EventImpl.fire(EventImpl.java:82)
        at io.quarkus.arc.runtime.ArcRecorder.fireLifecycleEvent(ArcRecorder.java:157)
        at io.quarkus.arc.runtime.ArcRecorder.handleLifecycleEvents(ArcRecorder.java:108)
        at io.quarkus.deployment.steps.LifecycleEventsBuildStep$startupEvent1144526294.deploy_0(Unknown Source)
        at io.quarkus.deployment.steps.LifecycleEventsBuildStep$startupEvent1144526294.deploy(Unknown Source)
        ... 11 more

```

Note that Kafka broker is not required at this point, as the application fails before it even attempts to connect to it, but file `docker-compose.yml` with local Kafka instance configured has been provided for completeness. It can be run with:
```shell
docker-compose up -d
```

## Analysis
Following the stack trace, it appears that [KafkaStreamsProducer](https://github.com/quarkusio/quarkus/blob/ebee0a4b66d1b4dba9c51165e78d22fbf3b90d3e/extensions/kafka-streams/runtime/src/main/java/io/quarkus/kafka/streams/runtime/KafkaStreamsProducer.java#L106) simply doesn't support more than one topology and throws said exception when it detects more than one `org.apache.kafka.streams.Topology` beans available.

## Attempted fix
To address the exception, custom `@jakarta.inject.Qualifier`-annotated annotations were applied to the methods producing topologies, following remarks from [Quarkus' intro to CDI](https://quarkus.io/guides/cdi#you-talked-about-some-qualifiers).

Unfortunately, the effect was that whilst the exception was no longer thrown, the topology producer methods were no longer invoked, and Kafka Streams not initialised.

This is understandable as KafkaStreamsProducer's constructor only expects `@Default` beans, and once annotated with custom qualifiers, none of the topology beans match this requirement.    

To reproduce:
* uncomment lines `// @TopologyA` and `// @TopologyB` in `TopologyProducerA` and `TopologyProducerB`, respectively, 
* re-run the application,
* observe that there are no indications in the logs that the topology producer methods were invoked (no 'Building topologyA' nor 'Building topologyB' logs present), nor that the Kafka Streams are being initialised. 


