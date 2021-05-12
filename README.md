![Health Check](https://github.com/hadilq/LiveEvent/workflows/Health%20Check/badge.svg?branch=main)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.hadilq/live-event/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.hadilq/live-event)

Live Event
---
This library holds a class to handle single live events in Android MVVM architectural pattern. This class is extended
form LiveData class, from `androidx.lifecycle:lifecycle-extensions` library, to propagate the data as an event,
which means it emits data just once, not after configuration changes again. Note that event will only be sent 
to active observers, any observers that started observing after the emit won't be notified of the event. 

Usage
---
This source has a sample app where you can find `LiveEventViewModel` in it, in which the `LiveEvent` class is used as
follows.
```kotlin
class LiveEventViewModel : ViewModel() {
    private val clickedState = LiveEvent<String>()
    val state: LiveData<String> = clickedState

    fun clicked() {
        clickedState.value = ...
    }
}
```

Download
---
Download via gradle
```groovy
implementation "com.github.hadilq:live-event:$libVersion"
```
where you can find the `libVersion` in the [Releases](https://github.com/hadilq/LiveEvent/releases) page of this repository.

Snapshots of the development version are available in [Sonatype's snapshots repository](https://oss.sonatype.org/content/repositories/snapshots).

Contribution
---
Just create your branch from the master branch, change it, write additional tests, satisfy all tests, create your pull
request, thank you, you're awesome.
