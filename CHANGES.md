Changes by Version
==================
Release Notes.

6.2.0
------------------

#### Project
* ElasticSearch implementation performance improved, and CHANGED totally. Must delete all existing indexes to do upgrade.
* CI and Integration tests provided by ASF INFRA.
* Plan to enhance tests including e2e, plugin tests in all pull requests, powered by ASF INFRA.
* DataCarrier queue write index controller performance improvement. 3-5 times quicker than before.
* Add windows compile support in CI.

#### Java Agent
* Support collect SQL parameter in MySQL plugin.[Optional]
* Support SolrJ plugin.
* Support RESTEasy plugin.
* Support Spring Gateway plugin for 2.1.x
* TracingContext performance improvement.
* Support Apache ShardingSphere(incubating) plugin.
* Support `span#error` in application toolkit.
* Fix OOM by empty stack of exception.
* FIx wrong cause exception of stack in span log.
* Fix unclear the running context in SpringMVC plugin.
* Fix CPU usage accessor calculation issue.
* Fix SpringMVC plugin span not stop bug when doing HTTP forward.
* Fix lettuce plugin async commend bug and NPE.
* Fix webflux plugin cast exception.
* [CI]Support `import` check.

#### Backend
* Support time serious ElasticSearch storage.
* Provide dynamic configuration module and implementation. Slow SQL threshold supports dynamic config today.
* Dynamic Configuration module provide multiple implementations, DCS(gRPC based), Zookeeper, Apollo, Nacos.
* Provide P99/95/90/75/50 charts in topology edge.
* New topology query protocol and implementation.
* Support Envoy ALS in Service Mesh scenario.
* Support Nacos cluster management.
* Enhance metric exporter. Run in increment and total modes.
* Fix module provider is loaded repeatedly.
* Change TOP slow SQL storage in ES to Text from Keyword, as too long text issue.
* Fix H2TopologyQuery tiny bug.
* Fix H2 log query bug.(No feature provided yet)
* Filtering pods not in 'Running' phase in mesh scenario.
* Fix query alarm bug in MySQL and H2 storage.
* Codes refactor.

#### UI
* Fix some `ID is null` query(s).
* Page refactor, especially time-picker, more friendly.
* Login removed.
* Trace timestamp visualization issue fixed.
* Provide P99/95/90/75/50 charts in topology edge.
* Change all P99/95/90/75/50 charts style. More readable.
* Fix 404 in trace page.

#### Document
* Go2Sky project has been donated to SkyAPM, change document link.
* Add FAQ for ElasticSearch storage, and links from document.
* Add FAQ fro WebSphere installation.
* Add several open users.
* Add alarm webhook document.


