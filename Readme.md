# JAR JAR JAR JAR JAR JAR JAR JAR JAR JAR JAR


## HOW TO SHADE SBT PROJECTS

- FIND TOP LEVEL DEPENDENCY USING: `sbt dependencyGraph`
- ADD TO BUILD.SBT 
```
assemblyShadeRules in assembly ++= Seq(
  ShadeRule.rename("io.netty.**" -> "com.github.mauricio.@0")
    .inLibrary("com.github.mauricio" %% "postgresql-async" % "0.2.21")
    .inProject
    .inAll
)
```
- `sbt assembly && java -jar target/scala-2.12/jarjarjarjarjarjarjarjarajrajra....`
- PROFIT. (except sbt run still screwed)

## ORIGINAL STACK TRACE


```12:00:36.542 [run-main-0] DEBUG io.netty.util.ResourceLeakDetectorFactory - Loaded default ResourceLeakDetector: io.netty.util.ResourceLeakDetector@365c6383
[error] (run-main-0) java.lang.NoSuchMethodError: io.netty.handler.ssl.SslContextBuilder.protocols([Ljava/lang/String;)Lio/netty/handler/ssl/SslContextBuilder;
java.lang.NoSuchMethodError: io.netty.handler.ssl.SslContextBuilder.protocols([Ljava/lang/String;)Lio/netty/handler/ssl/SslContextBuilder;
	at org.asynchttpclient.netty.ssl.DefaultSslEngineFactory.buildSslContext(DefaultSslEngineFactory.java:45)
	at org.asynchttpclient.netty.ssl.DefaultSslEngineFactory.init(DefaultSslEngineFactory.java:69)
	at org.asynchttpclient.netty.channel.ChannelManager.<init>(ChannelManager.java:116)
	at org.asynchttpclient.DefaultAsyncHttpClient.<init>(DefaultAsyncHttpClient.java:85)
	at dispatch.Http.client$lzycompute(execution.scala:16)
	at dispatch.Http.client(execution.scala:16)
	at dispatch.Http.client(execution.scala:11)
	at dispatch.HttpExecutor.apply(execution.scala:120)
	at dispatch.HttpExecutor.apply$(execution.scala:117)
	at dispatch.Http.apply(execution.scala:11)
	at dispatch.HttpExecutor.apply(execution.scala:115)
	at dispatch.HttpExecutor.apply$(execution.scala:113)
	at dispatch.Http.apply(execution.scala:11)
	at pgsql$.delayedEndpoint$pgsql$1(pgsql.scala:61)
	at pgsql$delayedInit$body.apply(pgsql.scala:17)
	at scala.Function0.apply$mcV$sp(Function0.scala:34)
	at scala.Function0.apply$mcV$sp$(Function0.scala:34)
	at scala.runtime.AbstractFunction0.apply$mcV$sp(AbstractFunction0.scala:12)
	at scala.App.$anonfun$main$1$adapted(App.scala:76)
	at scala.collection.immutable.List.foreach(List.scala:389)
	at scala.App.main(App.scala:76)
	at scala.App.main$(App.scala:74)
	at pgsql$.main(pgsql.scala:17)
	at pgsql.main(pgsql.scala)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
```

Some conflict between netty-all in dispatch, and netty-handler in the postgres lib.


## How to JAR JARJARJARJARJARAJrjarjaksrjafkldfjasldkgjslkjgb

- `docker-compose up`
- sbt run
