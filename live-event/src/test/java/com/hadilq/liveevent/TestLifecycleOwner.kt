/**
 * Copyright 2019 Hadi Lashkari Ghouchani

 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at

 * http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hadilq.liveevent

import androidx.annotation.NonNull
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry


class TestLifecycleOwner() : LifecycleOwner {
    private val registry = LifecycleRegistry(this)

    val currentState: Lifecycle.State
        @NonNull
        get() = registry.currentState

    fun create(): TestLifecycleOwner {
        return handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
    }

    fun start(): TestLifecycleOwner {
        return handleLifecycleEvent(Lifecycle.Event.ON_START)
    }

    fun resume(): TestLifecycleOwner {
        return handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    fun pause(): TestLifecycleOwner {
        return handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    }

    fun stop(): TestLifecycleOwner {
        return handleLifecycleEvent(Lifecycle.Event.ON_STOP)
    }

    fun destroy(): TestLifecycleOwner {
        return handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    }

    private fun handleLifecycleEvent(@NonNull event: Lifecycle.Event): TestLifecycleOwner {
        registry.handleLifecycleEvent(event)
        return this
    }

    @NonNull
    override fun getLifecycle(): Lifecycle {
        return registry
    }
}