All issues and pull requests are [here](https://github.com/apache/skywalking/milestone/33?closed=1)

6.1.0
------------------

#### Project
**SkyWalking graduated as Apache Top Level Project**.
- Support compiling project agent, backend, UI separately.

#### Java Agent
- Support Vert.x Core 3.x plugin.
- Support Apache Dubbo plugin.
- Support `use_qualified_name_as_endpoint_name` and `use_qualified_name_as_operation_name` configs in SpringMVC plugin.
- Support span async close APIs in core. Used in Vert.x plugin.
- Support MySQL 5,8 plugins.
- Support set instance id manually(optional).
- Support customize enhance trace plugin in optional list.
- Support to set peer in Entry Span.
- Support Zookeeper plugin.
- Fix Webflux plugin created unexpected Entry Span. 
- Fix Kafka plugin NPE in Kafka 1.1+
- Fix wrong operation name in postgre 8.x plugin.
- Fix RabbitMQ plugin NPE.
- Fix agent can't run in JVM 6/7, remove `module-info.class`.
- Fix agent can't work well, if there is whitespace in agent path.
- Fix Spring annotation bug and inheritance enhance issue.
- Fix CPU accessor bug.

#### Backend
**Performance improved, especially in CPU limited environment. 3x improvement in service mesh scenario(no trace) in 8C16G VM. 
Significantly cost less CPU in low payload.**

- Support database metrics and SLOW SQL detection.
- Support to set max size of metadata query. And change default to 5000 from 100.
- Support ElasticSearch template for new feature in the future.
- Support shutdown Zipkin trace analysis, because it doesn't fit production environment.
- Support log type, scope HTTP_ACCESS_LOG and query. No feature provided, prepare for future  versions.
- Support .NET clr receiver.
- Support Jaeger trace format, no analysis.
- Support group endpoint name by regax rules in mesh receiver.
- Support `disable` statement in OAL.
- Support basic auth in ElasticSearch connection.
- Support metrics exporter module and gRPC implementor.
- Support `>, <, >=, <=` in OAL.
- Support role mode in backend.
- Support Envoy metrics.
- Support query segment by service instance.
- Support to set host/port manually at cluster coordinator, rather than based on core settings.
- Make sure OAP shutdown when it faces startup error.
- Support set separated gRPC/Jetty ip:port for receiver, default still use core settings.
- Fix JVM receiver bug.
- Fix wrong dest service in mesh analysis.
- Fix search doesn't work as expected.
- Refactor `ScopeDeclaration` annotation.
- Refactor register lock mechanism.
- Add SmartSql component for .NET
- Add integration tests for ElasticSearch client.
- Add test cases for exporter.
- Add test cases for queue consume.

#### UI
- RocketBot UI has been accepted and bind in this release.
- Support CLR metrics.

#### Document
- Documents updated, matching Top Level Project requirement.
- UI licenses updated, according to RocketBot UI IP clearance.
- User wall and powered-by list updated.
- CN documents removed, only consider to provide by volunteer out of Apache.


All issues and pull requests are [here](https://github.com/apache/skywalking/milestone/32?closed=1)


6.0.0-GA
------------------

#### Java Agent
- Support gson plugin(optional).
- Support canal plugin.
- Fix missing ojdbc component id.
- Fix dubbo plugin conflict.
- Fix OpenTracing tag match bug.
- Fix a missing check in ignore plugin.

#### Backend
- Adjust service inventory entity, to add properties.
- Adjust service instance inventory entity, to add properties.
- Add nodeType to service inventory entity.
- Fix when operation name of local and exit spans in ref, the segment lost.
- Fix the index names don't show right in logs. 
- Fix wrong alarm text.
- Add test case for span limit mechanism.
- Add telemetry module and prometheus implementation, with grafana setting.
- A refactor for register API in storage module.
- Fix H2 and MySQL endpoint dependency map miss upstream side.
- Optimize the inventory register and refactor the implementation.
- Speed up the trace buffer read.
- Fix and removed unnecessary inventory register operations.

#### UI
- Add new trace view.
- Add word-break to tag value. 

#### Document
- Add two startup modes document.
- Add PHP agent links.
- Add some cn documents.
- Update year to 2019
- User wall updated.
- Fix a wrong description in `how-to-build` doc.

All issues and pull requests are [here](https://github.com/apache/skywalking/milestone/30?closed=1)

6.0.0-beta
------------------

#### Protocol
- Provide Trace Data Protocol v2
- Provide SkyWalking Cross Process Propagation Headers Protocol v2.

#### Java Agent
- Support Trace Data Protocol v2
- Support SkyWalking Cross Process Propagation Headers Protocol v2.
- Support SkyWalking Cross Process Propagation Headers Protocol v1 running in compatible way. Need declare open explicitly.
- Support SpringMVC 5
- Support webflux
- Support a new way to override agent.config by system env. 
- Span tag can override by explicit way.
- Fix Spring Controller Inherit issue.
- Fix ElasticSearch plugin NPE.
- Fix agent classloader dead lock in certain situation.
- Fix agent log typo.
- Fix wrong component id in resettemplete plugin.
- Fix use transform `ignore()` in wrong way.
- Fix H2 query bug.

#### Backend
- Support Trace Data Protocol v2. And Trace Data Protocol v1 is still supported.
- Support MySQL as storage.
- Support TiDB as storage.
- Support a new way to override application.yml by system env.
- Support service instance and endpoint alarm.
- Support namespace in istio receiver.
- Support service throughput(cpm), successful rate(sla), avg response time and p99/p95/p90/p75/p50 response time.
- Support backend trace sampling.
- Support Zipkin format again.
- Support init mode.
- Support namespace in Zookeeper cluster management.
- Support consul plugin in cluster module.
- OAL generate tool has been integrated into main repo, in the maven `compile` stage.
- Optimize trace paging query.
- Fix trace query don't use fuzzy query in ElasticSearch storage.
- Fix alarm can't be active in right way.
- Fix unnecessary condition in database and cache number query.
- Fix wrong namespace bug in ElasticSearch storage.
- Fix `Remote clients selector error: / by zero `.
- Fix segment TTL is not working.

#### UI
- Support service throughput(cpm), successful rate(sla), avg response time and p99/p95/p90/p75/p50 response time.
- Fix TopN endpoint link doesn't work right.
- Fix trace stack style.
- Fix CI.

#### Document
- Add more agent setting documents.
- Add more contribution documents.
- Update user wall and powered-by page.
- Add RocketBot UI project link in document.

All issues and pull requests are [here](https://github.com/apache/skywalking/milestone/31?closed=1)

6.0.0-alpha
------------------

SkyWalking 6 is totally new milestone for the project. At this point, we are not just a distributing
tracing system with analysis and visualization capabilities. We are an **Observability Analysis Platform(OAL)**.

The core and most important features in v6 are
1. Support to collect telemetry data from different sources, such as multiple language agents and service mesh.
1. Extensible stream analysis core. Make SQL and cache analysis available in core level, although haven't
provided in this release.
1. Provide **Observability Analysis Language(OAL)** to make analysis metrics customization available.
1. New GraphQL query protocol. Not binding with UI now.
1. UI topology is better now.
1. New alarm core provided. In alpha, only on service related metrics.

All issues and pull requests are [here](https://github.com/apache/skywalking/milestone/29?closed=1)

5.x releases
------------------
You could find all CHANGES of 5.x at [here](https://github.com/apache/skywalking/blob/5.x/CHANGES.md)
