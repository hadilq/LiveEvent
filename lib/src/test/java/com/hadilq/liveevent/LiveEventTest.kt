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

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule


class LiveEventTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var liveEvent: LiveEvent<String>
    private lateinit var owner: TestLifecycleOwner
    private lateinit var observer: Observer<String>

    @Before
    fun setup() {
        liveEvent = LiveEvent()
        owner = TestLifecycleOwner()
        observer = mock()
    }

    @Test
    fun observe() {
        // Given
        owner.start()
        liveEvent.observe(owner, observer)

        val event = "event"

        // When
        liveEvent.value = event

        // Then
        verify(observer, times(1)).onChanged(event)
    }

    @Test
    fun observeForever() {
        // Given
        liveEvent.observeForever(observer)

        val event = "event"

        // When
        liveEvent.value = event

        // Then
        verify(observer, times(1)).onChanged(event)
    }

    @Test
    fun `observe multi observers`() {
        // Given
        owner.start()
        val observer1 = mock<Observer<String>>()
        liveEvent.observe(owner, observer)
        liveEvent.observe(owner, observer1)

        val event = "event"

        // When
        liveEvent.value = event

        // Then
        verify(observer, times(1)).onChanged(event)
        verify(observer1, times(1)).onChanged(event)
    }

    @Test
    fun `observeForever multi observers`() {
        // Given
        val observer1 = mock<Observer<String>>()
        liveEvent.observeForever(observer)
        liveEvent.observeForever(observer1)

        val event = "event"

        // When
        liveEvent.value = event

        // Then
        verify(observer, times(1)).onChanged(event)
        verify(observer1, times(1)).onChanged(event)
    }

    @Test
    fun `observe after start`() {
        // Given
        owner.create()
        liveEvent.observe(owner, observer)

        val event = "event"

        // When
        liveEvent.value = event

        // Then
        verify(observer, never()).onChanged(event)

        // When
        owner.start()

        // Then
        verify(observer, times(1)).onChanged(event)
    }

    @Test
    fun `observe after start with multi observers`() {
        // Given
        owner.create()
        val observer1 = mock<Observer<String>>()
        liveEvent.observe(owner, observer)
        liveEvent.observe(owner, observer1)

        val event = "event"

        // When
        liveEvent.value = event

        // Then
        verify(observer, never()).onChanged(event)
        verify(observer1, never()).onChanged(event)

        // When
        owner.start()

        // Then
        verify(observer, times(1)).onChanged(event)
        verify(observer1, times(1)).onChanged(event)
    }

    @Test
    fun `observe after stop`() {
        // Given
        owner.stop()
        liveEvent.observe(owner, observer)

        val event = "event"

        // When
        liveEvent.value = event

        // Then
        verify(observer, never()).onChanged(event)
    }

    @Test
    fun `observe after start again`() {
        // Given
        owner.stop()
        liveEvent.observe(owner, observer)

        val event = "event"

        // When
        liveEvent.value = event

        // Then
        verify(observer, never()).onChanged(event)

        // When
        owner.start()

        // Then
        verify(observer, times(1)).onChanged(event)
    }

    @Test
    fun `observe after one observation`() {
        // Given
        owner.start()
        liveEvent.observe(owner, observer)

        val event = "event"

        // When
        liveEvent.value = event

        // Then
        verify(observer, times(1)).onChanged(event)

        // When
        owner.stop()

        // Then
        verify(observer, times(1)).onChanged(event)

        // When
        owner.start()

        // Then
        verify(observer, times(1)).onChanged(event)
    }

    @Test
    fun `observe after one observation multi owner`() {
        // Given
        owner.start()
        liveEvent.observe(owner, observer)
        val owner1 = TestLifecycleOwner()
        val observer1 = mock<Observer<String>>()
        owner1.start()

        val event = "event"

        // When
        liveEvent.value = event

        // Then
        verify(observer, times(1)).onChanged(event)

        // Given
        liveEvent.observe(owner1, observer1)

        // Then
        verify(observer1, never()).onChanged(event)

        // When
        liveEvent.value = event

        // Then
        verify(observer, times(2)).onChanged(event)
        verify(observer1, times(1)).onChanged(event)
    }

    @Test
    fun `observe after one observation with new owner`() {
        // Given
        owner.start()
        liveEvent.observe(owner, observer)

        val event = "event"

        // When
        liveEvent.value = event

        // Then
        verify(observer, times(1)).onChanged(event)

        // When
        owner.destroy()

        // Then
        verify(observer, times(1)).onChanged(event)

        // When
        owner = TestLifecycleOwner()
        observer = mock()
        liveEvent.observe(owner, observer)
        owner.start()

        // Then
        verify(observer, never()).onChanged(event)
    }

    @Test
    fun `observe after one observation with new owner after start`() {
        // Given
        owner.start()
        liveEvent.observe(owner, observer)

        val event = "event"

        // When
        liveEvent.value = event

        // Then
        verify(observer, times(1)).onChanged(event)

        // When
        owner.destroy()

        // Then
        verify(observer, times(1)).onChanged(event)

        // When
        owner = TestLifecycleOwner()
        observer = mock()
        owner.start()
        liveEvent.observe(owner, observer)

        // Then
        verify(observer, never()).onChanged(event)
    }

    @Test
    fun `observe after remove`() {
        // Given
        owner.start()
        liveEvent.observe(owner, observer)

        val event = "event"

        // When
        liveEvent.value = event

        // Then
        verify(observer, times(1)).onChanged(event)

        // When
        liveEvent.removeObserver(observer)
        liveEvent.value = event

        // Then
        verify(observer, times(1)).onChanged(event)
    }

    @Test
    fun `observe after remove before emit`() {
        // Given
        owner.start()
        liveEvent.observe(owner, observer)
        liveEvent.removeObserver(observer)

        val event = "event"

        // When
        liveEvent.value = event

        // Then
        verify(observer, never()).onChanged(event)
    }

    @Test
    fun `observe after remove owner`() {
        // Given
        owner.start()
        liveEvent.observe(owner, observer)

        val event = "event"

        // When
        liveEvent.value = event

        // Then
        verify(observer, times(1)).onChanged(event)

        // When
        liveEvent.removeObservers(owner)
        liveEvent.value = event

        // Then
        verify(observer, times(1)).onChanged(event)
    }

    @Test
    fun `observe after remove owner before emit`() {
        // Given
        owner.start()
        liveEvent.observe(owner, observer)
        liveEvent.removeObservers(owner)

        val event = "event"

        // When
        liveEvent.value = event

        // Then
        verify(observer, never()).onChanged(event)
    }

    @Test
    fun `observe after remove multi owner`() {
        // Given
        owner.start()
        liveEvent.observe(owner, observer)
        val owner1 = TestLifecycleOwner()
        val observer1 = mock<Observer<String>>()
        owner1.start()

        val event = "event"

        // When
        liveEvent.value = event

        // Then
        verify(observer, times(1)).onChanged(event)
        verify(observer1, never()).onChanged(event)

        // When
        liveEvent.observe(owner1, observer1)
        liveEvent.removeObserver(observer)
        liveEvent.value = event

        // Then
        verify(observer, times(1)).onChanged(event)
        verify(observer1, times(1)).onChanged(event)
    }

    @Test
    fun `observe after remove owner multi owner`() {
        // Given
        owner.start()
        liveEvent.observe(owner, observer)
        val owner1 = TestLifecycleOwner()
        val observer1 = mock<Observer<String>>()
        owner1.start()

        val event = "event"

        // When
        liveEvent.value = event

        // Then
        verify(observer, times(1)).onChanged(event)
        verify(observer1, never()).onChanged(event)

        // When
        liveEvent.observe(owner1, observer1)
        liveEvent.removeObservers(owner)
        liveEvent.value = event

        // Then
        verify(observer, times(1)).onChanged(event)
        verify(observer1, times(1)).onChanged(event)
    }

    @Test
    fun `observe when value exists`() {
        // Given
        val event = "event"
        liveEvent.value = event
        owner.start()

        // When
        liveEvent.observe(owner, observer)

        // Then
        verify(observer, times(1)).onChanged(event)
    }

    @Test
    fun `observe after start when value exists`() {
        // Given
        val event = "event"
        liveEvent.value = event
        owner.create()

        // When
        liveEvent.observe(owner, observer)

        // Then
        verify(observer, never()).onChanged(any())

        // When
        owner.start()

        // Then
        verify(observer, times(1)).onChanged(event)
    }

    @Test
    fun `observe when value exists multi observers`() {
        // Given
        val event = "event"
        liveEvent.value = event
        owner.start()
        val observer1 = mock<Observer<String>>()

        // When
        liveEvent.observe(owner, observer)
        liveEvent.observe(owner, observer1)

        // Then
        verify(observer, times(1)).onChanged(event)
        verify(observer1, never()).onChanged(any())
    }

    @Test
    fun `observe after start when value exists multi observers`() {
        // Given
        val event = "event"
        liveEvent.value = event
        owner.create()
        val observer1 = mock<Observer<String>>()

        // When
        liveEvent.observe(owner, observer)
        liveEvent.observe(owner, observer1)

        // Then
        verify(observer, never()).onChanged(any())
        verify(observer1, never()).onChanged(any())

        // When
        owner.start()

        // Then
        verify(observer, times(1)).onChanged(event)
        verify(observer1, times(1)).onChanged(event)
    }
}
