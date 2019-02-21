[![Build Status](https://travis-ci.org/hadilq/LiveEvent.svg?branch=master)](https://travis-ci.org/hadilq/LiveEvent)

Live Event
---
This library holds a class to handle single live events in Android MVVM architectural pattern. This class is extended
form LiveData class, from `androidx.lifecycle:lifecycle-extensions` library, to propagate the data as an event,
which means it emits data just once.

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
implementation "com.github.hadilq.liveevent:liveevent:$libVersion"
```
where the `libVersion` is [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.hadilq.liveevent/liveevent/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.hadilq.liveevent/liveevent).

Contribution
---
Just create your branch from the master branch, change it, write additional tests, satisfy all tests, create your pull
request, thank you, you're awesome.