# kotlinlibs

### Collection of kotlin-native and kotlin jvm libraries

##### JVM Libraries

- core 
    -   Utility classes and extension functions
- coroutines 
    -   Flow extension operators (doOnXX)
    -   Flow Emitter (hybrid bridge for classic callbacks)
    -   Flow Processor akin to RxJava FlowableProcessor
    -   Flow Backpressure Support - BUFFER or DROP
    
##### Update 24/03/21

Since `StateFlow` and `SharedFlow` apis included with `1.4.0+` 
some classes like `FlowEmitter` and `FlowProcessor` which 
wrap the `Channel` api will become deprecated overtime as it is the
intent to deprecate the `Channel` api entirely in favour of the newer
more simple api.


